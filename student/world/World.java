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
    int siz;

    public World(int _siz) {
        siz = _siz;
        world = new ArrayHexGrid<Set<Entity>>(siz);
    }
    
    public void step() {
        for(Entity e : world) 
            if(e != null)
                e.timeStep();
    }
}
