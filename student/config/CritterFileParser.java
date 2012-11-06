/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import student.grid.Critter;
import student.grid.HexGrid;
import student.grid.Tile;
import student.parse.ParserFactory;
import student.parse.Program;
import student.world.World;

/**
 *
 * @author haro
 */
public class CritterFileParser {
    /**
     * Generates a critter from the specified file, in the given world and location
     * @param filename the given file
     * @param world the given world
     * @param _pos the given location
     * @return
     */
    public static Critter generateCritter(String filename, World world, HexGrid.Reference<Tile> pos) {
        Critter c = null;
        try {
                BufferedReader inStreamReader= new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
                String[] arr = new String[6];
                for(int i = 0; i<6; i++)
                {
                arr[i] = inStreamReader.readLine();
                arr[i] = arr[i].substring(arr[i].indexOf(':')+2);
                }
                c.setDefense(Integer.parseInt(arr[1]));
                c.setOffense(Integer.parseInt(arr[2]));
                c.setSize(Integer.parseInt(arr[3]));
                c.setEnergy(Integer.parseInt(arr[4]));
                c.setAppearance(new File(arr[5]));
                Program program = ParserFactory.getParser().parse(inStreamReader);
                c = new Critter(world, pos, program);
                //System.out.println(program.prettyPrint());
            } catch (FileNotFoundException e) {
                System.out.println("The given file was not found.");
            }
            catch (IOException ex) {
                System.out.println("The given file is invalid.");
            }
            catch(NumberFormatException e) {
                System.out.println("The given file has invalid data: Integer expected.");
            }
        return c;
    }
}
