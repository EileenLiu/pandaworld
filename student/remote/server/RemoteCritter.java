package student.remote.server;

import java.rmi.Remote;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface RemoteCritter extends Remote {

    /**
     * Instructs this critter to perform the specified action
     */
    public void act(Action action);

    /**
     * An enumeration of all possible actions.
     */
    public static enum Action {

        WAIT,
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT,
        EAT,
        ATTACK,
        GROW,
        BUD,
        MATE;
        /**
         * The list of actions.
         */
        public static final List<Action> VALUES =
                Collections.unmodifiableList(Arrays.asList(values()));
        /**
         * The number of actions.
         */
        public static final int NUM_ACTIONS = VALUES.size();

        @Override
        public String toString() {
            char n[] = name().toCharArray(), d = 'a' - 'A';
            for(int i = 0; i < n.length; n[i++]-=d);
            return new String(n);
        }
    }
}
