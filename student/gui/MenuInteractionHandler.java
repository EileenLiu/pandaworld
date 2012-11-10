/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import javax.swing.JFileChooser;
import student.config.WorldFileParser;
import student.grid.Constants;
import student.world.World;

/**
 *
 * @author Eileen Liu <el544@cornell.edu>
 */
public class MenuInteractionHandler {
    private World model;
    private WorldFrame view;

    public MenuInteractionHandler(final World _model, final WorldFrame _view) {
        model = _model;
        view = _view;
        view.importWorld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int returnVal = view.fileSelector.showOpenDialog(view);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    String filename = view.fileSelector.getSelectedFile().getName();
                    WorldFileParser.generateWorld(filename, Constants.MAX_ROW , Constants.MAX_COLUMN);
                }
            }
        });
        
        view.importCritter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view.fileSelector.showOpenDialog(view);
            }
        });
        view.importSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view.fileSelector.showOpenDialog(view);
            }
        });
    }
}
