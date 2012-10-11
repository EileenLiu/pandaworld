package student;

// Represents +, -, *, /, mod
public abstract class BinaryOp extends Expression { // need not be abstract
    private Expression left, right;
    private char op; //the operation
    // how to keep track of which operator it is?
    
	/**
	 * Creates a new BinaryOp with the given left expression, right expression, and value (the operation)
	 * @param l the given left
	 * @param r the given right
	 * @param v the given value
	 */
	public BinaryOp(Expression l, Expression r, char v) {
		left = l;
		right = r;
		op = v;
		
	}
	public void eval()
	{
		if(op=='+')
			value = left.value + right.value;
		else if(op == '-')
			value = left.value - right.value;
		else if(op == '*')
			value = left.value*right.value;
		else if(op == '/')
			value = left.value*right.value; 
		else if(op == '%')
			value = left.value%right.value;
		else
			throw new InvalidBinaryOpException();
	}
	private class InvalidBinaryOpException extends 
	{
		private InvalidBinaryOpException
	}
}
