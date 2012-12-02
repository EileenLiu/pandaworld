package student.remote.client;

import java.awt.Component;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;
import student.remote.login.LoginClient;
import student.remote.login.LoginClient.LoginException;
import student.remote.server.AdminServer;
import student.remote.server.PlayerServer;
import student.remote.server.Server;

public class Client {

    String HOST, SERV;
    Registry registry;
    protected Server stub;
    protected LoginClient login = null;
    /**
     * A constructor that will connect to the given host via RMI
     */
    public Client(String host, String serv) {
        HOST = host;
        SERV = serv;
        connectToServer();
    }

    public Client(String host) {
        this(host,"Server");
    }

    /**
     * Establishes an RMI connection to the server specified by host and
     * retrieves a stub of interface type Server.
     */
    private void connectToServer() {
        try {
            registry = LocateRegistry.getRegistry(HOST);
            stub = (Server) registry.lookup(SERV);
        } catch (RemoteException re) {
            System.err.println("Remote Server Exception: " + re.toString());
            re.printStackTrace();
        } catch (NotBoundException abe) {
            System.err.println("Server Binding Exception: " + abe.toString());
            abe.printStackTrace();
        }
    }

    /**
     * Retrieves an object of interface type AdminServer using the Server stub.
     * userName and password should pass through authentication before the
     * AdminServer is provided
     *
     * @return an object of type AdminServer
     */
    protected AdminServer getAdminServer(String userName, String password) {
        try {
            try {
                login = new LoginClient(HOST, SERV, userName, password);
            } catch (NotBoundException ex) {
                System.err.println("No such server");
                return null;
            } catch (LoginException ex) {
                System.err.println("Incorrect username/password");
                return null;
            }
            return stub.getAdminServer(login.getToken(), password);
        } catch (RemoteException e) {
            System.err.println("Unable to retrieve Admin Server");
            return null;
        }
    }

    /**
     * Retrieves an object of interface type PlayerServer using the Server stub.
     * userName and password should pass through authentication before the
     * PlayerServer is provided
     *
     * @return an object of type PlayerServer
     */
    protected PlayerServer getPlayerServer(String userName, String password) {
        try {
            try {
                login = new LoginClient(HOST, SERV, userName, password);
            } catch (NotBoundException ex) {
                System.err.println("No such server");
                return null;
            } catch (LoginException ex) {
                System.err.println("Incorrect username/password");
                return null;
            }
            return stub.getPlayerServer(login.getToken(), password);
        } catch (RemoteException e) {
            System.err.println("Unable to retrieve Admin Server");
            return null;
        }
    }
    
    public byte []getToken() {
        if(login == null)
            return null;
        return login.getToken();
    }
    
    public static void connectionError(Component p) {
        JOptionPane.showMessageDialog(p, "Connection to server failed", "Connection error", JOptionPane.ERROR_MESSAGE);
    }
}
