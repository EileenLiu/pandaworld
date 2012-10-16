package student;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import student.util.Functions;

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
        l.setParent((Node) this); //*I* know it's fine.
        r.setParent((Node) this);
        op = BinaryOp.forSym(v);
        //eval();
    }

    /**
     * Creates a new BinaryArithmeticOperator with the given left and right
     * expressions and a randomly generated BinaryOp
     *
     * @param l the given left
     * @param r the given right
     */
    public BinaryArithmeticOperator(Expression<?> l, Expression<?> r) {
        super(Arrays.asList(new Expression<?>[]{l, r}));
        l.setParent((Node)this);
        r.setParent((Node)this);
        op = Functions.randEnum(BinaryOp.class);
    }

    /**
     * Retrieves the BinaryOp's left
     *
     * @return
     */
    public Expression left() {
        return children.get(0);
    }

    /**
     * Retrieves the BinaryOp's right
     *
     * @return
     */
    public Expression right() {
        return children.get(1);
    }

    /**
     * Evaluates the BinaryOp to update its value
     */
    @Override
    public int eval() {
        return op.apply(children.get(0).eval(), children.get(1).eval());
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
    public boolean deleteChild(Expression<?> n) {
        if (!(n instanceof BinaryArithmeticOperator)) {
            return false;
        }
        if (children.get(0).equals(n))//left
        {
            children.set(0, ((Expression<?>) (n.randomChild())));
        } else if (children.get(1).equals(n))//right
        {
            children.set(1, ((Expression<?>) (n.randomChild())));
        } else {
            return false;
        }
        return true;
    }

    @Override
    public Node mutate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public StringBuffer toString(StringBuffer sb) {
        ppsn(sb,left());
        sb.append(' ');
        sb.append(op.getSym());
        sb.append(' ');
        ppsn(sb,right());
        return sb;
    }
    
    private void ppsn(StringBuffer sb, Node n) { //TODO (if time): take precedence into account
        if(n instanceof BinaryBooleanOperator) {
            sb.append('(');
            n.toString(sb);
            sb.append(')');
        } else
            n.toString(sb);
    }
    
    @Override
    public boolean randomize()
    {
    	op = Functions.randEnum(BinaryOp.class);
    	return true;
    }
    /**
     * An enumeration of all possible binary operators.
     */
    private enum BinaryOp {

        PLUS('+') {
            @Override
            public int apply(int l, int r) {
                return l + r;
            }
        },
        MINUS('-') {
            @Override
            public int apply(int l, int r) {
                return l - r;
            }
        },
        TIMES('*') {
            @Override
            public int apply(int l, int r) {
                return l * r;
            }
        },
        DIVIDE('/') {
            @Override
            public int apply(int l, int r) {
                return l / r;
            }
        },
        MOD('%') {
            @Override
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