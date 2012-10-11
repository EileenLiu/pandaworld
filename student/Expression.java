package student;

// A critter program expression that has an integer value.
public abstract class Expression implements Node {
	protected int value;
	public Expression() {
		value=0;
	}
	public Expression(int v) {
		value = v;
	}
	@Override
	public int size() {
		return 1;
	}
	
}
