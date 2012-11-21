/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.concurrency;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Eileen Liu <el544@cornell.edu>
 */
public class RingBuffer<T> implements BlockingQueue<T>{
    private T[] queue; 
    private int headIndex = -1;
    private int tailIndex = -1;

    public RingBuffer(int capacity) {
        queue = (T [])(Array.newInstance(Object.class, capacity));
        headIndex = 0;
        tailIndex = 0;
    }
    private void incrementHeadIndex(){
        headIndex = headIndex%queue.length + 1;
    }
    private void incrementTailIndex() {
        tailIndex = tailIndex % queue.length + 1;
    }

    @Override
    public boolean add(T e) {
        if ((tailIndex-headIndex)<(queue.length)) {
        queue[tailIndex] = e;
        tailIndex++;
        return true;
        }
        return false;
    }
    
    @Override
    public boolean remove(Object o) {
        if(!(tailIndex==headIndex))
        {
            Iterator<T> iter = this.iterator();
            while(iter.hasNext())
            {
                T next = iter.next();
                if(next.equals(o))
                {
                    iter.remove();
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean offer(T e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void put(T e) throws InterruptedException {
        
    }

    @Override
    public boolean offer(T e, long timeout, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public T take() throws InterruptedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public T poll(long timeout, TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int remainingCapacity() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int drainTo(Collection<? super T> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int drainTo(Collection<? super T> c, int maxElements) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public T remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public T poll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public T element() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public T peek() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterator<T> iterator() {
        return new RingIterator();        
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        //////////////////////
        return true;
    }
    
    
    ////////////===========UNSUPPORTED METHODS============//////////////
    
    
        @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
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

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    //////////////////////////////////////////
    
    private class RingIterator implements Iterator<T> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return (index != (tailIndex % queue.length));
        }

        @Override
        public T next() {
            try {
                T next = queue[index];
                index++;
                return next;
            } catch (ArrayIndexOutOfBoundsException a) {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            try {
                queue[index-1] = null;
            } catch (ArrayIndexOutOfBoundsException a) {
                throw new IllegalStateException();
            }
        }

    }
}
