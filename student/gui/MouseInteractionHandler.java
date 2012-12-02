/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.rmi.server.ServerRef;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import student.grid.Critter;
import student.grid.HexGrid.Reference;
import student.grid.RReference;
import student.grid.Tile;
import student.parse.Constant;
import student.parse.Program;
import student.parse.Tag;
import student.remote.client.Client;
import student.remote.login.Permission;
import student.remote.server.AdminServer;
import student.remote.server.RemoteCritter;

/**
 *
 * @author Panda^H^H^H^H^Hhwh48
 */
public class MouseInteractionHandler extends MouseAdapter implements java.awt.event.KeyListener {

    //private final WorldFrame view;
    private InteractionHandler masterController;
    private RReference<Tile> rclxtar = null;
    private JPopupMenu men;
    private String msg;
    private Action rock, unrock,
            plant, unplant;
    private int i;
    private Action crit, critMenIts[];
    private boolean EXIT = false;

    public MouseInteractionHandler(final InteractionHandler _parent) {

        //final World _model, final WorldFrame _view) {
        masterController = _parent;
//view = masterController.getView();
        masterController.getView().worldDisplay.gridpane.addMouseListener(this);
        masterController.getView().worldDisplay.gridpane.addKeyListener(this);

        masterController.getView().worldDisplay.scrollpane.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                masterController.getView().repaint();
            }
        });
        masterController.getView().worldDisplay.scrollpane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                masterController.getView().repaint();
            }
        });
        masterController.getView().addWindowListener(new ExitHandler());

        boolean admin = masterController.getAdmin() != null;
        final String uname = masterController.getLogin().getUser();

        rock = admin? new LocAxn("put rock") {
            @Override
            public void act() {
                try {
                    masterController.getAdmin().putRock(masterController.getLogin().getToken(), uname, rclxtar);
                } catch (RemoteException ex) {
                    Client.connectionError(masterController.getView());
                }
            }
        } : new DisLocAxn("put rock");

        unrock = admin? new LocAxn("derock") {
            @Override
            public void act() {
                try {
                    masterController.getAdmin().takeRock(masterController.getLogin().getToken(), uname, rclxtar);
                } catch (RemoteException ex) {
                    Client.connectionError(masterController.getView());
                }
            }
        } : new DisLocAxn("derock");

        plant = admin? new LocAxn("plant") {
            @Override
            protected void act() {
                try {
                    masterController.getAdmin().putPlant(masterController.getLogin().getToken(), uname, rclxtar);
                } catch (RemoteException ex) {
                    Client.connectionError(masterController.getView());
                }
            }
        } : new DisLocAxn("plant");

        unplant = admin? new LocAxn("weed-x") {
            @Override
            protected void act() {
                try {
                    masterController.getAdmin().takePlant(masterController.getLogin().getToken(), uname, rclxtar);
                } catch (RemoteException ex) {
                    Client.connectionError(masterController.getView());
                }
            }
        } : new DisLocAxn("weed-x");

        crit = admin? new LocAxn("add critter") {
            @Override
            protected void act() {
                try {
                    masterController.getAdmin().putCritter(masterController.getLogin().getToken(), uname, masterController.getModel().makeCritter(rclxtar, null));
                } catch (RemoteException ex) {
                    Client.connectionError(masterController.getView());
                }
            }
        } : new DisLocAxn("add critter");
        
        critMenIts = admin? new Action[] {
            new CrLocAxn("forward"),
            new CrLocAxn("backward"),
            new CrLocAxn("left"),
            new CrLocAxn("right"),
            new CrLocAxn("eat"),
            new CrLocAxn("attack"),
            new CrLocAxn("grow"),
            new CrLocAxn("remove"),
            new CrLocAxn("bud"),
            new CrLocAxn("mate"),
            new LocAxn("tag") {
                @Override
                public void act() {
                    try {
                        if (rclxtar.contents().critter()) {
                            men.setVisible(false);
                            do try {
                                i = Integer.parseInt((msg = JOptionPane.showInputDialog(masterController.getView(), "New tag value:", "Tagging ahead critter", JOptionPane.QUESTION_MESSAGE)));
                                rclxtar.contents().getCritter().act(new Tag(new Constant(i)));
                                return;
                            } catch (NumberFormatException nfe) {
                                if (!"".equals(msg))
                                    continue;
                            } while (false); //just give up...
                        }
                    } catch (RemoteException ex) {
                        Client.connectionError(masterController.getView());
                    }
                }
            },
            new LocAxn("view program") {
                @Override
                public void act() {
                    try {
                        if (rclxtar.contents().critter()) {
                            men.setVisible(false);
                            JOptionPane.showMessageDialog(masterController.getView(),
                                    masterController.getServer().getCritterProgram(rclxtar.contents().getCritter().hashCode()));
                        }
                    } catch (RemoteException ex) {
                        Client.connectionError(masterController.getView());
                    }
                }
            }}
        : new Action[] {
            new DisLocAxn("cannot control critters")
        };
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (e.getButton()) {
            case 1:
                leftClick(e);
                break;
            case 3:
                rightClick(e);
                break;
        }
    }

    private void leftClick(MouseEvent e) {
        rclxtar = rlookup(e);
        masterController.getView().display().setCurrentLocation(rclxtar);
        masterController.getView().repaint();
    }

    private void rightClick(MouseEvent e) {
        if (men != null) {
            men.setVisible(false);
        }
        try {
            rclxtar = rlookup(e);
            masterController.getView().display().setCurrentLocation(rclxtar);
            men = new JPopupMenu();
            men.addKeyListener(this);
            if (rclxtar.contents().rock()) {
                men.add(unrock);
            } else if (rclxtar.contents().critter()) {
                for (Action a : critMenIts) {
                    men.add(a);
                }
            } else {
                if (rclxtar.contents().plant()) {
                    men.add(unplant);
                } else {
                    men.add(plant);
                }
                men.add(rock);
                men.add(crit);
            }
            men.setLocation(e.getLocationOnScreen());
            men.setVisible(true);
            masterController.getView().repaint();//display().update();//repaint();
        } catch (RemoteException remoteException) {
            Client.connectionError(masterController.getView());
        }
    }

    private RReference<Tile> rlookup(MouseEvent e) {
        try {
            int x = e.getX();
            int y = e.getY();
            x -= masterController.getView().worldDisplay.scrollpane.getHorizontalScrollBar().getValue();
            y -= masterController.getView().worldDisplay.scrollpane.getVerticalScrollBar().getValue();
            int ret[] = masterController.getView().display().grid().hexAt(x, y);
            if (ret == null) {
                return null;
            }
            int r = ret[0];
            int c = ret[1];
            return masterController.getModel().at(r, c);
        } catch (RemoteException remoteException) {
            Client.connectionError(masterController.getView());
            return null;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Kev");
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && men != null) {
            System.out.println("ESC");
            men.setVisible(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyPressed(e);
    }

    private abstract class LocAxn extends AbstractAction {

        @Override
        public final void actionPerformed(ActionEvent e) {
            act();
            men.setVisible(false);
            masterController.getView().repaint();
        }

        protected abstract void act();

        public LocAxn(String s) {
            super(s);
        }
    }
    
    private class DisLocAxn extends LocAxn {
        @Override
        protected void act() {
            throw new RuntimeException("Invalid operation");
        }
        
        public DisLocAxn(String s) {
            super(s);
            setEnabled(false);
        }
    }

    private class CrLocAxn extends LocAxn {

        private final student.parse.Action a;

        public CrLocAxn(String s) {
            super(s);
            a = new student.parse.Action(s);
        }

        @Override
        public void act() {
            try {
                if (rclxtar.contents().critter()) {
                    RemoteCritter c = rclxtar.contents().getCritter();
                    c.act(a);
                    masterController.getView().display().setCurrentLocation(c.loc());
                    c.checkDeath();
                }
            } catch (RemoteException remoteException) {
                Client.connectionError(masterController.getView());
            }
        }
    }

    private class ExitHandler extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            EXIT = true;
        }
    }
}
