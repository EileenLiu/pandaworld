package student;

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
    public Node mutate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void prettyPrint(StringBuffer sb) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean deleteChild(Expression<?> n) {
        return false;
    }

    @Override
    public int numChildren() {
        return 2;
    }

    @Override
    public boolean eval() {
        throw new UnsupportedOperationException("Not supported yet.");
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
