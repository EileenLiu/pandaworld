package student.parse;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import student.config.Constants;
import student.grid.CritterState;
import student.parse.ParserImpl.HistObj;

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
            p.addChild(r);
            hist.pop();
        }
        return p;
    }
    
    private Program() {
        super(new LinkedList<Rule>());
    }
    
    public Program(Rule...s) {
        super(s);
    }
    
    public List<Rule> rules() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public StringBuffer toString(StringBuffer sb) {
        for(Rule r : children)
            sb = r.toString(sb);
        return sb;
    }
    
    @Override
    public int hashCode() {
        //return toString(new StringBuffer).hashCode();
        //TODO: use the hashcode of toString
        return toString().hashCode();
    }
    public Program mutate() {
        return (Program)this.copy().mutate(this);
    }
    
    public Action run(CritterState s) {
        int i = 0; a:
        do for(Rule r : children) if(r.sat(s)) {
            Action a = r.ap(s);
            if(a != null) return   a;
            else          continue a;
        } while (i++ < Constants.MAX_RULES_PER_TURN);
        return new Action("wait");
    }
}
