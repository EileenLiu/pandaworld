package student;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import student.ParserImpl.HistObj;
import static student.util.PrettyPrint.*;

/**
 * A representation of a critter rule.
 */
public class Rule extends Node<Update> {

    private Condition condition;
    private Action action = null;

    public Rule(Condition c, List<Update> u, Action a) {
        super(u);
        condition = c;
        action = a;
    }

    public Rule(Condition c, List<Update> u) {
        this(c, u, null);
    }
    
    protected Rule() {
        super(new LinkedList<Update>());
    }
    
    public static Rule parse(LinkedList<HistObj> hist) throws SyntaxError {
        Rule r = new Rule();
        HistObj self = hist.pop();
        //Rule => Condition --> Command ;
        r.condition =  Condition.parse(hist);
        hist.pop().expect("-->");
        while(hist.peek().rule.startsWith("Command") && hist.pop().production.length > 0) {
            if(hist.peek().rule.equals("Update"))
                r.children.add(Update.parse(hist));
            else if(hist.peek().rule.equals("Action")) {
                r.action = Action.parse(hist);
                break;
            }
        }
        hist.pop().expect(";");
        return r;
    }

    @Override
    public int size() {
        return super.size() + 2; //2 extra for the condition and action
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
        int ts = tabWidth(sb);
        boolean first = true; //TODO: FIX THIS TERRIBLE KLUDGE
        for(Update u : children) {
            if(!first) 
                tab(sb, ts);
            else
                first = false;
            u.prettyPrint(sb);
        }
        if (action != null) {
            if(!children.isEmpty())
                tab(sb,ts);
            action.prettyPrint(sb);
        }
        sb.append(';');
    }

    @Override
    public boolean hasChildren() {
        return true;
    }
}
