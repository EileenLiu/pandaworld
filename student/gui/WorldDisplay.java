/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
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
    public final int xSTART;
    public final int ySTART;

    public WorldDisplay(World world) {
        xSTART = 100;
        ySTART = 100;
        gridpanel = generateGridpanel();
        statepanel = new JPanel();
        state = new JTextArea();
        statepanel.add(state);
        setLayout(new BorderLayout());
        add(gridpanel, BorderLayout.EAST);
        add(statepanel, BorderLayout.WEST);
    }
    public final JPanel generateGridpanel(){
        JPanel grid = new JPanel();
        
        return grid;
    }
    public void updateState(){
        //state.setText(world.currentLocation.getState());
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
    /**
     * Draws the entire grid of hexagons
     * @param length the number of hexagons that make of a side length of the hexagon grid 
     * @param hexsize the size of each hexagon
     */
    public void drawGrid(int length, int hexsize)
    {
        int numrows = length*2-1;
        int numcols = length;
        int medianrow = length-1;
        int[] rowstartpoint = new int[]{(hexsize-1)*(length-1),0};
        for(int i =0; i<numrows; i++)
        {
            for(int j = 0; j<numcols; j++)
            {
                int[] point = point(i, j, rowstartpoint, hexsize);
                //drawHexagon(hexCoordinates(point[0]+xSTART, point[1]+ySTART, hexsize));
            }
            if(i > medianrow)
            {
                rowstartpoint[0] = 0;
                rowstartpoint[1] = rowstartpoint[1] + hexsize;
                numcols--;
            }
            else
            {
                rowstartpoint[0] = 0;
                rowstartpoint[1] = rowstartpoint[1] + hexsize;
                numcols++;
            }
            
        }
    }
    /**
     * Draws a hexagon of the given coordinates 
     * @param hexCoordinates the given coordinates
     * @param g graphics
     */
    public void drawHexagon(int[][] hexCoordinates, Graphics g)
    {
        for(int i = 0; i<hexCoordinates.length-1; i++)
        {
            g.setColor(Color.GREEN);
            g.drawLine(hexCoordinates[i][0], hexCoordinates[i][1], hexCoordinates[i+1][0], hexCoordinates[i+1][1]);
        }
        g.drawLine(hexCoordinates[hexCoordinates.length-1][0], hexCoordinates[hexCoordinates.length-1][1], hexCoordinates[0][0], hexCoordinates[0][1]);
    }
    /**
     * Returns the coordinates (in the form [x, y]) of a single hexagon drawn starting from the given coordinates
     * Precondition: length is divisible by 4
     * @param startX the given start x coordinate
     * @param startY the given start y coordinate
     * @param size the side length of square the hexagon will be contained in
     * @return 
     */
    public int[][] hexCoordinates(int startX, int startY, int size){
        int[][] hexCoord =  new int[6][2]; //[Xj, Yj] for j= 1,2,3,4,5,6
        hexCoord[0] = new int[]{size/4, 0};
        hexCoord[1] = new int[]{size*3/4, 0};
        hexCoord[2] = new int[]{size, size/2};
        hexCoord[3] = new int[]{size*3/4, size};
        hexCoord[4] = new int[]{size/4, size};
        hexCoord[5] = new int[]{0, size/2};
        /*hexCoord[0] = new int[]{startX, B};
        hexCoord[1] = new int[]{sidelength/2, startY};
        hexCoord[2] = new int[]{A+sidelength, startY};
        hexCoord[3] = new int[]{2*sidelength, B};
        hexCoord[4] = new int[]{A+sidelength, 2*B};
        hexCoord[5] = new int[]{A, 2*B};*/
        return hexCoord;
    }
    
}
