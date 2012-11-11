/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui.render;

import java.awt.Color;
import java.awt.image.RGBImageFilter;

    /**
     * An image filter class that tints colors based on the tint provided to the
     * constructor (the color of an object). Taken from GridWorld Case Study\GridWorldCode\framework\info\gridworld\gui\ImageDisplay.java
     * http://apcentral.collegeboard.com/apc/public/repository/GridWorldCode.zip
     * which is modified under "the terms of the GNU General Public License as published by the Free Software Foundation".
     */
	  
      public class TintFilter extends RGBImageFilter
      {
         private int tintR, tintG, tintB;
      
        /**
         * Constructs an image filter for tinting colors in an image.
         * @param color the tint color
         */
         public TintFilter(Color color)
         {
            canFilterIndexColorModel = true;
            int rgb = color.getRGB();
            tintR = (rgb >> 16) & 0xff;
            tintG = (rgb >> 8) & 0xff;
            tintB = rgb & 0xff;
         }
      
          @Override
         public int filterRGB(int x, int y, int argb)
         {
            // Separate pixel into its RGB coomponents.
            int alpha = (argb >> 24) & 0xff;
            int red = (argb >> 16) & 0xff;
            int green = (argb >> 8) & 0xff;
            int blue = argb & 0xff;
            // Use NTSC/PAL algorithm to convert RGB to gray level
            double lum = (0.2989 * red + 0.5866 * green + 0.1144 * blue) / 255;
         
            // interpolate between tint and pixel color. Pixels with
            // gray level 0.5 are colored with the tint color,
            // white and black pixels stay unchanged.
            // We use a quadratic interpolation function
            // f(x) = 1 - 4 * (x - 0.5)^2 that has
            // the property f(0) = f(1) = 0, f(0.5) = 1
            
            // Note: Julie's algorithm used a linear interpolation
            // function f(x) = min(2 - 2 * x, 2 * x);
            // and it interpolated between tint and 
            // (lum < 0.5 ? black : white)
         
            double scale = 1 - (4 * ((lum - 0.5) * (lum - 0.5)));
            
            red = (int) (tintR * scale + red * (1 - scale));
            green = (int) (tintG * scale + green * (1 - scale));
            blue = (int) (tintB * scale + blue * (1 - scale));
            return (alpha << 24) | (red << 16) | (green << 8) | blue;
         }
      }

