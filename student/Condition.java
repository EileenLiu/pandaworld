package student;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import static student.util.PrettyPrint.*;

/**
 * An interface representing a Boolean condition in a critter program.
 *
 */
public abstract class Condition<SubNodeType extends Condition<?>> extends Node<SubNodeType> {
    
    public Condition(List<SubNodeType> subs) {
        super(subs);
    }
    
    public Condition(SubNodeType...subs) {
        this(Arrays.asList(subs));
    }
    
    public abstract boolean eval();
}
