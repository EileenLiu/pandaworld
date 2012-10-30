/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import student.grid.Critter;
import student.grid.HexGrid.Reference;
import student.grid.Tile;
import student.world.World;

/**
 *
 * @author Panda^H^H^H^H^Hhwh48
 */
public class MouseInteractionHandler extends MouseAdapter {
    private World model;
    private WorldFrame view;
    private Reference<Tile> rclxtar = null;
    
    private JPopupMenu genMen = new JPopupMenu("poooooop");
    
    public MouseInteractionHandler(final World _model, final WorldFrame _view) {
        model = _model;
        view = _view;
        genMen.add(new LocAxn("rock") {
            @Override
            public void act(ActionEvent e) {
                rclxtar.setContents(new Tile.Rock());
            }
        });
        genMen.add(new LocAxn("derock") {
            @Override
            public void act(ActionEvent e) {
                rclxtar.setContents(new Tile(false, 0));
            }
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch(e.getButton()) {
            case 1:
                leftClick(e);
                break;
            case 3:
                rightClick(e);
                break;
        }
    }

    private void leftClick(MouseEvent e) {
        Reference<Tile> at = lookup(e);
        at.setContents(new Tile.Rock());
        view.repaint();
    }

    private void rightClick(MouseEvent e) {
        rclxtar = lookup(e);
        if(rclxtar.contents() == null)
            rclxtar.setContents(new Tile(false,0));
        genMen.setLocation(e.getLocationOnScreen());
        genMen.setVisible(true);
    }
    
    private Reference<Tile> lookup(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        int ret[] = view.display().grid().hexAt(x, y);
        int r = ret[0];
        int c = ret[1];
        return model.at(r, c);
    }
    
    private abstract class LocAxn extends AbstractAction {
        @Override
        public final void actionPerformed(ActionEvent e) {
            act(e);
            genMen.setVisible(false);
            view.repaint();
        }
        protected abstract void act(ActionEvent e);
        public LocAxn(String s) {
            super(s);
        }
    }
}
