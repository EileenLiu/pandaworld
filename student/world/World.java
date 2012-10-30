/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.world;

import java.util.Set;
import student.grid.ArrayHexGrid;
import student.grid.Entity;
import student.grid.HexGrid;
import student.grid.HexGrid.Reference;

/**
 *
 * @author Panda
 */
public class World {

    private static final int DEFAULT_ROWS = 6;
    private static final int DEFAULT_COLS = 6;
    HexGrid<Set<Entity>> world;
    public World() {
        this(DEFAULT_ROWS, DEFAULT_COLS);
    }
    public World(int _r, int _c) {
        world = new ArrayHexGrid<Set<Entity>>(_r, _c);
    }
    public String getStatus() {
        //TODO: get status
        return "";
    }
    public void step() {
        for(Set<Entity> e : world) 
            for(Entity ee : e)
                if(ee != null)
                    ee.timeStep();
    }

    public int height() {
        return world.nRows();
    }
    
    public int width() {
        return world.nCols();
    }
    
    public Reference<Set<Entity>> at(int r, int c) {
        return world.ref(c, r);
    }
}
