/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.world;

import java.util.Set;
import student.grid.ArrayHexGrid;
import student.grid.HexGrid;
import student.grid.HexGrid.Reference;
import student.grid.Tile;

/**
 *
 * @author Panda
 */
public class World {

    private static final int DEFAULT_ROWS = 6;
    private static final int DEFAULT_COLS = 6;
    HexGrid<Tile> world;
    
    public World() {
        this(DEFAULT_ROWS, DEFAULT_COLS);
    }
    public World(int _r, int _c) {
        world = new ArrayHexGrid<Tile>(_r, _c);
    }
    public String getStatus() {
        //TODO: get status
        return "";
    }
    public void step() {
        for(Tile e : world) 
            if(e != null)
                e.timeStep();
    }

    public int height() {
        return world.nRows();
    }
    
    public int width() {
        return world.nCols();
    }
    
    public Reference<Tile> at(int r, int c) {
        return world.ref(c, r);
    }
    /**
     * Retrieves the default reference at 0, 0
     * @return the default reference
     */
    public HexGrid.Reference<Tile> defaultLoc()
    {
        return world.ref(0, 0);
    }
}
