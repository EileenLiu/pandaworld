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
public class Critter /*extends Entity*/ {

    private World wor;
    private Reference<Tile> pos;
    private HexDir dir;
    private int mem[];
    private boolean acted;

    public Critter(World _wor, Reference<Tile> _pos, int memsiz) {
        wor = _wor;
        pos = _pos;
        mem = new int[memsiz];
        defaultMemory();
        dir = HexDir.N;
    }
    public void defaultMemory(){
        mem = new int[]{9, 1, 1, 1, 10, 0, 0, 0, 0};
    }
    public HexDir direction() {
        return dir;
    }

    public int size() {
        return mem[3];
    }
    
    public int memsize() {
        return mem.length;
    }
    
    public int defense() {
        return mem[1];
    }
    
    public int offense() {
        return mem[2];
    }
    
    public int ruleCount() {
        return mem[5];
    }
    
    public int log() {
        return mem[6];
    }
    
    public int tag() {
        return mem[7];
    }
    
    public int posture() {
        return mem[8];
    }
    
    public int[] memory() {
        return mem;
    }
    public void randomizeMemory()
    {
        
    }
    public int energy() {
        return mem[4];
    }

    public int read() {
        return 100000 * tag() + 1000 * size() + posture();
    }   
    
    public void timeStep() {
        if (!acted) {
            _wait();
        }
        acted = false;
        if (mem[4] < 0) //if run out of energy then
        {//die
           pos.contents().addFood(Constants.FOOD_PER_SIZE*size());
           pos.contents().removeCritter();
        }
    }
    public void randomAct() {
        switch ((int) (Math.random() * 8)) {
            case 0:
                _wait();
            case 1:
                forward();
                break;
            case 2:
                backward();
                break;
            case 3:
                eat();
                break;
            case 4:
                left();
                break;
            case 5:
                right();
                break;
            case 6:
                grow();
                break;
            case 7:
                bud();
                break;
        }
    }
    
    public void _wait() {
        mem[4] -= mem[3];
        acted = true;
    }
    
    public void forward() {
        mem[4] -= mem[3] * MOVE_COST;
        Reference<Tile> newPos = pos.adj(dir);
        if(!(newPos==null||newPos.contents().rock())){
        /*if(newPos.contents().rock())
            System.out.println("Won't do that; it's a rock");
        else {*/
            pos.contents().removeCritter();
            newPos.contents().putCritter(this);
            pos = newPos;
            acted = true;
        }
    }
    
    public void backward() {
        Reference<Tile> newPos = pos.lin(-1,dir);
        if(!(newPos==null||newPos.contents().rock())){
            pos.contents().removeCritter();
            newPos.contents().putCritter(this);
            pos = newPos;
            acted = true;
        }
    }
    
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
    
    public void eat() {
        mem[4] -= mem[3];
        if (pos.contents().food() || pos.contents().plant()) {
            int ene = pos.contents().foodValue()
                    +(pos.contents().plant()? Constants.ENERGY_PER_PLANT: 0);
            System.out.println("Ate " + ene + " units of energy");
            pos.contents().removePlant();
            pos.contents().takeFood();
            mem[4] += ene;
            return;
        } else
            System.out.println("No food there");
        acted = true;
    }
    
    public void attack() {
        mem[4] -= mem[3] * ATTACK_COST;
        Tile ahead = pos.adj(dir).contents();
        if (ahead.critter()) {
            Critter c = ahead.getCritter();
            double damage = BASE_DAMAGE * mem[3]
                    * lgs(DAMAGE_INC * (mem[3] * mem[2] - c.mem[3] * c.mem[1]));
            c.mem[4] -= (int) damage;
            return;
        }
        acted = true;
    }
    
    public void tag(int t) {
        mem[4] -= mem[3];
        Tile ahead = pos.adj(dir).contents();
        if (ahead.critter()) {
            Critter c = ahead.getCritter();
            c.mem[7] = t;
        }
        acted = true;
    }
    
    public void grow() {
        mem[4] -= mem[3] * complexity() * GROW_COST;
        mem[3]++;
        acted = true;
    }
    
    public void bud() {
        Reference<Tile> np = pos.lin(-1, dir);
        if(np == null || np.contents().rock())
            return; //we're in a corner, can't put a critter there.
        Critter baby = new Critter(wor, np, mem[0]);
        baby.mem[4] = Constants.INITIAL_ENERGY;
        np.contents().putCritter(baby);
        mem[4] -= complexity() * Constants.BUD_COST;
        acted = true;
    }

    private static double lgs(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    private int complexity() {
        return /*rules * RULE_COST +*/ (mem[1] + mem[2]) * ABILITY_COST;
    }

    public String state() {
        String s = "/nMemory: " + mem[0]
                + "/nDefense: " + mem[1]
                + "/nOffense: " + mem[2]
                + "/nSize: " + mem[3]
                + "/nEnergy: " + mem[4]
                + "/nRule Counter: " + mem[5]
                + "/nEvent Log: /n" + eventLog()//mem[6]
                + "/nTag: " + mem[7]
                + "/nPosture: " + mem[8];
        return s;
    }
    /**
     * Generates the event log of the critter
     * @return the event log as a string
     */
    public String eventLog() {
        String eventLog = "";
        int events = mem[6];
        while (events > 99) {
            int e = events % 1000;
            if (e < 300) {
                eventLog = eventLog + "/tThis critter was " + ((e < 200) ? "attacked" : "tagged") + " from direction "+direction(e%100);
            }
            events = events / 1000;
        }
        return eventLog;
    }

    private final String direction(int d) {
        String direction = "";
        switch (d) {
            case 0:direction = "north";
                break;
            case 1:direction = "northeast";
                break;
            case 2:direction = "southeast";
                break;
            case 3:direction = "south";
                break;
            case 4:direction = "southwest";
                break;
            case 5:direction = "northwest";
                break;
        }
        return direction;
    }

    public Reference<Tile> loc() {
        return this.pos;
    }
}
