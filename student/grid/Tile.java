/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.grid;

/**
 *
 * @author haro
 */
public class Tile {
    private boolean plant;
    private int food;
    private Critter critter;
    
    public Tile(boolean _plant, int _food) {
        plant = _plant; food = _food;
    }
    
    public boolean rock() {
        return false;
    }
    
    public boolean food() {
        return food > 0;
    }
    
    public boolean plant() {
        return plant;
    }
    
    public boolean critter() {
        return critter != null;
    }
    
    public int foodValue() {
        return food;
    }
    
    public void putPlant() {
        plant = true;
    }
    
    public void removePlant() {
        plant = false;
    }
    
    public void addFood(int dfood) {
        assert(dfood>0);
        food += dfood;
    }
    
    public void takeFood() {
        food = 0;
    }
    
    public Critter getCritter() {
        return critter;
    }
    
    public void putCritter(Critter _critter) {
        assert(critter==null);
        critter = _critter;
    }

    public void removeCritter() {
        critter = null;
    }

    public void timeStep() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public static class Rock extends Tile {
        public Rock() {
            super(false, 0);
        }
        
        @Override
        public boolean rock() {
            return true;
        }

        @Override
        public void putPlant() {
            throw new RuntimeException("It's a rock");
        }

        @Override
        public void addFood(int dfood) {
            throw new RuntimeException("It's a rock");
        }

        @Override
        public void putCritter(Critter _critter) {
            throw new RuntimeException("It's a rock");
        }
        
        
    }
}