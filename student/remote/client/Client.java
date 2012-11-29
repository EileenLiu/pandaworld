package student.remote.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import student.remote.server.AdminServer;
import student.remote.server.PlayerServer;
import student.remote.server.Server;

public abstract class Client {

    String HOST, SERV;
    Registry registry;
    private Server stub;

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
            Registry registry = LocateRegistry.getRegistry(HOST);
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
            return stub.getAdminServer(userName, password);
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
            return stub.getPlayerServer(userName, password);
        } catch (RemoteException e) {
            System.err.println("Unable to retrieve Admin Server");
            return null;
        }
    }
}
