package student;

import java.util.LinkedList;
import java.util.List;

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
        this((Node) null);
    }

    /**
     * The number of nodes in this AST, including the current node. This can be
     * helpful for implementing mutate() correctly.
     */
    public int size() {
        int siz = 1;
        for (SubNodeType n : subNodes) {
            siz += n.size();
        }
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
     * Retrieves the node's parent
     *
     * @return the node's parent
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Creates a copy of the node and its subtree
     *
     * @return
     */
    public Node copy() {
        try {
            return (Node) this.clone();
        } catch (CloneNotSupportedException ex) {
            throw new Error("Yes, we *do* support .clone()");
        }
    }

    public Node[] toArray() {
        Node[] arr = (Node[]) buildArray(new LinkedList<Node>()).toArray();
        return arr;
    }

    protected LinkedList<Node> buildArray(LinkedList<Node> list) {
        list.add(this);
        return list;
    }

    /**
     * Sets the current node to the given node(including its subtree)
     *
     * @param n
     */
    public void set(Node n) {
        throw new Error("Unimplemented");
    }

    /**
     * Retrieves the number of children the node has
     *
     * @return number of children the node has
     */
    public int numChildren() {
        return subNodes.size();
    }

    /**
     * Swaps two children of the Node, if there are more than two, the two
     * children are randomly selected Precondition: Node has at least 2 children
     */
    public boolean swapChildren() {
        int nc = numChildren();
        if(nc < 2)
            return false;
        int c1, c2;
        do {
            c1 = (int)(Math.random()*nc);
            c2 = (int)(Math.random()*nc);
        } while(c1 == c2);
        SubNodeType temp = subNodes.get(c1);
        subNodes.set(c1, subNodes.get(c2));
        subNodes.set(c2, temp);
        return true;
    }
    
    public boolean deleteChild() {
        return deleteChild(subNodes.get((int)(numChildren()*Math.random())));
    }

    /**
     * Delete the given child node Precondition: n must be a child of the node
     *
     * @param n the given child node
     */
    public boolean deleteChild(SubNodeType n) {
        return subNodes.remove(n);
    }
}
