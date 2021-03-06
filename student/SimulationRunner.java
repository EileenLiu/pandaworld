package student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import student.config.Constants;
import student.config.WorldFileParser;
import student.gui.InteractionHandler;
import student.remote.login.LoginServer;
import student.remote.login.Permission;
import student.remote.server.AdminServerImpl;
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

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        try {
            Constants.loadFromFile(new File("constants.txt"));
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        World model = null;
        if (args.length>0) {
            try {
                model = WorldFileParser.generateWorld(new File(args[0]), Constants.MAX_ROW, Constants.MAX_COLUMN);
            } catch (FileNotFoundException ex) {
                System.err.println("File not found");
                System.exit(34);
            }
            System.out.println("Generate world from file");
        }
        else 
            model = new World(Constants.MAX_ROW,Constants.MAX_COLUMN);
        
        LoginServer ls = new LoginServer();
        ls.addUser("admin", "password");
        ls.grantPermission("admin", Permission.USER, Permission.ADMIN);
        ls.register("Server");
        
        AdminServerImpl asi = new AdminServerImpl(ls);
        
        InteractionHandler controller = new InteractionHandler();
    }
}   
