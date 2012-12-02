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
    public final String hostname, servername, password, username;
    private JTextField hostfield, userfield, serverfield;
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
        username = userfield.getText();
        password = new String(passwordfield.getPassword());
    }
    private JPanel generateLabels(){
        JLabel label_hostname = new JLabel("Hostname: \t");
        JLabel label_servername = new JLabel("Servername: \t");
        JLabel label_username = new JLabel("Username: \t");
        JLabel label_password = new JLabel("Password: \t");
        JPanel labelgroup = new JPanel();
        labelgroup.setLayout(new GridLayout(3, 1));
        labelgroup.add(label_hostname);
        labelgroup.add(label_servername);
        labelgroup.add(label_username);
        labelgroup.add(label_password);
        return labelgroup;
    }
    private JPanel generateFields(){
        (hostfield = new JTextField(15)).setText("localhost");
        (serverfield = new JTextField(15)).setText("Server");
        (userfield = new JTextField(15)).setText("admin");
        (passwordfield = new JPasswordField()).setText("password");
        JPanel fieldgroup = new JPanel();
        fieldgroup.setLayout(new GridLayout(3, 1));
        fieldgroup.add(hostfield);
        fieldgroup.add(serverfield);
        fieldgroup.add(userfield);
        fieldgroup.add(passwordfield);
        return fieldgroup;
    }
}
