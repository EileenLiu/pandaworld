package student.parse;

import student.grid.CritterState;
import student.parse.util.Functions;
import static student.parse.util.Functions.*;

/**
 * Represents a conjunction or disjunction.
 */
public class BinaryBooleanOperator extends Condition<Condition<?>> {
    private Op op;

    BinaryBooleanOperator(Condition<?> l, String op, Condition<?> r) {
        super(l, r);
        this.op = forName(Op.class, op);
    }
    
    BinaryBooleanOperator(Condition<?> l, Condition<?> r) {
        super(l, r);
    	this.op = Functions.randEnum(Op.class);
    }
    
    /**
     * Returns this condition's left child.
     */
    public Condition<?> left() {
        return children.get(0);
    }

    /**
     * Returns this condition's right child.
     */
    public Condition<?> right() {
        return children.get(1);
    }

    @Override
    public boolean eval(CritterState s) {
        return op.apply(left().eval(s), right().eval(s));
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

    @Override
    public StringBuffer toString(StringBuffer sb) {
        Node l = left();
        if(l instanceof BinaryBooleanOperator && ((BinaryBooleanOperator)l).op != op) {
            sb.append('{');
            l.toString(sb);
            sb.append('}');
        } else
            l.toString(sb);
        sb.append(' ');
        sb.append(en2s(op));
        sb.append(' ');
        Node r = right();
        if(r instanceof BinaryBooleanOperator) {
            if(((BinaryBooleanOperator)r).op != op) {
                sb.append('{');
                r.toString(sb);
                sb.append('}');
            } else {
                sb.append('\n');
                r.toString(sb);
            }
        } else
            r.toString(sb);
        return sb;
    }
    
    private void ppsn(StringBuffer sb, Node n) { 

    }
}