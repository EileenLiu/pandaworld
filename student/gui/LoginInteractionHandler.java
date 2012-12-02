/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package student.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Eileen Liu <el544@cornell.edu>
 */
public class LoginInteractionHandler {

    private InteractionHandler masterController;
    private String hostname, servername, password;
    private JTextField hostfield, serverfield;
    private JPasswordField passwordfield;
    
    public LoginInteractionHandler(final InteractionHandler _parent) {
        masterController = _parent;
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout(0,20));
        content.add(generateLabels(), BorderLayout.WEST);
        content.add(generateFields(), BorderLayout.CENTER);
        Object[] options = {"Login", "Cancel"};
        int result = JOptionPane.showOptionDialog(masterController.getView(), content, "Login", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        hostname = hostfield.getText();
        servername = serverfield.getText();
        password = new String(passwordfield.getPassword());
    }
    private JPanel generateLabels(){
        JLabel label_hostname = new JLabel("Hostname: \t");
        JLabel label_servername = new JLabel("Servername: \t");
        JLabel label_password = new JLabel("Password: \t");
        JPanel labelgroup = new JPanel();
        labelgroup.setLayout(new GridLayout(3, 1));
        labelgroup.add(label_hostname);
        labelgroup.add(label_servername);
        labelgroup.add(label_password);
        return labelgroup;
    }
    private JPanel generateFields(){
        hostfield = new JTextField(15);
        serverfield = new JTextField(15);
        passwordfield = new JPasswordField();
        JPanel fieldgroup = new JPanel();
        fieldgroup.setLayout(new GridLayout(3, 1));
        fieldgroup.add(hostfield);
        fieldgroup.add(serverfield);
        fieldgroup.add(passwordfield);
        return fieldgroup;
    }
}
