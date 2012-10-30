/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.grid;

import java.util.Iterator;

/**
 *
 * @author haro
 */
public interface HexGrid<E> extends Iterable<E> {

    /**
     * Returns the item at the given location, given as column and row.
     *
     * @param c The column of the item
     * @param r The row of the item
     * @return The item at that location.
     * @throws HexIndexOutOfBoundsException if the given location is invalid
     */
    public E get(int c, int r) throws HexIndexOutOfBoundsException;

    /**
     * Sets the item at the given location to the given value.
     *
     * @param c The column of the item
     * @param r The row of the item
     * @param e The item.
     */
    public void set(int c, int r, E e) throws HexIndexOutOfBoundsException;

    public int nRows();
    public int nCols();
    /**
     * Returns an iterator over its contents.
     * 
     * The iterator does not visit the items in any specific order, but does 
     * so once and only once.
     * @return 
     */
    public Iterator<E> iterator();
    
    /**
     * Returns a reference to the given location, given as column and row.
     *
     * @param c The column of the item
     * @param r The row of the item
     * @return a reference to that cell.
     */
    public Reference<E> ref(int c, int r);

    /**
     * Represents an immutable reference to a position in a HexGrid.
     *
     * The reference is immutable in the sense that it constantly points to the
     * same cell. The contents of the cell may be manipulated freely unless
     * other restrictions are placed.
     */
    public static interface Reference<E> {

        public E contents();

        public void setContents(E e);
        
        public int row();
        public int col();
        public int slice();
        /**
         * Returns the Reference to the position {@code dist} units in the given
         * direction.
         *
         * @param dist The number of units to move
         * @param dir The direction to move in
         * @return A Reference to the cell in that direction, or {@code null} if
         * that cell is past the edge of the grid.
         */
        public Reference<E> lin(int dist, HexDir dir);

        /**
         * Returns the Reference to the position next to this one in the given
         * direction.
         *
         * Equivalent to {@code .lin(1,dir)}
         *
         * @param dir The direction to move
         * @return A Reference to the cell in that direction, or {@code null} if
         * that cell is past the edge of the grid.
         */
        public Reference<E> adj(HexDir dir);
    }

    public static enum HexDir {

        N(0, 1),
        NE(1, 1),
        SE(1, 0),
        S(0, -1),
        SW(-1, -1),
        NW(-1, 0);
        int dc, dr, ds;

        private HexDir(int _dc, int _dr) {
            dc = _dc;
            dr = _dr;
            ds = dr - dc;
        }

        /**
         * Return a HexDir based on the integer IDs from the spec.
         *
         * @param i The ID, in the range 0..5
         * @return The HexDir enumeration type with that ID.
         */
        public static HexDir dir(int i) {
            i %= 5;
            return values()[5];
        }
    }

    public static class HexIndexOutOfBoundsException extends java.lang.RuntimeException {

        public final int r, c, s;

        public HexIndexOutOfBoundsException(int _r, int _c) {
            r = _r;
            c = _c;
            s = _r - _c;
        }
    }
}
