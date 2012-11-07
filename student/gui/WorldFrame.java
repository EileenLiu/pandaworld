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
        worldDisplay = new WorldDisplay(world);
        worldDisplay.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        setJMenuBar(createMenuBar());
        setLayout(new BorderLayout());
        this.add(worldDisplay, BorderLayout.CENTER);
        this.setFullScreen();
    }
    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu filemenu, viewmenu, helpmenu;
        JMenuItem menuItem;
        JCheckBoxMenuItem cbMenuItemGraphs, cbMenuItemData, cbMenuItemTextbox, cbMenuItemSensor;

        //Create the menu bar.
        menuBar = new JMenuBar();
        filemenu = new JMenu("File");
        menuBar.add(filemenu);
        menuItem = new JMenuItem("Settings");
        filemenu.add(menuItem);
        menuItem = new JMenuItem("Exit");
        filemenu.add(menuItem);
        viewmenu = new JMenu("View");
        menuBar.add(viewmenu);
        cbMenuItemGraphs = new JCheckBoxMenuItem("Display Graphs");
        viewmenu.add(cbMenuItemGraphs);
        cbMenuItemData = new JCheckBoxMenuItem("Display Data");
        viewmenu.add(cbMenuItemData);
        cbMenuItemTextbox = new JCheckBoxMenuItem("Laymen's Text Box");
        viewmenu.add(cbMenuItemTextbox);
        cbMenuItemSensor = new JCheckBoxMenuItem("Sensor Display");
        viewmenu.add(cbMenuItemSensor);
        helpmenu = new JMenu("Help");
        menuBar.add(helpmenu);
        return menuBar;
    }

    public WorldDisplay display() {
        return worldDisplay;
    }

    public void repaint() {
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
