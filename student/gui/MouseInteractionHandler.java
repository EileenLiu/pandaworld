/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import java.awt.event.MouseAdapter;
import student.world.World;

/**
 *
 * @author Panda
 */
public class MouseInteractionHandler {

    public MouseInteractionHandler(final World model, final WorldFrame view) {
        view.worldDisplay.addMouseListener(new MouseAdapter() {
			/*@Override public void mouseEntered(MouseEvent e) {
				view.button.setEnabled(true);
			}
			@Override public void mouseExited(MouseEvent e) {
				Point p = new Point(e.getPoint().x - view.panel.getBounds().x,
							e.getPoint().y - view.panel.getBounds().y);
				if (!view.button.getBounds().contains(p))
					view.button.setEnabled(false);	
			}*/
		});
    }
    
}
