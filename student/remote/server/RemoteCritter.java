package student.remote.server;

import java.rmi.Remote;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface RemoteCritter extends Remote {

    /**
     * Instructs this critter to perform the specified action
     */
    public void act(student.parse.Action action);
}
