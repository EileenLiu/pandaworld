/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.grid;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import student.config.Constants;

/**
 *
 * @author haro
 */
public class Tile extends UnicastRemoteObject implements RTile {
    private boolean plant, rock;
    private int food;
    private Critter critter;
    
    public Tile(boolean _plant, int _food) throws RemoteException {
        plant = _plant; food = _food;
    }
    public Tile(boolean _rock) throws RemoteException {
        this(false, 0);
        rock = _rock;
    }
    protected Tile(Tile t) throws RemoteException {
        this(t.plant, t.food);
        critter = t.critter;
    }
    @Override
    public boolean isEmpty(){
        return !(rock()||food()||plant()||critter());
    }
    @Override
    public boolean rock() {
        return rock;
    }
    
    @Override
    public boolean food() {
        return food > 0;
    }
    
    @Override
    public boolean plant() {
        return plant;
    }
    
    @Override
    public boolean critter() {
        return critter != null;
    }
    
    @Override
    public int foodValue() {
        return food;
    }
    
    public void putPlant() {
        if(rock) throw new IllegalStateException("It's a rock");
        plant = true;
    }
    
    public void removePlant() {
        if(rock) throw new IllegalStateException("It's a rock");
        plant = false;
    }
    
    public void addFood(int dfood) {
        if(rock) throw new IllegalStateException("It's a rock");
        assert(dfood>0);
        food += dfood;
    }
    
    public void takeFood() {
        if(rock) throw new IllegalStateException("It's a rock");
        food = 0;
    }
    
    @Override
    public Critter getCritter() {
        return critter;
    }
    
    public void putCritter(Critter _critter) {
        if(rock) throw new IllegalStateException("It's a rock");
        assert(critter==null);
        critter = _critter;
    }

    public void removeCritter() {
        critter = null;
    }
    
    public int encode() {
        if(critter())
            return critter.read();
        return encodeIgnoringCritter();
    }
    
    public int encodeIgnoringCritter() {        
        if(rock())
            return Constants.ROCK_VALUE;
        if(food() || plant())
            return -foodValue() + (plant()?-Constants.ENERGY_PER_PLANT:0);
        return 0;
    }
}
