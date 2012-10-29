/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.world;

import java.util.Set;
import student.grid.ArrayHexGrid;
import student.grid.Entity;
import student.grid.HexGrid;

/**
 *
 * @author Panda
 */
public class World {

    public String getStatus() {
        //TODO: get status
        return "";
    }
    HexGrid<Set<Entity>> world;

    public World(int _r, int _c) {
        world = new ArrayHexGrid<Set<Entity>>(_r, _c);
    }
    
    public void step() {
        for(Set<Entity> e : world) 
            for(Entity ee : e)
                if(ee != null)
                    ee.timeStep();
    }
}
