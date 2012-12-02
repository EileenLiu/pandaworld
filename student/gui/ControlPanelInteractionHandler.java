/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import student.remote.client.Client;
import student.remote.login.Permission;
import student.remote.server.AdminServer;
import student.world.World;

/**
 *
 * @author Eileen Liu <el544@cornell.edu>
 */
public class ControlPanelInteractionHandler {

    private InteractionHandler masterController;
    //private final WorldFrame view;
    ControlPanel cp;
    private Timer rntmr = new Timer("StepTimer",true);
    private TmrTsk rntsk;
    private int period;

    public ControlPanelInteractionHandler(final InteractionHandler _parent){//final World _model, final WorldFrame _view) {
        masterController = _parent;
        //view = masterController.getView();
        cp = masterController.getView().worldDisplay.controls;
        
        /*cp.random.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                masterController.getModel().toggleWait();
            }
        });
        cp.wait.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                masterController.getModel().toggleWait();
            }
        });*/
        cp.runButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                //rntsk.setRunning(true);
                rntsk = new TmrTsk();
                period = cp.speedSlider.getValue();
                rntmr.schedule(rntsk, 0, period);
                //TODO: FIX, don't start timer from zero, save current time and start from that
                masterController.getView().worldDisplay.controls.runButton.setEnabled(false);
                masterController.getView().worldDisplay.controls.stopButton.setEnabled(true);
                masterController.getView().worldDisplay.controls.stepButton.setEnabled(false);  
                //masterController.getModel().toggleRun();
            }
        });
        cp.stopButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                rntsk.cancel();
                rntmr.purge();
                //rntsk.setRunning(false);
                masterController.getView().worldDisplay.controls.stopButton.setEnabled(false);
                masterController.getView().worldDisplay.controls.stepButton.setEnabled(true);
                masterController.getView().worldDisplay.controls.runButton.setEnabled(true); 
                //masterController.getModel().toggleRun();
            }
        });
        cp.stepButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                new TmrTsk().run();
            }
        });
        cp.speedSlider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                rntsk.cancel();
                rntmr.purge();
                //rntsk.setRunning(false);
                masterController.getView().worldDisplay.controls.stopButton.setEnabled(false);
                masterController.getView().worldDisplay.controls.stepButton.setEnabled(true);
                masterController.getView().worldDisplay.controls.runButton.setEnabled(true); 
                //masterController.getModel().toggleRun();
            }
        });
    }
    
    private class TmrTsk extends TimerTask {
        /*private boolean running = true;
        public void setRunning(boolean b){
            running = b;
        }*/
        @Override
        public void run() {
            //if(running){
            try {
                if (!masterController.isRemote())
                    masterController.getRealModel().step();
                else if (masterController.getLogin().hasPermission(Permission.ADMIN)) 
                    ((AdminServer) masterController.getServer()).simStep(masterController.getLogin().getToken(), masterController.getLogin().getUser());
            } catch (RemoteException ex) {
                Client.connectionError(masterController.getView());
            }
            masterController.getView().repaint();
            //}
       }
    }
}
