/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

/**
 *
 * @author nguye
 */
import javax.swing.*;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class MessageDialog extends JDialog {
    public MessageDialog(String message) {
        JLabel label = new JLabel(message);
        add(label);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }
}
