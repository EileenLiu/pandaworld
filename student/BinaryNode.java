package student;

public abstract class BinaryNode extends Node {
	protected Node right, left, parent;
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
	@Override
	public int size() {
		if(right==null&&left==null)
			return 1;
		else if(left==null&&right!=null)
			return 1+right.size();
		else if (left!=null&&right==null)
			return 1+left.size();
		else
			return 1+right.size()+left.size();
	}
	@Override
	public boolean hasChildren()
	{
		return (left!=null||right!=null);
	}
	@Override
	public Node mutate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void prettyPrint(StringBuffer sb) {
		// TODO Auto-generated method stub

	}


}
