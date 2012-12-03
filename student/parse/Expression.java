package student.parse;

// A critter program expression that has an integer value.
import java.util.LinkedList;
import java.util.List;
import student.grid.CritterState;
import static student.parse.ParserImpl.*;
import student.parse.ParserImpl.HistObj;
import static student.parse.util.PrettyPrint.*;

public abstract class Expression<SubNodeType extends Expression<?>> extends Node<SubNodeType> {
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
                    res = new BinaryArithmeticOperator(res, fac, tok.token);
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
                    res = new BinaryArithmeticOperator(res, at, tok.token);
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
                hist.pop(); //take care of the token;
                return new Constant(i);
            } else if(self.production[0].equals("food")) {
                return new Food();
            } else {
                return Access.parse(hist);
            }
        }
        throw new Error(self.rule);
    }
    protected Expression() {
        super();
    }

    protected Expression(SubNodeType...subs) {
        super(subs);
    }
    
    public abstract int eval(CritterState s);
    
    @Override
    public StringBuffer toString(StringBuffer sb) {
        return test("Expression", sb);
    }
}
