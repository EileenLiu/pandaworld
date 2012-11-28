package student.remote;

import java.math.BigInteger;

/**
 *
 * @author haro
 */
public interface LLogin extends java.rmi.Remote {
    public boolean verifyRequest(String uname, byte []token)
        throws java.rmi.RemoteException;
}
