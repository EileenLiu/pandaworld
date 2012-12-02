/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.rmi.RemoteException;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import student.grid.HexGrid;
import student.grid.Tile;
import student.remote.world.RWorld;

public class WorldDisplay extends JPanel{
    public GridPanel gridpane;
    //public JScrollPane 
    public JPanel infoDisplay;
    public JPanel worldStatusPanel;
    public JTextArea attributes;
    public ControlPanel controls;
    public JScrollPane scrollpane;
    public JLabel timestep, crittercount, plantcount, foodcount, rockcount;
    
    public RWorld WORLD;
    public HexGrid.Reference<Tile> currentLocation;
    
    public WorldDisplay(RWorld world) throws RemoteException {
        WORLD = world;
        currentLocation = WORLD.at(0,0);
        
        setLayout(new BorderLayout());
        
        controls = new ControlPanel();
        controls.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));//createBevelBorder(0, 10, 0, 10));
        attributes = generateAttributes();
        gridpane = generateGridPanel();
        worldStatusPanel = generateWorldStatusPanel();
        infoDisplay = generateInfoPanel();
        
        updateWorldStatus();
        updateAttributes();
        scrollpane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollpane.setViewportView(gridpane);
             
        add(scrollpane, BorderLayout.CENTER);
        add(infoDisplay, BorderLayout.EAST);
        gridpane.setVisible(true);
        infoDisplay.setVisible(true);
        attributes.setVisible(true);
    }
    private final JTextArea generateAttributes(){
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        //textArea.setLineWrap(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        textArea.setOpaque(false);
        textArea.setBackground(new Color(164, 173, 210));
        return textArea;
    }
    private final GridPanel generateGridPanel() throws RemoteException {
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
        //wst.setBackground(new Color(68, 82, 138));
        return wst;
    }
    private final void updateWorldStatus(){
        try {
            int[] population = WORLD.population();
            timestep.setText("Timestep: "+WORLD.getTimesteps());
            crittercount.setText("\n\tCritters: "+population[0]);
            plantcount.setText("\n\tPlants: "+population[1]);
            foodcount.setText("\n\tFood: "+population[2]);
            rockcount.setText("\n\tRocks: "+population[3]);
        } catch (RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Connection to server failed", "Connection error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private final JPanel generateInfoPanel(){
        JPanel infoDisp = new JPanel();
        infoDisp.setLayout(new BorderLayout());
        infoDisp.add(worldStatusPanel, BorderLayout.NORTH);
        infoDisp.add(attributes, BorderLayout.CENTER);
        infoDisp.add(controls, BorderLayout.SOUTH);
        infoDisp.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        //textDisp.setBackground(new Color(68, 82, 138));
        return infoDisp;
    }
    public GridPanel grid() {
        return gridpane;
    }
    
    private final void updateAttributes() {
        //state.setText()
        String s = "The currently selected\nlocation has ";
        if (currentLocation == null || currentLocation.mutableContents() == null || currentLocation.mutableContents().isEmpty())
            s = s+"\nnothing... ";
        else
        {
        if (currentLocation.mutableContents().rock())
            s = s + "\na rock... ";
        if (currentLocation.mutableContents().food())
            s = s + "\nfood worth " + currentLocation.mutableContents().foodValue() + " units of energy...";
        if (currentLocation.mutableContents().plant())
            s = s + "\na plant... ";
        if (currentLocation.mutableContents().critter()) {
            s = s + "\na critter with ";
            s = s + currentLocation.mutableContents().getCritter().state()+"\n";
 //           s = s + "\nwhich sees food: "+WORLD.smell(currentLocation, World.TilePredicate.isFood, 10);
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
