/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import java.awt.*;
import javax.swing.*;
import student.world.World;

public class WorldFrame extends JFrame {

    private World world;
    //public JTextArea worldStatusArea; //text display of world status
    public WorldDisplay worldDisplay; //- made up of two JPanels, one is the grid, one is the current attributes

    public WorldFrame(World w) {
        world = w;
        //worldStatusArea = generateWorldStatusArea();
        //JPanel worldStatusPanel = new JPanel();
        //worldStatusPanel.setLayout(new BorderLayout());
        //worldStatusPanel.add(worldStatusArea, BorderLayout.CENTER);
        worldDisplay = new WorldDisplay(world);
        worldDisplay.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        worldDisplay.setBackground(new Color(112, 126, 185));
        this.setBackground(new Color(112, 126, 185));
        //this.setSize(worldDisplay.getWidth(), worldDisplay.getHeight() + worldStatusArea.getHeight());
        setLayout(new BorderLayout());

        //this.add(worldStatusPanel, BorderLayout.NORTH);
        this.add(worldDisplay, BorderLayout.CENTER);
        this.setFullScreen();
        //this.setSize(1000, 1000);
    }

    public JTextArea generateWorldStatusArea() {
        JTextArea tA = new JTextArea();
        tA.setLayout(new BorderLayout());
        tA.setEditable(false);
        tA.setLineWrap(true);
        tA.setOpaque(false);
        tA.setText("World Status\n\n" + world.getStatus());
        return tA;
    }

    public WorldDisplay display() {
        return worldDisplay;
    }

    public void repaint() {
        //String status = "World Status\n\n" + world.getStatus();
        //worldStatusArea.setText(status);
        //worldStatusArea.update();
        worldDisplay.update();//repaint();
        super.repaint();
    }
    /**
     * Sets the WorldFrame to fullscreen.
     * @param	frame	the JFrame to set to fullscreen
     */
    private void setFullScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int taskBarSize = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom;
        this.setSize(screenSize.width, screenSize.height-taskBarSize);
    }
}
