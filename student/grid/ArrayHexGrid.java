/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.grid;

import java.lang.reflect.Array;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import static student.grid.HexGrid.HexDir.*;


public class ArrayHexGrid<E extends UnicastRemoteObject> implements HexGrid<E> {
    private int rs, cs;
    private Ref data[][];
    
    public ArrayHexGrid(int _rs, int _cs) throws RemoteException {
        rs = _rs; cs = _cs;
        data = (Ref[][]) Array.newInstance(Ref[].class, rs);
        for(int r = 0; r < rs; r++) {
            data[r] = (Ref[]) Array.newInstance(Ref.class, cs);
            for(int c = 0; c < cs; c++)
                data[r][c] = new Ref(r,c);
        }
    }

    @Override
    public E get(int c, int r) throws HexIndexOutOfBoundsException {
        Reference<E> ref = ref(c,r);
        if(ref == null)
            throw new HexIndexOutOfBoundsException(r, c);
        return ref.mutableContents();
    }

    @Override
    public void set(int c, int r, E e) throws HexIndexOutOfBoundsException {
        Reference<E> ref = ref(c,r);
        if(ref == null)
            throw new HexIndexOutOfBoundsException(r, c);
        ref.setContents(e);
    }

    @Override
    public int nRows() {
        return rs;
    }

    @Override
    public int nCols() {
        return cs;
    }

    @Override
    public Reference<E> ref(int c, int r) {
        if(r < 0 || c < 0 || r >= rs || c >= cs){
            //System.out.println("ArrayHexGrid: Reference<E> ref("+r+","+c+") returns null");
            return null;
        }
        //System.out.println("ArrayHexGrid: Reference<E> ref("+r+","+c+") returns data["+r+"]"+"["+c+"]:"+data[r][c]);
        return data[r][c];
    }
    
    @Override
    public RReference<E> rat(int c, int r) {
        if(r < 0 || c < 0 || r >= rs || c >= cs){
            //System.out.println("ArrayHexGrid: RReference<E> rat("+r+","+c+") returns null");
            return null;
        }
        //System.out.println("ArrayHexGrid: RReference<E> rat("+r+","+c+") returns data["+r+"]"+"["+c+"]:"+data[r][c]);
        return ((RReference<E>)data[r][c]);
    }

    @Override
    public Iterator<Reference<E>> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<Reference<E>> {
        int row = -1, col = -1;
        
        @Override
        public boolean hasNext() {
            return row < data.length - 1
                || col < data[row].length - 1;
        }

        @Override
        public Reference<E> next() {
            col++;
            if(row < 0 || col >= data[row].length) {
                row ++;
                col = 0;
            }
            return data[row][col];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.^H^H^H^Hever.");
        }
        
    }
   
    private class Ref extends UnicastRemoteObject implements Reference<E>, RReference<E> {
        int r, c;
        E e;
        
        public Ref(int _r, int _c) throws RemoteException {
            r = _r; c = _c;
        }
        
        @Override
        public E mutableContents() {
            return e;
        }
        
        @Override
        public E contents() {
            return e;
        }

        @Override
        public void setContents(E _e) {
            e = _e;
        }

        @Override
        public int row() {
            return r;
        }

        @Override
        public int col() {
            return c;
        }

        @Override
        public int slice() {
            return c - r;
        }

        @Override
        public Ref adj(HexDir dir) {
            int er = r, ec = c;
            if(c%2==0) {
                switch(dir) {
                    case N:
                        er -= 1; break;
                    case S:
                        er += 1; break;
                    case NE:
                        ec += 1; break;
                    case NW:
                        ec -= 1; break;
                    case SE:
                        ec += 1;
                        er += 1; break;
                    case SW:
                        ec -= 1;
                        er += 1; break;
                }
            } else {
                switch(dir) {
                    case N:
                        er -= 1; break;
                    case S:
                        er += 1; break;
                    case NE:
                        ec += 1;
                        er -= 1; break;
                    case NW:
                        ec -= 1;
                        er -= 1; break;
                    case SW:
                        ec -= 1; break;
                    case SE:
                        ec += 1; break;
                }
            }
            //System.out.println("("+er+","+ec+")");
            if(er < 0 || er >= data.length || ec < 0 || ec >= data[er].length)
                return null;
            return data[er]
                       [ec];
        }

        @Override
        public Ref lin(int dist,HexDir dir) {
            if(dist == 0)
                return this;
            else if(dist < 0)
                return lin(-dist, dir == N  ?
                             S  : dir == S  ?
                             N  : dir == NE ?
                             SW : dir == SW ?
                             NE : dir == NW ?
                             SE : NW);
            if(adj(dir)!=null)
                return adj(dir).lin(dist-1,dir);
            return null;
        }
        
        @Override
        public int hashCode() {
            return (int)(((long)r&(long)c)*((long)r^(long)c)%Integer.MAX_VALUE);
        }

        @Override
        public RReference<E> export() {
            return this;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Ref other = (Ref) obj;
            if (this.r != other.r) {
                return false;
            }
            if (this.c != other.c) {
                return false;
            }
            if (!this.outer().equals(other.outer())) {
                return false;
            }
            return true;
        }     
        
        private ArrayHexGrid outer () {
            return ArrayHexGrid.this;
        }
        
        /*
        @Override
        protected void finalize() throws Throwable {
            try {
                unexportObject(this, true);
            } catch (NoSuchObjectException noSuchObjectException) {
                System.err.println("ArrayHexGrid$Ref.finalize(): NoSuchObjectException");
            }
            super.finalize();
        }
        */
        @Override
        public String toString(){
            return ("Ref:  row "+row()+" col "+col()+" slice "+slice()+" with contents: "+((contents()!=null)?(contents().toString()):""));
        }
    }
}
