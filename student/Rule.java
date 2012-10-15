package student;

import java.util.Iterator;
import java.util.List;
import static student.util.PrettyPrint.*;

/**
 * A representation of a critter rule.
 */
public class Rule extends Node<Update> {

    private Condition condition;
    private Action action;

    public Rule(Program p, Condition c, List<Update> u, Action a) {
        super(p,u);
        condition = c;
        action = a;
    }

    public Rule(Program p, Condition c, List<Update> u) {
        this(p, c, u, null);
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
        Iterator<Update> i = children.iterator();
        i.next().prettyPrint(sb);
        while (i.hasNext()) {
            tab(sb);
            i.next().prettyPrint(sb);
        }
        if (action != null) {
            tab(sb);
            action.prettyPrint(sb);
        }
        sb.append(';');
    }

    @Override
    public boolean hasChildren() {
        return true;
    }
}
