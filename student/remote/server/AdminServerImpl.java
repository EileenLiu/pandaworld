package student.remote.server;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigInteger;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import static student.config.Constants.*;
import student.config.CritterFileParser;
import student.config.WorldFileParser;
import student.grid.Critter;
import student.parse.Action;
import student.remote.login.*;
import student.world.World;
import student.world.World.InvalidWorldAdditionException;

public class AdminServerImpl implements AdminServer, RLogin {
    LoginServer login;
    RLogin loginstub;
    World zaWarudo;
    Timer timer = new Timer();
    TimerTask ttask = null;
    long rate = 60;
    
    Map<String, String> userRequests  = new HashMap<String, String>(),
                        adminRequests = new HashMap<String, String>();
    Set<String> userApprovals  = new HashSet<String>(),
                adminApprovals = new HashSet<String>();
    
    boolean canUpload = false;
    /**
     * @param args
     */
    public static void main(String[] args) {

        // Bind an object of interface type Server to this computer's registry
        try {
            Runtime.getRuntime().exec("rmiregistry");
        } catch (IOException e) {
            System.err.println("RMIRegistry Error " + e.toString());
        }
        try {
            Server obj = new AdminServerImpl();
            Server stub = (Server) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Server", stub);
            System.err.println("Server ready");
        } catch (RemoteException re) {
            System.err.println("Remote Server Exception: " + re.toString());
        } catch (AlreadyBoundException abe) {
            System.err.println("Server Binding Exception: " + abe.toString());
        }

        // A button to terminate the server once it is no longer needed
        JFrame frame = new JFrame();
        JButton unbind = new JButton("Terminate Server");
        unbind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Registry registry = null;
                try {
                    registry = LocateRegistry.getRegistry();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                try {
                    if (registry != null) {
                        registry.unbind("Server");
                    } else {
                        throw new RemoteException();
                    }
                } catch (AccessException e) {
                    e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (NotBoundException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        });
        frame.add(unbind);
        frame.setSize(new Dimension(200, 200));
        frame.setVisible(true);
    }

    public AdminServerImpl() {
        // Register the location of the codebase with your rmi registry
        System.setProperty("java.rmi.server.codebase",
                AdminServerImpl.class.getProtectionDomain().getCodeSource().getLocation().toString());
        try {
            login = new LoginServer();
            RLogin stub = (RLogin)UnicastRemoteObject.exportObject(login);
            System.err.println("Login server ready");
        } catch (RemoteException ex) {
            System.err.println("Couldn't start login server");
        }
        zaWarudo = new World(MAX_ROW, MAX_COLUMN);
    }

    @Override
    public int maxColumn() throws RemoteException {
        return zaWarudo.width();
    }

    @Override
    public int maxRow() throws RemoteException {
        return zaWarudo.height();
    }

    @Override
    public Cell[] getSubsection(int llCol, int llRow, int numCols, int numRows)
            throws RemoteException {
        return null;
    }

    @Override
    public boolean isRunning() throws RemoteException {
        return ttask != null;
    }

    @Override
    public long getSimRate() throws RemoteException {
        return rate;
    }

    @Override
    public int stepsCount() throws RemoteException {
        return zaWarudo.getTimesteps();
    }

    @Override
    public int numCritters() throws RemoteException {
        return zaWarudo.population()[World.CRIT];
    }

    @Override
    public int numPlants() throws RemoteException {
        return zaWarudo.population()[World.PLANT];
    }

    @Override
    public String getCritterProgram(int id) throws RemoteException {
        return zaWarudo.critterForID(id).prog.prettyPrint();
    }

    @Override
    public int[] getCritterMemory(int id) throws RemoteException {
        return zaWarudo.critterForID(id).memory();
    }
    
    @Override
    public RemoteCritter getCritter(int id) throws RemoteException {
        return (RemoteCritter)UnicastRemoteObject.exportObject(zaWarudo.critterForID(id));
    }

    @Override
    public String getCritterCurrentRule(int id) throws RemoteException {
        return null;
    }

    @Override
    public Action getCritterAction(int id) throws RemoteException {
        return zaWarudo.critterForID(id).recentAction();
    }

    @Override
    public String requestUserAcc(String user, String pw) throws RemoteException {
        return requestAcc(user, pw, userRequests, userApprovals)
                ? "SUCCESS: user access granted"
                : "FAILURE: an administrator denied your request";
    }

    @Override
    public String requestAdminAcc(String user, String pw)
            throws RemoteException {
        return requestAcc(user, pw, adminRequests, adminApprovals)
                ? "SUCCESS: admin access granted"
                : "FAILURE: an administrator denied your request";
    }
    
    private boolean requestAcc(String user, String pw, Map<String,String> queue, Set<String> app) {
        synchronized(queue) {
            queue.put(user, pw);
            while(queue.containsKey(user)) {
                try {
                    queue.wait();
                } catch (InterruptedException ex) {
                    System.err.println("Please don't interrupt. It's rude.");
                }
            }
        }
        if(app.contains(user)) {
            app.remove(user);
            return true;
        }
        else return false;
    }

    @Override
    public void loadWorld(byte[] token, String uname, String worldFileContent) throws RemoteException {
        if(login.verifyRequest(uname, token, Permission.ADMIN))
            zaWarudo = WorldFileParser.generateWorld(worldFileContent, MAX_ROW, MAX_COLUMN);
        //else throw new RemoteException("You lack permissions to do that!");
    }

    @Override
    public void simStep(byte[] token, String uname) throws RemoteException {
        if(login.verifyRequest(uname, token, Permission.ADMIN))
            zaWarudo.step();
    }

    @Override
    public void startSim(byte[] token, String uname) throws RemoteException {
        if(login.verifyRequest(uname, token, Permission.ADMIN))
            doStartSim();
    }
    
    private void doStartSim() {
        if(ttask == null) {
            ttask = new TimerTask() {
                    @Override public void run() {
                        zaWarudo.step();
                    }
                };
            timer.schedule(ttask, 0L, this.rate);
        }
    }

    @Override
    public void pauseSim(byte[] token, String uname) throws RemoteException {
        if(login.verifyRequest(uname, token, Permission.ADMIN)) {
            ttask.cancel();
            timer.purge();
            ttask = null;
        }
    }

    @Override
    public void resetSim(byte[] token, String uname) throws RemoteException {
        if(login.verifyRequest(uname, token, Permission.ADMIN))
            zaWarudo.reset();
    }

    @Override
    public void setSimRate(byte[] token, String uname, long rate) throws RemoteException {
        if(login.verifyRequest(uname, token, Permission.ADMIN)) {
            ttask.cancel();
            ttask = null;
            doStartSim();
        }
    }

    @Override
    public void killAll(byte[] token, String uname) throws RemoteException {
        if(login.verifyRequest(uname, token, Permission.ADMIN))
            resetSim(token, uname);
    }

    @Override
    public void kill(byte[] token, String uname, int id) throws RemoteException {
        if(login.verifyRequest(uname, token, Permission.ADMIN))
            zaWarudo.critterForID(id).setEnergy(-1);
    }

    @Override
    public void control(byte[] token, String uname, RemoteCritter critter, Action a) throws RemoteException {
        if(login.verifyRequest(uname, token, Permission.ADMIN))
            critter.act(a);
    }

    @Override
    public boolean uploadsOn(byte[] token, String uname) throws RemoteException {
        boolean t = canUpload;
        setCritterUploads(token, uname, true);
        return t;
    }

    @Override
    public void setCritterUploads(byte[] token, String uname, boolean on) throws RemoteException {
        if(login.verifyRequest(uname, token, Permission.ADMIN))
            canUpload = on;
    }

    @Override
    public boolean downloadsOn(byte[] token, String uname) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setCritterDownloads(byte[] token, String uname, boolean on) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /*/
    @Override
    public String[] listCritterFiles(byte[] token, String uname) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /*/
    @Override
    public String[] getPlayerList(byte[] token, String uname) throws RemoteException {
        if(login.verifyRequest(uname, token, Permission.ADMIN)) {
            Iterator<String> i = login.users();
            LinkedList<String> ll = new LinkedList<String>();
            while(i.hasNext()) {
                String u = i.next();
                if(login.hasPermission(uname, Permission.USER))
                    ll.add(u);
            }
            return ll.toArray(new String[]{});
        }
        else return null;
    }

    @Override
    public String[] getPlayerRequests(byte[] token, String uname) throws RemoteException {
        return login.verifyRequest(uname, token, Permission.ADMIN)
                ? userRequests.keySet().toArray(new String[]{})
                : null;
    }

    @Override
    public void addPlayer(byte[] token, String uname, String name) throws RemoteException {
        if(login.verifyRequest(uname, token, Permission.ADMIN)) {
            synchronized(userRequests) {
                String pwd = userRequests.get(name);
                if(pwd != null) {
                    login.addUser(name, pwd);
                    login.grantPermission(name, Permission.USER);
                    userRequests.remove(name);
                    userApprovals.add(name);
                    userRequests.notifyAll();
                }
            }
        }
    } 

    @Override
    public void removePlayer(byte[] token, String uname, String name) throws RemoteException {
        if(login.verifyRequest(uname, token, Permission.ADMIN))
            login.delUser(name);
    }
    
    @Override
    public void rejectPlayer(byte[] token, String uname, String name) throws RemoteException {
        if(login.verifyRequest(uname, token, Permission.ADMIN)) {
            synchronized(userRequests) {
                String pwd = userRequests.get(name);
                if(pwd != null) {
                    userRequests.remove(name);
                    userRequests.notifyAll();
                }
            }
        }
    }

    @Override
    public String[] getAdminList(byte[] token, String uname) throws RemoteException {
        if(login.verifyRequest(uname, token, Permission.ADMIN)) {
            Iterator<String> i = login.users();
            LinkedList<String> ll = new LinkedList<String>();
            while(i.hasNext()) {
                String u = i.next();
                if(login.hasPermission(uname, Permission.ADMIN))
                    ll.add(u);
            }
            return ll.toArray(new String[]{});
        }
        else return null;
    }

    @Override
    public String[] getAdminRequests(byte[] token, String uname) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addAdmin(byte[] token, String uname, String name) throws RemoteException {
        if(login.verifyRequest(uname, token, Permission.ADMIN)) {
            synchronized(adminRequests) {
                String pwd = userRequests.get(name);
                if(pwd != null) {
                    login.addUser(name, pwd);
                    login.grantPermission(name, Permission.ADMIN, Permission.USER);
                    adminRequests.remove(name);
                    adminApprovals.add(name);
                }
            }
        }
    }

    @Override
    public void rejectAdmin(byte[] token, String uname, String name) throws RemoteException {
        if(login.verifyRequest(uname, token, Permission.ADMIN)) {
            synchronized(userRequests) {
                String pwd = adminRequests.get(name);
                if(pwd != null) {
                    adminRequests.remove(name);
                    adminRequests.notifyAll();
                }
            }
        }
    }

    @Override
    public void removeAdmin(byte[] token, String uname, String name) throws RemoteException {
        if(login.verifyRequest(uname, token, Permission.ADMIN))
            login.delUser(name);
    }

    @Override
    public String uploadCritter(byte[] token, String uname, String critterFileContent) throws RemoteException {
        if(canUpload && login.verifyRequest(uname, token, Permission.USER)) {
            Critter c = CritterFileParser.generateCritter(critterFileContent, zaWarudo, null, 0);
            try {
                zaWarudo.add(c, c.loc());
            } catch (InvalidWorldAdditionException ex) {
                throw new RemoteException("while uploading critter: ", ex);
            }
            return c.name();
        }
        return null;
    }

    @Override
    public PlayerServer getPlayerServer(byte[] token, String uname) throws RemoteException {
        return login.verifyRequest(uname, token, Permission.USER)
                ? this
                : null;
    }

    @Override
    public AdminServer getAdminServer(byte[] token, String uname) throws RemoteException {
        return login.verifyRequest(uname, token, Permission.USER)
                ? this
                : null;
    }

    @Override
    public int[] getSpeciesAttributes(int species_id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getSpeciesProgram(int species_id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int[] getLineage(int species_id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BigInteger startLogin(String uname, BigInteger nonsecret) throws RemoteException {
        return login.startLogin(uname, nonsecret);
    }

    @Override
    public boolean doLogin(String uname, BigInteger pwordhash) throws RemoteException {
        return login.doLogin(uname, pwordhash);
    }

    @Override
    public void logout(String uname) throws RemoteException {
        login.logout(uname);
    }

    @Override
    public boolean hasPermission(String uname, Permission p) throws RemoteException {
        return login.hasPermission(uname, p);
    }

    @Override
    public RLogin getLoginServer(String user) throws RemoteException {
        return loginstub;
    }
}