package student;

/**
 * A representation of a binary Boolean condition: 'and' or 'or'
 *
 */
public class BinaryCondition extends Condition {
	private BinaryConditionOperator operator;
	/**
	 * Create an AST representation of l op r.
	 * @param l
	 * @param op
	 * @param r
	 */
	public BinaryCondition(Condition l, BinaryConditionOperator op, Condition r) {
		left = l;
		right = r;
		//set left and right's parent to this??
		operator = op;
	}
	/**
	 * Retrieves the BinaryCondition's operator 
	 * @return BinaryCondition's operator 
	 */
	public BinaryConditionOperator getConditionOp(){
		return operator;
	}
	/**
	 * Sets the BinaryCondition's operator to the given BinaryConditionOperator
	 * @param b the given BinaryConditionOperator
	 */
	public void setConditionOp(BinaryConditionOperator b){
		operator=b;
	}
	/*@Override
	public boolean eval(State s) {
		// TODO Auto-generated method stub
		return false;
	}*/

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
	public void remove() {
		// TODO Auto-generated method stub
		
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
}
