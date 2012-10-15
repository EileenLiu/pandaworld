package student;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Represents +, -, *, /, %
public class BinaryArithmeticOperator extends Expression<Expression<?>> { // need not be abstract
    //private Expression left, right;

    private BinaryOp op; //the operation

    /**
     * Creates a new BinaryOp with the given left expression, right expression,
     * and value (the operation)
     *
     * @param l the given left
     * @param r the given right
     * @param v the given value
     * @throws InvalidBinaryOpException
     */
    public BinaryArithmeticOperator(Expression<?> l, Expression<?> r, char v) {
        super(Arrays.asList(new Expression<?>[]{l, r}));
        l.setParent(this);
        r.setParent(this);
        op = BinaryOp.forSym(v);
        //eval();
    }

    /**
     * Retrieves the BinaryOp's left
     *
     * @return
     */
    public Expression getLeft() {
        return children.get(0);
    }

    /**
     * Retrieves the BinaryOp's right
     *
     * @return
     */
    public Expression getRight() {
        return children.get(1);
    }

    /**
     * Evaluates the BinaryOp to update its value
     */
    public void eval() {
        value = op.apply(children.get(0).value, children.get(1).value);
    }

    /**
     * Retrieves the BinaryOp's BinaryOperator
     *
     * @return BinaryOp's BinaryOperator
     */
    public BinaryOp getBinaryOp() {
        return op;
    }

    /**
     * Sets the BinaryCondition's operator to the given BinaryConditionOperator
     *
     * @param b the given BinaryConditionOperator
     */
    public void setBinaryOp(BinaryOp b) {
        op = b;
    }

    @Override
    public Node mutate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void prettyPrint(StringBuffer sb) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * An enumeration of all possible binary operators.
     */
    private enum BinaryOp {

        PLUS('+') {
            public int apply(int l, int r) {
                return l + r;
            }
        },
        MINUS('-') {
            public int apply(int l, int r) {
                return l - r;
            }
        },
        TIMES('*') {
            public int apply(int l, int r) {
                return l * r;
            }
        },
        DIVIDE('/') {
            public int apply(int l, int r) {
                return l / r;
            }
        },
        MOD('%') {
            public int apply(int l, int r) {
                return l % r;
            }
        };
        private char sym;

        private BinaryOp(char s) {
            sym = s;
        }

        /**
         * Retrieves the Binary Operator's symbol
         *
         * @return symbol of the Binary Operator
         */
        public char getSym() {
            return sym;
        }

        /**
         * Applies the operation to the given integers
         *
         * @param l,r the integers to apply the operation to.
         * @return the result
         */
        public abstract int apply(int l, int r);

        /**
         * Returns the binary operator associated with the character, or null if
         * no such operator
         *
         * @param s a character representing a binary integer operation
         * @return the operation
         */
        public static BinaryOp forSym(char s) {
            for (BinaryOp bo : VALUES) {
                if (bo.sym == s) {
                    return bo;
                }
            }
            return null;
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