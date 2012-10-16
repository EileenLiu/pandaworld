/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.util.Arrays;
import java.util.LinkedList;
import student.ParserImpl.HistObj;
import static student.util.PrettyPrint.*;

/**
 *
 * @author haro
 */
public class Update extends Node<Expression<?>> {

    public static Update parse(LinkedList<HistObj> hist) throws SyntaxError {
        HistObj self = hist.pop();
        hist.pop().expect("mem");
        hist.pop().expect("[");
        Expression ind = Expression.parse(hist);
        hist.pop().expect("]");
        hist.pop().expect(":=");
        Expression val = Expression.parse(hist);
        return new Update(ind, val);
    }
    public Update(Expression<?> ind, Expression<?> val) {
        super(ind, val);
    }
    
    public Expression<?> index() {
        return children.get(0);
    }
    
    public Expression<?> newValue() {
        return children.get(1);
    }
    
    public int oldValue() {
        throw new Error("No state yet");
    }
    
    public void apply() {
        throw new Error("No state yet");
    }

    @Override
    public boolean deleteChild(Expression<?> n) {
            return false;
    }

    @Override
    public Node mutate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public StringBuffer toString(StringBuffer sb) {
        sb.append("mem[");
        index().prettyPrint(sb);
        sb.append("] := ");
        return newValue().toString(sb);
    }
}   
