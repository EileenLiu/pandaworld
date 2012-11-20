/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import student.grid.Critter;
import student.world.World;
import student.world.World.InvalidWorldAdditionException;

/**
 *
 * @author haro
 */
public class WorldFileParser {
    private static Pattern comment = Pattern.compile("//");
    public static World generateWorld(String filename, int MAX_ROW, int MAX_COLUMN) {
        World world = null;
        try {
            Scanner infile = new Scanner(new File(filename));
            world = new World(MAX_ROW, MAX_COLUMN);
            while (infile.hasNext()) {
                String s = comment.split(infile.nextLine(), 2)[0];
//                System.out.println(s + s.startsWith("//"));
                if (!s.isEmpty()) //ignore empty lines or lines beginning with "//"
                {   
                    String arr[] = s.split(" ");
                    System.out.println(Arrays.asList(arr));
                    if (s.startsWith("critter ")) {
                        int row = Integer.parseInt(arr[2]);
                        int col = Integer.parseInt(arr[3]);
                        Critter crit = CritterFileParser.generateCritter(arr[1], world, world.at(row, col), Integer.parseInt(arr[4]));
                        world.add(crit, row, col);
                    } else {
                        world.add(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("WorldFileParser: The given file was not found.");
        } catch (NumberFormatException e) {
            System.out.println("WorldFileParser: The given file has invalid data: Integer expected.");
        } catch (InvalidWorldAdditionException ex) {
            System.out.println("WorldFileParser: The given file has invalid data: Item(s) could not be added to the world.");
        }
        return world;
    }
}
