package student.remote;

import java.math.BigInteger;
import java.rmi.registry.LocateRegistry;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


/**
 *
 * @author haro
 */
public class LoginServer implements LLogin, RLogin {
    private Map<String, LoginState> loginTable = new ConcurrentHashMap<String, LoginState>();
    private Map<String, DHM>        inProgress = new ConcurrentHashMap<String, DHM>();
    private Map<String, Password>   users      = new ConcurrentHashMap<String, Password>();
    
    public LoginServer () throws RemoteException {
        super();
    }
    
    @Override
    public boolean verifyRequest(String uname, byte []token) {
        LoginState s = loginTable.get(uname);
        boolean good = s != null && s.check(token);
        System.out.println((good?"S":"Uns")+"uccessful request: "+uname);
        return good;
    }
    
    @Override
    public BigInteger startLogin(String uname, BigInteger nonsecret) throws RemoteException {
        System.out.println("startLogin");
        if(!users.containsKey(uname))
            return null;
        DHM dhm = new DHM();
        dhm.gb(nonsecret);
        inProgress.put(uname, dhm);
	System.out.println("startLogin success");
        return dhm.ga();
    }

    @Override
    public boolean doLogin(String uname, BigInteger pwordhash) throws RemoteException {
        System.out.println("doLogin");
        DHM dhm = inProgress.get(uname);
        if(dhm == null) 
            return false;
        Password target = users.get(uname);
        if(target == null) 
            return false;
        if(!target.check(pwordhash.xor(dhm.gab()))) 
            return false;
        //if we get here they're good.
        System.out.println("doLogin success");
        SecureRandom sync = PRNG.getSHA1PRNG();
        sync.setSeed(dhm.gab().toByteArray());
        LoginState ls = new LoginState(sync);
        loginTable.put(uname, ls);
        return true;
    }
    
    @Override
    public void logout(String uname) throws RemoteException {
        loginTable.remove(uname);
    }
    
    public void register(String name) throws RemoteException, MalformedURLException {
        RLogin stub = (RLogin)UnicastRemoteObject.exportObject(this);
        LocateRegistry.getRegistry().rebind(name,stub);
    }

    public static void main(String []args) throws Exception {
        LoginServer ls = new LoginServer();
        ls.users.put("user", new Password("user", "pass"));
        ls.register("test");
    }
}
final class LoginState {
    public static final int DEPTH = 5;
    
    private SecureRandom rand;
    private byte next[][];
    
    public LoginState(SecureRandom _rand) {
        rand = _rand;
        next = new byte[DEPTH][];
        for(int i = 0; i < DEPTH; i++) {
            randomize(i);
        }
    }
    
    public boolean check(byte []targ) {
        for(int i = 0; i < DEPTH; i++) {
            if(Arrays.equals(targ,next[i])) {
                //forget this and all previous
                System.arraycopy(next, i+1, next, 0, next.length-i-1);
                for(int j = next.length - i; j < DEPTH; j++)
                    randomize(j);
                return true;
            }
        }
        return false;
    }

    private void randomize(int i) {
        next[i] = new byte[32]; //256 bits = 32 bytes
        rand.nextBytes(next[i]);
    }
}

final class Password {
    private static final Random rand = new Random();
    private static final int SALT_SIZE = 8;
    public final byte hash[], salt[];
    
    public Password(String uname, String pword) {
        salt = new byte[SALT_SIZE];
        rand.nextBytes(salt);
        hash = doHash(uname, pword);
    }
    
    private byte []doHash(String uname, String pword) {
        String fmt = String.format("%s:%s", uname, pword);
        byte dig1[];
        dig1 = SHA256.hash(fmt.getBytes());
        return SHA256.hash(dig1, salt);
    }
    
    public boolean check(BigInteger bi) {
        byte tb[] = bi.toByteArray(), 
             dig[] = SHA256.hash(tb, salt);
        return Arrays.equals(dig, hash);
    }
}
