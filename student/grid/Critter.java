/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.grid;

import java.util.Set;
import student.grid.HexGrid.HexDir;
import static student.grid.HexGrid.HexDir.*;
import student.grid.HexGrid.Reference;
import student.world.World;

/**
 *
 * @author haro
 */
public class Critter extends Entity {
    private static final int BASE_DAMAGE = 100;
    private static final double DAMAGE_INC = 0.2;
    private static final int MOVE_COST = 3;
    private static final int ATTACK_COST = 5;
    private static final int GROW_COST = 1;
    private static final int ABILITY_COST = 25;
    
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
        if(energy < 0)
            ;//die
    }
    
    @IsAction("wait")
    public void _wait() {
        energy -= size;
        acted = true;
    }
    
    @IsAction
    public void forward() {
        energy -= size * MOVE_COST;
        pos.contents().remove(this);
        pos = pos.adj(dir);
        pos.contents().add(this);
        acted = true;
    }
    
    @IsAction
    public void backward() {
        energy -= size * MOVE_COST;
        pos.contents().remove(this);
        pos = pos.lin(-1, dir);
        pos.contents().add(this);
        acted = true;
    }
    
    @IsAction
    public void left() {
        energy -= size;
        dir = dir == N?NW:
              dir == NW?SW:
              dir == SW?S:
              dir == S?SE:
              dir == SE?NE:
              dir == NE?N:null;
        acted = true;
    }
    
    @IsAction
    public void right() {
        energy -= size;
        dir = dir == N?NE:
              dir == NE?SE:
              dir == SE?S:
              dir == S?SW:
              dir == SW?NW:
              dir == NW?N:null;
        acted = true;
    }
    
    @IsAction
    public void eat() {
        energy -= size;
        for(Entity e : pos.contents())
            if(e instanceof Food) {
                int ene = ((Food)e).energy;
                System.out.println("Ate "+ene+" units of energy");
                energy += ene;
                return;
            }
        acted = true;
    }
    
    @IsAction
    public void attack() {
        energy -= size * ATTACK_COST;
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
        acted = true;
    }
    
    @IsAction
    public void tag(int t) {
        energy -= size;
        Set<Entity> ahead = pos.adj(dir).contents();
        for(Entity e : ahead) {
            if(e instanceof Critter) {
                Critter c = (Critter)e;
                c.mem[7] = t;
            }
        }
        acted = true;
    }
    
    @IsAction
    public void grow() {
        energy -= size * complexity() * GROW_COST;
        size++;
        acted = true;
    }
    
    private static double lgs(double x) {
        return 1/(1+Math.exp(-x));
    }
    
    private int complexity() {
        return /*rules * RULE_COST +*/ (mem[1] + mem[2])*ABILITY_COST;
    }
}
