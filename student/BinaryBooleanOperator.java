package student;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A representation of a binary Boolean condition: 'and' or 'or'
 *
 */
public class BinaryBooleanOperator extends Node<Condition<?>> {

    private BinaryOp operator;

    /**
     * Create an AST representation of l op r.
     *
     * @param l
     * @param op
     * @param r
     */
    public BinaryBooleanOperator(Condition<?> l, BinaryOp op, Condition<?> r) {
        super(Arrays.asList(new Condition<?>[]{l, r}));
        //set left and right's parent to this??
        operator = op;
    }

    /**
     * Retrieves the BinaryCondition's operator
     *
     * @return BinaryCondition's operator
     */
    public BinaryOp getConditionOp() {
        return operator;
    }

    /**
     * Sets the BinaryCondition's operator to the given BinaryConditionOperator
     *
     * @param b the given BinaryConditionOperator
     */
    public void setConditionOp(BinaryOp b) {
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

    private enum BinaryOp {

        OR("or") {
            @Override
            public boolean apply(boolean l, boolean r) {
                return l || r;
            }
        },
        AND("and") {
            @Override
            public boolean apply(boolean l, boolean r) {
                return l && r;
            }
        };
        private String sym;

        private BinaryOp(String sym) {
            this.sym = sym;
        }

        public abstract boolean apply(boolean l, boolean r);

        public String sym() {
            return sym;
        }

        public static BinaryOp forSym(String s) {
            if (s.equals("and")) {
                return AND;
            } else if (s.equals("or")) {
                return OR;
            } else {
                return null;
            }
        }
        /**
         * The list of operators.
         */
        public static final List<BinaryOp> VALUES =
                Collections.unmodifiableList(Arrays.asList(values()));
        /**
         * The number of operators.
         */
        public static final int NUM_OPS = VALUES.size();
    }
}
