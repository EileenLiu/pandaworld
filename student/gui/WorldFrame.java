/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import javax.swing.*;
import student.world.World;

public class WorldFrame extends JFrame {

    private World world;
    public JTextArea worldStatusArea; //text display of world status
    public WorldDisplay worldDisplay; //- made up of two JPanels, one is the grid, one is the current attributes
    public WorldFrame(World w) {
        world = w;
        worldStatusArea = new JTextArea("World Status/n"+"");
        worldDisplay = new WorldDisplay(world);
        //this.setSize(worldDisplay.getWidth(), worldDisplay.getHeight()+worldStatusArea.getHeight());
        this.add(worldDisplay);

        this.setSize(1000,1000);
    }

    public WorldDisplay display() {
        return worldDisplay;
    }
    
    public void repaint() {
        String status = world.getStatus();
        worldStatusArea.setText(status);
        worldStatusArea.repaint();
        worldDisplay.repaint();
        super.repaint();
    }
}