package student;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import student.ParserImpl.HistObj;

/**
 * A representation of a critter program.
 *
 */
public class Program extends Node<Rule> {
    
    public static Program parse(LinkedList<HistObj> hist) throws SyntaxError {
        Program p = new Program();
        HistObj self = hist.pop();
        assert(self.rule.equals("Program"));
        //Rule is: Program => Rule Program
        while(hist.size() > 0) {
            Rule r = Rule.parse(hist);
            p.children.add(r);
            hist.pop();
        }
        return p;
    }
    
    private Program() {
        super(new LinkedList<Rule>());
    }
    
    public List<Rule> rules() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public Node mutate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void prettyPrint(StringBuffer sb) {
        for(Rule r : children) {
            r.prettyPrint(sb);
            sb.append("\n\n");
        }
    }
}
