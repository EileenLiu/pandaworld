package student;

import java.util.Arrays;

/**
 * A representation of a binary Boolean condition: 'and' or 'or'
 *
 */
public class BinaryCondition extends Node<Condition<?>> {

    private BinaryBooleanOperator operator;

    /**
     * Create an AST representation of l op r.
     *
     * @param l
     * @param op
     * @param r
     */
    public BinaryCondition(Condition<?> l, BinaryBooleanOperator op, Condition<?> r) {
        super(Arrays.asList(new Condition<?>[] { l, r }));
        //set left and right's parent to this??
        operator = op;
    }

    /**
     * Retrieves the BinaryCondition's operator
     *
     * @return BinaryCondition's operator
     */
    public BinaryBooleanOperator getConditionOp() {
        return operator;
    }

    /**
     * Sets the BinaryCondition's operator to the given BinaryConditionOperator
     *
     * @param b the given BinaryConditionOperator
     */
    public void setConditionOp(BinaryBooleanOperator b) {
        operator = b;
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

    public boolean eval(/*State s*/) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasChildren() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
