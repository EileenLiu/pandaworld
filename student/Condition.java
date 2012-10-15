package student;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import student.ParserImpl.HistObj;
import static student.util.PrettyPrint.*;

/**
 * An interface representing a Boolean condition in a critter program.
 *
 */
public abstract class Condition<SubNodeType extends Node<?>> extends Node<SubNodeType> {

    public static Condition parse(LinkedList<HistObj> hist) throws SyntaxError {
        HistObj self = hist.pop();
        if(self.rule.equals("Condition")) {
            Condition res = Condition.parse(hist);
            HistObj next;
            while((next = hist.peek()).rule.equals("Condition1")) {
                hist.pop();
                if(next.production.length == 0)  //Condition1 => ep
                    return res;
                else { //Condition1 => or Conjunction Condition1
                    HistObj tok = hist.pop();
                    tok.expect("or"); //or
                    Condition cnj = Condition.parse(hist); //Conjunction
                    res = new BinaryBooleanOperator(res, "or", cnj);
                }
            }
            return res;
        } else if(self.rule.equals("Conjunction")) {
            Condition res = Condition.parse(hist);
            HistObj next;
            while((next = hist.peek()).rule.equals("Conjunction1")) {
                hist.pop();
                if(next.production.length == 0)  //Conjunction1 => ep
                    return res;
                else { //Conjunction1 => and Relation Conjunction1
                    HistObj tok = hist.pop();
                    tok.expect("and"); //and
                    Condition rel = Condition.parse(hist); //Relation
                    res = new BinaryBooleanOperator(res, "and", rel);
                }
            }
            return res;
        } else if(self.rule.equals("Relation")) {
            if(self.production[0].equals("{")) { //subcondition
                hist.pop().expect("{");
                Condition sub = Condition.parse(hist);
                hist.pop().expect("}");
                return sub;
            } else if(self.production[0].equals("Expression")) {
                Expression l = Expression.parse(hist);
                hist.pop(); //Rel
                String rel = hist.pop().token;
                assert(rel != null);
                Expression r = Expression.parse(hist);
                return new BinaryRelation(l, r, rel);
            }
        }
        if(false) throw new Error("unreachable"+self.rule);
        return null;
    }
    
    public Condition(List<SubNodeType> subs) {
        super(subs);
    }
    
    public Condition(SubNodeType...subs) {
        this(Arrays.asList(subs));
    }
    
    public abstract boolean eval();
}
