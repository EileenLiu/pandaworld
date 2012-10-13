package student;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A node in the abstract syntax tree of a program.
 */
public abstract class Node<SubNodeType extends Node<?>> implements Cloneable {

    protected Node parent;
    protected final List<SubNodeType> subNodes;
    
    public Node(Node par, List<SubNodeType> subs) {
        subNodes = subs;
        parent = par;
    }
    
    public Node(Node par) {
        this(par, new LinkedList<SubNodeType>());
    }
    
    public Node(List<SubNodeType> subs) {
        this(null, subs);
    }
    
    public Node() {
        this((Node)null);
    }

    /**
     * The number of nodes in this AST, including the current node. This can be
     * helpful for implementing mutate() correctly.
     */
    public int size() {
        int siz = 1;
        for(SubNodeType n : subNodes)
            siz += n.size();
        return siz;
    }

    /**
     * Return a version of the same AST with one random mutation in it. May have
     * side effects on the original AST.
     */
    public abstract Node mutate();

    /**
     * Appends the program represented by this node prettily to the given
     * StringBuffer.
     *
     * @param sb The StringBuffer to be appended
     */
    public abstract void prettyPrint(StringBuffer sb);

    /**
     * Removes the node
     */
    public void remove() {
        throw new Error("Unimplemented");        
    }

    /**
     * Checks if the node has a parent
     *
     * @return whether the node has a parent
     */
    public boolean hasParent() {
        return parent != null;
    }

    /**
     * Checks if the node has children
     *
     * @return whether the node has children
     */
    public boolean hasChildren() {
        return !subNodes.isEmpty();
    }

    /**
     * Sets the node's parent to the given node
     *
     * @param n the given node
     */
    public void setParent(Node n) {
        parent = n;
    }

    /**
     * Creates a copy of the node and its subtree
     *
     * @return
     */
    public Node copy() {
        try {
            return (Node)this.clone();
        } catch (CloneNotSupportedException ex) {
            throw new Error("Yes, we *do* support .clone()");
        }
    }

    /**
     * Sets the current node to the given node(including its subtree)
     *
     * @param n
     */
    public void set(Node n) {
        throw new Error("Unimplemented");
    }
}
