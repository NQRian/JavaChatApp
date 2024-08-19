/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import java.awt.Component;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author nguye
 */
  class FriendListCellRenderer extends DefaultListCellRenderer {
    private final String username;
    private final ImageIcon iconOnline;
    private final ImageIcon iconOffline;
    private final DefaultListModel<String> listModel;
    
    public FriendListCellRenderer(String username, DefaultListModel<String> listModel) {
        this.username = username;
        this.listModel = listModel;
        iconOnline = new ImageIcon(getClass().getResource("/icon/online.png"));
        iconOffline = new ImageIcon(getClass().getResource("/icon/offline.png"));
    }
    
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        String friendName = value.toString();
        String friendStatus = DBConnection.getUserFriendStatus(username, friendName);
        if ("Online".equals(friendStatus)) {
            label.setIcon(iconOnline);
        } else {
            label.setIcon(iconOffline);
        }
        
        return label;
    }
}