package student;

/**
 * A node in the abstract syntax tree of a program.
 */
public abstract class Node {

    /**
     * The number of nodes in this AST, including the current node. This can be
     * helpful for implementing mutate() correctly.
     */
    abstract int size();

    /**
     * Return a version of the same AST with one random mutation in it. May have
     * side effects on the original AST.
     */
    abstract Node mutate();

    /**
     * Appends the program represented by this node prettily to the given
     * StringBuffer.
     *
     * @param sb The StringBuffer to be appended
     */
    abstract void prettyPrint(StringBuffer sb);
    
}
