/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import student.world.World;

/**
 *
 * @author Eileen Liu <el544@cornell.edu>
 */
public class ControlPanelInteractionHandler {

    private InteractionHandler masterController;
    private final WorldFrame view;
    ControlPanel cp;
    private Timer rntmr = new Timer("StepTimer",true);
    private TimerTask rntsk;
    private int period;

    public ControlPanelInteractionHandler(final InteractionHandler _parent){//final World _model, final WorldFrame _view) {
        masterController = _parent;
        view = masterController.getView();
        cp = view.worldDisplay.controls;
        cp.random.addActionListener(new ActionListener(){
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
        });
        cp.runButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                rntsk = new TmrTsk();
                period = cp.speedSlider.getValue();
                rntmr.schedule(rntsk, 0, period);
                //TODO: FIX, don't start timer from zero, save current time and start from that
                view.worldDisplay.controls.runButton.setEnabled(false);
                view.worldDisplay.controls.stopButton.setEnabled(true);
                view.worldDisplay.controls.stepButton.setEnabled(false);  
                masterController.getModel().toggleRun();
            }
        });
        cp.stopButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                rntsk.cancel();
                rntmr.purge();
                view.worldDisplay.controls.stopButton.setEnabled(false);
                view.worldDisplay.controls.stepButton.setEnabled(true);
                view.worldDisplay.controls.runButton.setEnabled(true); 
                masterController.getModel().toggleRun();
            }
        });
        cp.stepButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                masterController.getModel().step();
                view.repaint();
            }
        });
        cp.speedSlider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                rntsk.cancel();
                rntmr.purge();
                view.worldDisplay.controls.stopButton.setEnabled(false);
                view.worldDisplay.controls.stepButton.setEnabled(true);
                view.worldDisplay.controls.runButton.setEnabled(true); 
                masterController.getModel().toggleRun();
            }
        });
    }
    
    private class TmrTsk extends TimerTask {
        @Override
        public void run() {
            masterController.getModel().step();
            view.repaint();
        }
    }
}
