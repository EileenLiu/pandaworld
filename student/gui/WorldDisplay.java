/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import javax.swing.*;
import student.world.World;

/**
 *
 * @author Panda
 */
public class WorldDisplay extends JPanel{
    public JPanel gridpanel;
    public JPanel statepanel;
    public JTextArea state;
    public World world;

    public WorldDisplay(World world) {
        this.gridpanel = new JPanel();
        this.statepanel = new JPanel();
        this.state = new JTextArea();
    }
    public void updateState(){
        //state.setText(world.currentLocation.getState());
    }
    
}
