package student;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import static student.PrettyPrint.*;

/**
 * An interface representing a Boolean condition in a critter program.
 *
 */
public abstract class Condition<SubNodeType extends Condition<?>> extends Node<SubNodeType> {
    
    public Condition(Condition<?> par, List<SubNodeType> subs) {
        super(par,subs);
    }
    
    public Condition(Rule par, List<SubNodeType> subs) {
        super(par,subs);
    }
    
    public Condition(Condition<?> par) {
        this(par, new LinkedList<SubNodeType>());
    }
    
    public Condition(Rule par) {
        this(par, new LinkedList<SubNodeType>());
    }
    
    public Condition(List<SubNodeType> subs) {
        super(null,subs);
    }
    
    public abstract boolean eval();
}
