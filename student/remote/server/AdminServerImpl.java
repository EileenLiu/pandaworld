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
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import static student.config.Constants.*;
import student.remote.login.*;
import student.world.World;

public class AdminServerImpl implements AdminServer, RLogin {
    LoginServer login;
    RLogin loginstub;
    World zaWarudo;
    Timer timer = new Timer();
    TimerTask ttask = null;
    long rate = 60;
    
    Map<String, String> userRequests  = new HashMap<String, String>(),
                        adminRequests = new HashMap<String, String>();
    
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int[] getCritterMemory(int id) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getCritterCurrentRule(int id) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Action getCritterAction(int id) throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String requestUserAcc(String user, String pw) throws RemoteException {
        return null;
    }

    @Override
    public String requestAdminAcc(String user, String pw)
            throws RemoteException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void loadWorld(byte[] token, String worldFileContent) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void simStep(byte[] token) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void startSim(byte[] token) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void pauseSim(byte[] token) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void resetSim(byte[] token) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setSimRate(byte[] token, long rate) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void killAll(byte[] token) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void kill(byte[] token, int id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void control(byte[] token, RemoteCritter critter, Action a) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean uploadsOn(byte[] token) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setCritterUploads(byte[] token, boolean on) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean downloadsOn(byte[] token) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setCritterDownloads(byte[] token, boolean on) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String[] listCritterFiles(byte[] token) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String[] getPlayerList(byte[] token) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String[] getPlayerRequests(byte[] token) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addPlayer(byte[] token, String name) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void rejectPlayer(byte[] token, String name) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removePlayer(byte[] token, String name) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String[] getAdminList(byte[] token) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String[] getAdminRequests(byte[] token) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addAdmin(byte[] token, String name) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void rejectAdmin(byte[] token, String name) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeAdmin(byte[] token, String name) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String uploadCritter(byte[] token, String critterFileContent) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PlayerServer getPlayerServer(byte[] token, String user) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AdminServer getAdminServer(byte[] token, String user) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean doLogin(String uname, BigInteger pwordhash) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void logout(String uname) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasPermission(String uname, Permission p) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RLogin getLoginServer(String user) throws RemoteException {
        return loginstub;
    }
}