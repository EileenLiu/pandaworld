/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.Set;
import javax.swing.*;
import javax.swing.border.LineBorder;
import student.grid.HexGrid.Reference;
import student.grid.Tile;
import student.world.World;

public class GridPanel extends JPanel {

    private static Image TILE_IMG = new ImageIcon("tile.png").getImage();
    private static Image ROCK_IMG = new ImageIcon("rock.png").getImage();
    
    private Polygon hexen[][];
    public int xSTART = 0;
    public int ySTART = 0;
    //A MULTIPLE OF FOUR
    private int HEXSIZE = 100;
    public World zaWarudo; //WRYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY
    public GridPanel(World world) {
        zaWarudo = world;
        this.setBorder(new LineBorder(Color.MAGENTA, 3));
        hexen = new Polygon[world.height()][world.width()];
        for (int c = 0; c < zaWarudo.width(); c++) {
            int x = pnX(0, c);
            for (int r = 0; r < zaWarudo.height(); r++) {
                int y = pnY(r, c);
                //System.out.println(""+x+","+y);
                //gp.fillOval(x, y, 10, 10);
                hexen[r][c] = makePoly(x,y,HEXSIZE);
            }
        }
    }
    
    public int []hexAt(int x, int y) {
        for(int r = 0; r < zaWarudo.height(); r++)
            for(int c = 0; c < zaWarudo.width(); c++)
                if(hexen[r][c].contains(x, y))
                    return new int[]{r,c};
        return null;
    }
    
    /**
     * Calculates the dimensions and coordinates of the grid based off of
     * the panel size then paints the display.
     *
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    public void paintComponent(Graphics g) {
        /*
         * HEXSIZE = 
         * xSTART = 
         * ySTART =
         */
        /*        
         *   
         *
         //IMAGEWIDTH = (int)(this.getSize().getWidth()*9.0/10.0); //calculates the width of the images being displayed
         //IMAGEHEIGHT= (int)(this.getSize().getHeight()*9.0/10.0);
         
         //STARTX=(int)(this.getSize().getWidth()-IMAGEWIDTH)/2;
         //STARTY=(int)(this.getSize().getHeight()-IMAGEHEIGHT)/2;*/

        update(g);
    }

    /**
     *
     * @see javax.swing.JComponent#update(java.awt.Graphics)
     */
    @Override
    public void update(Graphics g)//overrides update method to prevent continuous uneccessary repainting
    {
        g.setColor(Color.BLACK);
        //g.fillRect(0, 0, this.getWidth(), this.getHeight());
        drawGrid(HEXSIZE, g);
    }

    /**
     * Draws the entire grid of hexagons
     *
     * @param length the number of hexagons that make of a side length of the
     * hexagon grid
     * @param hexsize the size of each hexagon
     */
   /* public void drawGrid(int length, int hexsize, Graphics g) {
        int numrows = length * 2 - 1;
        int numcols = length;
        int medianrow = length - 1;
        int[] rowstartpoint = new int[]{(hexsize - 1) * (length - 1), 0};
        for (int i = 0; i < numrows; i++) {
            for (int j = 0; j < numcols; j++) {
                //int[] point = point(i, j, rowstartpoint, hexsize);
                drawHexagon(hexCoordinates(pnX(i, j) + xSTART, pnY(i, j) + ySTART, hexsize), g);
                //drawHexagon(hexCoordinates(point[0]+xSTART, point[1]+ySTART, hexsize));
            }
            if (i > medianrow) {
                rowstartpoint[0] = 0;
                rowstartpoint[1] = rowstartpoint[1] + hexsize;
                numcols--;
            } else {
                rowstartpoint[0] = 0;
                rowstartpoint[1] = rowstartpoint[1] + hexsize;
                numcols++;
            }

        }
    }*/

    /**
     * Draws a hexagon of the given coordinates
     *
     * @param hexCoordinates the given coordinates
     * @param g graphics
     */
    public void drawHexagon(int x, int y, int r, int c, int hexsize, Graphics g, Image i) {
        /*int [][]hexCoordinates = hexCoordinates(x, y, hexsize);
        for (int i = 0; i < hexCoordinates.length - 1; i++) {
            g.setColor(Color.GREEN);
            g.drawLine(hexCoordinates[i][0], hexCoordinates[i][1], hexCoordinates[i + 1][0], hexCoordinates[i + 1][1]);
        }
        g.drawLine(hexCoordinates[hexCoordinates.length - 1][0], hexCoordinates[hexCoordinates.length - 1][1], hexCoordinates[0][0], hexCoordinates[0][1]);
        */
        
        g.drawImage(i, x, y, hexsize, hexsize, this);
    }
    
    Polygon p;

    public int pnX(int row, int col) {
        return col * HEXSIZE / 4 * 3;
    }
    
    public int pnY(int row, int col) {
        return row * HEXSIZE
             + (col%2==0
                 ? HEXSIZE/2 
                 : 0);
    }

    /**
     * Returns the coordinates (in the form [x, y]) of a single hexagon drawn
     * starting from the given coordinates Precondition: length is divisible by
     * 4
     *
     * @param startX the given start x coordinate
     * @param startY the given start y coordinate
     * @param size the side length of square the hexagon will be contained in
     * @return
     */
    public static Polygon makePoly(int startX, int startY, int size) {
        int xs[] = new int[6], ys[] = new int[6]; //[Xj, Yj] for j= 1,2,3,4,5,6
        xs[0] = size / 4 + startX;
            ys[0] = startY;
        xs[1] = size * 3 / 4 + startX;
            ys[1] = startY;
        xs[2] = size + startX;
            ys[2] = size / 2+ startY;
        xs[3] = size * 3 / 4 + startX;
            ys[3] = size+ startY;
        xs[4] = size / 4 + startX;
            ys[4] = size+ startY;
        xs[5] = startX;
            ys[5] = size / 2+ startY;
        /*hexCoord[0] = new int[]{startX, B};
         hexCoord[1] = new int[]{sidelength/2, startY};
         hexCoord[2] = new int[]{A+sidelength, startY};
         hexCoord[3] = new int[]{2*sidelength, B};
         hexCoord[4] = new int[]{A+sidelength, 2*B};
         hexCoord[5] = new int[]{A, 2*B};*/
        return new Polygon(xs, ys, 6);
    }
   /**
     * Draws the entire grid of hexagons
     */
    //@Deprecated
    public void drawGrid(int hexsize, Graphics gp) {//HWHdrawGrid(int hxsz, Graphics gp) {
        for (int c = 0; c < zaWarudo.width(); c++) {
            for (int r = 0; r < zaWarudo.height(); r++) {
                Polygon loc = hexen[r][c];
                Rectangle bbx = loc.getBounds();
                Tile t = zaWarudo.at(r, c).contents();
                if(t != null && t.rock()) 
                    drawHexagon(bbx.x, bbx.y, r, c, hexsize, gp, ROCK_IMG);
                else
                    drawHexagon(bbx.x, bbx.y, r, c, hexsize, gp, TILE_IMG);
            }
        }
    }
}
