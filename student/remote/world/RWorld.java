/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.remote.world;

import java.rmi.RemoteException;
import student.grid.RReference;
import student.grid.RTile;
import student.parse.Program;
import student.remote.server.RemoteCritter;

/**
 *
 * @author haro
 */
public interface RWorld extends java.rmi.Remote {

    RTile tileAt(int r, int c)
            throws RemoteException;

    RemoteCritter critterForID(int id)
            throws RemoteException;

    String getStatus()
            throws RemoteException;

    int getTimesteps()
            throws RemoteException;

    int height()
            throws RemoteException;

    int[] population()
            throws RemoteException;

    RReference<RTile> randomLoc()
            throws RemoteException;

    int width()
            throws RemoteException;
    
    RemoteCritter makeCritter(RReference<RTile> loc, Program p)
            throws RemoteException;
    RemoteCritter makeCritter(RReference<RTile> loc, Program p, int direction)
            throws RemoteException;

    public RReference refAt(int r, int c)
            throws RemoteException;
}
