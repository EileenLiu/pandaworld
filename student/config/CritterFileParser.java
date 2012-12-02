/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import student.grid.Critter;
import student.grid.HexGrid;
import student.grid.RReference;
import student.grid.Tile;
import student.parse.ParserFactory;
import student.parse.Program;
import student.remote.server.PlayerServer;
import student.remote.server.RemoteCritter;
import student.remote.world.RWorld;
import student.world.World;
import student.world.World.InvalidWorldAdditionException;

/**
 *
 * @author haro
 */
public class CritterFileParser {

    /*public static Critter generateCritter(World world, HexGrid.Reference<Tile> pos, int direction) {
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
     }*/
    /**
     * Generates a critter from the specified file, in the given world and
     * location
     *
     * @param filename the given file
     * @param world the given world
     * @param _pos the given location
     * @return null
     */
    public static Critter generateCritter(Reader r, World world, HexGrid.Reference<Tile> pos, int direction) {
        Critter c = null;
        try {
            BufferedReader inStreamReader = new BufferedReader(r);
            String[] arr = new String[6];
            for (int i = 0; i < 6; i++) {
                arr[i] = inStreamReader.readLine();
                arr[i] = arr[i].substring(arr[i].indexOf(':') + 2);
            }
            Program program = ParserFactory.getParser().parse(inStreamReader);
            if (pos == null) {
                pos = world.randomLoc();
            }
            if (!(direction > 0 && direction < 6)) {
                c = new Critter(world, pos, program);
            } else {
                c = new Critter(world, pos, program, direction);
            }
            c.setDefense(Integer.parseInt(arr[1]));
            c.setOffense(Integer.parseInt(arr[2]));
            c.setSize(Integer.parseInt(arr[3]));
            c.setEnergy(Integer.parseInt(arr[4]));
            c.setAppearance(arr[5]);
            //System.out.println(program.prettyPrint());
            world.add(c, pos);

        } catch (InvalidWorldAdditionException ex) {
            System.err.println("Shouldn't happen");
        } catch (FileNotFoundException e) {
            System.out.println("CritterFileParser: The given file was not found.");
        } catch (IOException ex) {
            System.out.println("CritterFileParser: The given file is invalid.");
        } catch (NumberFormatException e) {
            System.out.println("CritterFileParser: The given file has invalid data: Integer expected.");
        }
        return c;
    }
    
    public static Critter generateCritter(String contents, World world, HexGrid.Reference<Tile> pos, int direction) {
        return generateCritter(new StringReader(contents), world, pos, direction);
    }
    
    public static Critter generateCritter(File file, World world, HexGrid.Reference<Tile> pos, int direction) {
        try {
            return generateCritter(new FileReader(file), world, pos, direction);
        } catch (FileNotFoundException ex) {
            System.err.println("Critter file not found");
            return null;
        }
    }
}
