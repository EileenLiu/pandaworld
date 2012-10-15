package student;

public class BinaryBooleanOperator extends Condition<Condition<?>> {
    private Op op;
    
    public BinaryBooleanOperator(Condition<?> l, String op, Condition<?> r) {
        super(l, r);
        this.op = Op.forSym(op);
    }

    public Condition<?> left() {
        return children.get(0);
    }
    
    public Condition<?> right() {
        return children.get(1);
    }
    
    @Override
    public boolean eval() { //I like Lisp.
        return op.apply(left().eval(), right().eval());
    }

    @Override
    public Node mutate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void prettyPrint(StringBuffer sb) {
        left().prettyPrint(sb);
        sb.append(" " + op.sym() + " ");
        right().prettyPrint(sb);
    }
	@Override
	public boolean deleteChild(Condition<?> n) {
		if(!(n instanceof BinaryBooleanOperator))
			return false;
		if(children.get(0).equals(n))//left
		{
			children.set(0, ((Condition<?>)(n.randomChild())));
		}
		else if(children.get(1).equals(n))//right
		{
			children.set(1, ((Condition<?>)(n.randomChild())));
		}
		else
			return false;
		return true;
    }

    /**
     * An enumeration of all possible binary condition operators.
     */
    public enum Op {
        OR("or") {
            @Override
            public boolean apply(boolean a, boolean b) { 
                return a || b;
            }
        },
        AND("and") {
            @Override
            public boolean apply(boolean a, boolean b) {
                return a && b;
            }
        };
        
        private String sym;
        
        private Op(String _sym) {
            sym = _sym;
        }
        
        public String sym() {
            return sym;
        }
        
        public abstract boolean apply(boolean a, boolean b);
        
        public static Op forSym(String sym) {
            if("or".equals(sym))
                return OR;
            if("and".equals(sym))
                return AND;
            return null;
        }
    }
}