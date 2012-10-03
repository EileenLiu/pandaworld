package student;

/**
 * A NumToken is a token containing a number.
 * @author Chinawat
 *
 */
public class NumToken extends Token {

	protected int value;

	public NumToken(int value, int lineNo) {
		super(NUM, lineNo);
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}
}
