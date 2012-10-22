/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.grid;

import java.lang.reflect.Array;
import static student.grid.HexGrid.HexDir.*;


public class ArrayHexGrid<E> implements HexGrid<E> {
    private int wid;
    private Ref data[][];
    
    public ArrayHexGrid(int _wid) {
        wid = _wid;
        data = (Ref[][]) Array.newInstance(Ref[].class, 2*wid-1);
        for(int r = 0; r < 2*wid-1; r++) {
            data[r] = (Ref[]) Array.newInstance(Ref.class, rowSize(r));
            for(int c = 0; c < rowSize(r); c++)
                data[r][c] = new Ref(r,c);
        }
    }
    
    private int rowSize(int r) {
        if(r > wid - 1)
            r = 2*wid - r - 2;
        return wid + r;
    }

    @Override
    public E get(int c, int r) throws HexIndexOutOfBoundsException {
        Reference<E> ref = ref(c,r);
        if(ref == null)
            throw new HexIndexOutOfBoundsException(r, c);
        return ref.contents();
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
        return 2*wid - 1;
    }

    @Override
    public int nCols() {
        return 2*wid - 1;
    }

    @Override
    public int nSlices() {
        return 2*wid - 1;
    }

    @Override
    public Reference<E> ref(int c, int r) {
        if(r < 0 || c < 0 || r >= data.length || c >= rowSize(r))
            throw new HexIndexOutOfBoundsException(r, c);
        return data[r][c];
    }
   
    private class Ref implements Reference<E> {
        int r, c;
        E e;
        
        public Ref(int _r, int _c){
            r = _r; c = _c;
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
        public Reference<E> lin(int dist, HexDir dir) {
            if(dir == S) {
                dir = N;
                dist = -dist;
            } else if(dir == SW) {
                dir = NE;
                dist = -dist;
            } else if(dir == NW) {
                dir = SE;
                dist = -dist;
            }
            int er = r, ec = c;
            if(dir == N || dir == NE) 
                er += dist;
            if(dir == SE || dir == NE)
                ec += dist;
            return data[er][ec];
        }

        @Override
        public Reference<E> adj(HexDir dir) {
            return lin(1, dir);
        }
    }
}
