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
<<<<<<< HEAD
    HexGrid<Tile> world;
    
=======
    HexGrid<Set<Entity>> world;

>>>>>>> origin/gui
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
<<<<<<< HEAD
        for(Tile e : world) 
            if(e != null)
                e.timeStep();
=======
        for (Set<Entity> e : world) {
            for (Entity ee : e) {
                if (ee != null) {
                    ee.timeStep();
                }
            }
        }
>>>>>>> origin/gui
    }

    public int height() {
        return world.nRows();
    }

    public int width() {
        return world.nCols();
    }
<<<<<<< HEAD
    
    public Reference<Tile> at(int r, int c) {
=======

    public Reference<Set<Entity>> at(int r, int c) {
>>>>>>> origin/gui
        return world.ref(c, r);
    }

    /**
     * Retrieves the default reference at 0, 0
     *
     * @return the default reference
     */
<<<<<<< HEAD
    public HexGrid.Reference<Tile> defaultLoc()
    {
=======
    public HexGrid.Reference<Set<Entity>> defaultLoc() {
>>>>>>> origin/gui
        return world.ref(0, 0);
    }
    public String population()
    {
        String pop = "Population/n/n"
                    +"Critters: "//+getCrittersCount()
                    +"Plants: "//+getPlantsCount()
                    +"Rocks: "//+getRocksCount()
                    +"Food: ";//+getFoodCount;
        return pop;
    }
}
