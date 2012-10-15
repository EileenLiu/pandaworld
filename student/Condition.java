package student;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import static student.util.PrettyPrint.*;

/**
 * An abstract class representing a Boolean condition in a critter program.
 */
public abstract class Condition<SubNodeType extends Node<?>> extends Node<SubNodeType> {
    
    public Condition(List<SubNodeType> subs) {
        super(subs);
    }
    
    /**
     * Evaluates the Condition
     * @return the boolean evaluation of the condition
     */
    public abstract boolean eval();

}
