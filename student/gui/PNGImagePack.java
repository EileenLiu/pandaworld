/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import java.awt.Image;
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
    
    public PNGImagePack(String _filename, String[] imgnames) {
        //filename=_filename;
        pack = new HashMap<String, Image>(imgnames.length);
        try {
            if (imgnames.length != 1) { //assume is a zipfile
                ZipFile zf = new ZipFile(_filename);
                for (String s : imgnames) {
                    ZipEntry e = zf.getEntry(s + ".png");
                    InputStream i = zf.getInputStream(e);
                    pack.put(s, ImageIO.read(i));
                }
            } else { //not in a zipfile, assume is an image file (.png, .jpg,..etc)
                pack.put(imgnames[0], ImageIO.read(new File(_filename)));
            }
        } catch (IOException ex) {
               valid = false;
        } catch (NullPointerException n){ 
               valid = false;
        }
        }
    public Image get(String key){
        return pack.get(key);
    }
    public boolean isValid(){
        return valid;
    }
        /*private void load(String filename, String[] imgnames) {
         try {
         //this.getClass().
         ZipFile zf = new ZipFile(filename);
         ZipEntry eti = zf.getEntry("tile.png"),
         erk = zf.getEntry("rock.png"),
         epl = zf.getEntry("plnt.png"),
         efd = zf.getEntry("food.png"),
         enn = zf.getEntry("nn.png"),
         ene = zf.getEntry("ne.png"),
         enw = zf.getEntry("nw.png"),
         ese = zf.getEntry("se.png"),
         esw = zf.getEntry("sw.png"),
         ess = zf.getEntry("ss.png");
         InputStream iti = zf.getInputStream(eti),
         irk = zf.getInputStream(erk),
         ipl = zf.getInputStream(epl),
         ifd = zf.getInputStream(efd),
         inn = zf.getInputStream(enn),
         ine = zf.getInputStream(ene),
         inw = zf.getInputStream(enw),
         ise = zf.getInputStream(ese),
         isw = zf.getInputStream(esw),
         iss = zf.getInputStream(ess);
         } catch (IOException ex) {
         }*/
    }
