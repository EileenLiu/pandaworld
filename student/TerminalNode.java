/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

/**
 *
 * @author haro
 */
public abstract class TerminalNode extends Node {
    @Override
    public final int size() {
        return 1;
    }
    
    @Override
    public final boolean hasChildren() {
        return false;
    }
}
