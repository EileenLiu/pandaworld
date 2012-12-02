/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import student.config.WorldFileParser;
import student.config.Constants;
import student.config.CritterFileParser;
import student.grid.Critter;
import student.remote.client.Client;
import student.remote.login.Permission;
import student.remote.server.AdminServer;
import student.remote.server.PlayerServer;
import student.world.World;
import student.world.World.InvalidWorldAdditionException;

/**
 *
 * @author Eileen Liu <el544@cornell.edu>
 */
public class MenuInteractionHandler {
    //private WorldFrame view;
    private InteractionHandler masterController;
    public MenuInteractionHandler(final InteractionHandler _parent){
        try {
            Permission perm = _parent.getLogin().hasPermission(Permission.ADMIN) ? Permission.ADMIN
                            : _parent.getLogin().hasPermission(Permission.USER)  ? Permission.USER
                                                                                 : Permission.WORLD;
            masterController = _parent;
            if(!masterController.isRemote()) {
                masterController.getView().importWorld.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        int returnVal = masterController.getView().fileSelector.showOpenDialog(masterController.getView());
                        if(returnVal == JFileChooser.APPROVE_OPTION) {
                            String filename = masterController.getView().fileSelector.getSelectedFile().getName();
                            World newImportedWorld;
                            try {
                                newImportedWorld = WorldFileParser.generateWorld(new File(filename), Constants.MAX_ROW , Constants.MAX_COLUMN);
                                masterController.setModel(newImportedWorld);
                            } catch (FileNotFoundException ex) {
                                System.err.println("File not found");
                                JOptionPane.showMessageDialog(masterController.getView(),
                                                              "File not found: " +filename, 
                                                              "File not found", 
                                                              JOptionPane.ERROR_MESSAGE);
                            } catch (RemoteException re) {
                                //Client.connectionError(masterController.getView());
                            }
                        }
                    }
                });
            } else if(perm == Permission.ADMIN) {// move into action performed
                masterController.getView().importWorld.addActionListener(new java.awt.event.ActionListener() {
                    @Override public void actionPerformed(ActionEvent e) {
                        int returnVal = masterController.getView().fileSelector.showOpenDialog(masterController.getView());
                        if(returnVal == JFileChooser.APPROVE_OPTION) {
                            try {
                                String filename = masterController.getView().fileSelector.getSelectedFile().getName();
                                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
                                StringBuilder sb = new StringBuilder();
                                String line;
                                while((line = br.readLine()) != null)
                                    sb.append(line);
                                ((AdminServer)masterController.getServer()).loadWorld(masterController.getLogin().getToken(), masterController.getLogin().getUser(), sb.toString());
                            } catch (IOException ex) {
                                //TODO: Logger.getLogger(MenuInteractionHandler.class.getName()).log(Level.SEVERE, null, ex);
                            } 
                        }
                    }
                });
            } else {
                masterController.getView().importWorld.setEnabled(false);
            }
            
            if(!masterController.isRemote())
                masterController.getView().importCritter.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        int returnVal = masterController.getView().fileSelector.showOpenDialog(masterController.getView());
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            String filename = masterController.getView().fileSelector.getSelectedFile().getName();
                            Critter newImportedCritter = CritterFileParser.generateCritter(new File(filename), masterController.getRealModel(), null, -1);
                            try {
                                masterController.getView().display().setCurrentLocation(masterController.getRealModel().add(newImportedCritter, newImportedCritter.loc()));
                                masterController.getView().repaint();
                            } catch (InvalidWorldAdditionException ex) {
                                System.out.println("Failed to import: Critter import file is invalid");
                            } catch (RemoteException re) {
                                System.err.println("UNREACHABLE");
                            }
                        }
                    }
                });
            else if (perm != Permission.WORLD)
                masterController.getView().importCritter.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        int returnVal = masterController.getView().fileSelector.showOpenDialog(masterController.getView());
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            try {
                                String filename = masterController.getView().fileSelector.getSelectedFile().getName(), temp;
                                StringBuffer data = new StringBuffer();
                                BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
                                while ((temp = in.readLine()) != null) {
                                    data.append(temp);
                                }
                                ((PlayerServer) masterController.getServer()).uploadCritter(masterController.getLogin().getToken(), masterController.getLogin().getUser(), data.toString());
                            } catch (IOException iOException) {
                                JOptionPane.showMessageDialog(masterController.getView(), "Failed to read file", "I/O error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                });
            else masterController.getView().importCritter.setEnabled(false);
                
            masterController.getView().importSettings.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    masterController.getView().fileSelector.showOpenDialog(masterController.getView());
                }
            });
            
            if(!masterController.isRemote())
                masterController.getView().createWorld.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        Object[] options = {"OK", "Cancel"};
                        int n = JOptionPane.showOptionDialog(masterController.getView(), "Destroy world and replace with a new one?",
                                "New world", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                        if (n == 0) {
                            try {
                                masterController.setModel(new World());
                            } catch (RemoteException ex) {
                                System.err.println("Could not export resetted world!");
                            }
                        }
                    }
                });
            else if(perm != Permission.WORLD)
                                masterController.getView().createWorld.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        Object[] options = {"OK", "Cancel"};
                        int n = JOptionPane.showOptionDialog(masterController.getView(), "Destroy world and replace with a new one?",
                                "New world", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                        if (n == 0) {
                            try {
                                ((AdminServer)masterController.getServer()).resetSim(masterController.getLogin().getToken(), masterController.getLogin().getUser());
                            } catch (RemoteException ex) {
                                System.err.println("Could not export resetted world!");
                            }
                        }
                    }
                });
            else masterController.getView().createWorld.setEnabled(false);
            /*
            masterController.getView().createCritter.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    Object[] options = {"OK", "Cancel"};
                    int n = JOptionPane.showOptionDialog(masterController.getView(), "Add a critter to a random location?",
                            "Create critter", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                    if (n == 0) {
                        Critter newCritter = new Critter(masterController.getModel(), null, null);
                        try {
                            masterController.getView().display().setCurrentLocation(masterController.getModel().add(newCritter, newCritter.loc()));
                            masterController.getView().repaint();
                        } catch (InvalidWorldAdditionException ex) {
                            System.out.println("Unreachable code");
                        } catch (RemoteException re) {
                            System.err.println("Failed to import: could not export tile");
                        }
                    }
                }
            });
            */
            
            if(!masterController.isRemote())
                masterController.getView().createPlant.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        Object[] options = {"OK", "Cancel"};
                        int n = JOptionPane.showOptionDialog(masterController.getView(), "Add a plant to a random location?",
                                "Create plant", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                        if (n == 0) {
                            try {
                                masterController.getView().display().setCurrentLocation(masterController.getRealModel().add("plant", null));
                            } catch (InvalidWorldAdditionException ex) {
                                System.err.println("Unreachable");
                            } catch (RemoteException ex) {
                                System.err.println("Unreachable");
                            }
                            masterController.getView().repaint();
                        }
                    }
                });
            /*else if(perm == Permission.ADMIN)
                masterController.getView().createPlant.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        Object[] options = {"OK", "Cancel"};
                        int n = JOptionPane.showOptionDialog(masterController.getView(), "Add a plant to a random location?",
                                "Create plant", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                        if (n == 0) 
                            masterController.getView().display().setCurrentLocation(masterController.getModel().add("plant", null));
                            masterController.getView().repaint();
                        }
                    }
                });*/
            else masterController.getView().createPlant.setEnabled(false);
            
            if(!masterController.isRemote())
                masterController.getView().createRock.addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        Object[] options = {"OK", "Cancel"};
                        int n = JOptionPane.showOptionDialog(masterController.getView(), "Add a rock to a random location?",
                                "Create rock", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                        if (n == 0) {
                            try {
                                masterController.getView().display().setCurrentLocation(masterController.getRealModel().add("rock", null));
                                masterController.getView().repaint();
                            } catch (InvalidWorldAdditionException ex) {
                                System.out.println("Unreachable code");
                            } catch (RemoteException re) {
                                System.err.println("Failed to import: could not export tile");
                            }
                        }
                    }
                });
            else masterController.getView().createRock.setEnabled(false);
        } catch (RemoteException ex) {
            Logger.getLogger(MenuInteractionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
