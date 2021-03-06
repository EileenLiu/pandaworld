package student.parse;

import student.grid.CritterState;
import student.parse.util.Functions;

public class BinaryRelation extends Condition<Expression<?>> {

    private Rel relation;

    /**
     * Creates a new BinaryRelation with the given expressions and relation
     *
     * @param l the given expression on the left of the relation
     * @param r the given expression on the right of the relation
     * @param rltn the given relation
     */
    public BinaryRelation(Expression<?> l, Expression<?> r, Rel rltn) {
        super(l, r);
        relation = rltn;
    }

    public BinaryRelation(Expression<?> l, Expression<?> r, String rltn) {
        this(l, r, Rel.forSym(rltn));
    }

    /**
     * Retrieves the BinaryRelation's relation
     *
     * @return the BinaryRelation's relation
     */
    public Rel getRelation() {
        return relation;
    }

    /**
     * Sets the BinaryRelation's relation to the given Relation
     *
     * @param r the given Relation
     */
    public void setRelation(Rel r) {
        relation = r;
    }

    @Override
    public boolean eval(CritterState s) {
        return relation.apply(children.get(0).eval(s), children.get(1).eval(s));
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
    public int numChildren() {
        return 2;
    }
    
    public Expression left() {
        return children.get(0);
    }
    
    public Expression right() {
        return children.get(1);
    }

    @Override
    public StringBuffer toString(StringBuffer sb) {
        left().toString(sb);
        sb.append(' ');
        sb.append(relation.getSym());
        sb.append(' ');
        right().toString(sb);
        return sb;
    }

    @Override
    public boolean randomize() {
        relation = Functions.randEnum(Rel.class);
       	return true;
    }
    /**
     * An enumeration of all possible binary operators.
     */
    public enum Rel {

        LESSTHAN("<") {
            @Override
            public boolean apply(int l, int r) {
                return l < r;
            }
        },
        LESSTHANEQUALS("<=") {
            @Override
            public boolean apply(int l, int r) {
                return l <= r;
            }
        },
        EQUALS("=") {
            @Override
            public boolean apply(int l, int r) {
                return l == r;
            }
        },
        GREATERTHANEQUALS(">=") {
            @Override
            public boolean apply(int l, int r) {
                return l >= r;
            }
        },
        GREATERTHAN(">") {
            @Override
            public boolean apply(int l, int r) {
                return l > r;
            }
        },
        NOTEQUALS("!=") {
            @Override
            public boolean apply(int l, int r) {
                return l != r;
            }
        };
        private String sym;

        private Rel(String s) {
            sym = s;
        }

        /**
         * Retrieves the Binary Operator's symbol
         *
         * @return symbol of the Binary Operator
         */
        public String getSym() {
            return sym;
        }

        /**
         * Applies the operation to the given integers
         *
         * @param l,r the integers to apply the operation to.
         * @return the result
         */
        public abstract boolean apply(int l, int r);

        /**
         * Returns the relation associated with the string, or null if no such
         * operator
         *
         * @param s a string representing a binary relation
         * @return the operation
         */
        public static Rel forSym(String s) {
            for (Rel r : values()) {
                if (r.sym.equals(s)) {
                    return r;
                }
            }
            return null;
        }
    }
}
