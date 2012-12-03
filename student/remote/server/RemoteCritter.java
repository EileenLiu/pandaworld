package student.remote.server;

import java.awt.Color;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import student.grid.CritterState;
import student.grid.HexGrid;
import student.grid.RReference;
import student.grid.RTile;
import student.grid.Tile;

public interface RemoteCritter extends Remote {

    /**
     * Instructs this critter to perform the specified action
     */
    public void act(student.parse.Action.Act action)
            throws RemoteException;
    
    public RReference<RTile> loc()
            throws RemoteException;

    public void checkDeath()
            throws RemoteException;

    public String getAppearance()
            throws RemoteException;

    public Color getColor()
            throws RemoteException;

    public double getScaleFactor()
            throws RemoteException;

    public HexGrid.HexDir direction()
            throws RemoteException;

    public String state()
            throws RemoteException;

    public void doTag(int i)
            throws RemoteException;

    public CritterState copyState()
            throws RemoteException;
}
