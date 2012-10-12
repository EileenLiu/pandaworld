/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.util.Iterator;
import java.util.LinkedList;
import static student.PrettyPrint.*;


/**
 *
 * @author haro
 */
public class Conjunction extends UnlimitedArityNode<Relation> {
    
    public Conjunction() {
        super();
    }

    public Conjunction(LinkedList<Relation> rels) {
        super(rels);
    }
    
    public boolean eval() {
        for(Relation rel : subNodes)
            if(!rel.eval())
                return false;
        return true;
    }

    @Override
    public Node mutate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void prettyPrint(StringBuffer sb) {
        prettyPrint(sb, false);
    }
    
    public void prettyPrint(StringBuffer sb, boolean parenp) {
        parenp &= subNodes.size() > 1;
        if(parenp)
            sb.append('(');
        Iterator<Relation> i = subNodes.iterator();
        i.next().prettyPrint(sb);
        while(i.hasNext()) {
            if(shouldBreak(sb, CONDITION_BREAK))
                sb.append("\nand ");
            else
                sb.append(" and ");
            i.next().prettyPrint(sb);
        }
        if(parenp)
            sb.append(')');
    }
}