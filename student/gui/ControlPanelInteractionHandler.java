/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import student.world.World;

/**
 *
 * @author Eileen Liu <el544@cornell.edu>
 */
public class ControlPanelInteractionHandler {

    private World model;
    private WorldFrame view;
    private Timer rntmr = new Timer("StepTimer",true);
    private TimerTask rntsk;
    private long period = 100;

    public ControlPanelInteractionHandler(final World _model, final WorldFrame _view) {
        model = _model;
        view = _view;
        view.worldDisplay.controls.random.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                model.toggleWait();
            }
        });
        view.worldDisplay.controls.wait.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                model.toggleWait();
            }
        });
        view.worldDisplay.controls.runButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                rntsk = new TmrTsk();
                rntmr.schedule(rntsk, 0, period);
                view.worldDisplay.controls.runButton.setEnabled(false);
                view.worldDisplay.controls.stopButton.setEnabled(true);
                view.worldDisplay.controls.stepButton.setEnabled(false);  
                model.toggleRun();
            }
        });
        view.worldDisplay.controls.stopButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                rntsk.cancel();
                rntmr.purge();
                view.worldDisplay.controls.stopButton.setEnabled(false);
                view.worldDisplay.controls.stepButton.setEnabled(true);
                view.worldDisplay.controls.runButton.setEnabled(true); 
                model.toggleRun();
            }
        });
        view.worldDisplay.controls.stepButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                model.step();
            }
        });
    }
    
    private class RunTask extends TimerTask {
        @Override
        public void run() {
            //put the step code here
        }
    }
    
    private class TmrTsk extends TimerTask {
        @Override
        public void run() {
            model.step();
        }
    }
}
