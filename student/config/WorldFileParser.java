/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import student.grid.Critter;
import student.world.World;
import student.world.World.InvalidWorldAdditionException;

/**
 *
 * @author haro
 */
public class WorldFileParser {

    public static World generateWorld(String filename, int MAX_ROW, int MAX_COLUMN) {
        World world = null;
        try {
            Scanner infile = new Scanner(new File(filename));
            world = new World(MAX_ROW, MAX_COLUMN);
            while (infile.hasNext()) {
                String s = infile.nextLine();
                if (!s.isEmpty() || s.startsWith("//")) //ignore empty lines or lines beginning with "//"
                {
                    String arr[] = s.split(" ");
                    if (s.startsWith("critter ")) {
                        int row = Integer.parseInt(arr[2]);
                        int col = Integer.parseInt(arr[3]);
                        Critter crit = CritterFileParser.generateCritter(arr[1], world, world.at(row, col), Integer.parseInt(arr[4]));
                        world.addCritter(crit, row, col);
                    } else {
                        world.add(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("The given file was not found.");
        } catch (NumberFormatException e) {
            System.out.println("The given file has invalid data: Integer expected.");
        } catch (InvalidWorldAdditionException ex) {
            System.out.println("The given file has invalid data: Item(s) could not be added to the world.");
        }
        return world;
    }
}
