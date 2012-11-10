package student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import student.config.Constants;
import student.config.WorldFileParser;
import student.gui.InteractionHandler;
import student.gui.MouseInteractionHandler;
import student.gui.WorldFrame;
import student.world.World;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Panda
 */
public class SimulationRunner {

    public static void main(String[] args) {
        try {
            Constants.loadFromFile(new File("constants.txt"));
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        World model = null;
        if (args.length>0) {
            model = WorldFileParser.generateWorld(args[0], Constants.MAX_ROW, Constants.MAX_COLUMN);
            System.out.println("Generate world from file");
        }
        model = new World(Constants.MAX_ROW,Constants.MAX_COLUMN);
        WorldFrame view = new WorldFrame(model);
        InteractionHandler controller = new InteractionHandler(model, view);
    }
}   
