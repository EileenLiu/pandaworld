package student;

import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

/**
 * A representation of a critter program.
 *
 */
public class Program extends Node<Rule> {
    
    public Program(List<Rule> l) {
        super(l);
    }
    
    public List<Rule> rules() {
        return Collections.unmodifiableList(subNodes);
    }

    @Override
    public Node mutate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void prettyPrint(StringBuffer sb) {
        for(Rule r : subNodes) {
            r.prettyPrint(sb);
            sb.append("\n\n");
        }
    }
}
