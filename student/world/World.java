/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.world;

import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import student.grid.ArrayHexGrid;
import student.config.Constants;
import student.grid.Critter;
import student.grid.HexGrid;
import student.grid.HexGrid.HexDir;
import student.grid.HexGrid.Reference;
import student.grid.Tile;

/**
 *
 * @author Panda
 */
public class World {

    HexGrid<Tile> grid;
    private int timesteps = 0;
    private boolean WAIT = true; //if false, random action

    public World() {
        this(Constants.MAX_ROW, Constants.MAX_COLUMN);
    }

    public World(int _r, int _c) {
        grid = new ArrayHexGrid<Tile>(_r, _c);
    }

    public String getStatus() {
        return "Timesteps: " + timesteps + "\n" + population();
    }
    
    public void toggleWait() {
        WAIT =! WAIT;
    }

    public void step() {
        for (int i = 0; i < Constants.PLANTS_CREATED_PER_TURN; i++) {
            int r = (int) (grid.nRows() * Math.random());
            int c = (int) (grid.nCols() * Math.random());
            if (!grid.get(c, r).plant() && !grid.get(c, r).rock()) {
                grid.get(c, r).putPlant();
            } else {
                i--;
            }
        }
        int cr = 0;
        for (Reference<Tile> e : grid) {
            if (e.contents() != null && e.contents().critter()) {
                cr++;
            }
        }
        double prob = Constants.PLANT_GROW_PROB / (cr == 0 ? 1 : cr);
        for (Reference<Tile> e : grid) {
            Tile t = e.contents();
            if (t.plant()) {
                for (HexDir d : HexDir.values()) {
                    if (e.adj(d) != null
                            && !e.adj(d).contents().plant()
                            && !e.adj(d).contents().rock()
                            && Math.random() < prob) {
                        e.adj(d).contents().putPlant();
                    }
                }
            }
            if (t.critter()) {
                if (!WAIT) {
                    t.getCritter().randomAct();
                }
                if (t.critter()) {
                    t.getCritter().timeStep();
                }
            }
        }
        timesteps++;
        System.out.println("-----------------" + timesteps);
    }

    public int getTimesteps() {
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
     * Adds the given object to the location with the given row and column
     * Throws InvalidWorldAdditionException if the location is invalid
     * @param what the object to add
     * @param row the given row
     * @param col the given col
     * @throws student.world.World.InvalidWorldAdditionException 
     */
    public void add(Object what, int row, int col) throws InvalidWorldAdditionException {
        HexGrid.Reference<Tile> loc = grid.ref(col, row);
        if (loc != null) { //out of bounds
            add(what, loc);
        } else {
            throw new InvalidWorldAdditionException();
        }
    }
    /**
     * Adds the given object to the given location
     * Throws InvalidWorldAdditionException if the object is invalid
     * Precondition: loc is a valid location, or null
     * @param what the object to add
     * @param loc the location to add it, if null, picks a random location
     * @throws student.world.World.InvalidWorldAdditionException 
     */
    public HexGrid.Reference<Tile> add(Object what, HexGrid.Reference<Tile> loc) throws InvalidWorldAdditionException{
            if (loc==null){
                loc = this.randomLoc();
            }
            if (loc.contents() == null) {
                loc.setContents(new Tile(false, 0));
            }
            if (what instanceof Critter) {
                loc.contents().putCritter((Critter)what);
            }
            else if (what instanceof String && what.equals("plant")) {
                loc.contents().putPlant();
            }
            else if (what instanceof String && what.equals("rock")){
                loc.setContents(new Tile.Rock());
            }
            else { //not a valid option to add
                throw new InvalidWorldAdditionException();
            }
            return loc;
    }
    /**
     * Retrieves the default reference at 0, 0
     *
     * @return the default reference
     */
    public HexGrid.Reference<Tile> defaultLoc() {
        return grid.ref(0, 0);
    }
    public HexGrid.Reference<Tile> randomLoc() {
        return grid.ref((int)(Math.random()*height()), (int)(Math.random()*height()));
    }
    public static final int CRIT = 0, PLANT = 1, FOOD = 2, ROCK = 3;
    public int[] population() {
        int[] population = new int[4]; //[critters, plants, food, rocks]
        Iterator<Reference<Tile>> it = grid.iterator();
        while (it.hasNext()) {
            Tile t = it.next().contents();
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

    public static class InvalidWorldAdditionException extends Exception {

        public InvalidWorldAdditionException() {
        }
    }
}
