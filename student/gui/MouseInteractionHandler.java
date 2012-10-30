/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import student.grid.Entity;
import student.grid.HexGrid.Reference;
import student.grid.Rock;
import student.world.World;

/**
 *
 * @author Panda^H^H^H^H^Hhwh48
 */
public class MouseInteractionHandler extends MouseAdapter {
    private World model;
    private WorldFrame view;
    
    public MouseInteractionHandler(final World _model, final WorldFrame _view) {
        model = _model;
        view = _view;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch(e.getButton()) {
            case 1:
                leftClick(e);
                break;
            case 2:
                rightClick(e);
                break;
        }
    }

    private void leftClick(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        int ret[] = view.display().grid().hexAt(x, y);
        int r = ret[0];
        int c = ret[1];
        Reference<Set<Entity>> at = model.at(r, c);
        if(at.contents() == null)
            at.setContents(new HashSet<Entity>());
        at.contents().add(new Rock());
        System.out.println("put rock at ("+r+","+c+")");    v
    }

    private void rightClick(MouseEvent e) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    
}
