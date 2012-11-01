package student;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import student.grid.Constants;
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
            Constants.loadFromFile(new File("constants"));
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        World model = new World(20,99);//new World(6,12);
        WorldFrame view = new WorldFrame(model);
        MouseInteractionHandler controller = new MouseInteractionHandler(model, view);
    }
}   
