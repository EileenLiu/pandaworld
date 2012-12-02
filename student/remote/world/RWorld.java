/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.remote.world;

import java.rmi.RemoteException;
import student.grid.Critter;
import student.grid.HexGrid;
import student.grid.HexGrid.Reference;
import student.grid.Tile;

/**
 *
 * @author haro
 */
public interface RWorld extends java.rmi.Remote {

    Reference<Tile> at(int r, int c)
            throws RemoteException;

    Critter critterForID(int id)
            throws RemoteException;

    String getStatus()
            throws RemoteException;

    int getTimesteps()
            throws RemoteException;

    int height()
            throws RemoteException;

    int[] population()
            throws RemoteException;

    Reference<Tile> randomLoc()
            throws RemoteException;

    int width()
            throws RemoteException;
    
}
