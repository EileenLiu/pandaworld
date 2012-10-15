/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.util.Arrays;

/**
 *
 * @author haro
 */
abstract class Update extends Node<Expression<?>> {
    public Update(Expression<?> ind, Expression<?> val) {
        super(ind, val);
    }
    
    public Expression<?> index() {
        return subNodes.get(0);
    }
    
    public Expression<?> newValue() {
        return subNodes.get(1);
    }
    
    public int oldValue() {
        throw new Error("No state yet");
    }
    
    public void apply() {
        throw new Error("No state yet");
    }
}   
