/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Set;
import javax.swing.*;
import student.grid.Critter;
import student.grid.Entity;
import student.grid.HexGrid;
import student.world.World;

public class WorldDisplay extends JPanel{
    public GridPanel gp;
    public JPanel ap;
    public JTextArea attributes;
    public World WORLD;
    public HexGrid.Reference<Set<Entity>> currentLocation;

    public WorldDisplay(World world) {
        WORLD = world;
        currentLocation = WORLD.defaultLoc();
//        xSTART = 100;
//        ySTART = 100;
        gp = generateGridPanel();
        ap = new JPanel();
        attributes = new JTextArea();
        updateAttributes();
        ap.add(attributes);
        setLayout(new BorderLayout());
        add(gp, BorderLayout.CENTER);
        add(ap, BorderLayout.WEST);
        gp.setVisible(true);
    }
    public final GridPanel generateGridPanel(){
        GridPanel grid = new GridPanel(WORLD);
        grid.setMinimumSize(new Dimension(WORLD.width(), WORLD.height()));
        grid.setSize(WORLD.width()*100, WORLD.height()*100);
        return grid;
    }
    
    public GridPanel grid() {
        return gp;
    }
    public final JPanel generateAttPanel(){
        JPanel attpanel = new JPanel();
        return attpanel;
    }
    public final JTextArea generateAttArea(){
        JTextArea attArea = new JTextArea();
        return attArea;
    }
    public void updateAttributes(){
        //state.setText()
        String s = "";
        for(Entity e: currentLocation.contents())
        {
            s = s+e.getClass().getName()+"/n";
            int[] memory = null;
            if(e instanceof Critter)
            {
               memory = ((Critter)e).memory();
            s =    s+"/nMemory: "       + memory[0]
                    +"/nDefense: "      + memory[1]
                    +"/nOffense: "      + memory[2]
                    +"/nSize: "         + memory[3]
                    +"/nEnergy: "       + memory[4]
                    +"/nRule Counter: " + memory[5]
                    +"/nEvent Log: "    + memory[6]
                    
                    +"/nTag: "          + memory[7]
                    +"/nPosture: "      + memory[8];
            }
            s = s+"/n/n";
        }
        attributes.setText(s);
    }
   
    /**
     * unit = hexlength/4
     * height = (numhexes*2-1)hexlength
     * width = numhexes*hexlength   //numhexes*hexlength-[(numhexes-1)*2]*hexlength/4
     */
    /**
     * draw first hex at 
     * x = numhexes*hexlength - (numhexes-1)
     * y = 0
     */
    /**
     * draw hex at
     * y = startY + (
     */
    /**
     * Finds the point 
     * @param row
     * @param col
     * @param startpoint
     * @param hexsize
     * @return 
     */
    public int[] point(int row, int col, int[] startpoint, int hexsize)
    {
        return new int[]{startpoint[0]+(hexsize*3/4)*row, startpoint[1]+(hexsize/2)*col}; //[x, y]
    }
}
