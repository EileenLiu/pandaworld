package student;

import student.util.Functions;
import static student.util.Functions.*;

public class BinaryBooleanOperator extends Condition<Condition<?>> {
    
    private Op op;

    public BinaryBooleanOperator(Condition<?> l, String op, Condition<?> r) {
        super(l, r);
        this.op = forName(Op.class, op);
    }
    public BinaryBooleanOperator(Condition<?> l, Condition<?> r) {
        super(l, r);
    	this.op = Functions.randEnum(Op.class);
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
        sb.append(" ");
        sb.append(en2s(op));
        sb.append(" ");
        right().prettyPrint(sb);
    }

    @Override
    public boolean deleteChild(Condition<?> n) {
        if (!(n instanceof BinaryBooleanOperator)) {
            return false;
        }
        if (children.get(0).equals(n))//left
        {
            children.set(0, ((Condition<?>) (n.randomChild())));
        } else if (children.get(1).equals(n))//right
        {
            children.set(1, ((Condition<?>) (n.randomChild())));
        } else {
            return false;
        }
        return true;
    }
    @Override
        public boolean randomize()
    {
    	op = Functions.randEnum(Op.class);
    	return true;
    }
    /**
     * An enumeration of all possible binary condition operators.
     */
    public enum Op {

        OR {
            @Override
            public boolean apply(boolean a, boolean b) {
                return a || b;
            }
        },
        AND {
            @Override
            public boolean apply(boolean a, boolean b) {
                return a && b;
            }
        };

        public abstract boolean apply(boolean a, boolean b);
    }

    public Op getConditionOp() {
        return op;
    }

    public void setConditionOp(Op conditionOp) {
        op = conditionOp;
    }
}