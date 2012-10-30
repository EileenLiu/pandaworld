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
import javax.swing.JPopupMenu;
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
    
    private JPopupMenu men;
    
    private Action genMenIts[] = new Action[2];
    private Action criMenIts[] = new Action[7];
    
    public MouseInteractionHandler(final World _model, final WorldFrame _view) {
        model = _model;
        view = _view;
        genMenIts[0] = new LocAxn("rock") {
            @Override
            public void act() {
                rclxtar.setContents(new Tile.Rock());
            }
        };
        genMenIts[1] = new LocAxn("derock") {
            @Override
            public void act() {
                rclxtar.setContents(new Tile(false, 0));
            }
        };
        criMenIts[0] = new LocAxn("forward") {
            @Override
            public void act() {
                if(rclxtar.contents().critter())
                    rclxtar.contents().getCritter().forward();
            }
        };
        criMenIts[1] = new LocAxn("backward") {
            @Override
            public void act() {
                if(rclxtar.contents().critter())
                    rclxtar.contents().getCritter().backward();
            }
        };
        criMenIts[2] = new LocAxn("left") {
            @Override
            public void act() {
                if(rclxtar.contents().critter())
                    rclxtar.contents().getCritter().left();
            }
        };
        criMenIts[3] = new LocAxn("right") {
            @Override
            public void act() {
                if(rclxtar.contents().critter())
                    rclxtar.contents().getCritter().right();
            }
        };
        criMenIts[4] = new LocAxn("eat") {
            @Override
            public void act() {
                if(rclxtar.contents().critter())
                    rclxtar.contents().getCritter().eat();
            }
        };
        criMenIts[5] = new LocAxn("attack") {
            @Override
            public void act() {
                if(rclxtar.contents().critter())
                    rclxtar.contents().getCritter().attack();
            }
        };
        criMenIts[6] = new LocAxn("grow") {
            @Override
            public void act() {
                if(rclxtar.contents().critter())
                    rclxtar.contents().getCritter().grow();
            }
        };
        /*criMenIts[0] = new LocAxn("bud") {
            @Override
            public void act() {
                if(rclxtar.contents().critter())
                    rclxtar.contents().getCritter().bud();
            }
        };*/
        /*criMenIts[0] = new LocAxn("mate") {
            @Override
            public void act() {
                if(rclxtar.contents().critter())
                    rclxtar.contents().getCritter().mate();
            }
        };*/
        /*criMenIts[0] = new LocAxn("tag") {
            @Override
            public void act() {
                if(rclxtar.contents().critter())
                    rclxtar.contents().getCritter().tag();
            }
        };*/
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
        men = new JPopupMenu();
        if(rclxtar.contents().critter())
            for(Action a : criMenIts)
                men.add(a);
        for(Action a : genMenIts)
            men.add(a);
        men.setLocation(e.getLocationOnScreen());
        men.setVisible(true);
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
            act();
            men.setVisible(false);
            view.repaint();
        }
        protected abstract void act();
        public LocAxn(String s) {
            super(s);
        }
    }
}
