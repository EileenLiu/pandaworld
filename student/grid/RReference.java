/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.grid;

import java.rmi.RemoteException;
import student.grid.HexGrid.HexDir;
import student.grid.HexGrid.Reference;

/**
 *
 * @author haro
 */
public interface RReference<E> extends java.rmi.Remote {

    RReference<E> adj(HexDir dir) throws RemoteException;

    int col() throws RemoteException;

    E contents() throws RemoteException;

    RReference<E> lin(int dist, HexDir dir) throws RemoteException;

    int row() throws RemoteException;

    int slice() throws RemoteException;
    
}
