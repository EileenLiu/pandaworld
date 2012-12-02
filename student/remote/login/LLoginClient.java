/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.remote.login;

import java.rmi.RemoteException;

/**
 *
 * @author haro
 */
public interface LLoginClient {

    byte[] getToken();

    String getUser();

    boolean hasPermission(Permission p) throws RemoteException;
    
}
