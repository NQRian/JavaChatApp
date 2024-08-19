package test;

import javaswingdev.drawer.DrawerItem;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaswingdev.drawer.Drawer;
import javaswingdev.drawer.DrawerController;
import javaswingdev.drawer.EventDrawer;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.text.Utilities;

public class Test extends javax.swing.JFrame {

    private DrawerController drawer;
    public String username;
    String host;
    int port;
    Socket socket;
    DataOutputStream dos;
    private String selectedUserFriend;
      private String selectedUserGlobal;
    private String selectedGroup;
      public  String a;
    List<String> friendNames;
    List<String> groupNames;
    public boolean attachmentOpen = false;
    private boolean isConnected = false;
    private boolean isTextAddedToLine = false; // Biến kiểm tra xem đã thêm văn bản vào dòng chưa
     private boolean isItemSelected = false; // Biến cờ để kiểm tra xem đã có mục được chọn hay chưa
    private String mydownloadfolder = "D:\\";
     DefaultListModel<String> listModel2 = new DefaultListModel<>();     
     private boolean isClicked = false;
   public Test() {
        initComponents();
        
        jPanel8.setVisible(false);
        drawer = Drawer.newDrawer(this)
                .background(new Color(90, 90, 90))
                .closeOnPress(true)
                .backgroundTransparent(0.3f)
                .leftDrawer(true)
                .enableScroll(true)
                .enableScrollUI(false)
                .headerHeight(160)
                .header(new Header())
                .space(3)
                .addChild(new DrawerItem("Friend ").icon(new ImageIcon(getClass().getResource("/icon/contacts.png"))).build())
                .addChild(new DrawerItem("Group").icon(new ImageIcon(getClass().getResource("/icon/group.png"))).build())
                .addChild(new DrawerItem("Chat").icon(new ImageIcon(getClass().getResource("/icon/chat.png"))).build())
                .addChild(new DrawerItem("Account").icon(new ImageIcon(getClass().getResource("/icon/personal.png"))).build())
                .addFooter(new DrawerItem("Log out").icon(new ImageIcon(getClass().getResource("/icon/logout.png"))).build())
                .event(new EventDrawer() {
                    @Override
                    public void selected(int index, DrawerItem item) {
                     if(drawer.isShow()){
                         drawer.hide();
                     }
                   // Xử lý sự kiện cho các item trong drawer
                    switch (index) {
                        case 0: // Item "Friend"
                            // Xử lý khi chọn item "Friend"
                           jPanel1.setVisible(true);
                           jPanel2.setVisible(false);
                           jPanel3.setVisible(false);
                           jPanel4.setVisible(false);
                           jPanel5.setVisible(false); 
                           jPanel8.setVisible(false);
                           jTextPane1.setEditable(true);
                           jTextPane1.setText("");
                           jTextPane1.setEditable(false);
                           jTextPane2.setEditable(true);
                           jTextPane2.setText("");
                           jTextPane2.setEditable(false);
                           jTextPane4.setEditable(true);
                           jTextPane4.setText("");
                           jTextPane4.setEditable(false);
                           RequestAll();
                           updateGroupList();
                           updateFriendList();
                            break;
                        case 1: // Item "Group"
                            // Xử lý khi chọn item "Group"
                           jPanel1.setVisible(false);
                           jPanel2.setVisible(true);
                           jPanel3.setVisible(false);
                           jPanel4.setVisible(false);
                           jPanel5.setVisible(false);
                           jPanel9.setVisible(false);
                           jTextPane1.setEditable(true);
                           jTextPane1.setText("");
                           jTextPane1.setEditable(false);
                           jTextPane2.setEditable(true);
                           jTextPane2.setText("");
                           jTextPane2.setEditable(false);
                           jTextPane4.setEditable(true);
                           jTextPane4.setText("");
                           jTextPane4.setEditable(false);
                              RequestAll();
                           updateGroupList();
                           updateFriendList();
                            break;
                        case 2: // Item "Chat"
                            // Xử lý khi chọn item "Chat"
                           jPanel1.setVisible(false);
                           jPanel2.setVisible(false);
                           jPanel3.setVisible(true);
                           jPanel4.setVisible(false);
                           jPanel5.setVisible(false);
                           jPanel7.setVisible(false);
                           jTextPane1.setEditable(true);
                           jTextPane1.setText("");
                           jTextPane1.setEditable(false);
                           jTextPane2.setEditable(true);
                           jTextPane2.setText("");
                           jTextPane2.setEditable(false);
                           jTextPane4.setEditable(true);
                           jTextPane4.setText("");
                           jTextPane4.setEditable(false);
                            RequestAll();
                           updateGroupList();
                           updateFriendList();
                            break;
                        case 3: // Item "Account"
                            // Xử lý khi chọn item "Account"
                            jPanel1.setVisible(false);
                           jPanel2.setVisible(false);
                           jPanel3.setVisible(false);
                           jPanel4.setVisible(true);
                           jPanel5.setVisible(false);
                           txtUser1.setEditable(false);
                           txtPass2.setEditable(false);
                           txtUser4.setEditable(false);
                           jComboBox1.setEnabled(false);
                           txtUser3.setEditable(false); 
                           jTextPane1.setEditable(true);
                           jTextPane1.setText("");
                           jTextPane1.setEditable(false);
                           jTextPane2.setEditable(true);
                           jTextPane2.setText("");
                           jTextPane2.setEditable(false);
                           jTextPane4.setEditable(true);
                           jTextPane4.setText("");
                           jTextPane4.setEditable(false);
                           RequestAll();
                           updateGroupList();
                           updateFriendList();
                            break;
                        case 4: // Item "Log out"
                            // Xử lý khi chọn item "Log out"
                            Main main = new Main();
                            main.setVisible(true);
                            dispose();
                            break;
                        default:
                      
                           jPanel1.setVisible(false);
                           jPanel2.setVisible(false);
                           jPanel3.setVisible(true);
                           jPanel4.setVisible(false);
                           jPanel5.setVisible(false);
                           jTextPane1.setEditable(true);
                           jTextPane1.setText("");
                           jTextPane1.setEditable(false);
                           jTextPane2.setEditable(true);
                           jTextPane2.setText("");
                           jTextPane2.setEditable(false);
                           jTextPane4.setEditable(true);
                           jTextPane4.setText("");
                           jTextPane4.setEditable(false);
                             
                            break;
                    }
                }
            })
                .build();
    }
  public void initFrame1(String username,String host,int port,String hasedPassword,String fullname,String gender,String dob) {
        this.username = username;
        this.host = host;
        this.port = port;
        register(hasedPassword,fullname,gender,dob);
       }
       public void initFrame(String username, String host, int port){
        this.username = username;
        this.host = host;
        this.port = port;
        setTitle("Bạn đang được đăng nhập với tên: " + username);
        //Kết nối 
        connect();
       RequestAll();
    }
       public void initFrame2(String username,String host,int port) {
        this.username = username;
        this.host = host;
        this.port = port;
        setTitle("Bạn đang được đăng nhập với tên: " + username);
       }
         public void register(String hasedPassword,String fullname,String gender,String dob){
        try {
            socket = new Socket(host, port);
            dos = new DataOutputStream(socket.getOutputStream());
            // gửi username đang kết nối
            String content = username + " " + hasedPassword + " " + fullname + " " + gender + " " + dob;
            dos.writeUTF("CMD_REGISTER "+ content);     
            // Khởi động Client Thread 
            new Thread(new Client(socket, this)).start();
            // đã được kết nối
            isConnected = true;    
            socket.close();
        }
        catch(IOException e) {
            isConnected = false;
            JOptionPane.showMessageDialog(this, "Không thể kết nối đến máy chủ, vui lòng thử lại sau.!","Kết nối thất bại",JOptionPane.ERROR_MESSAGE);
            appendMessage("[IOException]: "+ e.getMessage(), "Lỗi", Color.RED, Color.RED);
        }
    }
      
  public void connect(){
//        appendMessage(" Đang kết nối...", "Trạng thái", Color.GREEN, Color.GREEN);
        try {
            socket = new Socket(host, port);
            dos = new DataOutputStream(socket.getOutputStream());
            // gửi username đang kết nối
            dos.writeUTF("CMD_LOGIN "+ username);         
//            appendMessage(" Đã kết nối", "Trạng thái", Color.GREEN, Color.GREEN);
//            appendMessage(" gửi tin nhắn bây giờ.!", "Trạng thái", Color.GREEN, Color.GREEN);
//           
            // Khởi động Client Thread 
            new Thread(new Client(socket, this)).start();
            jButton1.setEnabled(true);
            // đã được kết nối
            isConnected = true;
            
        }
        catch(IOException e) {
            isConnected = false;
            JOptionPane.showMessageDialog(this, "Không thể kết nối đến máy chủ, vui lòng thử lại sau.!","Kết nối thất bại",JOptionPane.ERROR_MESSAGE);
            appendMessage("[IOException]: "+ e.getMessage(), "Lỗi", Color.RED, Color.RED);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jButton21 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextPane3 = new javax.swing.JTextPane();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextPane4 = new javax.swing.JTextPane();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jButton17 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtUser1 = new com.raven.swing.TextField();
        cmdSignIn3 = new com.raven.swing.Button();
        txtPass2 = new com.raven.swing.PasswordField();
        jComboBox1 = new javax.swing.JComboBox<>();
        txtUser2 = new com.raven.swing.TextField();
        txtUser3 = new com.raven.swing.TextField();
        txtUser4 = new com.raven.swing.TextField();
        cmdSignIn8 = new com.raven.swing.Button();
        jPanel5 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton18 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jList4 = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(201, 199, 255));

        jButton1.setText("|||");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jList1);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("FriendList");

        jTextPane1.setEditable(false);
        jScrollPane2.setViewportView(jTextPane1);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-sticker-30.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-send-file-30.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jTextField1.setText("Type your message....");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-send-30 (1).png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(201, 199, 255));

        jLabel3.setText("jLabel3");

        jLabel2.setText("jLabel2");

        jButton9.setText("Block");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton9)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8))
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(201, 199, 255));

        jButton2.setText("|||");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(jList2);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Group Chat");
        jLabel4.setToolTipText("");

        jTextPane2.setEditable(false);
        jScrollPane4.setViewportView(jTextPane2);

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-sticker-30.png"))); // NOI18N
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-send-file-30.png"))); // NOI18N
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jTextField2.setText("Type your message....");

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-send-30 (1).png"))); // NOI18N
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton19.setText("+");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jPanel9.setBackground(new java.awt.Color(201, 199, 255));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("jLabel5");

        jButton21.setText("Block");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jButton21)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton21)
                .addGap(13, 13, 13)
                .addComponent(jLabel5))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(78, 78, 78))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton13)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane4)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                            .addComponent(jButton19)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(4, 4, 4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(201, 199, 255));

        jButton3.setText("|||");
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextPane3.setEditable(false);
        jScrollPane5.setViewportView(jTextPane3);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Global User");

        jTextPane4.setEditable(false);
        jScrollPane6.setViewportView(jTextPane4);

        jButton15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-sticker-30.png"))); // NOI18N
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-send-file-30.png"))); // NOI18N
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jTextField3.setText("Type your message....");

        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-send-30 (1).png"))); // NOI18N
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(201, 199, 255));

        jLabel8.setText("jLabel8");

        jLabel7.setText("jLabel7");

        jButton14.setText("Block");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 18, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton14)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jButton3)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane6)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton15, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                            .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(201, 199, 255));

        jButton4.setText("|||");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(5, 151, 230));
        jPanel6.setOpaque(false);

        jLabel9.setBackground(new java.awt.Color(201, 199, 255));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Cập nhật thông tin tài khoản");
        jLabel9.setToolTipText("");

        txtUser1.setBackground(new java.awt.Color(201, 199, 255));
        txtUser1.setLabelText("User Name");
        txtUser1.setLineColor(new java.awt.Color(131, 126, 253));
        txtUser1.setSelectionColor(new java.awt.Color(157, 153, 255));
        txtUser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUser1ActionPerformed(evt);
            }
        });

        cmdSignIn3.setBackground(new java.awt.Color(157, 153, 255));
        cmdSignIn3.setForeground(new java.awt.Color(255, 255, 255));
        cmdSignIn3.setText("Change");
        cmdSignIn3.setEffectColor(new java.awt.Color(199, 196, 255));
        cmdSignIn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSignIn3ActionPerformed(evt);
            }
        });

        txtPass2.setBackground(new java.awt.Color(201, 199, 255));
        txtPass2.setLabelText("Password");
        txtPass2.setLineColor(new java.awt.Color(131, 126, 253));
        txtPass2.setSelectionColor(new java.awt.Color(157, 153, 255));
        txtPass2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPass2ActionPerformed(evt);
            }
        });

        jComboBox1.setBackground(new java.awt.Color(201, 199, 255));
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ", "Khác" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        txtUser2.setEditable(false);
        txtUser2.setBackground(new java.awt.Color(201, 199, 255));
        txtUser2.setLabelText("Giới tính");
        txtUser2.setLineColor(new java.awt.Color(131, 126, 253));
        txtUser2.setSelectionColor(new java.awt.Color(157, 153, 255));
        txtUser2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUser2ActionPerformed(evt);
            }
        });

        txtUser3.setBackground(new java.awt.Color(201, 199, 255));
        txtUser3.setText("yy/mm/dd");
        txtUser3.setLabelText("Ngày sinh");
        txtUser3.setLineColor(new java.awt.Color(131, 126, 253));
        txtUser3.setSelectionColor(new java.awt.Color(157, 153, 255));
        txtUser3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUser3ActionPerformed(evt);
            }
        });

        txtUser4.setBackground(new java.awt.Color(201, 199, 255));
        txtUser4.setLabelText("Họ tên");
        txtUser4.setLineColor(new java.awt.Color(131, 126, 253));
        txtUser4.setSelectionColor(new java.awt.Color(157, 153, 255));
        txtUser4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUser4ActionPerformed(evt);
            }
        });

        cmdSignIn8.setBackground(new java.awt.Color(157, 153, 255));
        cmdSignIn8.setForeground(new java.awt.Color(255, 255, 255));
        cmdSignIn8.setText("Save");
        cmdSignIn8.setEffectColor(new java.awt.Color(199, 196, 255));
        cmdSignIn8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSignIn8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtUser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPass2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUser3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUser4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                        .addComponent(txtUser2, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 3, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(cmdSignIn3, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmdSignIn8, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtUser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(txtPass2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUser4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(txtUser3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdSignIn3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdSignIn8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jButton4)
                .addGap(0, 831, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(177, 177, 177)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(189, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jButton4)
                .addGap(0, 578, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(52, 52, 52)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(52, Short.MAX_VALUE)))
        );

        jPanel5.setBackground(new java.awt.Color(201, 199, 255));

        jButton5.setText("|||");
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTextField4.setEditable(false);
        jTextField4.setText("jTextField1");

        jTextField5.setText("Hãy đặt tên nhóm");
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jScrollPane7.setViewportView(jList3);

        jLabel10.setText("Tên thành viên");

        jLabel11.setText("Tên nhóm");

        jLabel12.setText("Tên người tạo");

        jButton18.setText("OK");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jLabel13.setText("Thành viên chọn");

        jScrollPane8.setViewportView(jList4);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jButton5)
                .addGap(0, 831, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(120, 120, 120)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField4)
                                            .addComponent(jTextField5)))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel13))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGap(263, 263, 263)
                            .addComponent(jButton18)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 316, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(121, 121, 121)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jButton5)
                .addGap(0, 578, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(9, 9, 9)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12))
                    .addGap(25, 25, 25)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel10)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(31, 31, 31)
                    .addComponent(jLabel13)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 177, Short.MAX_VALUE)
                    .addComponent(jButton18)
                    .addGap(10, 10, 10)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(16, 16, 16)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(16, 16, 16)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(16, 16, 16)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(16, 16, 16)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (drawer.isShow()) {
            drawer.hide();
        } else {
            drawer.show();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (drawer.isShow()) {
            drawer.hide();
        } else {
            drawer.show();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (drawer.isShow()) {
            drawer.hide();
        } else {     
            drawer.show();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if (drawer.isShow()) {
            drawer.hide();
        } else {
            drawer.show();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if (drawer.isShow()) {
            drawer.hide();
        } else {
            drawer.show();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtUser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUser1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUser1ActionPerformed

    private void cmdSignIn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSignIn3ActionPerformed
        txtPass2.setEditable(true);
        txtUser4.setEditable(true);
        jComboBox1.setEnabled(true);
        txtUser3.setEditable(true);
    }//GEN-LAST:event_cmdSignIn3ActionPerformed

    private void txtPass2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPass2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPass2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void txtUser2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUser2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUser2ActionPerformed

    private void txtUser3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUser3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUser3ActionPerformed

    private void txtUser4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUser4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUser4ActionPerformed

    private void cmdSignIn8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSignIn8ActionPerformed
    requestupdateInfo();
    }//GEN-LAST:event_cmdSignIn8ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
        // Lấy dữ liệu từ jTextField
        createGroup();
        jPanel5.setVisible(false);
        jPanel2.setVisible(true);
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        sendMessage();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        showStickerList();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
          if(selectedUserFriend ==null) {
             // Hiển thị thông báo cho người dùng chưa chọn người nhận tin nhắn
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một người dùng để gửi tin nhắn!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            if(!attachmentOpen){
            SendFile s = new SendFile();
            if(s.prepare(username,selectedUserFriend, host, port, this)){
                s.setLocationRelativeTo(null);
                s.setVisible(true);
                attachmentOpen = true;
            } else {
                JOptionPane.showMessageDialog(this, "Không thể thiết lập Chia sẻ File tại thời điểm này, xin vui lòng thử lại sau.!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
          showStickerList2();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
        sendMessage2();
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
  if(selectedUserGlobal ==null) {
             // Hiển thị thông báo cho người dùng chưa chọn người nhận tin nhắn
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một người dùng để gửi tin nhắn!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            if(!attachmentOpen){
            SendFile s = new SendFile();
            if(s.prepare(username,selectedUserGlobal, host, port, this)){
                s.setLocationRelativeTo(null);
                s.setVisible(true);
                attachmentOpen = true;
            } else {
                JOptionPane.showMessageDialog(this, "Không thể thiết lập Chia sẻ File tại thời điểm này, xin vui lòng thử lại sau.!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        // TODO add your handling code here:
        jPanel5.setVisible(true);
        jPanel1.setVisible(false);
        jPanel2.setVisible(false);
        jPanel3.setVisible(false);
        jPanel4.setVisible(false);
        RequestAll();
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        showStickerList1();
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        sendMessage1();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
      // TODO add your handling code here:
    if (jButton9.getText().equals("Block")) {
        DBConnection.blockUser(username, selectedUserFriend);
        jButton9.setText("Unblock");
         JOptionPane.showMessageDialog(this, "Đã block người dùng " + selectedUserFriend, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    } else {
        DBConnection.unblockUser(username, selectedUserFriend);
        jButton9.setText("Block");
         JOptionPane.showMessageDialog(this, "Đã unblock người dùng " + selectedUserFriend, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
           // TODO add your handling code here:
    if (jButton14.getText().equals("Block")) {
        DBConnection.blockUser(username, selectedUserGlobal);
        jButton14.setText("Unblock");
          JOptionPane.showMessageDialog(this, "Đã block người dùng " + selectedUserGlobal, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    } else {
        DBConnection.unblockUser(username, selectedUserGlobal);
        jButton14.setText("Block");
         JOptionPane.showMessageDialog(this, "Đã unblock người dùng " + selectedUserGlobal, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
     if (jButton21.getText().equals("Block")) {
        if (DBConnection.isGroupBlocked(username, DBConnection.getGroupIdByGroupNameAndUsername(selectedGroup,username))) {
            JOptionPane.showMessageDialog(this, "Nhóm " + selectedGroup + " đã bị chặn.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            DBConnection.blockGroup(username, DBConnection.getGroupIdByGroupNameAndUsername(selectedGroup,username));
            jButton21.setText("Unblock");
            JOptionPane.showMessageDialog(this, "Đã block nhóm " + selectedGroup, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    } else {
        DBConnection.unblockGroup(username, DBConnection.getGroupIdByGroupNameAndUsername(selectedGroup,username));
        jButton21.setText("Block");
        JOptionPane.showMessageDialog(this, "Đã unblock nhóm " + selectedGroup, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        if(selectedGroup ==null) {
             // Hiển thị thông báo cho người dùng chưa chọn người nhận tin nhắn
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 nhóm để gửi!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            if(!attachmentOpen){
            SendFile s = new SendFile();
            if(s.prepare(username,selectedGroup, host, port, this)){
                s.setLocationRelativeTo(null);
                s.setVisible(true);
                attachmentOpen = true;
            } else {
                JOptionPane.showMessageDialog(this, "Không thể thiết lập Chia sẻ File tại thời điểm này, xin vui lòng thử lại sau.!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        }        //
    }//GEN-LAST:event_jButton12ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Test().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.swing.Button cmdSignIn3;
    private com.raven.swing.Button cmdSignIn8;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JList<String> jList3;
    private javax.swing.JList<String> jList4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JTextPane jTextPane3;
    private javax.swing.JTextPane jTextPane4;
    private com.raven.swing.PasswordField txtPass2;
    private com.raven.swing.TextField txtUser1;
    private com.raven.swing.TextField txtUser2;
    private com.raven.swing.TextField txtUser3;
    private com.raven.swing.TextField txtUser4;
    // End of variables declaration//GEN-END:variables
  public String getMyUsername(){
        return this.username;
    }
    /*
        Được kết nối
    */
    public boolean isConnected(){
        return this.isConnected;
    }
    private void performDeleteAction() {
    String groupName = selectedGroup; // Tên nhóm cần xóa

    boolean deleted = DBConnection.deleteGroup(groupName); // Gọi hàm xóa nhóm

    if (deleted) {
        JOptionPane.showMessageDialog(this, "Đã xóa nhóm " + groupName, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this, "Xóa nhóm thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
}
       public void appendMessage(String msg, String header, Color headerColor, Color contentColor){
        jTextPane1.setEditable(true);
        getMsgHeader(header, headerColor);
        getMsgContent(msg, contentColor);
        jTextPane1.setEditable(false);
    }
    public void appendServerMessage(String serverMsg) {
          JOptionPane.showMessageDialog(this, "Tin nhắn từ server: " + " "+serverMsg, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    /*
        Tin nhắn chat
    */
    public void appendMyMessage(String msg, String header){
        jTextPane1.setEditable(true);
        getMsgHeader(header, Color.ORANGE);
        getMsgContent(msg, Color.LIGHT_GRAY);
        jTextPane1.setEditable(false);
    }
    /*
        Tiêu đề tin nhắn
    */
    public void getMsgHeader(String header, Color color){
        int len = jTextPane1.getDocument().getLength();
        jTextPane1.setCaretPosition(len);
        jTextPane1.setCharacterAttributes(MessageStyle.styleMessageContent(color, "Impact", 13), false);
        jTextPane1.replaceSelection(header+":");
    }
    /*
        Nội dung tin nhắn
    */
    public void getMsgContent(String msg, Color color){
        int len = jTextPane1.getDocument().getLength();
        jTextPane1.setCaretPosition(len);
        jTextPane1.setCharacterAttributes(MessageStyle.styleMessageContent(color, "Arial", 12), false);
        jTextPane1.replaceSelection(msg +"\n\n");
    
}
     public void appendOnlineList(Vector list){
        sampleOnlineList(list); 
        
    }
    
    /*
        Hiển thị danh sách đang online
    */
    public void showOnLineList(Vector list){
        try {
            jTextPane3.setEditable(true);
            jTextPane3.setContentType("text/html");
            StringBuilder sb = new StringBuilder();
            Iterator it = list.iterator();
            sb.append("<html><table>");
            while(it.hasNext()){
                Object e = it.next();
                URL url = getImageFile();
                Icon icon = new ImageIcon(this.getClass().getResource("/icon/online.png"));
                sb.append("<tr><td><b>></b></td><td>").append(e).append("</td></tr>");
            }
            sb.append("</table></body></html>");
            jTextPane3.removeAll();
            jTextPane3.setText(sb.toString());
            jTextPane3.setEditable(false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    /*
      ************************************  Hiển thị danh sách online  *********************************************
    */
    private void sampleOnlineList(Vector list){
        jTextPane3.setEditable(true);
        jTextPane3.removeAll();
        jTextPane3.setText("");
        Iterator i = list.iterator();
         Icon icon = new ImageIcon(this.getClass().getResource("/icon/online.png"));
        while(i.hasNext()){
            Object e = i.next();
            /*  Hiển thị Username Online   */
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));
            panel.setBackground(Color.white);
           panel.addMouseListener(new MouseAdapter() {
                @Override
            public void mouseClicked(MouseEvent mouseEvent) {
             // Kiểm tra xem đã nhấn vào trước đó chưa
          
            // Lấy tên người dùng được chọn
            String selectedUsername = e.toString(); 
            // Gọi phương thức để xác định người dùng được chọn
            selectUserGlobal(selectedUsername);
          
            
            }
        });
             panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                // Kiểm tra xem chuột phải đã được nhấn
                if (SwingUtilities.isRightMouseButton(mouseEvent)) {
                    // Lấy tên người dùng được chọn
                    String selectedUsername = e.toString();
                    
                    // Hiển thị hộp thoại Yes/No
                    int choice = JOptionPane.showConfirmDialog(null, "Bạn có muốn kết bạn với " + selectedUsername + "?", "Xác nhận kết bạn", JOptionPane.YES_NO_OPTION);
                    
                    // Xử lý sự lựa chọn của người dùng
                    if (choice == JOptionPane.YES_OPTION) {
                        // Gọi phương thức để kết bạn
                        addFriend(selectedUsername);
                    }
                }
            }
        });
            JLabel label = new JLabel(icon);
            label.setText(" "+ e);
            panel.add(label);
            int len = jTextPane3.getDocument().getLength();
            jTextPane3.setCaretPosition(len);
            jTextPane3.insertComponent(panel);
             // Thiết lập màu nền cho JTextPane
            /*  Append Next Line   */
            sampleAppend();
        }
        jTextPane3.setEditable(false);
    }
    private void sampleAppend(){
        int len = jTextPane3.getDocument().getLength();
        jTextPane3.setCaretPosition(len);
        jTextPane3.replaceSelection("\n");
    }
    /*
      ************************************  Show Online Sample  *********************************************
    */
     private void addFriend(String selectedUsername) {
    try {
        if (selectedUsername != null) {
            String content = username + " " + selectedUsername + " " + "Kết bạn";
            dos.writeUTF("CMD_FRIENDREQUEST " + content);
        } else {
            // Hiển thị thông báo cho người dùng chưa chọn người nhận tin nhắn
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một người dùng để kết bạn!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (IOException e) {
       e.printStackTrace();
    }
}
     public void JDiaLog(String username) {
            // Hiển thị hộp thoại Yes/No
                    int choice = JOptionPane.showConfirmDialog(null, "Bạn có muốn kết bạn với " + username + "?", "Xác nhận kết bạn", JOptionPane.YES_NO_OPTION);
                     if (choice == JOptionPane.YES_OPTION) {
                        // Gọi phương thức để kết bạn
                        acceptFriend(username);
                    }
                    
}
       public void JDiaLog1(String username) {
            // Hiển thị hộp thoại Yes/No
                 JOptionPane.showMessageDialog(this, "Đã kết bạn rồi");              
}
     
     private void acceptFriend(String username1) {
         try {
        if (username1 != null) {
            String content = username + " " + username1 + " " + "Chấp nhận";
            dos.writeUTF("CMD_ACCEPTFRIENDREQUEST " + content);
        }
         } catch (IOException e) {
       e.printStackTrace();
   }
    }
    /*
        Get image file path
    */
    public URL getImageFile(){
        URL url = this.getClass().getResource("/images/online.png");
        return url;
    }
    public void requestMessage(String username) {
           try {
            String content = this.username + " " + username;
            dos.writeUTF("CMD_REQUESTMESSAGE " + content);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  private void selectUserFriend(String username) {
    // Cập nhật giao diện và thông tin
    jTextPane1.setVisible(true);
    jPanel8.setVisible(true);
    jLabel2.setText(username);
    // Lưu lại người dùng được chọn
    selectedUserFriend = username;
    String username1 = this.username;
        try {
            String content = this.username + " " + username;
            dos.writeUTF("CMD_ISSEEN " + content);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    String a = DBConnection.getUserFriendStatus(this.username, selectedUserFriend);
    jLabel3.setText(a);
    boolean block = DBConnection.isBlocked(this.username, username);
    if(block) {
        jButton9.setText("Unblock");
        jTextPane1.setEditable(true);
        jTextPane1.setText("Không thể trò chuyện. Tìm hiểu thêm!");
        jTextPane1.setEditable(false);
    } else {
        jButton9.setText("Block");
    }
    jTextPane1.setEditable(true);
    jTextPane1.setText("");
    jTextPane1.setEditable(false);
    List<String> messages = DBConnection.retrieveMessages(this.username, username);
    for (String message : messages) {
    String[] parts = message.split(" : ");
    String retrievedUsername = parts[0];
    String messageContent = parts[1];
    String messageContent1 = messageContent.trim();
    if (retrievedUsername.equals(this.username)) {
    if (messageContent1.contains("/icon")) {
        appendMySticker(messageContent1,this.username); }
    else {
         appendMyMessage(messageContent, retrievedUsername);
    }
    } else {
         if (messageContent1.contains("/icon")) {
           appendSticker(messageContent1,username); }
         else {
            appendMessage(messageContent,username,Color.MAGENTA,Color.MAGENTA);   
         }
    }
}
    jTextPane1.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        // Lấy vị trí của chuột khi bấm
        int offset = jTextPane1.viewToModel(e.getPoint());

        try {
            // Lấy phần tử được bấm (dòng) trong jTextPane1
            int start = Utilities.getRowStart(jTextPane1, offset);
            int end = Utilities.getRowEnd(jTextPane1, offset);
            String clickedLine = jTextPane1.getText(start, end - start);

            // Xử lý sự kiện bấm vào dòng tin nhắn
            handleClickedMessage(clickedLine,username1,username);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }
});
}
    private void handleClickedMessage(String clickedLine,String username,String usernamefriend) {
        int a = DBConnection.isMessageSRS(username, usernamefriend);
        switch (a) {
            case 0:
                appendTextToJTextPane(jTextPane1, "Đã gửi");
                break;
            case 1:
                appendTextToJTextPane(jTextPane1, "Đã nhận");
                break;
            case 2:
                appendTextToJTextPane(jTextPane1, "Đã xem");
                break;
            default:
                break;
       
    }
         }
     private void handleClickedMessage1(String clickedLine,String username,String usernamefriend) {
        int a = DBConnection.isMessageSRS(username, usernamefriend);
        switch (a) {
            case 0:
                appendTextToJTextPane(jTextPane4, "Đã gửi");
                break;
            case 1:
                appendTextToJTextPane(jTextPane4, "Đã nhận");
                break;
            case 2:
                appendTextToJTextPane(jTextPane4, "Đã xem");
                break;
            default:
                break;
       
    }
         }
   private void appendTextToJTextPane(JTextPane textPane, String text) {
    StyledDocument doc = textPane.getStyledDocument();
    try {
        // Kiểm tra xem văn bản ngay đầu dòng có tồn tại hay không
        int startOfLine = Utilities.getRowStart(textPane, doc.getLength());
        int endOfLine = Utilities.getRowEnd(textPane, doc.getLength());

        if (startOfLine == doc.getLength() || endOfLine == doc.getLength()) {
            // Nếu văn bản ngay đầu dòng tồn tại, xóa nó trước khi thêm văn bản mới
            if (isTextAddedToLine) {
                doc.remove(startOfLine, endOfLine - startOfLine);
                isTextAddedToLine = false; // Đặt lại trạng thái đã thêm văn bản mới
                return; // Kết thúc hàm sau khi xóa văn bản mới
            }
        }

        // Thêm văn bản mới
        doc.insertString(doc.getLength(), text, null);
        isTextAddedToLine = true; // Đánh dấu là đã thêm văn bản mới
    } catch (BadLocationException e) {
        e.printStackTrace();
    }
}
    private void selectGroup(String groupname) {
    // Cập nhật giao diện và thông tin
    jTextPane2.setVisible(true);
    jPanel9.setVisible(true);
    jLabel5.setText(groupname);
    // Lưu lại người dùng được chọn
    selectedGroup = groupname;
      int groupId = DBConnection.getGroupIdByGroupNameAndUsername(selectedGroup, username);
      boolean block = DBConnection.isGroupBlocked(this.username, groupId);
    if(block) {
        jButton21.setText("Unblock");
        jTextPane2.setEditable(true);
        jTextPane2.setText("Không thể trò chuyện. Tìm hiểu thêm!");
        jTextPane2.setEditable(false);
        jTextPane2.setVisible(false);
    } else {
        jButton21.setText("Block");
    }
    jTextPane2.setEditable(true);
    jTextPane2.setText("");
    jTextPane2.setEditable(false);
      List<String> messages = DBConnection.retrieveGroupMessages(groupname);
  for (String message : messages) {
    String[] parts = message.split(" : ");
    String retrievedUsername = parts[0];
    String messageContent = parts[1];
    String messageContent1 = messageContent.trim();
   if (retrievedUsername.equals(this.username)) {
    if (messageContent1.contains("/icon")) {
        appendMySticker1(messageContent1,retrievedUsername); }
    else {
         appendMyMessage1(messageContent, retrievedUsername);
    }
    } else {
         if (messageContent1.contains("/icon")) {
           appendSticker1(messageContent1,retrievedUsername); }
         else {
            appendMessage1(messageContent,retrievedUsername,Color.MAGENTA,Color.MAGENTA);   
         }
    }
}
}
       private void selectUserGlobal(String username) {
    // Cập nhật giao diện và thông tin
    jTextPane4.setVisible(true);
    jPanel7.setVisible(true);
    jLabel7.setText(username);
    jLabel8.setText("Active");
    jTextPane4.setEditable(true);
    jTextPane4.setText("");
    jTextPane4.setEditable(false);
    // Lưu lại người dùng được chọn
    selectedUserGlobal = username;
    String username1 = this.username;
     boolean block = DBConnection.isBlocked(this.username, username);
    if(block) {
        jButton14.setText("Unblock");
        jTextPane4.setEditable(true);
        jTextPane4.setText("Không thể trò chuyện. Tìm hiểu thêm!");
        jTextPane4.setEditable(false);
    } else {
        jButton14.setText("Block");
    }
    List<String> messages = DBConnection.retrieveMessages(this.username, username);

for (String message : messages) {
    String[] parts = message.split(" : ");
    String retrievedUsername = parts[0];
    String messageContent = parts[1];
    String messageContent1 = messageContent.trim();
   if (retrievedUsername.equals(this.username)) {
    if (messageContent1.contains("/icon")) {
        appendMySticker2(messageContent1,this.username); }
    else {
         appendMyMessage2(messageContent, retrievedUsername);
    }
    } else {
         if (messageContent1.contains("/icon")) {
           appendSticker(messageContent1,username); }
         else {
            appendMessage2(messageContent,username,Color.MAGENTA,Color.MAGENTA);   
         }
    }
}
 jTextPane4.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        // Lấy vị trí của chuột khi bấm
        int offset = jTextPane4.viewToModel(e.getPoint());

        try {
            // Lấy phần tử được bấm (dòng) trong jTextPane1
            int start = Utilities.getRowStart(jTextPane4, offset);
            int end = Utilities.getRowEnd(jTextPane4, offset);
            String clickedLine = jTextPane4.getText(start, end - start);

            // Xử lý sự kiện bấm vào dòng tin nhắn
            handleClickedMessage1(clickedLine,username1,username);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }
});
       }
    public String isOnline1(String a) {
        return this.a;    
   }
    /*
        Set myTitle
    */
    public void setMyTitle(String s){
        setTitle(s);
    }
    
    /*
        Phương thức tải get download
    */
    public String getMyDownloadFolder(){
        return this.mydownloadfolder;
    }
    
    /*
        Phương thức get host
    */
    public String getMyHost(){
        return this.host;
    }
    
    /*
        Phương thức get Port
    */
    public int getMyPort(){
        return this.port;
    }
   
    
    /*
        Cập nhật Attachment 
    */
    public void updateAttachment(boolean b){
        this.attachmentOpen = b;
    }
    
    /*
        Hàm này sẽ mở 1 file chooser
    */
    public void openFolder(){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int open = chooser.showDialog(this, "Mở Thư Mục");
        if(open == chooser.APPROVE_OPTION){
            mydownloadfolder = chooser.getSelectedFile().toString()+"\\";
        } else {
            mydownloadfolder = "D:\\";
        }
    }
private void sendMessage() {
    try {
        if (jTextField1.getText().length() > 0) {
            if (selectedUserFriend != null) {
                boolean blockBySender = DBConnection.isBlocked(username, selectedUserFriend);
                boolean blockByReceiver = DBConnection.isBlocked(selectedUserFriend, username);

                if (blockBySender || blockByReceiver) {
                    JOptionPane.showMessageDialog(this, "Không thể gửi tin nhắn vì một trong hai người dùng đã block nhau.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String content = username + " " + selectedUserFriend + " " + jTextField1.getText();
                    dos.writeUTF("CMD_CHAT " + content);
                    appendTextToJTextPane(jTextPane1, "");
                    appendMyMessage(" " + jTextField1.getText(), username);
                }
            } else {
                // Hiển thị thông báo cho người dùng chưa chọn người nhận tin nhắn
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một người dùng để gửi tin nhắn!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    } catch (IOException e) {
        appendMessage(" Không thể gửi tin nhắn đi bây giờ, không thể kết nối đến Máy Chủ tại thời điểm này, xin vui lòng thử lại sau hoặc khởi động lại ứng dụng này.!", "Lỗi", Color.RED, Color.RED);
    }
}
 private void sendMessage2() {
    try {
        if (jTextField3.getText().length() > 0) {
            if (selectedUserGlobal != null) {
                boolean blockBySender = DBConnection.isBlocked(username, selectedUserGlobal);
                boolean blockByReceiver = DBConnection.isBlocked(selectedUserGlobal, username);

                if (blockBySender || blockByReceiver) {
                    JOptionPane.showMessageDialog(this, "Không thể gửi tin nhắn vì một trong hai người dùng đã block nhau.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String content = username + " " + selectedUserGlobal + " " + jTextField3.getText();
                    dos.writeUTF("CMD_CHATGLOBAL " + content);
                    appendMyMessage2(" " + jTextField3.getText(), username);
                }
            } else {
                // Hiển thị thông báo cho người dùng chưa chọn người nhận tin nhắn
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một người dùng để gửi tin nhắn!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    } catch (IOException e) {
        appendMessage2(" Không thể gửi tin nhắn đi bây giờ, không thể kết nối đến Máy Chủ tại thời điểm này, xin vui lòng thử lại sau hoặc khởi động lại ứng dụng này.!", "Lỗi", Color.RED, Color.RED);
    }
}
 
  // Hàm mã hóa mật khẩu bằng thuật toán SHA-256
    private String hashPassword(String password) {
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(password.getBytes());

        // Chuyển byte array sang dạng hex string
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    }

    return null;
}
public String getStickerPath(Icon sticker) {
    // Lấy tên tệp của sticker
    String stickerFileName = "sticker.png"; // Mặc định nếu không có tên tệp cụ thể
    if (sticker instanceof ImageIcon) {
        ImageIcon imageIcon = (ImageIcon) sticker;
        String imageName = imageIcon.getDescription();
        // Lấy tên tệp từ đường dẫn ảnh (nếu có)
        if (imageName != null && !imageName.isEmpty()) {
            int lastSlash = imageName.lastIndexOf('/');
            if (lastSlash != -1 && lastSlash < imageName.length() - 1) {
                stickerFileName = imageName.substring(lastSlash + 1);
            }
        }
    }
    // Đặt đường dẫn cố định cho sticker trong package icon
    String stickerPath = "/icon/" + stickerFileName;
    // Rest of the code remains the same
    return stickerPath;
}

private void insertSticker(String stickerPath) {
    try {
        // Tạo đường dẫn tới hình ảnh trong package icon
        // Load hình ảnh từ đường dẫn
        String stickerPath1= stickerPath.trim();
        ImageIcon stickerIcon = new ImageIcon(getClass().getResource(stickerPath1));

        // Thêm hình ảnh vào JTextPane
        jTextPane1.setCaretPosition(jTextPane1.getDocument().getLength());
        jTextPane1.insertIcon(stickerIcon);

        // Chèn ký tự xuống dòng sau khi chèn biểu tượng
        jTextPane1.getDocument().insertString(jTextPane1.getDocument().getLength(), "\n", null);

        jTextPane1.setCaretPosition(jTextPane1.getDocument().getLength());

    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void insertSticker1(String stickerPath) {
    try {
        // Tạo đường dẫn tới hình ảnh trong package icon
        // Load hình ảnh từ đường dẫn
        String stickerPath1= stickerPath.trim();
        ImageIcon stickerIcon = new ImageIcon(getClass().getResource(stickerPath1));

        // Thêm hình ảnh vào JTextPane
        jTextPane2.setCaretPosition(jTextPane2.getDocument().getLength());
        jTextPane2.insertIcon(stickerIcon);

        // Chèn ký tự xuống dòng sau khi chèn biểu tượng
        jTextPane2.getDocument().insertString(jTextPane2.getDocument().getLength(), "\n", null);

        jTextPane2.setCaretPosition(jTextPane2.getDocument().getLength());

    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void insertSticker2(String stickerPath) {
    try {
        // Tạo đường dẫn tới hình ảnh trong package icon
        // Load hình ảnh từ đường dẫn
        String stickerPath1= stickerPath.trim();
        ImageIcon stickerIcon = new ImageIcon(getClass().getResource(stickerPath1));

        // Thêm hình ảnh vào JTextPane
        jTextPane4.setCaretPosition(jTextPane4.getDocument().getLength());
        jTextPane4.insertIcon(stickerIcon);

        // Chèn ký tự xuống dòng sau khi chèn biểu tượng
        jTextPane4.getDocument().insertString(jTextPane4.getDocument().getLength(), "\n", null);

        jTextPane4.setCaretPosition(jTextPane4.getDocument().getLength());

    } catch (Exception e) {
        e.printStackTrace();
    }
}



  private void showStickerList() {
    JFrame stickerListFrame = new JFrame("Danh sách sticker");
    stickerListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      // Tạo một danh sách chứa các sticker
    DefaultListModel<Icon> stickerListModel = new DefaultListModel<>();
    // Thêm các sticker vào danh sách
  stickerListModel.addElement(new ImageIcon(getClass().getResource("/icon/a1.jpg")));
    stickerListModel.addElement(new ImageIcon(getClass().getResource("/icon/a2.png")));
     stickerListModel.addElement(new ImageIcon(getClass().getResource("/icon/a3.png")));
    //Set vị trí
    // Xác định vị trí và kích thước của khung
    int buttonX = jButton8.getLocationOnScreen().x;
    int buttonY = jButton8.getLocationOnScreen().y;
    int frameWidth = 20; // Đặt kích thước khung là 200
    int frameHeight = 30; // Đặt kích thước khung là 300

    // Đặt vị trí và kích thước của khung
    stickerListFrame.setBounds(buttonX, buttonY - frameHeight, frameWidth, frameHeight);
   
    // Tạo một JList để hiển thị danh sách sticker
    JList<Icon> stickerList = new JList<>(stickerListModel);
    stickerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    // Gắn sự kiện cho JList để xử lý khi người dùng chọn sticker
    stickerList.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                // Lấy sticker được chọn
                Icon selectedSticker = stickerList.getSelectedValue();
                
                // Gửi sticker đến người nhận
                sendSticker(selectedSticker);
                
                // Đóng giao diện danh sách sticker
                stickerListFrame.dispose();
            }
        }
    });
    
    // Đặt JList vào một JScrollPane để hỗ trợ cuộn khi danh sách quá dài
    JScrollPane stickerScrollPane = new JScrollPane(stickerList);
    stickerListFrame.getContentPane().add(stickerScrollPane);
    
    // Hiển thị giao diện danh sách sticker
    stickerListFrame.pack();
    stickerListFrame.setVisible(true);
}
   private void showStickerList1() {
    JFrame stickerListFrame = new JFrame("Danh sách sticker");
    stickerListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      // Tạo một danh sách chứa các sticker
    DefaultListModel<Icon> stickerListModel = new DefaultListModel<>();
    // Thêm các sticker vào danh sách
    stickerListModel.addElement(new ImageIcon(getClass().getResource("/icon/a1.jpg")));
    stickerListModel.addElement(new ImageIcon(getClass().getResource("/icon/a2.png")));
     stickerListModel.addElement(new ImageIcon(getClass().getResource("/icon/a3.png")));
    //Set vị trí
    // Xác định vị trí và kích thước của khung
    int buttonX = jButton13.getLocationOnScreen().x;
    int buttonY = jButton13.getLocationOnScreen().y;
    int frameWidth = 20; // Đặt kích thước khung là 200
    int frameHeight = 30; // Đặt kích thước khung là 300

    // Đặt vị trí và kích thước của khung
    stickerListFrame.setBounds(buttonX, buttonY - frameHeight, frameWidth, frameHeight);
   
    // Tạo một JList để hiển thị danh sách sticker
    JList<Icon> stickerList = new JList<>(stickerListModel);
    stickerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    // Gắn sự kiện cho JList để xử lý khi người dùng chọn sticker
    stickerList.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                // Lấy sticker được chọn
                Icon selectedSticker = stickerList.getSelectedValue();
                
                // Gửi sticker đến người nhận
                sendSticker1(selectedSticker);
                
                // Đóng giao diện danh sách sticker
                stickerListFrame.dispose();
            }
        }
    });
    
    // Đặt JList vào một JScrollPane để hỗ trợ cuộn khi danh sách quá dài
    JScrollPane stickerScrollPane = new JScrollPane(stickerList);
    stickerListFrame.getContentPane().add(stickerScrollPane);
    
    // Hiển thị giao diện danh sách sticker
    stickerListFrame.pack();
    stickerListFrame.setVisible(true);
}
      public void SetData( List<String> friendUsernames) {
           jTextField4.setText(username);
           DefaultListModel<String> listModel1 = new DefaultListModel<>();
           for (String friendUsername : friendUsernames) {
                listModel1.addElement(friendUsername);
              }
            jList3.setModel(listModel1);
            jList3.addMouseListener(new MouseAdapter() {
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) { // Kiểm tra xem người dùng đã double-click vào mục hay chưa
            String selectedValue = jList3.getSelectedValue();

            // Kiểm tra xem mục đã tồn tại trong JList2 chưa
            if (!listModel2.contains(selectedValue)) {
                listModel1.removeElement(selectedValue); // Xóa mục khỏi JList1
                listModel2.addElement(selectedValue); // Thêm mục vào JList2
                jList4.setModel(listModel2);
            }
        }
    }
});

jList4.addMouseListener(new MouseAdapter() {
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) { // Kiểm tra xem người dùng đã double-click vào mục hay chưa
            String selectedValue = jList4.getSelectedValue();

            // Kiểm tra xem mục đã tồn tại trong JList1 chưa
            if (!listModel1.contains(selectedValue)) {
                listModel2.removeElement(selectedValue); // Xóa mục khỏi JList2
                listModel1.addElement(selectedValue); // Thêm mục vào JList1
                jList3.setModel(listModel1);
            }
        }
    }
});

       }
    private void createGroup(){
     String groupName = jTextField5.getText();
    String createdBy = username;
     // Kiểm tra số lượng mục trong jList2
    int itemCount = jList4.getModel().getSize();
    if (itemCount < 2) {
        // Hiển thị thông báo cho người dùng rằng cần ít nhất 2 thành viên trong nhóm
        JOptionPane.showMessageDialog(this, "Cần ít nhất 2 thành viên trong nhóm.");
        return; // Dừng việc tạo nhóm và thoát khỏi phương thức
    }
    else {
       listModel2.addElement(username);
       // Tạo chuỗi danh sách thành viên của nhóm
        StringBuilder membersStringBuilder = new StringBuilder();
        for (int i = 0; i < listModel2.getSize(); i++) {
            membersStringBuilder.append(listModel2.getElementAt(i));
            membersStringBuilder.append(" "); // Thêm khoảng trắng để tách các thành viên
        }
        String membersString = membersStringBuilder.toString().trim();
       
        try {
            System.out.println(listModel2);
            // Gửi thông tin nhóm đến server
            groupName = groupName.replaceAll("\\s+", ""); // Loại bỏ khoảng trắng và ghép các từ lại với nhau
            dos.writeUTF("CMD_CREATEGROUP " + groupName + " "+ createdBy + " " + membersString);
            this.listModel2.clear();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối đến máy chủ, vui lòng thử lại sau.!", "Kết nối thất bại", JOptionPane.ERROR_MESSAGE);
        }
}
    }
    public void createGroupS(boolean success) {
    
       if (success) {
            JOptionPane.showMessageDialog(this, "Tạo nhóm thành công!","",JOptionPane.INFORMATION_MESSAGE);
       }
       else {
             JOptionPane.showMessageDialog(this, "Tên nhóm không được trùng!","",JOptionPane.ERROR_MESSAGE);
       }   
     
    }
        public void appendMessage1(String msg, String header, Color headerColor, Color contentColor){
        jTextPane2.setEditable(true);
        getMsgHeader1(header, headerColor);
        getMsgContent1(msg, contentColor);
        jTextPane2.setEditable(false);
    }
         public void appendMessage2(String msg, String header, Color headerColor, Color contentColor){
        jTextPane4.setEditable(true);
        getMsgHeader2(header, headerColor);
        getMsgContent2(msg, contentColor);
        jTextPane4.setEditable(false);
    }
    /*
        Tin nhắn chat
    */
    public void appendMyMessage1(String msg, String header){
        jTextPane2.setEditable(true);
        getMsgHeader1(header, Color.ORANGE);
        getMsgContent1(msg, Color.LIGHT_GRAY);
        jTextPane2.setEditable(false);
    } 
     public void appendMyMessage2(String msg, String header){
        jTextPane4.setEditable(true);
        getMsgHeader2(header, Color.ORANGE);
        getMsgContent2(msg, Color.LIGHT_GRAY);
        jTextPane4.setEditable(false);
    } 
      public void getSenderMessage2(String msg, String header){
        jTextPane4.setEditable(true);
        getMsgHeader2(header, Color.ORANGE);
        getMsgContent2(msg, Color.LIGHT_GRAY);
        jTextPane4.setEditable(false);
    } 
        public void getReceiveMessage2(String msg, String header, Color headerColor, Color contentColor){
        jTextPane4.setEditable(true);
        getMsgHeader2(header, headerColor);
        getMsgContent2(msg, contentColor);
        jTextPane4.setEditable(false);
    }
    /*
        Tiêu đề tin nhắn
    */
    public void getMsgHeader1(String header, Color color){
        int len = jTextPane2.getDocument().getLength();
        jTextPane2.setCaretPosition(len);
        jTextPane2.setCharacterAttributes(MessageStyle.styleMessageContent(color, "Impact", 13), false);
        jTextPane2.replaceSelection(header+":");
    }
     public void getMsgHeader2(String header, Color color){
        int len = jTextPane4.getDocument().getLength();
        jTextPane4.setCaretPosition(len);
        jTextPane4.setCharacterAttributes(MessageStyle.styleMessageContent(color, "Impact", 13), false);
        jTextPane4.replaceSelection(header+":");
    }
    /*
        Nội dung tin nhắn
    */
    public void getMsgContent1(String msg, Color color){
        int len = jTextPane2.getDocument().getLength();
        jTextPane2.setCaretPosition(len);
        jTextPane2.setCharacterAttributes(MessageStyle.styleMessageContent(color, "Arial", 12), false);
        jTextPane2.replaceSelection(msg +"\n\n");
    }
     public void getMsgContent2(String msg, Color color){
        int len = jTextPane4.getDocument().getLength();
        jTextPane4.setCaretPosition(len);
        jTextPane4.setCharacterAttributes(MessageStyle.styleMessageContent(color, "Arial", 12), false);
        jTextPane4.replaceSelection(msg +"\n\n");
    }
    /*
        Phương thức get host
    */

    
    /*
        Cập nhật Attachment 
    */
  
    /*
        Hàm này sẽ mở 1 file chooser
    */
  
  private void sendMessage1() {
    try {
        if (jTextField2.getText().length() > 0) {
            if (selectedGroup != null) {
                if (DBConnection.isGroupBlocked(this.username, DBConnection.getGroupIdByGroupNameAndUsername(selectedGroup,this.username))) {
                    JOptionPane.showMessageDialog(this, "Bạn đã chặn nhóm " + selectedGroup, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    Socket socket = new Socket(host, port);
                    dos = new DataOutputStream(socket.getOutputStream());
                    String content = username + " " + selectedGroup + " " + jTextField2.getText();
                    dos.writeUTF("CMD_CHATALL " + content);
                    appendMyMessage1(" " + jTextField2.getText(), username);
                }
            } else {
                // Hiển thị thông báo cho người dùng chưa chọn nhóm để gửi tin nhắn
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhóm để gửi tin nhắn", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    } catch (IOException e) {
        appendMessage1("Không thể gửi tin nhắn đi bây giờ. Không thể kết nối đến Máy Chủ tại thời điểm này. Vui lòng thử lại sau hoặc khởi động lại ứng dụng này.", "Lỗi", Color.RED, Color.RED);
    }
}
  private void sendSticker(Icon sticker) {
    try {
        if (selectedUserFriend != null) {
            String stickerPath = getStickerPath(sticker); // Chuyển đổi Icon sang đường dẫn của sticker
            String content = username + " " + selectedUserFriend + " " + stickerPath;
            dos.writeUTF("CMD_CHATSTICKER " + content);
            appendMySticker(stickerPath, username);
        } else {
            // Hiển thị thông báo cho người dùng chưa chọn người nhận tin nhắn
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một người dùng để gửi tin nhắn!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (IOException e) {
        appendMessage("Không thể gửi tin nhắn đi bây giờ, không thể kết nối đến Máy Chủ tại thời điểm này, xin vui lòng thử lại sau hoặc khởi động lại ứng dụng này!", "Lỗi", Color.RED, Color.RED);
    }
}
  private void sendSticker1(Icon sticker) {
   try {
        if (selectedGroup != null) {
            String stickerPath = getStickerPath(sticker); // Chuyển đổi Icon sang đường dẫn của sticker
            String content = username + " " + selectedGroup + " " + stickerPath;
            dos.writeUTF("CMD_CHATALLSTICKER " + content);
            appendMySticker1(stickerPath, username);
        } else {
            // Hiển thị thông báo cho người dùng chưa chọn người nhận tin nhắn
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhóm để gửi tin nhắn!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (IOException e) {
        appendMessage1("Không thể gửi tin nhắn đi bây giờ, không thể kết nối đến Máy Chủ tại thời điểm này, xin vui lòng thử lại sau hoặc khởi động lại ứng dụng này!", "Lỗi", Color.RED, Color.RED);
    }
}
  private void sendSticker2(Icon sticker) {
    try {
        if (selectedUserGlobal != null) {
            String stickerPath = getStickerPath(sticker); // Chuyển đổi Icon sang đường dẫn của sticker
            String content = username + " " + selectedUserGlobal + " " + stickerPath;
            dos.writeUTF("CMD_CHATSTICKER1 " + content);
            appendMySticker2(stickerPath, username);
        } else {
            // Hiển thị thông báo cho người dùng chưa chọn người nhận tin nhắn
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một người dùng để gửi tin nhắn!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (IOException e) {
        appendMessage2("Không thể gửi tin nhắn đi bây giờ, không thể kết nối đến Máy Chủ tại thời điểm này, xin vui lòng thử lại sau hoặc khởi động lại ứng dụng này!", "Lỗi", Color.RED, Color.RED);
    }
}
 public void appendMySticker(String imagePath, String header) {
    jTextPane1.setEditable(true);
    getMsgHeader(header, Color.ORANGE);
    insertSticker(imagePath);
    jTextPane1.setEditable(false);
}
public void appendSticker(String stickerPath, String sender) {
    jTextPane1.setEditable(true);
    getMsgHeader(sender, Color.BLUE);
    insertSticker(stickerPath);
    jTextPane1.setEditable(false);
}
 public void appendMySticker1(String imagePath, String header) {
    jTextPane2.setEditable(true);
    getMsgHeader1(header, Color.ORANGE);
    insertSticker1(imagePath);
    jTextPane2.setEditable(false);
}
 public void appendSticker1(String stickerPath, String sender) {
    jTextPane2.setEditable(true);
    getMsgHeader1(sender, Color.BLUE);
    insertSticker1(stickerPath);
    jTextPane2.setEditable(false);
}
 public void appendMySticker2(String imagePath, String header) {
    jTextPane4.setEditable(true);
    getMsgHeader2(header, Color.ORANGE);
    insertSticker2(imagePath);
    jTextPane4.setEditable(false);
}
 public void appendSticker2(String stickerPath, String sender) {
    jTextPane4.setEditable(true);
    getMsgHeader2(sender, Color.BLUE);
    insertSticker2(stickerPath);
    jTextPane4.setEditable(false);
}

  private void showStickerList2() {
    JFrame stickerListFrame = new JFrame("Danh sách sticker");
    stickerListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      // Tạo một danh sách chứa các sticker
    DefaultListModel<Icon> stickerListModel = new DefaultListModel<>();
    // Thêm các sticker vào danh sách
   stickerListModel.addElement(new ImageIcon(getClass().getResource("/icon/a1.jpg")));
    stickerListModel.addElement(new ImageIcon(getClass().getResource("/icon/a2.png")));
     stickerListModel.addElement(new ImageIcon(getClass().getResource("/icon/a3.png")));
    //Set vị trí
    // Xác định vị trí và kích thước của khung
    int buttonX = jButton3.getLocationOnScreen().x;
    int buttonY = jButton3.getLocationOnScreen().y;
    int frameWidth = 20; // Đặt kích thước khung là 200
    int frameHeight = 30; // Đặt kích thước khung là 300

    // Đặt vị trí và kích thước của khung
    stickerListFrame.setBounds(buttonX, buttonY - frameHeight, frameWidth, frameHeight);
   
    // Tạo một JList để hiển thị danh sách sticker
    JList<Icon> stickerList = new JList<>(stickerListModel);
    stickerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    // Gắn sự kiện cho JList để xử lý khi người dùng chọn sticker
    stickerList.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                // Lấy sticker được chọn
                Icon selectedSticker = stickerList.getSelectedValue();
                
                // Gửi sticker đến người nhận
                sendSticker2(selectedSticker);
                
                // Đóng giao diện danh sách sticker
                stickerListFrame.dispose();
            }
        }
    });
    
    // Đặt JList vào một JScrollPane để hỗ trợ cuộn khi danh sách quá dài
    JScrollPane stickerScrollPane = new JScrollPane(stickerList);
    stickerListFrame.getContentPane().add(stickerScrollPane);
    
    // Hiển thị giao diện danh sách sticker
    stickerListFrame.pack();
    stickerListFrame.setVisible(true);
}
//  private void updateFriendList() {
//      getFriendList();
//  }
  private void RequestAll() {
      try {
            dos.writeUTF("CMD_GROUPLIST " + this.username);
            dos.writeUTF("CMD_FRIENDLIST " + this.username);
            dos.writeUTF("CMD_USERINFO " + this.username);
              // gửi username đang kết nối
            dos.writeUTF("CMD_SETDATA "+ this.username);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
public void getFriendList(List<String> friendNames) {
    // Tạo một đối tượng Timer
        // Gọi phương thức truy vấn để lấy danh sách friendNames
       
        DefaultListModel<String> listModel1 = new DefaultListModel<>();

        // Xử lý danh sách friendNames
        for (String friendName : friendNames) {
            listModel1.addElement(friendName);
        }     
        // Cập nhật dữ liệu cho jList1 trên luồng giao diện chính
            // Gán Renderer tùy chỉnh cho jList1
            jList1.setCellRenderer(new FriendListCellRenderer(username,listModel1));
            jList1.setModel(listModel1);
    // Thêm ListSelectionListener cho jList1
    jList1.addListSelectionListener((ListSelectionEvent event) -> {
        if (!event.getValueIsAdjusting() && !isItemSelected) {
            JList<String> source = (JList<String>) event.getSource();
            List<String> selectedValues = source.getSelectedValuesList();
            // Xử lý các giá trị đã chọn ở đây
            for (String selectedValue : selectedValues) {
                selectUserFriend(selectedValue);
            }
            isItemSelected = true; // Đánh dấu đã có mục được chọn
            isItemSelected = false; // Cho phép chọn mục khác
        }
    });
}

   public void getListGroup( List<String> groupNames) {
    // Gọi phương thức truy vấn để lấy danh sách group_name
    jTextPane1.setVisible(false);
    DefaultListModel<String> listModel1 = new DefaultListModel<>();
    // Xử lý danh sách group_name
      for (String groupName : groupNames) {
                listModel1.addElement(groupName);
              }
      
            jList2.setModel(listModel1);
            jList2.addListSelectionListener((ListSelectionEvent event) -> {
                if (!event.getValueIsAdjusting() && !isItemSelected) {
                    JList<String> source = (JList<String>) event.getSource();
                    List<String> selectedValues = source.getSelectedValuesList();
                    // Xử lý các giá trị đã chọn ở đây
                    for (String selectedValue : selectedValues) {
                        selectGroup(selectedValue);
                       jList2.addMouseListener(new MouseAdapter() {
                 @Override
                public void mousePressed(MouseEvent e) {
                 if (SwingUtilities.isRightMouseButton(e)) {
                int index = jList2.locationToIndex(e.getPoint());
                jList2.setSelectedIndex(index);
                showPopupMenu(e.getX(), e.getY(),selectedValue);
                    }
                }
    });
                    }
                    isItemSelected = true; // Đánh dấu đã có mục được chọn
                    isItemSelected = false; //cho phep chon muc khac
                }
    });
   
}      
   private void showPopupMenu(int x, int y,String selectedGroup) {
    JPopupMenu popupMenu = new JPopupMenu();
    JMenuItem leaveGroupItem = new JMenuItem("Rời khỏi nhóm");
    leaveGroupItem.addActionListener(e -> {
        if (selectedGroup != null) {
            int confirmation = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn rời khỏi nhóm?");
            if (confirmation == JOptionPane.YES_OPTION) {
                try {
                    String content = selectedGroup + " " + this.username;
                    // Thực hiện chức năng xóa người dùng khỏi nhóm
                    dos.writeUTF("CMD_DELETEMEMBER " + content);
                } catch (IOException ex) {
                    Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    });
    popupMenu.add(leaveGroupItem);
    popupMenu.show(jList2, x, y);
}
   public void SuccessMove(boolean a) {
                           if (a) {
                        JOptionPane.showMessageDialog(this, "Rời nhóm thành công");
                        jTextPane2.setEditable(true);
                        jTextPane2.setText("");
                        jTextPane2.setEditable(false); 
                    }
   }
   // Hàm để cập nhật danh sách group_name
public void updateGroupList() {
    try {
            dos.writeUTF("CMD_GROUPLIST " + this.username);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
}
public void updateFriendList() {
    try {
            dos.writeUTF("CMD_FRIENDLIST " + this.username);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
}

 public void getInfo( String[] userInfo) {
         // Kiểm tra nếu userInfo không rỗng
    if (userInfo != null) {
        String username = userInfo[0]; // Thông tin tên người dùng
        String password = userInfo[1]; // Thông tin password người dùng
        String fullname = userInfo[2];
        String gender = userInfo[3];
        String dob = userInfo[4];
        // Gán giá trị vào các textfield
        txtUser1.setText(username);
        txtPass2.setText(password);
        txtUser4.setText(fullname);
        jComboBox1.setSelectedItem(gender); 
        txtUser3.setText(dob);
    }
    }
 public void requestupdateInfo() {
      try {
        String username = txtUser1.getText();
        char[] password = txtPass2.getPassword();
        String password1 = new String(password).trim();
        String hashedPassword = hashPassword(password1); // Mã hóa mật khẩu
        String fullname = txtUser4.getText();
        String gender = (String) jComboBox1.getSelectedItem();
        String dob = txtUser3.getText();
            dos.writeUTF("CMD_UPDATEUSERINFO " + this.username + " " + hashedPassword+ " " + fullname+ " " +gender+ " "+ dob);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
 }
 public void updateInfo(boolean updateSuccessful) {
        // Kiểm tra kết quả cập nhật và thông báo cho người dùng
        if (updateSuccessful) {
            JOptionPane.showMessageDialog(this, "Thông tin tài khoản đã được lưu lại.");
        } else {
            JOptionPane.showMessageDialog(this, "Lưu thông tin tài khoản thất bại.");
        }
        txtPass2.setEditable(false);
        txtUser4.setEditable(false);
        jComboBox1.setEnabled(false);
        txtUser3.setEditable(false);
 }
}