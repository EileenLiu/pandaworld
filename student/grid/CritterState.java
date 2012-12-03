/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.grid;

/**
 *
 * @author haro
 */
public interface CritterState {
    public int getMem(int i);
    public void setMem(int i, int v);
    public int ahead(int i);
    public int nearby(int i);
    public int food();
}
