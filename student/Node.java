package student;

/**
 * A node in the abstract syntax tree of a program.
 */
public abstract class Node {
	protected Node right, left;
	protected Object value;
	/**
	 * The number of nodes in this AST, including the current node.
     * This can be helpful for implementing mutate() correctly.
	 */
	public int size()
	{
		if(right==null&&left==null)
			return 1;
		else if(left==null&&right!=null)
			return 1+right.size();
		else if (left!=null&&right==null)
			return 1+left.size();
		else
			return 1+right.size()+left.size();
	}

	/**
	 * Return a version of the same AST with one random mutation in it.
	 * May have side effects on the original AST.
	 */
	public abstract Node mutate();

	/**
	 * Appends the program represented by this node prettily to
	 * the given StringBuffer.
	 * @param sb The StringBuffer to be appended
	 */
	public abstract void prettyPrint(StringBuffer sb);
	public abstract void remove();
	public boolean hasChildren()
	{
		return (left!=null||right!=null);
	}
	
	/**
	 * Swaps the order of the node's children
	 * Precondition: Node has children
	 */
	public void swapChildren()
	{
		Node left = this.left;
		this.left = right;
		this.right = left;
	}
	public abstract Node copy();
	public abstract void set(Node n);
	public abstract Object getValue();
	public abstract void setValue(Object o);
}