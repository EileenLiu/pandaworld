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
    private WorldFrame view;
    private InteractionHandler masterController;
    public MenuInteractionHandler(final InteractionHandler _parent){
        masterController = _parent; 
        view = masterController.getView();
        view.importWorld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int returnVal = view.fileSelector.showOpenDialog(view);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    String filename = view.fileSelector.getSelectedFile().getName();
                    World newImportedWorld = WorldFileParser.generateWorld(filename, Constants.MAX_ROW , Constants.MAX_COLUMN);
                    masterController.setModel(newImportedWorld);
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
