/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui.render;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.imageio.ImageIO;

/**
 *
 * @author Eileen Liu <el544@cornell.edu>
 */
public class PNGImagePack {

    //private String filename;
    private HashMap<String, Image> pack;
    private boolean valid = true; //does the zipfile contain all the specified images?
    
    public PNGImagePack(String _filename, String[] imgnames, Color c, double scaleFactor) {
        //filename=_filename;
        pack = new HashMap<String, Image>(imgnames.length);
        try {
            if (imgnames.length != 1) { //assume is a zipfile
                ZipFile zf = new ZipFile(_filename);
                for (String s : imgnames) {
                    ZipEntry e = zf.getEntry(s + ".png");
                    InputStream i = zf.getInputStream(e);
                    Image img = ImageIO.read(i);
                    /*if(c == null)
                        pack.put(s, img);
                    else
                    {
                        pack.put(s, changeColor(c, img));
                        //System.out.println("changed color to "+c);
                    }*/
                    pack.put(s, formatImage(c, scaleFactor, img));
                }
            } else { //not in a zipfile, assume is an image file (.png, .jpg,..etc)
                pack.put(imgnames[0], ImageIO.read(new File(_filename)));
            }
        } catch (IOException ex) {
            valid = false;
        } catch (NullPointerException n) {
            valid = false;
        }
    }

    public Image get(String key) {
    //    System.out.println("log");
        return pack.get(key);
    }

    public boolean isValid(){
        return valid;
    }
    private Image formatImage(Color cl, double scaleFactor, Image original)
    {
        return changeColor(cl, cropResize(scaleFactor, original));
    }
    /**
     * Changes the color of the specified image to the specified color and
     * returns the result.
     *
     * @param cl the Color to change the image to.
     * @param untinted	the image to change the color of.
     * @return the tinted image
     */
    public Image changeColor(Color cl, Image untinted) {
        if(cl == null)
            return untinted;
        ImageFilter colorfilter = new TintFilter(cl); //creates a ImageFilter that will filter an image to the specified color
        return Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(untinted.getSource(), colorfilter)); //returns the colored changed version of the image
    }
    /**
     * Crops and enlarges the image by the given percentage
     * @param scaleFactor the scaleFactor of the image to crop and enlarge by
     * @param unedited the original image
     * @return the cropped and enlarged image
     */
    public Image cropResize(double scaleFactor, Image unedited){
        if(scaleFactor==0.0)
            return unedited;
        CropImageFilter cropfilter = new CropImageFilter((int)(unedited.getWidth(null)*scaleFactor/2),
                (int)(unedited.getHeight(null)*scaleFactor/2), unedited.getWidth(null)-(int)(unedited.getWidth(null)*scaleFactor/2),
                unedited.getHeight(null)-(int)(unedited.getHeight(null)*scaleFactor/2));
        Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(unedited.getSource(), cropfilter));
        return img.getScaledInstance(unedited.getWidth(null), unedited.getHeight(null),Image.SCALE_DEFAULT);
    }
}