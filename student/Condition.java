package student;

/**
 * An interface representing a Boolean condition in a critter program.
 *
 */
public abstract class Condition extends Node {

    /**
     * Evaluates the Boolean value of this condition.
     *
     * @param s The state to be evaluated in
     * @return The Boolean value of this condition
     */
    public abstract boolean eval(State s);
}
