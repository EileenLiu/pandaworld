package student.remote.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import student.grid.RReference;
import student.grid.Tile;

public interface RemoteCritter extends Remote {

    /**
     * Instructs this critter to perform the specified action
     */
    public void act(student.parse.Action action)
            throws RemoteException;
    
    public RReference<Tile> loc()
            throws RemoteException;

    public void checkDeath()
            throws RemoteException;
}
