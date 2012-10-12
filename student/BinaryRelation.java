package student;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BinaryRelation extends BinaryNode {
	private Relation relation;
	
	/**
	 * Creates a new BinaryRelation with the given expressions and relation
	 * @param l the given expression on the left of the relation
	 * @param r the given expression on the right of the relation
	 * @param rltn the given relation
	 */
	public BinaryRelation(Expression l, Expression r, Relation rltn) {
		left = l;
		right = r;
		left.setParent(this);
		right.setParent(this);
		relation = rltn;
	}
	/**
	 * Retrieves the BinaryRelation's relation 
	 * @return the BinaryRelation's relation
	 */
	public Relation getRelation() {
		return relation;
	}
	/**
	 * Sets the BinaryRelation's relation to the given Relation
	 * @param r the given Relation
	 */
	public void setRelation(Relation r) {
		relation = r;
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
	@Override
	public boolean remove() {
		// TODO Auto-generated method stub
		
	}
    @Override
    public boolean deleteChild(Node n)
    {
    	if(!(n instanceof BinaryOp))
    		return false;
    	
    	if(left.equals(n))
    	{
    		left = ((BinaryOp)n).getRight();
    	}
    	else if(right.equals(n))
    	{
    		
    	}
    	else
    		return false;
    }
	@Override
	public Node copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set(Node n) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * An enumeration of all possible binary operators.
	 */
	public enum  Relation{

		LESSTHAN("<") {
			public boolean apply(int l, int r) { return l < r; }
		},
		LESSTHANEQUALS("<=") {
			public boolean apply(int l, int r) { return l <= r; }
		},
		EQUALS("=") {
			public boolean apply(int l, int r) { return l == r; }
		},
		GREATERTHANEQUALS(">=") {
			public boolean apply(int l, int r) { return l >= r; }
		},
		GREATERTHAN(">") {
			public boolean apply(int l, int r) { return l > r; }
		},
		NOTEQUALS("!="){
			public boolean apply(int l, int r) { return l != r; }
		};
	
		private String sym;

		private Relation(String s) {
			sym = s;
		}

		/**
		 * Retrieves the Binary Operator's symbol
		 * @return symbol of the Binary Operator
		 */
		public String getSym() {
			return sym;
		}
		
		/**
		 * Applies the operation to the given integers
		 * @param l,r the integers to apply the operation to.
		 * @return the result
		 */
		public abstract boolean apply(int l, int r);
		
		/**
		 * Returns the relation associated with the string,
		 * or null if no such operator
		 * @param s a string representing a binary relation
		 * @return the operation
		 */
		public static Relation forSym(String s) {
			for(Relation r : VALUES)
				if(r.sym == s)
					return r;
			return null;
		}
		
		/**
		 * The list of relations.
		 */
		public static final List<Relation> VALUES =
				Collections.unmodifiableList(Arrays.asList(values()));
		/**
		 * The number of relations.
		 */
		public static final int NUM_OPS = VALUES.size();

	}


}
