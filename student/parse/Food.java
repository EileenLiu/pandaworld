/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.parse;

import student.grid.CritterState;

/**
 *
 * @author haro
 */
public class Food extends Expression<Expression<?>> {

    @Override
    public int eval(CritterState s) {
        return s.food();
    }
    
    @Override
    public StringBuffer toString(StringBuffer sb) {
        return sb.append("food");
    }
    
}
