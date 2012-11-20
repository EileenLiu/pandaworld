/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.parse;

import student.grid.CritterState;

public class Constant extends Expression<Constant> {
    private int value;

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean hasChildren() {
        return false;
    }

    @Override
    public Node<?> copy() {
        return new Constant(value);
    }

    @Override
    public int numChildren() {
        return 0;
    }

    @Override
    public boolean deleteChild() {
        return false;
    }

    @Override
    public boolean deleteChild(Constant n) {
        return false;
    }

    @Override
    public Node<?> randomChild() {
        return null;
    }
    
    @Override
    public int eval(CritterState s) {
        return value;
    }
    
    @Override
    public StringBuffer toString(StringBuffer sb) {
        sb.append(value);
        return sb;
    }

    public Constant(int val) {
        super();
        value = val;
    }
    
    public Constant() {
    	value = (int) (Math.random() * 999);
    }
    
}
