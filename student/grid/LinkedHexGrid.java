/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.grid;

import java.util.List;


public class LinkedHexGrid<E> implements HexGrid<E> {
    Ref base;
    int nr, nc;
    
    public LinkedHexGrid(int _r, int _h) {
        nr = _r; nc = _h;
        base = new Ref();
        //make the first column
        Ref cur = base, nxt;
        while(_r --> 0) { 
            nxt = new Ref();
            cur.refs[0] = nxt;
            nxt.refs[3] = cur;
        }
        //make the rows
        _r = nr; cur = base;
        while(_r --> 0) {
            while(_h --> 0) {
                nxt = new Ref();
            }
        }
    }

    @Override
    public E get(int c, int r) throws HexIndexOutOfBoundsException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean set(int c, int r, E e) throws HexIndexOutOfBoundsException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int nRows() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int nCols() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int nSlices() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Reference<E> ref(int c, int r) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private class Ref implements Reference<E> {
        private E contents;
        private Ref[] refs;
        private int r, c;

        @Override
        public E contents() {
            return contents;
        }

        @Override
        public void setContents(E e) {
            contents = e;
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
            return r-c;
        }

        @Override
        public Reference<E> lin(int dist, HexDir dir) {
            if(dist < 0)
                throw new IndexOutOfBoundsException("HexGrid distance should be >= 1 but was: "+dist);
            Ref res = this;
            while(dist --> 0)
                res = res.refs[dir.ordinal()];
            return res;
        }

        @Override
        public Reference<E> adj(HexDir dir) {
            return refs[dir.ordinal()];
        }
    }
}
