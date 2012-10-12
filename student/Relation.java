/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

/**
 *
 * @author haro
 */
public class Relation extends Node {
    private BinaryCondition binc = null;
    private Condition cond = null;
    
    public Relation(Condition c) {
        cond = c;
    }
    
    public Relation(BinaryCondition bc) {
        binc = bc;
    }
    
    public boolean eval() {
        return binc != null ? binc.eval() : cond.eval();
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Node mutate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void prettyPrint(StringBuffer sb) {
        if(binc != null)
            binc.prettyPrint(sb);
        else 
            cond.prettyPrint(sb,true);
    }
    
    @Override
    public boolean hasChildren() {
        return true;
    }
}
