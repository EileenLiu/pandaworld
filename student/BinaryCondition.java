package student;

/**
 * A representation of a binary Boolean condition: 'and' or 'or'
 *
 */
public class BinaryCondition implements Condition {
	private Condition left, right;
	/**
	 * Create an AST representation of l op r.
	 * @param l
	 * @param op
	 * @param r
	 */
	public BinaryCondition(Condition l, BinaryConditionOperator op, Condition r) {
		left = l;
		right = r;
	}

	/*@Override
	public boolean eval(State s) {
		// TODO Auto-generated method stub
		return false;
	}*/

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 1 + left.size() + right.size();
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
