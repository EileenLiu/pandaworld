package student;

import java.util.Iterator;
import java.util.List;
import static student.PrettyPrint.*;

/**
 * An interface representing a Boolean condition in a critter program.
 *
 */
public class Condition extends UnlimitedArityNode<Conjunction> {
    
    public Condition() {
        super();
    }
    
    public Condition(List<Conjunction> lcc) {
        super(lcc);
    }
    
    public boolean eval() {
        for(Conjunction conj : subNodes)
            if(conj.eval())
                return true;
        return false;
    }

    @Override
    public Node mutate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void prettyPrint(StringBuffer sb) {
        prettyPrint(sb,false);
    }
    
    public void prettyPrint(StringBuffer sb, boolean parenp) {
        parenp = parenp && subNodes.size() > 1;
        if(parenp)
            sb.append('(');
        Iterator<Conjunction> i = subNodes.iterator();
        i.next().prettyPrint(sb,parenp);
        while(i.hasNext()) {
            if(shouldBreak(sb, CONDITION_BREAK))
                sb.append("\nor ");
            else
                sb.append(" or ");
            i.next().prettyPrint(sb,true);
        }
        if(parenp)
            sb.append(')');
    }
}
