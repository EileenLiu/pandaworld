/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

/**
 *
 * @author haro
 */
public abstract class Action extends Node {
    public abstract boolean execute();

    @Override
    public Node mutate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void prettyPrint(StringBuffer sb) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
