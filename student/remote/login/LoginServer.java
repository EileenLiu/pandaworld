package student.remote.login;

import java.math.BigInteger;
import java.rmi.registry.LocateRegistry;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;




/**
 *
 * @author haro
 */
public class LoginServer extends UnicastRemoteObject implements LLogin, RLogin {
    private Map<String, LoginState> loginTable = new ConcurrentHashMap<String, LoginState>();
    private Map<String, DHM>        inProgress = new ConcurrentHashMap<String, DHM>();
    private Map<String, Password<Permission>>   
                                    users      = new ConcurrentHashMap<String, Password<Permission>>();
    private EnumSet<Permission> defaultPermission;
    
    public LoginServer() throws RemoteException {
        defaultPermission = EnumSet.of(Permission.WORLD);
    }
    
    @Override
    public boolean verifyRequest(String uname, byte []token, Permission p) {
        if("anonymous".equals(uname) && p == Permission.WORLD)
            return true;
        LoginState s = loginTable.get(uname);
        boolean good = s != null && s.check(token);
        good = good && users.get(uname).permissions.contains(p);
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

    public Iterator<String> users() {
        return users.keySet().iterator();
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
        inProgress.remove(uname);
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
        LocateRegistry.getRegistry().rebind(name + "__login",this);
    }

    @Override
    public boolean addUser(String user, String pass) {
        if(users.containsKey(user))
            return false;
        Password<Permission> pword 
                = new Password<Permission>(user, pass, defaultPermission);
        users.put(user, pword);
        System.out.println("Added user "+user);
        return true;
    }

    @Override
    public boolean addUser(String user, byte[] passhash) {
        if(users.containsKey(user))
            return false;
        Password<Permission> pword 
                = new Password<Permission>(user, passhash, defaultPermission);
        users.put(user, pword);
        System.out.println("Added user "+user);
        return true;
    }

    @Override
    public void delUser(String user) {
        loginTable.remove(user);
        inProgress.remove(user);
        users.remove(user);
    }

    @Override
    public EnumSet<Permission> userPermissions(String user) {
        Password p = users.get(user);
        if(p == null)
            return null;
        return p.permissions.clone();
    }

    @Override
    public void grantPermission(String user, Permission... p) {
        Password pass = users.get(user);
        if(pass == null)
            return;
        pass.permissions.addAll(EnumSet.of(p[0], p));
    }

    @Override
    public void revokePermission(String user, Permission... p) {
        Password pass = users.get(user);
        if(pass == null)
            return;
        pass.permissions.removeAll(EnumSet.of(p[0], p));
    }

    @Override
    public boolean hasPermission(String uname, Permission p) throws RemoteException {
        return userPermissions(uname).contains(p);
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

final class Password<P extends Enum<P>> {
    private static final Random rand = new Random();
    private static final int SALT_SIZE = 8;
    private final byte salt[];
    private byte hash[];
    public final EnumSet<P> permissions;        
    
    private Password(EnumSet<P> _permissions) {
        permissions = EnumSet.copyOf(_permissions);
        salt = new byte[SALT_SIZE];
        rand.nextBytes(salt);
    }
    
    public Password(String uname, String pword, EnumSet<P> _permissions) {
        this(_permissions);
        hash = doHash(uname, pword);
    }
    
    public Password(String uname, byte []pword, EnumSet<P> _permissions) {
        this(_permissions);
        hash = SHA256.hash(pword, salt);
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
