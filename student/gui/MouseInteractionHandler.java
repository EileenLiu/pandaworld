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
    private Reference<Tile> lrclxtar = null;
    private RReference<Tile> rrclxtar = null;
    private JPopupMenu men;
    private String msg;
    private Action rock, unrock,
            plant, unplant;
    private int i;
    private Action crit, critMenIts[] = new Action[12];
    private boolean EXIT = false;

    public MouseInteractionHandler(final InteractionHandler _parent) {
        try {
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

            rock = new LocAxn("put rock") {
                @Override
                public void act() {
                    try {
                        lrclxtar.setContents(new Tile(true));
                    } catch (RemoteException ex) {
                        JOptionPane.showMessageDialog(masterController.getView(), "Could not instantiate rock", "Export error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            unrock = new LocAxn("derock") {
                @Override
                public void act() {
                    try {
                        lrclxtar.setContents(new Tile(false, 0));
                    } catch (RemoteException ex) {
                        JOptionPane.showMessageDialog(masterController.getView(), "Could not instantiate tile", "Export error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            plant = new LocAxn("plant") {
                @Override
                protected void act() {
                    lrclxtar.mutableContents().putPlant();
                }
            };
            unplant = new LocAxn("weed-x") {
                @Override
                protected void act() {
                    lrclxtar.mutableContents().removePlant();
                }
            };
            if (!masterController.isRemote()) {
                crit = new LocAxn("add critter") {
                    @Override
                    protected void act() {
                        lrclxtar.mutableContents().putCritter(new Critter(masterController.getRealModel(), lrclxtar, new Program()));
                    }
                };
            } else if (masterController.getLogin().hasPermission(Permission.ADMIN)) {
                crit = new LocAxn("add critter") {
                    @Override
                    protected void act() {
                        try {
                            RemoteCritter rc = masterController.getModel().makeCritter(rrclxtar, new Program());
                            ((AdminServer)masterController.getServer()).putCritter(masterController.getLogin().getToken(), masterController.getLogin().getUser(), rc);
                        } catch (RemoteException ex) {
                            Client.connectionError(masterController.getView());
                        }
                    }  
                };
            } else {
                crit = null;
            }
            critMenIts[0] = new CrLocAxn("forward") {
                @Override
                public void act() {
                    if (lrclxtar.mutableContents().critter()) {
                        Critter rclxtarcri = lrclxtar.mutableContents().getCritter();
                        rclxtarcri.forward();
                        masterController.getView().display().setCurrentLocation(rclxtarcri.loc());
                    }
                }
            };
            critMenIts[1] = new CrLocAxn("backward") {
                @Override
                public void act() {
                    if (lrclxtar.mutableContents().critter()) {
                        Critter rclxtarcri = lrclxtar.mutableContents().getCritter();
                        rclxtarcri.backward();
                        masterController.getView().display().setCurrentLocation(rclxtarcri.loc());
                    }
                }
            };
            critMenIts[2] = new CrLocAxn("left") {
                @Override
                public void act() {
                    if (lrclxtar.mutableContents().critter()) {
                        lrclxtar.mutableContents().getCritter().left();
                    }
                }
            };
            critMenIts[3] = new CrLocAxn("right") {
                @Override
                public void act() {
                    if (lrclxtar.mutableContents().critter()) {
                        lrclxtar.mutableContents().getCritter().right();
                    }
                }
            };
            critMenIts[4] = new CrLocAxn("eat") {
                @Override
                public void act() {
                    if (lrclxtar.mutableContents().critter()) {
                        lrclxtar.mutableContents().getCritter().eat();
                    }
                }
            };
            critMenIts[5] = new CrLocAxn("attack") {
                @Override
                public void act() {
                    if (lrclxtar.mutableContents().critter()) {
                        lrclxtar.mutableContents().getCritter().attack();
                    }
                }
            };
            critMenIts[6] = new CrLocAxn("grow") {
                @Override
                public void act() {
                    if (lrclxtar.mutableContents().critter()) {
                        lrclxtar.mutableContents().getCritter().grow();
                    }
                }
            };
            critMenIts[7] = new CrLocAxn("remove") {
                @Override
                public void act() {
                    if (lrclxtar.mutableContents().critter()) {
                        lrclxtar.mutableContents().removeCritter();
                    }
                }
            };
            critMenIts[8] = new CrLocAxn("bud") {
                @Override
                public void act() {
                    if (lrclxtar.mutableContents().critter()) {
                        lrclxtar.mutableContents().getCritter().bud();
                    }
                }
            };
            critMenIts[9] = new CrLocAxn("mate") {
                @Override
                public void act() {
                    if (lrclxtar.mutableContents().critter()) {
                        lrclxtar.mutableContents().getCritter().mate();
                    }
                }
            };
            critMenIts[10] = new LocAxn("tag") {
                @Override
                public void act() {
                    if (lrclxtar.mutableContents().critter()) {
                        men.setVisible(false);
                        do {
                            try {
                                lrclxtar.mutableContents().getCritter()._tag(i = Integer.parseInt((msg = JOptionPane.showInputDialog(masterController.getView(), "New tag value:", "Tagging ahead critter", JOptionPane.QUESTION_MESSAGE))));
                                lrclxtar.mutableContents().getCritter().recentAction = new Tag(new Constant(i));
                                return;
                            } catch (NumberFormatException nfe) {
                                if (!"".equals(msg)) {
                                    continue;
                                }
                            }
                        } while (false); //just give up...
                    }
                }
            };
            critMenIts[11] = new LocAxn("view program") {
                @Override
                public void act() {
                    if (lrclxtar.mutableContents().critter()) {
                        men.setVisible(false);
                        JOptionPane.showMessageDialog(masterController.getView(),
                                lrclxtar.mutableContents().getCritter().getProgram().toString(), "Critter Program",
                                JOptionPane.PLAIN_MESSAGE);
                    }
                }
            };
        } catch (RemoteException ex) {
            Logger.getLogger(MouseInteractionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        if(masterController.isRemote()) {
            RReference<Tile> at = rlookup(e);
            masterController.getView().display().setCurrentLocation(at);
        } else {
            Reference<Tile> at = llookup(e);
            masterController.getView().display().setCurrentLocation(at);
        }
        masterController.getView().repaint();
    }

    private void rightClick(MouseEvent e) {
        if (men != null) {
            men.setVisible(false);
        }
        if(masterController.isRemote()) {
            try {
                rrclxtar = rlookup(e);
                masterController.getView().display().setCurrentLocation(rrclxtar);
                men = new JPopupMenu();
                men.addKeyListener(this);
                if (rrclxtar.contents().rock()) {
                    men.add(unrock);
                } else if (rrclxtar.contents().critter()) {
                    for (Action a : critMenIts) {
                        men.add(a);
                    }
                } else {
                    if (rrclxtar.contents().plant()) {
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
        } else {
            lrclxtar = llookup(e);
            masterController.getView().display().setCurrentLocation(lrclxtar);
            if (lrclxtar.mutableContents() == null) {
                try {
                    lrclxtar.setContents(new Tile(false, 0));
                } catch (RemoteException ex) {
                    JOptionPane.showMessageDialog(masterController.getView(), "Could not instantiate tile", "Export error", JOptionPane.ERROR_MESSAGE);
                }
            }
            men = new JPopupMenu();
            men.addKeyListener(this);
            if (lrclxtar.mutableContents().rock()) {
                men.add(unrock);
            } else if (lrclxtar.mutableContents().critter()) {
                for (Action a : critMenIts) {
                    men.add(a);
                }
            } else {
                if (lrclxtar.mutableContents().plant()) {
                    men.add(unplant);
                } else {
                    men.add(plant);
                }
                men.add(rock);
                men.add(crit);
            }
            men.setLocation(e.getLocationOnScreen());
            men.setVisible(true);
            masterController.getView().repaint();
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
    
    private Reference<Tile> llookup(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        x -= masterController.getView().worldDisplay.scrollpane.getHorizontalScrollBar().getValue();
        y -= masterController.getView().worldDisplay.scrollpane.getVerticalScrollBar().getValue();
        int ret[] = masterController.getView().display().grid().hexAt(x, y);
        if (ret == null) {           return null;
        }
        int r = ret[0];
        int c = ret[1];
        return masterController.getRealModel().lat(r, c);
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
        public void actionPerformed(ActionEvent e) {
            act();
            men.setVisible(false);
            masterController.getView().repaint();
        }

        protected abstract void act();

        public LocAxn(String s) {
            super(s);
        }
    }

    private abstract class CrLocAxn extends LocAxn {

        private final student.parse.Action a;

        public CrLocAxn(String s) {
            super(s);
            a = new student.parse.Action(s);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Critter c = lrclxtar.mutableContents().getCritter();
            c.recentAction = a;
            super.actionPerformed(e);
            if (c != null) {
                c.checkDeath();
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
