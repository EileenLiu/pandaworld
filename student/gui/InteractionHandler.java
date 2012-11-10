/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import student.world.World;

/**
 *
 * @author Eileen Liu <el544@cornell.edu>
 */
public class InteractionHandler {
    private World model;
    private WorldFrame view;
    public InteractionHandler(final World _model, final WorldFrame _view)
    {
        model = _model;
        view = _view;
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
    public void setModel(World newWorld)
    {
        view.setVisible(false);
        //view.dispose();
        model = newWorld;
        view.loadWorld(newWorld);
        view.setVisible(true);
        view.repaint();

    }
    public WorldFrame getView()
    {
        return view;
    }
}
