/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import javax.swing.JFileChooser;
import student.config.WorldFileParser;
import student.config.Constants;
import student.world.World;

/**
 *
 * @author Eileen Liu <el544@cornell.edu>
 */
public class MenuInteractionHandler {
    //private WorldFrame view;
    private InteractionHandler masterController;
    public MenuInteractionHandler(final InteractionHandler _parent){
        masterController = _parent; 
        //view = masterController.getView();
        masterController.getView().importWorld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int returnVal = masterController.getView().fileSelector.showOpenDialog(masterController.getView());
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    String filename = masterController.getView().fileSelector.getSelectedFile().getName();
                    World newImportedWorld = WorldFileParser.generateWorld(filename, Constants.MAX_ROW , Constants.MAX_COLUMN);
                    masterController.setModel(newImportedWorld);
                }
            }
        });
        
        masterController.getView().importCritter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                masterController.getView().fileSelector.showOpenDialog(masterController.getView());
            }
        });
        masterController.getView().importSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                masterController.getView().fileSelector.showOpenDialog(masterController.getView());
            }
        });
    }
}
