/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.grid;

/**
 *
 * @author haro
 */
public class Food extends Entity {
    public final int energy = 0;

    @Override
    public int read() {
        return -energy;
    }

    @Override
    public void timeStep() {
        ;
    }
}
