/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import java.rmi.RemoteException;
import student.remote.login.LoginClient;
import student.remote.server.AdminServer;
import student.remote.server.Server;
import student.remote.world.RWorld;
import student.world.World;

/**
 *
 * @author Eileen Liu <el544@cornell.edu>
 */
public class InteractionHandler {
    private RWorld rmodel;
    private World wmodel;
    private WorldFrame view;
    private LoginClient login;
    private Server server;
    public InteractionHandler(final RWorld _model, final WorldFrame _view)
    {
        rmodel = _model;
        wmodel = _model instanceof World ? (World)_model : null;
        view = _view;
        login = null;
        server = null;
        load();
    }
    public InteractionHandler(RWorld _model, WorldFrame _view, LoginClient _client, Server _server)
    {
        rmodel = _model;
        wmodel = null;
        view = _view;
        login = _client;
        server = _server;
        load();
    }
    private void load()
    {
        MouseInteractionHandler controller = new MouseInteractionHandler(this);
        ControlPanelInteractionHandler cpih = new ControlPanelInteractionHandler(this);
        MenuInteractionHandler mih = new MenuInteractionHandler(this);
        view.setVisible(true);
        view.repaint();
        view.setDefaultCloseOperation(WorldFrame.EXIT_ON_CLOSE);
    }
    public RWorld getModel()
    {
        return rmodel;
    }
    public World getRealModel() {
        return wmodel;
    }
    public Server getServer()
    {
        return server;
    }
    public LoginClient getLogin()
    {
        return login;
    }
    public void setModel(World newWorld) throws RemoteException
    {
        if(isRemote())
            throw new RuntimeException("Remote");
        view.setVisible(false);
        view.dispose();
        rmodel = newWorld;
        view = new WorldFrame(rmodel);
        load();
        //view.loadWorld(newWorld);
        //view.setVisible(true);
        view.repaint();
    }
    public WorldFrame getView()
    {
        return view;
    }

    boolean isRemote() {
        return wmodel != null;
    }
}
