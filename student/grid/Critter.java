/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.grid;

import java.util.Set;
import static student.grid.Constants.*;
import student.grid.HexGrid.HexDir;
import static student.grid.HexGrid.HexDir.*;
import student.grid.HexGrid.Reference;
import student.world.World;

/**
 *
 * @author haro
 */
public class Critter extends Entity {

    private World wor;
    private Reference<Set<Entity>> pos;
    private HexDir dir;
    private int mem[];
    private boolean acted;

    public Critter(World _wor, Reference<Set<Entity>> _pos, int memsiz) {
        wor = _wor;
        pos = _pos;
        mem = new int[memsiz];
    }

    @IsProperty
    public HexDir direction() {
        return dir;
    }

    @IsProperty
    public int size() {
        return mem[3];
    }
    
    @IsProperty
    public int memsize() {
        return mem.length;
    }
    
    @IsProperty
    public int defense() {
        return mem[1];
    }
    
    @IsProperty
    public int offense() {
        return mem[2];
    }
    
    @IsProperty
    public int ruleCount() {
        return mem[5];
    }
    
    @IsProperty
    public int log() {
        return mem[6];
    }
    
    @IsProperty
    public int tag() {
        return mem[7];
    }
    
    @IsProperty
    public int posture() {
        return mem[8];
    }
    
    @IsProperty
    public int []memory() {
        int rest[] = new int[mem.length - 9];
        System.arraycopy(mem, 9, rest, 0, mem.length-9);
        return rest;
    }

    @IsProperty("energy")
    public int energy() {
        return mem[4];
    }
    
    @Override
    @IsProperty("appearance")
    public int read() {
        return 100000*tag() + 1000*size() + posture();
    }

    @Override
    public void timeStep() {
        if (!acted) {
            _wait();
        }
        acted = false;
        if (mem[4] < 0)
            ;//die
    }

    @IsAction("wait")
    public void _wait() {
        mem[4] -= mem[3];
        acted = true;
    }

    @IsAction
    public void forward() {
        mem[4] -= mem[3] * MOVE_COST;
        pos.contents().remove(this);
        pos = pos.adj(dir);
        pos.contents().add(this);
        acted = true;
    }

    @IsAction
    public void backward() {
        mem[4] -= mem[3] * MOVE_COST;
        pos.contents().remove(this);
        pos = pos.lin(-1, dir);
        pos.contents().add(this);
        acted = true;
    }

    @IsAction
    public void left() {
        mem[4] -= mem[3];
        dir = dir == N ? NW
                : dir == NW ? SW
                : dir == SW ? S
                : dir == S ? SE
                : dir == SE ? NE
                : dir == NE ? N : null;
        acted = true;
    }

    @IsAction
    public void right() {
        mem[4] -= mem[3];
        dir = dir == N ? NE
                : dir == NE ? SE
                : dir == SE ? S
                : dir == S ? SW
                : dir == SW ? NW
                : dir == NW ? N : null;
        acted = true;
    }

    @IsAction
    public void eat() {
        mem[4] -= mem[3];
        for (Entity e : pos.contents()) {
            if (e instanceof Food) {
                int ene = ((Food) e).energy;
                System.out.println("Ate " + ene + " units of mem[4]");
                mem[4] += ene;
                return;
            }
        }
        acted = true;
    }

    @IsAction
    public void attack() {
        mem[4] -= mem[3] * ATTACK_COST;
        Set<Entity> ahead = pos.adj(dir).contents();
        for (Entity e : ahead) {
            if (e instanceof Critter) {
                Critter c = (Critter) e;
                double damage = BASE_DAMAGE * mem[3]
                        * lgs(DAMAGE_INC * (mem[3] * mem[2] - c.mem[3] * c.mem[1]));
                c.mem[4] -= (int) damage;
                return;
            }
        }
        acted = true;
    }

    @IsAction
    public void tag(int t) {
        mem[4] -= mem[3];
        Set<Entity> ahead = pos.adj(dir).contents();
        for (Entity e : ahead) {
            if (e instanceof Critter) {
                Critter c = (Critter) e;
                c.mem[7] = t;
            }
        }
        acted = true;
    }

    @IsAction
    public void grow() {
        mem[4] -= mem[3] * complexity() * GROW_COST;
        mem[3]++;
        acted = true;
    }

    private static double lgs(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    private int complexity() {
        return /*rules * RULE_COST +*/ (mem[1] + mem[2]) * ABILITY_COST;
    }
}
