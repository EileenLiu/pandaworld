/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import student.remote.client.Client;
import student.remote.client.PlayerClient;
import student.remote.login.LoginClient;
import student.remote.login.LoginClient.LoginException;
import student.remote.login.Permission;
import student.remote.server.AdminServer;
import student.remote.server.PlayerServer;
import student.remote.server.Server;
import student.remote.world.RWorld;
import student.world.World;

/**
 *
 * @author Eileen Liu <el544@cornell.edu>
 */
public class InteractionHandler {
    private RWorld rmodel;
    //private World wmodel;
    private WorldFrame view;
    private LoginClient login;
    private Server server;
    private PlayerServer player;
    private AdminServer admin;
    public InteractionHandler() throws RemoteException {
        try {
            view = null;
            LoginInteractionHandler lih = new LoginInteractionHandler(this);
            login = new LoginClient(lih.hostname, lih.servername, lih.username, lih.password);
            Registry reg = LocateRegistry.getRegistry(lih.hostname);
            server = (Server) reg.lookup(lih.servername);
            view = new WorldFrame(this);
            player = login.hasPermission(Permission.USER)  ? server.getPlayerServer(login.getToken(), lih.username) : null;
            admin  = login.hasPermission(Permission.ADMIN) ? server.getAdminServer(login.getToken(), lih.username) : null;
            load();
        } catch (NotBoundException ex) {
            JOptionPane.showMessageDialog(view,
                                          "Cannot find server!",
                                          "NotBoundException", 
                                          JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (LoginException ex) {
            JOptionPane.showMessageDialog(view,
                                          "Login failed",
                                          "LoginException",
                                          JOptionPane.ERROR_MESSAGE);
            System.exit(2);
        }
    }
    private void load()
    {
        MouseInteractionHandler controller = new MouseInteractionHandler(this);
        ControlPanelInteractionHandler cpih = new ControlPanelInteractionHandler(this);
        MenuInteractionHandler mih = new MenuInteractionHandler(this);
        LoginInteractionHandler lih = new LoginInteractionHandler(this);
        view.setVisible(true);
        view.repaint();
        view.setDefaultCloseOperation(WorldFrame.EXIT_ON_CLOSE);
    }
    public RWorld getModel()
    {
        return rmodel;
    }
    //public World getRealModel() {
      //  return wmodel;
    //}
    public Server getServer()
    {
        return server;
    }
    public PlayerServer getPlayer() {
        return player;
    }
    public AdminServer getAdmin() {
        return admin;
    }
    public LoginClient getLogin()
    {
        return login;
    }
    /*public void setModel(World newWorld) throws RemoteException
    {
        if(admin == null)
            throw new RuntimeException("Insufficient Permissions"); //TODO: make nicer
        view.setVisible(false);
        view.dispose();
        rmodel = newWorld;
        admin.add
        view = new WorldFrame(this);
        load();
        //view.loadWorld(newWorld);
        //view.setVisible(true);
        view.repaint();
    }*/
    public WorldFrame getView()
    {
        return view;
    }

    //boolean isRemote() {
      //  return wmodel == null;
    //}
}
