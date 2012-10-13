package student;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import static student.util.PrettyPrint.*;

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
    
    public abstract boolean eval();
}
