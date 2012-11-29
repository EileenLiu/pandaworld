/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.concurrency;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Eileen Liu <el544@cornell.edu>
 */
public class RingBuffer<T> implements BlockingQueue<T> {
    private ReentrantLock lock;
    private Condition empty, full;
    
    private final T[] queue;
    private int headIndex = -1;
    private int tailIndex = -1;
    private long modcount = 0L;

    public RingBuffer(int capacity) {
        this(capacity, false);
    }
    private RingBuffer(int capacity, boolean fair) {
        if (capacity <= 0)
            throw new IllegalArgumentException();
        queue = (T[]) (Array.newInstance(Object.class, capacity));
        headIndex = 0;
        tailIndex = 0;
        lock = new ReentrantLock(fair);
        empty = lock.newCondition();
        full = lock.newCondition();
    }
    private static void checkNull(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
    }

    private T queue(int pos) {
        return queue[pos % queue.length];
    }

    private void queue(int pos, T e) {
        queue[pos % queue.length] = e;
    }
    ////////////-------------BlockingQueue Methods----------------//////////////
    @Override
    public boolean add(T e) {
        try {
            if(!lock.tryLock() || !addUnsafe(e))
                throw new IllegalStateException("RingBuffer.add(): No room");
            else
                return true;
        } finally {
            if(lock.isHeldByCurrentThread())
                lock.unlock();
        }
    }
    
    private boolean addUnsafe(T e) {
        if (this.size() < queue.length) {
            queue(tailIndex++, e);
            modcount++;
            empty.signalAll();
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        lock.lock();
        try {
            if (!isEmpty()) {
                for(int i = headIndex; i < tailIndex; i++) {
                    if(queue(i).equals(o)) {
                        for(i++; i < tailIndex; i++)
                            queue(i-1, queue(i)); //shift it down a space
                        tailIndex--;
                        modcount++;
                        full.signalAll();
                        return true;
                    }
                }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean offer(T e) {
        if(lock.tryLock()) try {
            if (this.size() == queue.length)
                return false;
            else {
                this.addUnsafe(e);
                return true;
            }
        } finally {
            lock.unlock();
        } else return false;
    }

    @Override
    public void put(T e) throws InterruptedException {
        checkNull(e);
        lock.lock();
        try {
            while(size() == queue.length)
                full.await();
            addUnsafe(e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean offer(T e, long timeout, TimeUnit unit) throws InterruptedException {
        checkNull(e);
        long dm = unit.toMillis(timeout), curr = System.currentTimeMillis();
        if(lock.tryLock(timeout, unit)) try {
            long rem = dm - (System.currentTimeMillis() - curr);
            while(rem > 0 && size() == queue.length) {
                full.await(rem, TimeUnit.MILLISECONDS);
                rem = dm - (System.currentTimeMillis() - curr);
            }
            if(rem <= 0)
                return false;
            else return addUnsafe(e);
        } finally {
            lock.unlock();
        } else return false;
    }

    @Override
    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (this.isEmpty())
                empty.await();
            return remove();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public T poll(long timeout, TimeUnit unit) throws InterruptedException {
        long dm = unit.toMillis(timeout), curr = System.currentTimeMillis();
        if(lock.tryLock(timeout, unit)) try {
            long rem = dm - (System.currentTimeMillis() - curr);
            while(rem > 0 && isEmpty()) {
                empty.await(rem, TimeUnit.MILLISECONDS);
                rem = dm - (System.currentTimeMillis() - curr);
            }
            if(rem <= 0)
                return null;
            else return poll();
        } finally {
            lock.unlock();
        } else return null;
    }

    @Override
    public int remainingCapacity() {
        return (queue.length - (tailIndex - headIndex));
    }

    @Override
    public boolean contains(Object o) {
        for(int i = headIndex; i < tailIndex; i++) {
            T next = queue(i);
            if (o.equals(next)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int drainTo(Collection<? super T> c) {
        if(c.equals(this))
        {
         throw new IllegalArgumentException();
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int drainTo(Collection<? super T> c, int maxElements) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    ////////////-------------Queue Methods----------------//////////////
    @Override
    public T remove() {
        T removed = this.poll();
        if (removed == null) {
            throw new NoSuchElementException();
        }
        return removed;
    }

    @Override
    public T poll() {
        lock.lock();
        try {
            return pollUnsafe();
        } finally {
            lock.unlock();
        }
    }
    private T pollUnsafe() {
        if(this.size() < 1)
            return null;
        T removed = queue(headIndex++);
        modcount++;
        return removed;
    }

    @Override
    public T element() {
        T elem = this.peek();
        if (elem == null) {
            throw new NoSuchElementException();
        }
        return elem;
    }

    @Override
    public T peek() {
        lock.lock();
        try {
            return isEmpty() ? null : queue(headIndex);
        } finally {
            lock.unlock();
        }
    }
    ////////////-------------Collection Methods----------------//////////////
    @Override
    public int size() {
        lock.lock();
        try {
            return (tailIndex - headIndex);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new RingIterator();
    }

    @Override
    public Object[] toArray() {
        lock.lock();
        try {
            T res[] = (T[])Array.newInstance(Object.class, size());
            for(int i = headIndex, j = 0; i < tailIndex; i++, j++)
                res[j] = queue(i);
            return res;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void clear() {
        lock.lock();
        try {
            headIndex = 0;
            tailIndex = 0;
            modcount++;
        } finally {
            lock.unlock();
        }
    }
    //extra nonrequired method implementation, unsafe to add stuff while trying to change at same time
    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean wasSuccessful = true;
        if (((tailIndex + c.size() - headIndex) > (queue.length))) {
            throw new IllegalStateException();
        }
        for (T elem : c) {
            wasSuccessful = (wasSuccessful && addUnsafe(elem));
        }
        return wasSuccessful;
    }

    public boolean addAll(T... c) {
        boolean wasSuccessful = true;
        if (((tailIndex + c.length - headIndex) > (queue.length))) {
            throw new IllegalStateException();
        }
        for (T elem : c) {
            wasSuccessful = (wasSuccessful && addUnsafe(elem));
        }
        return wasSuccessful;
    }
        @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return Arrays.equals(this.toArray(), ((RingBuffer)obj).toArray());
    }
    ////////////!!!========UNSUPPORTED METHODS=========!!!//////////////

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    //////////////////////////////////////////

    private class RingIterator implements Iterator<T> {

        protected int index = 0;
        protected boolean atStart = true;
        protected final long modcount = RingBuffer.this.modcount;
        
        protected void check() throws ConcurrentModificationException {
            if(modcount != RingBuffer.this.modcount)
                throw new ConcurrentModificationException();
        }
        
        @Override
        public boolean hasNext() {
            check();
            if (RingBuffer.this.isEmpty()) {
                return false;
            }
            return atStart || ((index%queue.length) != (tailIndex % queue.length));
        }

        @Override
        public T next() {
            check();
            atStart = false;
            try {
                T next = queue[(index+(headIndex%queue.length))%queue.length];
                index++;
                return next;
            } catch (ArrayIndexOutOfBoundsException a) {
                throw new NoSuchElementException();
            }
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException("Iterators shouldn't modify!");
        }
    }
    
    private class InternalRingIterator extends RingIterator {
        @Override
        public void remove() {
            try {
                System.arraycopy(queue, index, queue, index - 1, queue.length - index);
                tailIndex--;
            } catch (ArrayIndexOutOfBoundsException a) {
                throw new IllegalStateException();
            }
        }
    }
}
