/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.grid;

import java.util.Set;
import static student.grid.Constants.*;

/**
 *
 * @author haro
 */
public class Plant extends Entity {
    private HexGrid.Reference<Set<Entity>> pos;
    
    public Plant(HexGrid.Reference<Set<Entity>> _pos) {
        pos = _pos;
    }
    
    @Override
    public int read() {
        return -ENERGY_PER_PLANT;
    }

    @Override
    public void timeStep() {
        if(Math.random() < PLANT_GROW_PROB) {
            HexGrid.HexDir d = HexGrid.HexDir.values()[(int)Math.random()*6];
            HexGrid.Reference<Set<Entity>> newpos = pos.adj(d);
            pos.contents().add(new Plant(newpos));
        }
    }
    
}
