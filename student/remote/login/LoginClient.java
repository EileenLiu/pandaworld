package student.remote.login;

import java.math.BigInteger;
import java.rmi.registry.LocateRegistry;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.SecureRandom;

/**
 *
 * @author haro
 */
public class LoginClient {
    private RLogin remote;
    private SecureRandom sync;
    private String uname;
    
    public LoginClient(String hostname, String servname, String uname, String pword) throws RemoteException, NotBoundException, MalformedURLException, LoginException {
        this.uname = uname;
        remote = (RLogin)LocateRegistry.getRegistry(hostname).lookup(servname);
        DHM dhm = new DHM();
        BigInteger gb = remote.startLogin(uname, dhm.ga());
        if(gb == null)
            throw new LoginException("User " + uname + " not found!");
        dhm.gb(gb);
        BigInteger gab = dhm.gab();
        String fmt = String.format("%s:%s", uname, pword);
        BigInteger hash = new BigInteger(SHA256.hash(fmt.getBytes()));
        if(!remote.doLogin(uname, gab.xor(hash)))
            //didn't log in!
            throw new LoginException("Incorrect password!");
        sync = PRNG.getSHA1PRNG();
        sync.setSeed(gab.toByteArray());
    }
    
    public byte []getToken() {
        byte bytes[] = new byte[32];
        sync.nextBytes(bytes);
        return bytes;
    }
    
    public boolean hasPermission(Permission p) throws RemoteException {
        return remote.hasPermission(uname, p);
    }
    
    public static class LoginException extends Exception {
        public LoginException(String msg) {
            super(msg);
        }
    }
    /*/ //This only works if verifyRequest is added to RLogin (bad idea!)
    public static void main(String []argv) throws Exception {
        LoginClient lc = new LoginClient("localhost","test","user","pass");
        boolean good = lc.remote.verifyRequest("user",lc.getToken());
        System.out.println("Sync test "+(good?"":"un")+"successful");
        lc.getToken();
        lc.getToken();
        lc.getToken();
        good = lc.remote.verifyRequest("user",lc.getToken());
        System.out.println("Out-of-sync test "+(good?"":"un")+"successful");
        lc.getToken();
        lc.getToken();
        good = lc.remote.verifyRequest("user",lc.getToken());
        System.out.println("Over-sync test "+(good?"":"un")+"successful");
        lc.getToken();
        lc.getToken();
        lc.getToken();
        lc.getToken();
        lc.getToken();
        lc.getToken();
        good = lc.remote.verifyRequest("user",lc.getToken());
        System.out.println("De-sync test "+(!good?"":"un")+"successful");
    }
    /*/
}
