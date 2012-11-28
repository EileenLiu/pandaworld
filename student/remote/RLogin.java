package student.remote;

/**
 *
 * @author haro
 */
public interface RLogin extends java.rmi.Remote {
    /**
     * Implements the Diffie-Hellman-Merkle secret-sharing algorithm.
     * @param uname the username
     * @param nonsecret the non-secret 256-bit number from DHM
     * @return the other non-secred 256-bit number
     */
    public java.math.BigInteger startLogin(String uname,
                                           java.math.BigInteger nonsecret)
            throws java.rmi.RemoteException;
    
    /**
     * Verifies that the user is logged in
     * @param uname The username
     * @param pwordhash The SHA-256 hash of the string "username:password"
     * @return whether the login was successful
     */
    public boolean doLogin(String uname,
                           java.math.BigInteger pwordhash)
            throws java.rmi.RemoteException;
    
    /**
     * Logs the user out.
     * @param uname The user to log out.
     */
    public void logout(String uname)
            throws java.rmi.RemoteException;
}
