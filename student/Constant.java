/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student;


public class Constant extends Expression<Constant> {
    private final int value;

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
        return this;
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
    public int eval() {
        return value;
    }

    public Constant(int val) {
        super();
        value = val;
    }
    
}
