package student;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Represents +, -, *, /, %
public abstract class BinaryOp extends Expression { // need not be abstract
    private Expression left, right;//!!!!!!!!!!
    private BinaryOperator op; //the operation
    
	/**
	 * Creates a new BinaryOp with the given left expression, right expression, and value (the operation)
	 * @param l the given left
	 * @param r the given right
	 * @param v the given value
	 * @throws InvalidBinaryOpException 
	 */
	public BinaryOp(Expression l, Expression r, char v) throws InvalidBinaryOpException {
		if(op == null||l ==null||r==null)
			throw new InvalidBinaryOpException();
		left = l;
		right = r;
		left.setParent(this);
		right.setParent(this);
		op = BinaryOperator.forSym(v);		
		eval();
	}
	/**
	 * Retrieves the BinaryOp's left
	 * @return
	 */
	public Expression getLeft()
	{
		return left;
	}
	/**
	 * Retrieves the BinaryOp's right
	 * @return
	 */
	public Expression getRight()
	{
		return right;
	}
	/**
	 * Evaluates the BinaryOp to update its value
	 */
	public void eval()
	{
		value = op.apply(((Expression)left).value, ((Expression)right).value);
	}
	/**
	 * Retrieves the BinaryOp's BinaryOperator 
	 * @return BinaryOp's BinaryOperator
	 */
	public BinaryOperator getBinaryOp(){
		return op;
	}
	/**
	 * Sets the BinaryCondition's operator to the given BinaryConditionOperator
	 * @param b the given BinaryConditionOperator
	 */
	public void setBinaryOp(BinaryOperator b){
		op=b;
	}
	/** An exception thrown when an invalid BinaryOp object is attempted to be created**/
	private class InvalidBinaryOpException extends Exception
	{
		private static final long serialVersionUID = 1L;
		public InvalidBinaryOpException()
		{
			super("Invalid Binary Operation.");
		}
	}
	/**
	 * An enumeration of all possible binary operators.
	 */
	public enum  BinaryOperator{

		PLUS('+') {
			public int apply(int l, int r) { return l + r; }
		},
		MINUS('-') {
			public int apply(int l, int r) { return l - r; }
		},
		TIMES('*') {
			public int apply(int l, int r) { return l * r; }
		},
		DIVIDE('/') {
			public int apply(int l, int r) { return l / r; }
		},
		MOD('%') {
			public int apply(int l, int r) { return l % r; }
		};
		
		private char sym;

		private BinaryOperator(char s) {
			sym = s;
		}

		/**
		 * Retrieves the Binary Operator's symbol
		 * @return symbol of the Binary Operator
		 */
		public char getSym() {
			return sym;
		}
		
		/**
		 * Applies the operation to the given integers
		 * @param l,r the integers to apply the operation to.
		 * @return the result
		 */
		public abstract int apply(int l, int r);
		
		/**
		 * Returns the binary operator associated with the character,
		 * or null if no such operator
		 * @param s a character representing a binary integer operation
		 * @return the operation
		 */
		public static BinaryOperator forSym(char s) {
			for(BinaryOperator bo : VALUES)
				if(bo.sym == s)
					return bo;
			return null;
		}
		
		/**
		 * The list of operators.
		 */
		public static final List<BinaryOperator> VALUES =
				Collections.unmodifiableList(Arrays.asList(values()));
		/**
		 * The number of operators.
		 */
		public static final int NUM_OPS = VALUES.size();

	}
}
