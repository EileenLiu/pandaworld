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

    public static Critter generateCritter(World world, HexGrid.Reference<Tile> pos, int direction) {
        Critter c = null;
        if (pos == null) {
            pos = world.randomLoc();
        }
        if (!(direction > 0 && direction < 6)) {
            //c = new Critter(world, pos, program);
        } else {
            //c = new Critter(world, pos, program, direction);
        }
        return c;
    }
    /**
     * Generates a critter from the specified file, in the given world and location
     * @param filename the given file
     * @param world the given world
     * @param _pos the given location
     * @return null
     */
    public static Critter generateCritter(String filename, World world, HexGrid.Reference<Tile> pos, int direction) {
        Critter c = null;
        try {
                BufferedReader inStreamReader= new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
                String[] arr = new String[6];
                for(int i = 0; i<6; i++)
                {
                arr[i] = inStreamReader.readLine();
                arr[i] = arr[i].substring(arr[i].indexOf(':')+2);
                }
                Program program = ParserFactory.getParser().parse(inStreamReader);
                if(pos==null)
                    pos = world.randomLoc();
                if(!(direction>0&&direction<6))
                    c = new Critter(world, pos, program);
                else
                    c = new Critter(world, pos, program, direction);
                c.setDefense(Integer.parseInt(arr[1]));
                c.setOffense(Integer.parseInt(arr[2]));
                c.setSize(Integer.parseInt(arr[3]));
                c.setEnergy(Integer.parseInt(arr[4]));
                c.setAppearance(arr[5]);
                //System.out.println(program.prettyPrint());
            } catch (FileNotFoundException e) {
                System.out.println("CritterFileParser: The given file was not found.");
            }
            catch (IOException ex) {
                System.out.println("CritterFileParser: The given file is invalid.");
            }
            catch(NumberFormatException e) {
                System.out.println("CritterFileParser: The given file has invalid data: Integer expected.");
            }
        return c;
    }
}
