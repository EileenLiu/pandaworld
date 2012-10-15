package student;

// A critter program expression that has an integer value.
import java.util.LinkedList;
import java.util.List;
import student.ParserImpl.*;
import static student.ParserImpl.*;

public class Expression<SubNodeType extends Expression<?>> extends Node<SubNodeType> {
    public static Expression parse(LinkedList<HistObj> hist) throws SyntaxError {
        HistObj self = hist.pop();
        if(self.rule.equals("Expression")) {
            Expression res = Expression.parse(hist);
            HistObj next;
            while((next = hist.peek()).rule != null && next.rule.equals("Expression1")) {
                hist.pop();
                if(next.production.length == 0)  //Expression1 => ep
                    return res;
                else { //Expression1 => +- Factor Expression1
                    HistObj tok = hist.pop();
                    Expression fac = Expression.parse(hist); //Factor
                    res = new BinaryArithmeticOperator(res, fac, tok.token.charAt(0));
                }
            }
            return res;
        } else if(self.rule.equals("Factor")) {
            Expression res = Expression.parse(hist);
            HistObj next;
            while((next = hist.peek()).rule != null && next.rule.equals("Factor1")) {
                hist.pop();
                if(next.production.length == 0)  //Factor1 => ep
                    return res;
                else { //Factor1 => */% Atom Factor1
                    HistObj tok = hist.pop();
                    Expression at = Expression.parse(hist); //Atom
                    res = new BinaryArithmeticOperator(res, at, tok.token.charAt(0));
                }
            }
            return res;
        } else if(self.rule.equals("Atom")) {
            if(self.production[0].equals("(")) { //subexpression
                hist.pop().expect("(");
                Expression sub = Expression.parse(hist);
                hist.pop().expect(")");
                return sub;
            } else if(nump(self.production[0])) {
                int i = num(self.production[0]);
                return new Expression(i);
            } else {
                return Access.parse(hist);
            }
        }
        throw new Error(self.rule);
    }
    
    protected int value;

    /**
     * Creates a new Expression of random value between 0 and 999
     */
    public Expression() {
        value = (int) (Math.random() * 999);
    }

    /**
     * Creates a new Expression with the given value
     *
     * @param v the given value
     */
    public Expression(int v) {
        value = v;
    }

    public Expression(List<SubNodeType> subs) {
        super(subs);
    }

    /**
     * Retrieves the Expression's value
     *
     * @return the expression's value
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the Expression's value to the given integer
     *
     * @param v the given integer
     */
    public void setValue(int v) {
        value = v;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public String toString() {
        return "" + value;
    }

    @Override
    public Node mutate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void prettyPrint(StringBuffer sb) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
