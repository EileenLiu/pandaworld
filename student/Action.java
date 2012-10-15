/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.util.LinkedList;
import student.ParserImpl.HistObj;

/**
 *
 * @author haro
 */
public class Action extends Node {
    
    String type; //REMOVE
    
    public static Action parse(LinkedList<HistObj> hist) throws SyntaxError {
        HistObj self = hist.pop();
        String type = hist.pop().token;
        if("tag".equals(type)) {
            hist.pop().expect("[");
            Expression ind = Expression.parse(hist);
            hist.pop().expect("]");
            return new Tag(ind);
        } else
            return new Action(type);
    }

    private Action(String type) {
        super();
        this.type = type;
    }
    
    protected Action() {
        super();
    }
    
    public boolean execute() {
        return false;
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
