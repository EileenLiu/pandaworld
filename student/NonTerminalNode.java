/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

/**
 *
 * @author haro
 */
public abstract class NonTerminalNode<SubType extends Node> extends Node {
    protected final java.util.List<SubType> subNodes;
    
    protected NonTerminalNode() {
        subNodes = new java.util.LinkedList<SubType>();
    }
    
    protected NonTerminalNode(int s) {
        subNodes = new java.util.ArrayList<SubType>(s);
    }
    
    protected NonTerminalNode(java.util.List<SubType> subs) {
        subNodes = subs;
    }
    
    @Override
    public final int size() {
        //(1+ (reduce #'+ (map #'(lambda (n) (size n)) subnodes)))
        int s = 1;
        for(Node n : subNodes)
            s += n.size();
        return s;
    }
    
    @Override
    public final boolean hasChildren() {
        return subNodes.size() > 0;
    }
}
