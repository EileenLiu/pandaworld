package student;

import java.util.Iterator;
import java.util.List;
import static student.PrettyPrint.*;

/**
 * A representation of a critter rule.
 */
public class Rule extends Node {

    private Condition condition;
    private List<Update> updates;
    private Action action;
    
    public Rule(Condition c, List<Update> u, Action a) {
        condition = c;
        updates = u;
        action = a;
    }
    
    public Rule(Condition c, List<Update> u) {
        this(c,u,null);
    }

    @Override
    public int size() {
        return 2 
             + (action == null ? 0 : 1)
             + updates.size();
    }

    @Override
    public Node mutate() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void prettyPrint(StringBuffer sb) {
        condition.prettyPrint(sb);
        sb.append(" --> ");
        Iterator<Update> i = updates.iterator();
        i.next().prettyPrint(sb);
        while(i.hasNext()) {
            tab(sb);
            i.next().prettyPrint(sb);
        }
        if(action != null) {
            tab(sb);
            action.prettyPrint(sb);
        }
        sb.append(';');
    }
}
