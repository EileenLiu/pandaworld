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
    public WorldDisplay worldDisplay; //- made up of two JPanels, one is the grid, one is the current attributes

    public WorldFrame(World w) {
        setLayout(new BorderLayout());
        loadWorld(w);
        initMenubar();
        this.setFullScreen();
    }

    public final void loadWorld(World w) {
        world = w;
        /*if(worldDisplay!=null)
        {
            this.remove(worldDisplay);
            System.out.println("Attempted remove of worldDisplay");
        }*/
        worldDisplay = new WorldDisplay(world);
        worldDisplay.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));
        this.add(worldDisplay, BorderLayout.CENTER);
        System.out.println("Loaded world");
    }

    private void initMenubar() {

        MENUBAR = new javax.swing.JMenuBar();
        importMenu = new javax.swing.JMenu();
        importWorld = new javax.swing.JMenuItem();
        importCritter = new javax.swing.JMenuItem();
        importSettings = new javax.swing.JMenuItem();
        createMenu = new javax.swing.JMenu();
        createWorld = new javax.swing.JMenuItem();
        createCritter = new javax.swing.JMenuItem();
        createPlant = new javax.swing.JMenuItem();
        createFood = new javax.swing.JMenuItem();
        createRock = new javax.swing.JMenuItem();
        viewMenu = new javax.swing.JMenu();
        viewZoomIn = new javax.swing.JMenuItem();
        viewZoomOut = new javax.swing.JMenuItem();
        viewReset = new javax.swing.JMenuItem();
        settingsMenu = new javax.swing.JMenu();
        helpMenu = new javax.swing.JMenu();
        helpAbout = new javax.swing.JMenuItem();
        aboutCredits = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        importMenu.setText("Import");

        importWorld.setText("World");
        /*importWorld.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
         importWorldActionPerformed(evt);
         }
         });*/
        importMenu.add(importWorld);

        importCritter.setText("Critter");
        importMenu.add(importCritter);

        importSettings.setText("Settings");
        /*importSettings.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
         importSettingsActionPerformed(evt);
         }
         });*/
        importMenu.add(importSettings);

        MENUBAR.add(importMenu);

        createMenu.setText("Create");

        createWorld.setText("World");
        /*createWorld.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
         createWorldActionPerformed(evt);
         }
         });*/
        createMenu.add(createWorld);

        createCritter.setText("Critter");
        /*createCritter.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
         createCritterActionPerformed(evt);
         }
         });*/
        createMenu.add(createCritter);

        createPlant.setText("Plant");
        createMenu.add(createPlant);

        createFood.setText("Food");
        /*createFood.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
         createFoodActionPerformed(evt);
         }
         });*/
        createMenu.add(createFood);

        createRock.setText("Rock");
        createMenu.add(createRock);

        MENUBAR.add(createMenu);

        viewMenu.setText("View");

        viewZoomIn.setText("Zoom In");
        viewMenu.add(viewZoomIn);

        viewZoomOut.setText("Zoom Out");
        viewMenu.add(viewZoomOut);

        viewReset.setText("Reset");
        viewMenu.add(viewReset);

        MENUBAR.add(viewMenu);

        settingsMenu.setText("Settings");
        MENUBAR.add(settingsMenu);

        helpMenu.setText("Help");

        helpAbout.setText("About");
        helpMenu.add(helpAbout);

        aboutCredits.setText("Credits");
        helpMenu.add(aboutCredits);

        MENUBAR.add(helpMenu);

        setJMenuBar(MENUBAR);

        fileSelector = new javax.swing.JFileChooser();
        fileSelector.setDialogTitle("Import");

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
     *
     * @param	frame	the JFrame to set to fullscreen
     */
    private void setFullScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int taskBarSize = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration()).bottom;
        this.setSize(screenSize.width, screenSize.height - taskBarSize);
    }
    // Variables declaration - do not modify
    public javax.swing.JFileChooser fileSelector;
    private javax.swing.JMenuBar MENUBAR;
    public javax.swing.JMenuItem aboutCredits;
    public javax.swing.JMenuItem createCritter;
    public javax.swing.JMenuItem createFood;
    public javax.swing.JMenu createMenu;
    public javax.swing.JMenuItem createPlant;
    public javax.swing.JMenuItem createRock;
    public javax.swing.JMenuItem createWorld;
    public javax.swing.JMenuItem helpAbout;
    private javax.swing.JMenu helpMenu;
    public javax.swing.JMenuItem importCritter;
    private javax.swing.JMenu importMenu;
    public javax.swing.JMenuItem importSettings;
    public javax.swing.JMenuItem importWorld;
    public javax.swing.JMenu settingsMenu;
    private javax.swing.JMenu viewMenu;
    public javax.swing.JMenuItem viewReset;
    public javax.swing.JMenuItem viewZoomIn;
    public javax.swing.JMenuItem viewZoomOut;
    // End of variables declaration
}
