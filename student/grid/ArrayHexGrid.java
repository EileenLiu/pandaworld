/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.grid;

import java.lang.reflect.Array;
import java.util.ArrayList;
import static student.grid.HexGrid.HexDir.*;


public class ArrayHexGrid<E> implements HexGrid<E> {
    private int wid;
    private Ref data[][];
    
    public ArrayHexGrid(int _wid) {
        wid = _wid;
        data = (Ref[][]) Array.newInstance(Ref[].class, 2*wid-1);
        for(int r = 0; r < 2*wid-1; r++) {
            int j = r;
            if(j > wid - 1)
                j = 2*wid - r - 2;
            data[r] = (Ref[]) Array.newInstance(Ref.class, wid + j);
            for(int c = 0; c < wid+j; c++)
                data[r][c] = new Ref(r,c);
        }
    }

    @Override
    public E get(int c, int r) throws HexIndexOutOfBoundsException {
        return ref(c,r).contents();
    }

    @Override
    public void set(int c, int r, E e) throws HexIndexOutOfBoundsException {
        ref(c,r).setContents(e);
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
        return data[c][r];
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
            return data[ec][er];
        }

        @Override
        public Reference<E> adj(HexDir dir) {
            return lin(1, dir);
        }
    }
}
