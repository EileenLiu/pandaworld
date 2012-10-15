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
    public Update(Rule par, Expression<?> ind, Expression<?> val) {
        super(par, Arrays.asList(ind, val));
    }
    
    public Expression<?> index() {
        return children.get(0);
    }
    
    public Expression<?> newValue() {
        return children.get(1);
    }
    
    public int oldValue() {
        throw new Error("No state yet");
    }
    
    public void apply() {
        throw new Error("No state yet");
    }
	@Override
	public boolean deleteChild(Expression<?> n) {
		return false;
	}
}   
