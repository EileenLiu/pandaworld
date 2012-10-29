/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;
import student.world.World;

/**
 *
 * @author Panda
 */
public class WorldDisplay extends JPanel{
    public JPanel gp, sp;
    public JTextArea state;
    public World world;
    public int xSTART = 50;
    public int ySTART = 50;
    private int hxsz = 10;

    public WorldDisplay(World world) {
        xSTART = 100;
        ySTART = 100;
        gp = generateGridpanel();
        sp = new JPanel();
        state = new JTextArea();
        sp.add(state);
        setLayout(new BorderLayout());
        add(gp, BorderLayout.EAST);
        add(sp, BorderLayout.WEST);
        gp.setVisible(true);
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
    public int pnX(int row, int col) {
        return col*hxsz*2;
    }
    public int pnY(int row, int col) {
        return row*hxsz*2 
            +col% 
            2==0 
            ?hxsz/ 
            2:0; //It's just as readable as anything else here --el
    }
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
     */
    public void drawGrid()
    {
        Graphics2D gp2 =(Graphics2D) gp.getGraphics();
        for(int c = 0; c < world.width(); c++) {
            int x = pnX(0,c);
            for(int r = 0; r < world.height(); r++) {
                int y = pnY(r,c);
                drawHexagon(hexCoordinates(x, y, hxsz), gp2);
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
