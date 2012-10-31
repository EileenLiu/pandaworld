/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.world;

import java.util.Iterator;
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
    HexGrid<Tile> grid;
    private int timesteps = 0;
    private boolean RUNNING = false;
    private boolean STEP = false;
    private boolean WAIT = true; //if false, random action
    public World() {
        this(DEFAULT_ROWS, DEFAULT_COLS);
    }

    public World(int _r, int _c) {
        grid = new ArrayHexGrid<Tile>(_r, _c);
    }
    
    public String getStatus() {
        return "Timesteps: " + timesteps + "\n" + population();
    }
    public boolean isRunning()
    {
        return RUNNING;
    }
    public boolean shouldStep()
    {
        return STEP;
    }
    public boolean shouldWait()
    {
        return WAIT;
    }
    public void toggleRun()
    {
        RUNNING = !RUNNING;
    }
    public void toggleWait()
    {
        WAIT = !WAIT;
    }

    public void doStep() {
        STEP = true;
     }
    
    public void step() {
        for (Tile e : grid) {
            if (e != null) {
                e.timeStep(WAIT);
            }
        }
        timesteps++;
        STEP = false;
    }
    
    public int getTimesteps()
    {
        return timesteps;
    }
    public int height() {
        return grid.nRows();
    }

    public int width() {
        return grid.nCols();
    }

    public Reference<Tile> at(int r, int c) {
        return grid.ref(c, r);
    }

    /**
     * Retrieves the default reference at 0, 0
     *
     * @return the default reference
     */
    public HexGrid.Reference<Tile> defaultLoc() {
        return grid.ref(0, 0);
    }

    /*public String population() {
     int[] population = new int[4]; //[critters, plants, food, rocks]
     Iterator<Tile> it = grid.iterator();
     while (it.hasNext()) {
     Tile t = it.next();
     if(t==null)continue;
     population[0] = population[0] + (t.critter() ? 1 : 0);
     population[1] = population[1] + (t.plant() ? 1 : 0);
     population[2] = population[2] + (t.food() ? 1 : 0);
     population[3] = population[3] + (t.rock() ? 1 : 0);
     }
     String pop = "Population\n"
     + "\n\tCritters: "+population[0]
     + "\n\tPlants: "+population[1]
     + "\n\tFood: "+population[2]
     + "\n\tRocks: "+population[3];
     return pop;
     }*/
    public int[] population() {
        int[] population = new int[4]; //[critters, plants, food, rocks]
        Iterator<Tile> it = grid.iterator();
        while (it.hasNext()) {
            Tile t = it.next();
            if (t == null) {
                continue;
            }
            population[0] = population[0] + (t.critter() ? 1 : 0);
            population[1] = population[1] + (t.plant() ? 1 : 0);
            population[2] = population[2] + (t.food() ? 1 : 0);
            population[3] = population[3] + (t.rock() ? 1 : 0);
        }
        return population;
    }
}
