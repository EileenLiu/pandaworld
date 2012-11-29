/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

/**
 *
 * @author Eileen Liu <el544@cornell.edu>
 */
public class ControlPanel extends JPanel{
    public JButton stepButton, runButton, stopButton;
    public JRadioButton wait, random;
    public JSlider speedSlider;
    public static final int INDEFINITE = 0, FIXED_STEPS = 1, PROMPT_STEPS = 2;

    private static final int MIN_DELAY_MSECS = 33, MAX_DELAY_MSECS = 1000;
    private static final int INITIAL_DELAY = MIN_DELAY_MSECS
            + (MAX_DELAY_MSECS - MIN_DELAY_MSECS) / 2;

    public ControlPanel(){
        
        setLayout(new GridLayout(3,3));
        
        addSlider();
        addToggleButtons();
        addButtons();
        
    }
    private void addSlider()
    {
        speedSlider = new JSlider(MIN_DELAY_MSECS, MAX_DELAY_MSECS,
                INITIAL_DELAY);
        speedSlider.setInverted(true);
        speedSlider.setPreferredSize(new Dimension(100, speedSlider.getPreferredSize().height));
        speedSlider.setMaximumSize(speedSlider.getPreferredSize()); 
        JLabel slow = new JLabel("Slow", JLabel.RIGHT);
        JLabel fast = new JLabel("Fast", JLabel.LEFT);
        add(slow);
        add(speedSlider);
        add(fast);
    }
    private void addToggleButtons() {
        JLabel action = new JLabel("Action", JLabel.RIGHT);
        wait = new JRadioButton("Wait");
        wait.setMnemonic(KeyEvent.VK_W);
        wait.setActionCommand("Wait");
        wait.setSelected(true);
        random = new JRadioButton("Random");
        random.setMnemonic(KeyEvent.VK_R);
        random.setActionCommand("Random");
        ButtonGroup group = new ButtonGroup();
        group.add(wait);
        group.add(random);
        add(action);
        add(wait);
        add(random);
    }
        private void addButtons()
    {
        stepButton = new JButton("Step");
        runButton = new JButton("Run");
        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);
        add(stepButton);
        add(runButton);
        add(stopButton);
    }
    private JPanel generateButtonPanel()
    {
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1,3));
        stepButton = new JButton("Step");
        runButton = new JButton("Run");
        stopButton = new JButton("Stop");
        buttons.add(stepButton);
        buttons.add(runButton);
        buttons.add(stopButton);
        return buttons;
    }
    public void setEnabled(boolean b)
    {
        stepButton.setEnabled(b);
        runButton.setEnabled(b);
        stopButton.setEnabled(b);
        wait.setEnabled(b);
        random.setEnabled(b);
        speedSlider.setEnabled(b);
    }
    
}
