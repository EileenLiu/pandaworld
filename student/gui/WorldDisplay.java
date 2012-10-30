/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Set;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import student.grid.Critter;
import student.grid.HexGrid;
import student.grid.Tile;
import student.world.World;

public class WorldDisplay extends JPanel{
    public GridPanel gridpane;
    public JPanel textDisplay;
    public JPanel worldStatusPanel;
    public JTextArea attributes;
    public ControlPanel controls;
    public JLabel timestep, crittercount, plantcount, foodcount, rockcount;
    
    public World WORLD;
    public HexGrid.Reference<Tile> currentLocation;
    
    public WorldDisplay(World world) {
        WORLD = world;
        currentLocation = WORLD.defaultLoc();
        
        setLayout(new BorderLayout());
        
        controls = new ControlPanel();
        controls.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));//createBevelBorder(0, 10, 0, 10));
        attributes = generateAttributes();
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
    private final JTextArea generateAttributes(){
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        //textArea.setLineWrap(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        textArea.setOpaque(false);
        return textArea;
    }
    private final GridPanel generateGridPanel(){
        GridPanel grid = new GridPanel(WORLD);
        grid.setMinimumSize(new Dimension(WORLD.width(), WORLD.height()));
        grid.setSize(WORLD.width()*100, WORLD.height()*100);
        return grid;
    }
    private final JPanel generateWorldStatusPanel(){
        JPanel wst = new JPanel();
        wst.setLayout(new GridLayout(8,1));
        JLabel worldstatus = new JLabel("[ World Status ]\n\n", JLabel.CENTER);
        worldstatus.setFont(new Font("Serif", Font.BOLD, 16));
        wst.add(worldstatus); 
        timestep = new JLabel("", JLabel.CENTER);
        timestep.setFont(new Font("Serif", Font.BOLD, 14));
        wst.add(timestep); 
        JLabel population = new JLabel("Population: ", JLabel.CENTER);
        population.setFont(new Font("Serif", Font.BOLD, 14));
        wst.add(population);
        crittercount = new JLabel("", JLabel.CENTER);
        crittercount.setFont(new Font("Serif", Font.PLAIN, 12));
        wst.add(crittercount); 
        plantcount = new JLabel("", JLabel.CENTER);
        plantcount.setFont(new Font("Serif", Font.PLAIN, 12));
        wst.add(plantcount); 
        foodcount = new JLabel("", JLabel.CENTER);
        foodcount.setFont(new Font("Serif", Font.PLAIN, 12));
        wst.add(foodcount); 
        rockcount = new JLabel("", JLabel.CENTER);
        rockcount.setFont(new Font("Serif", Font.PLAIN, 12));
        wst.add(rockcount); 

        return wst;
    }
    private final void updateWorldStatus(){
        int[] population = WORLD.population();
        timestep.setText("Timestep: "+WORLD.getTimesteps());
        crittercount.setText("\n\tCritters: "+population[0]);
        plantcount.setText("\n\tPlants: "+population[1]);
        foodcount.setText("\n\tFood: "+population[2]);
        rockcount.setText("\n\tRocks: "+population[3]);
    }
    private final JPanel generateTextDisplayPanel(){
        JPanel textDisp = new JPanel();
        textDisp.setLayout(new BorderLayout());
        textDisp.add(worldStatusPanel, BorderLayout.NORTH);
        textDisp.add(attributes, BorderLayout.CENTER);
        textDisp.add(controls, BorderLayout.SOUTH);
        textDisp.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        return textDisp;
    }
    public GridPanel grid() {
        return gridpane;
    }
    
    private final void updateAttributes() {
        //state.setText()
        String s = "The currently selected\nlocation has ";
        if (currentLocation.contents() == null)
            s = s+"\nnothing... ";
        else
        {
        if (currentLocation.contents().rock())
            s = s + "\na rock... ";
        if (currentLocation.contents().food())
            s = s + "\nfood... ";
        if (currentLocation.contents().plant())
            s = s + "\na plant... ";
        if (currentLocation.contents().critter()) {
            s = s + "\na critter with ";
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
    /**
     * Updates the current location with the given location reference
     * @param r the given location reference
     */
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
