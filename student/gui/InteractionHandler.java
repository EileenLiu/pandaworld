/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import student.remote.login.LoginClient;
import student.remote.server.AdminServer;
import student.world.World;

/**
 *
 * @author Eileen Liu <el544@cornell.edu>
 */
public class InteractionHandler {
    private World model;
    private WorldFrame view;
    private LoginClient login;
    private AdminServer server;
    private boolean remote;
    public InteractionHandler(final World _model, final WorldFrame _view)
    {
        model = _model;
        view = _view;
        remote = false;
        login = null;
        server = null;
        load();
    }
    public InteractionHandler(final WorldFrame _view, LoginClient lc, AdminServer as)
    {
        model = null;
        view = _view;
        remote = true;
        login = lc;
        server = as;
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
    public World getModel()
    {
        return model;
    }
    public AdminServer getServer()
    {
        return server;
    }
    public LoginClient getLogin()
    {
        return login;
    }
    public void setModel(World newWorld)
    {
        if(remote)
            throw new RuntimeException("Remote");
        view.setVisible(false);
        view.dispose();
        model = newWorld;
        view = new WorldFrame(model);
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
        return remote;
    }
}
