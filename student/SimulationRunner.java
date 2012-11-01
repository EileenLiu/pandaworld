package student;

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
        World model = new World(20,99);//new World(6,12);
        WorldFrame view = new WorldFrame(model);
        MouseInteractionHandler controller = new MouseInteractionHandler(model, view);
        /*view.addMouseListener(controller);
        view.worldDisplay.gridpane.addKeyListener(controller);
        view.setVisible(true);
        view.setDefaultCloseOperation(WorldFrame.EXIT_ON_CLOSE);
        controller.gameLoop();*/
    }
}   
