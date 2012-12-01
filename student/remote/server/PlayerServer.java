package student.remote.server;

import java.rmi.RemoteException;
import student.remote.login.Permission;

/**
 * A PlayerServer exposes upload critter permissions to the user.
 */
public interface PlayerServer extends Server {

    /**
     * Upload a critter to the server.
     *
     * @param critterFileContent The string content of the critter definition
     * file.
     * @returns the name of the critter on the server, as it would be used in a
     * world definition
     * @throws RemoteException
     */
    @RemoteVisibility(Permission.USER)
    public String uploadCritter(byte []token, String uname, String critterFileContent) throws RemoteException;
}
