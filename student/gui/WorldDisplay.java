/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Set;
import javax.swing.*;
import student.grid.Critter;
import student.grid.HexGrid;
import student.grid.Tile;
import student.world.World;

public class WorldDisplay extends JPanel{
    public GridPanel gridpane;
    //public JPanel attributesPanel;
    public JPanel textDisplay;
    public JPanel worldStatusPanel;
    //public JPanel attributesPanel;
    public JLabel timestep, crittercount, plantcount, foodcount, rockcount;
    public JTextArea attributes;
    public World WORLD;
    public HexGrid.Reference<Tile> currentLocation;

    public WorldDisplay(World world) {
        WORLD = world;
        currentLocation = WORLD.defaultLoc();
        
        setLayout(new BorderLayout());
//        xSTART = 100;
//        ySTART = 100;
        attributes = new JTextArea();
        gridpane = generateGridPanel();
        worldStatusPanel = generateWorldStatusPanel();
        textDisplay = generateTextDisplayPanel();
        
        updateWorldStatus();
        updateAttributes();

        add(gridpane, BorderLayout.CENTER);
        add(textDisplay, BorderLayout.EAST);
        
        gridpane.setVisible(true);
        textDisplay.setVisible(true);
        attributes.setVisible(true);
    }
    public final GridPanel generateGridPanel(){
        GridPanel grid = new GridPanel(WORLD);
        grid.setMinimumSize(new Dimension(WORLD.width(), WORLD.height()));
        grid.setSize(WORLD.width()*100, WORLD.height()*100);
        return grid;
    }
    public final JPanel generateWorldStatusPanel(){
        JPanel wst = new JPanel();
        wst.setLayout(new GridLayout(8,1));
        wst.add(new JLabel("World Status\n\n")); //1
        timestep = new JLabel();
        wst.add(timestep); //2
        wst.add(new JLabel("Population")); //4
        crittercount = new JLabel(); 
        wst.add(crittercount); //5
        plantcount = new JLabel();
        wst.add(plantcount); //6
        foodcount = new JLabel();
        wst.add(foodcount); //7
        rockcount = new JLabel();
        wst.add(rockcount); //8

        return wst;
    }
    public final void updateWorldStatus(){
        int[] population = WORLD.population();
        timestep.setText("Timestep: "+WORLD.getTimesteps());
        crittercount.setText("\n\tCritters: "+population[0]);
        plantcount.setText("\n\tPlants: "+population[1]);
        foodcount.setText("\n\tFood: "+population[2]);
        rockcount.setText("\n\tRocks: "+population[3]);
    }
    public final JPanel generateTextDisplayPanel(){
        JPanel textDisp = new JPanel();
        textDisp.setLayout(new BorderLayout());
        textDisp.add(worldStatusPanel, BorderLayout.NORTH);
        textDisp.add(attributes, BorderLayout.CENTER);
        return textDisp;
    }
    public GridPanel grid() {
        return gridpane;
    }

    public final void updateAttributes() {
        //state.setText()
        String s = "The currently selected location has ";
        if (currentLocation.contents() == null)
            s = s+"nothing";
        else
        {
        if (currentLocation.contents().rock())
            s = s + "\na rock...";
        if (currentLocation.contents().food())
            s = s + "\nfood...";
        if (currentLocation.contents().plant())
            s = s + "\na plant...";
        if (currentLocation.contents().critter()) {
            s = s + "\na critter with";
            int[] memory = currentLocation.contents().getCritter().memory();
            s = s + "\n\tMemory: " + memory[0]
                    + "\n\tDefense: " + memory[1]
                    + "\n\tOffense: " + memory[2]
                    + "\n\tSize: " + memory[3]
                    + "\n\tEnergy: " + memory[4]
                    + "\n\tRule Counter: " + memory[5]
                    + "\n\tEvent Log: " + memory[6]
                    + "\n\tTag: " + memory[7]
                    + "\nPosture: " + memory[8];
        }
        }
        attributes.setText(s);
    }
    public void setCurrentLocation(HexGrid.Reference<Tile> r)
    {
        currentLocation = r;
    }
    public void update() {
        gridpane.repaint();
        updateWorldStatus();
        updateAttributes();
        
    }
}
