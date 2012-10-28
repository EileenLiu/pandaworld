/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.grid;

import java.util.Set;
import student.grid.HexGrid.HexDir;
import static student.grid.HexGrid.HexDir.*;
import student.grid.HexGrid.Reference;

/**
 *
 * @author haro
 */
public class Critter extends Entity {
    private static final int BASE_DAMAGE = 100;
    private static final double DAMAGE_INC = 0.2;
    
    private World wor;
    private Reference<Set<Entity>> pos;
    private HexDir dir;
    private int energy;
    private int size, mem[];
    private boolean acted;
    
    public Critter(World _wor, Reference<Set<Entity>> _pos, int memsiz) {
        wor = _wor; pos = _pos;
        mem = new int[memsiz];
    }
    
    @Override
    public int read() {
        return energy;
    }

    @Override
    public void timeStep() {
        if(!acted)
            _wait();
        acted = false;
    }
    
    @IsAction("wait")
    public void _wait() {
        energy -= size;
        acted = true;
    }
    
    @IsAction
    public void forward() {
        pos.contents().remove(this);
        pos = pos.adj(dir);
        pos.contents().add(this);
    }
    
    @IsAction
    public void backward() {
        pos.contents().remove(this);
        pos = pos.lin(-1, dir);
        pos.contents().add(this);
    }
    
    @IsAction
    public void left() {
        dir = dir == N?NW:
              dir == NW?SW:
              dir == SW?S:
              dir == S?SE:
              dir == SE?NE:
              dir == NE?N:null;
    }
    
    @IsAction
    public void right() {
        dir = dir == N?NE:
              dir == NE?SE:
              dir == SE?S:
              dir == S?SW:
              dir == SW?NW:
              dir == NW?N:null;
    }
    
    @IsAction
    public void eat() {
        for(Entity e : pos.contents())
            if(e instanceof Food) {
                int ene = ((Food)e).energy;
                System.out.println("Ate "+ene+" units of energy");
                energy += ene;
                return;
            }
    }
    
    @IsAction
    public void attack() {
        Set<Entity> ahead = pos.adj(dir).contents();
        for(Entity e : ahead) {
            if(e instanceof Critter) {
                Critter c = (Critter)e;
                double damage = BASE_DAMAGE * size
                              * lgs(DAMAGE_INC * (size * mem[2] - c.size * c.mem[1]));
                c.energy -= (int) damage;
                return;
            }
        }
    }
    
    private static double lgs(double x) {
        return 1/(1+Math.exp(-x));
    }
}
