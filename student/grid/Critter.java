/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.grid;

import java.awt.Color;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import student.config.Constants;
import static student.config.Constants.*;
import student.grid.HexGrid.HexDir;
import static student.grid.HexGrid.HexDir.*;
import student.grid.HexGrid.Reference;
import student.parse.Action;
import student.parse.Program;
import student.parse.Rule;
import student.remote.server.RemoteCritter;
import student.world.World;

/**
 *
 * @author haro
 */
public final class Critter /*extends Entity*/ implements CritterState, RemoteCritter {
    private static AtomicInteger SERIAL = new AtomicInteger();
    
    private World wor;
    private int serial = SERIAL.getAndIncrement();
    private Reference<Tile> pos;
    private HexDir dir;
    private int mem[];
    private boolean acted, amorous;
    /*/private/*/public Program prog;
    private String appearance;
    private Species species;
    private LinkedList<Integer> lineage;
    public Action recentAction = new Action("wait");
    private String name;
    
    public Critter(World _wor, Reference<Tile> _pos, Program _p) {
        this(_wor, _pos, _p, defaultMemory());
    }
    public Critter(World _wor, Reference<Tile> _pos, Program p, int d) {
        this(_wor, _pos, p);
        dir = HexDir.dir(d);
    }
    private Critter(World _wor, Reference<Tile> _pos, Program _p, int []_mem) {
        this(_wor, _pos, _p, _mem, new LinkedList(), false);
    }
    private Critter(World _wor, Reference<Tile> _pos, Program _p, LinkedList<Integer> ancestors) {
                this(_wor, _pos, _p, defaultMemory(), ancestors,false);
    }
    private Critter(World _wor, Reference<Tile> _pos, Program _p, int []_mem, LinkedList<Integer> ancestors, boolean mutate) {
        wor = _wor;
        if(_pos!=null)
            pos = _pos;
        else
            pos =_wor.randomLoc();
        mem = _mem;
        dir = HexDir.N;
        if(_p==null)
            _p = new Program();
        prog = _p;
        if(mutate) mutateCritter(this);
        species = Species.getInstance(new int[]{mem[0], mem[1], mem[2]}, prog, lineage);
        lineage = ancestors;
        lineage.add((Integer)species.hashCode());
        System.err.println("\tMade critter: program is"+prog);
        name = "Critter"+hashCode();
    }

    private static final int []defaultMemory = {MIN_MEMORY, 1, 1, 1, INITIAL_ENERGY};
    private static int []defaultMemory(){
        int mem[] = new int[MIN_MEMORY];
        System.arraycopy(defaultMemory, 0, mem, 0, defaultMemory.length);
        return mem;
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
    
    public boolean amorous() {
        return amorous;
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
    public void setDefense(int i)
    {
        mem[1] = i;
    }
    public void setOffense(int i)
    {
        mem[2] = i;
    }
    public void setSize(int i)
    {
        mem[3] = i;
    }
    public void setEnergy(int i)
    {
        mem[4] = i;
    }
    /**
     * Sets the critter's appearance to the given critter appearance file
     * (which must be a zip file zip file with files named 
     * nn.png, ne.png, nw.png, se.png, sw.png, ss.png 
     * as images of the critter facing in different directions)
     * if it is valid
     * @param filename the given critter appearance file 
     */
    public void setAppearance(String filename)
    {
        /*ZipFile zf = file;
            ZipEntry enn = zf.getEntry("nn.png"),
                     ene = zf.getEntry("ne.png"),
                     enw = zf.getEntry("nw.png"),
                     ese = zf.getEntry("se.png"),
                     esw = zf.getEntry("sw.png"),
                     ess = zf.getEntry("ss.png");*/
        appearance = filename;
    }
    public String getAppearance()
    {
        return appearance;
    }
    public Color getColor(){
        return species.getColor();
    }
    public Species getSpecies(){
        return species;
    }
    public void timeStep() {
        if (!acted) {
            _wait();
        }
        acted = false;
        checkDeath();
    }
    
    public void act() {
        amorous = false;
        (recentAction = prog.run(this)).execute(this);
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
        if(!(newPos==null||newPos.mutableContents().rock()||newPos.mutableContents().critter())){
            pos.mutableContents().removeCritter();
            newPos.mutableContents().putCritter(this);
            pos = newPos;
            acted = true;
        }
    }
    
    public void backward() {
        Reference<Tile> newPos = pos.lin(-1,dir);
        if(!(newPos==null||newPos.mutableContents().rock()||newPos.mutableContents().critter())){
            pos.mutableContents().removeCritter();
            newPos.mutableContents().putCritter(this);
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
        if (pos.mutableContents().food() || pos.mutableContents().plant()) {
            int ene = pos.mutableContents().foodValue()
                    +(pos.mutableContents().plant()? Constants.ENERGY_PER_PLANT: 0);
            System.out.println("Ate " + ene + " units of energy");
            pos.mutableContents().removePlant();
            pos.mutableContents().takeFood();
            if((mem[4] += ene) > mem[3] * Constants.ENERGY_PER_SIZE)
                mem[4] = Constants.ENERGY_PER_SIZE * mem[3];
            return;
        } else
            System.out.println("No food there");
        acted = true;
    }
    
    public void attack() {
        mem[4] -= mem[3] * ATTACK_COST;
        Tile ahead = pos.adj(dir).mutableContents();
        if (ahead.critter()) {
            Critter c = ahead.getCritter();
            double damage = BASE_DAMAGE * mem[3]
                    * lgs(DAMAGE_INC * (mem[3] * mem[2] - c.mem[3] * c.mem[1]));
            int perc =(int) (100 * damage) / c.mem[4];
            c.mem[4] -= (int) damage;
            mem[6] *= 1000;
            c.mem[6] *= 1000;
            mem[6] += 300 + perc;
            c.mem[6] += 100 + (dir.ordinal() - c.dir.ordinal() + 6) % 6;
            mem[6] %= 1000000;
            c.mem[6] %= 1000000;
            return;
        }
        acted = true;
    }
    
    public void tag(int t) {
        mem[4] -= mem[3];
        Tile ahead = pos.adj(dir).mutableContents();
        if (ahead.critter()) {
            Critter c = ahead.getCritter();
            c.mem[7] = t;
            c.mem[6] *= 1000;
            c.mem[6] += 200 + (dir.ordinal() - c.dir.ordinal() + 6) % 6;
            c.mem[6] %= 1000000;
        }
        acted = true;
    }
    
    public void grow() {
        mem[4] -= mem[3] * complexity() * GROW_COST;
        mem[3]++;
        acted = true;
    }
    
    public void bud() {
        if(pos==null)
            System.out.println("pos == null");
        Reference<Tile> np = pos.lin(-1, dir);
        if(np == null || np.mutableContents().rock() || np.mutableContents().critter())
            return; //we're in a corner, can't put a critter there.
        int newmem[] = new int[mem.length];
        System.arraycopy(mem, 0, newmem, 0, MIN_MEMORY);
        Critter baby = new Critter(wor, np, prog, newmem, lineage, true);
        baby.mem[3] = 1;
        baby.mem[4] = Constants.INITIAL_ENERGY;
        baby.mem[7] = 0;
        baby.mem[8] = 1;
        np.mutableContents().putCritter(baby);
        mem[4] -= complexity() * Constants.BUD_COST;
        mutateCritter(baby);
        acted = true;
    }
    
    public void mate() {
        Reference<Tile> rt = pos.adj(dir);
        if(rt==null)
            return;
        Tile t = rt.mutableContents();
        if(t.critter() && t.getCritter().amorous) {
            try {
                Critter c = t.getCritter();
                int nrules = ch(this,c).prog.numChildren(), 
                        tr = prog.numChildren(), 
                        cr = c.prog.numChildren();
                Rule r[] = new Rule[nrules];
                for(int i = 0; i < nrules; i++) 
                    r[i] = (i<tr?i<cr?Math.random()>.5?c:this:this:c).prog.rules().get(i);
                int msiz = ch(this,c).mem[0];
                int []bmem = new int[msiz];
                bmem[0] = msiz;
                bmem[1] = ch(this,c).mem[1];
                bmem[2] = ch(this,c).mem[2];
                bmem[3] = 1;
                bmem[4] = Constants.INITIAL_ENERGY;
                bmem[8] = 1;
                Critter cpos = ch(this,c);
                Reference<Tile> np = cpos.pos.lin(-1, cpos.dir);
                if(np==null || np.contents().rock()||np.contents().critter()) np = (cpos==this?c:this).pos.lin(-1, (cpos!=this?this:c).dir);
                Critter baby = new Critter(wor, np, prog, bmem, lineage, true);
                np.contents().putCritter(baby);
                mem[4] -= Constants.MATE_COST * complexity();
                c.mem[4] -= Constants.MATE_COST * c.complexity();
            } catch (RemoteException ex) {
                System.err.println("Failed to connect for mate");
            }
        }
        else amorous = true;
        acted = true;
    }

    public void _tag(int i) {
        Critter c = pos.adj(dir).mutableContents().getCritter();
        if(c != null)
            c.mem[7] = i;
        else
            System.err.println("Tag on non-existant critter");
    } 
    
    private <T >T ch(T a, T b) {
        return (Math.random()>.5?a:b);
    }
    
    private static double lgs(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    private int complexity() {
        return prog.numChildren() * Constants.RULE_COST + (mem[1] + mem[2]) * ABILITY_COST;
    }
    public Program getProgram(){
        return prog;
    }
    public String state() {
        String s =  "\n\tSpecies: " + species.hashCode()
                + "\n\tLineage: "+ this.lineage()
                + "\n\tMemory: " + mem[0]
                + "\n\tDefense: " + mem[1]
                + "\n\tOffense: " + mem[2]
                + "\n\n\tSize: " + mem[3]
                + "\n\tEnergy: " + mem[4]
                + "\n\tRule Counter: " + mem[5]
                + "\n\tEvent Log:" + eventLog()//mem[6]
                + "\n\tTag: " + mem[7]
                + "\n\tPosture: " + mem[8]
                + "\n\n\tLatest action: " + recentAction;
        return s;
    }
    /**
     * Prints the lineage of the critter
     * @return the lineage as a string
     */
    public String lineage() {
        String l = "Generation " + lineage.size();
        if(lineage.size()>1){
            l = l+"\n\t(From earliest to previous)";
            Iterator<Integer> iter = lineage.iterator();
            iter.next();
            while (iter.hasNext()) {
                l = l + "\n\t--->species " + iter.next();
            }
        }

        return l;
    }

    /**
     * Generates the event log of the critter
     *
     * @return the event log as a string
     */
    public String eventLog() {
        String eventLog = "";
        int events = mem[6];
        while (events > 99) {
            int e = events % 1000;
            if (e < 300) {
                eventLog = "\n"+eventLog + "\tThis critter was " + ((e < 200) ? "attacked" : "tagged") + " from direction "+direction(e%100);
            }
            events = events / 1000;
        }
        return eventLog;
    }

    private String direction(int d) {
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

    @Override
    public int getMem(int i) {
        return (i < 0 || i >= mem.length)?0
                : mem[i];
    }

    @Override
    public void setMem(int i, int v) {
        boolean b = (i < 0 || i >= mem.length) || (mem[i] = v)
          >3;
    }

    @Override
    public int ahead(int i) {
        if((i>0?i:-i)>Constants.MAX_SMELL_DISTANCE)
            return 0;
        if(i>0)
            return pos.lin(i, dir).mutableContents().encode();
        else
            return pos.lin(-i, dir).mutableContents().encodeIgnoringCritter();
        
    }

    @Override
    public int nearby(int i) {
        return pos.adj(HexDir.dir(i)).mutableContents().encode();
    }

    public void checkDeath() {
        if (mem[4] <= 0) //if run out of energy then
        {//die
            pos.mutableContents().addFood(Constants.FOOD_PER_SIZE * size());
            pos.mutableContents().removeCritter();
        }
    }
    /*/ //This is the *species's* hashCode
    @Override
    public int hashCode() {
        return species.hashCode();
    }
    /*/
    
    @Override
    public int hashCode() {
        return serial;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return (this.hashCode()== ((Critter)obj).hashCode());
    }

    @Override
    public String toString() {
        /* mem[0]   Memory
                *  mem[1]   Defense
                *  mem[2]   Offense*/
        return "critter with \nState:\n" + state()
               +wor.smell(pos, World.TilePredicate.isFood, 10)+"\nRuleset:\n" + prog;
    }

    public String name() {
        return name;
    }    
    private static Random mutRand = new Random();
    private static void mutateCritter(Critter c) {
        while(mutRand.nextInt(4) == 0) {
            if(mutRand.nextBoolean())
                c.prog = c.prog.mutate();
            else {
                int i = mutRand.nextInt(3);
                c.mem[i] += mutRand.nextBoolean() ? -1 : 1;
                if(i == 0 && c.mem[0] < 9)
                    c.mem[0] = 9;
                else if (c.mem[i] < 1)
                    c.mem[i] = 1;
            }
        }
    }

    @Override
    public void act(Action action) {
        (recentAction = action).execute(this);
    }
    
    public Action recentAction() {
        return recentAction;
    }
}
