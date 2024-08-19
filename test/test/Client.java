/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Client implements Runnable{
    
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
    Test main;
    StringTokenizer st;
    protected DecimalFormat df = new DecimalFormat("##,#00");
    
    public Client(Socket socket, Test main){
        this.socket = socket;
        this.main = main;
        try {
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            main.appendMessage("[IOException]: "+ e.getMessage(), "Lỗi", Color.RED, Color.RED);
        }
    }


    @Override
    public void run() {
        try {
            while(!Thread.currentThread().isInterrupted()){
                String data = dis.readUTF();
                st = new StringTokenizer(data);
                /** Get Message CMD **/
                String CMD = st.nextToken(); 
                switch(CMD){
                    case "CMD_MESSAGE":
                        String msg = "";
                        String frm = st.nextToken();
                        while(st.hasMoreTokens()){
                            msg = msg +" "+ st.nextToken();
                        }
                        main.appendMessage1(msg, frm, Color.BLACK, Color.BLACK);
                        break;
                    case "CMD_FRIENDREQUEST1":
                        String msg0 = "";
                        String frm0 = st.nextToken();
                        while(st.hasMoreTokens()){
                            msg0 = msg0 +" "+ st.nextToken();
                        }
                        main.JDiaLog(frm0);          
                        break;
                    case "CMD_EXISTSFRIEND":
                        String msg10="";
                        String frm10=st.nextToken();
                        while(st.hasMoreTokens()){
                            msg10 = msg10 +" "+ st.nextToken();
                        }
                        main.JDiaLog1(frm10); 
                        break;
                    case "CMD_SERVERMESSAGE":
                        String msgServer = "";
                        while (st.hasMoreTokens()) {
                            msgServer = msgServer + " " + st.nextToken();
                        }
                        main.appendServerMessage(msgServer);
                        break;
                    case "CMD_ISONLINE":
                        String friendStatus = st.nextToken();
                        main.isOnline1(friendStatus);
                        break;
                    case "CMD_DELETEMEMBER":
                        String a = st.nextToken();
                        if(a.equals("true")) {
                            boolean b = true;
                            main.SuccessMove(b);
                        }
                        break;
                    case "CMD_CREATEGROUP":
                        String c1 = st.nextToken();
                        if(c1.equals("true")) {
                            boolean c2 = true;
                            main.createGroupS(c2);
                        }
                        break;
                    case "CMD_SETDATA":
                        if(st.countTokens() >= 1) {             
                         String friendNames = st.nextToken();
                     
                        // Split the friendNamesString using the comma (',') separator
                        String[] friendNamesArray1 = friendNames.split(",");   
             
                        // Create a List<String> to hold the friend names
                        List<String> friendNamesList1 = Arrays.asList(friendNamesArray1);
                        
                        main.SetData(friendNamesList1);
                        }
                        break;
                    case "CMD_GROUPLIST":
                        if(st.countTokens() >= 1) {                      
                        String groupNames = st.nextToken();          
                        // Split the groupNames string using the comma (',') separator
                        String[] groupNamesArray = groupNames.split(",");                
                        // Create a List<String> to hold the group names
                        List<String> groupNamesList = Arrays.asList(groupNamesArray);
                    
                        // Pass the groupNamesList to the main.getListGroup() method
                        main.getListGroup(groupNamesList); 
                        }          
                        break;
                    case "CMD_USERINFO":
                        String a1 = st.nextToken();
                        String a2 = st.nextToken();
                        String a3 = st.nextToken();
                        String a4 = st.nextToken();
                        String a5 = st.nextToken();
                        // Khởi tạo mảng userInfo có độ dài là 5
                        String[] userInfo = new String[5];      
                        // Gán giá trị cho từng phần tử của mảng userInfo
                        userInfo[0] = a1;
                        userInfo[1] = a2;
                        userInfo[2] = a3;
                        userInfo[3] = a4;
                        userInfo[4] = a5;                  
                        main.getInfo(userInfo);
                        break;                    
                    case "CMD_UPDATEUSERINFO":
                        String b = st.nextToken();
                        if(b.equals("true")) {
                            boolean c = true;
                            main.updateInfo(c);
                        }
                        break;
                    case "CMD_FRIENDLIST":
                        if(st.countTokens() >= 1) {             
                        String friendNamesString = st.nextToken();
                        // Split the friendNamesString using the comma (',') separator
                        String[] friendNamesArray = friendNamesString.split(",");   
                       
                        // Create a List<String> to hold the friend names
                        List<String> friendNamesList = Arrays.asList(friendNamesArray);
         
                        main.getFriendList(friendNamesList);
                        }
                        break;
                    case "CMD_MESSAGE1":
                        String msg1 = "";
                        String frm1 = st.nextToken();
                        while(st.hasMoreTokens()) {
                            msg1 = msg1 + " " + st.nextToken();
                        }
                        main.appendMessage(msg1,frm1,Color.MAGENTA,Color.BLUE);
                        break;
                        case "CMD_MESSAGE2":
                        String msg2 = "";
                        String frm2 = st.nextToken();
                        while(st.hasMoreTokens()) {
                            msg2 = msg2 + " " + st.nextToken();
                        }
                        main.appendMessage2(msg2,frm2,Color.MAGENTA,Color.MAGENTA);
                        break;
                    case "CMD_STICKER":
                         String from = st.nextToken();
                         String stickerPath = "";
                         while(st.hasMoreTokens()) {
                             stickerPath = stickerPath + " " + st.nextToken(); // Add a space before appending the next token
                         }
                         main.appendSticker(stickerPath, from);
                         break;
                    case "CMD_ALLSTICKER":
                        String frm3 = st.nextToken();
                        String stickerPath2= "";
                        while(st.hasMoreTokens()) {
                             stickerPath2 = stickerPath2 + " " + st.nextToken(); // Add a space before appending the next token
                         }
                         main.appendSticker1(stickerPath2, frm3);
                        break;
                         case "CMD_STICKER1":
                         String from1 = st.nextToken();
                         String stickerPath1 = "";
                         while(st.hasMoreTokens()) {
                             stickerPath1 = stickerPath1 + " " + st.nextToken(); // Add a space before appending the next token
                         }
                         main.appendSticker2(stickerPath1, from1);
                         break;
                       case "CMD_ONLINE":
                        Vector online = new Vector();
                        while(st.hasMoreTokens()){
                            String list = st.nextToken();
                            if(!list.equalsIgnoreCase(main.username)){
                                online.add(list);
                            }
                        }
                        main.appendOnlineList(online);
                        break;
                    //  hàm này sẽ thông báo đến client rằng có một file nhận, Chấp nhận hoặc từ chối file  
                    case "CMD_FILE_XD":  // Format:  CMD_FILE_XD [sender] [receiver] [filename]
                        String sender = st.nextToken();
                        String receiver = st.nextToken();
                        String fname = st.nextToken();
                        int confirm = JOptionPane.showConfirmDialog(main, "Từ: "+sender+"\ntên file: "+fname+"\n bạn có Chấp nhận file này không.?");
                        //SoundEffect.FileSharing.play(); //   Play Audio
                        if(confirm == 0){ // client chấp nhận yêu cầu, sau đó thông báo đến sender để gửi file
                            /* chọn chỗ lưu file   */
                            main.openFolder();
                            try {
                                dos = new DataOutputStream(socket.getOutputStream());
                                // Format:  CMD_SEND_FILE_ACCEPT [ToSender] [Message]
                                String format = "CMD_SEND_FILE_ACCEPT "+sender+" Chấp nhận";
                                dos.writeUTF(format);
                                
                                /*  hàm này sẽ tạo một socket filesharing  để tạo một luồng xử lý file đi vào và socket này sẽ tự động đóng khi hoàn thành.  */
                                Socket fSoc = new Socket(main.getMyHost(), main.getMyPort());
                                DataOutputStream fdos = new DataOutputStream(fSoc.getOutputStream());
                                fdos.writeUTF("CMD_SHARINGSOCKET "+ main.getMyUsername());
                                /*  Run Thread for this   */
                                new Thread(new ReceivingFileThread(fSoc, main)).start();
                            } catch (IOException e) {
                                System.out.println("[CMD_FILE_XD]: "+e.getMessage());
                            }
                        } else { // client từ chối yêu cầu, sau đó gửi kết quả tới sender
                            try {
                                dos = new DataOutputStream(socket.getOutputStream());
                                // Format:  CMD_SEND_FILE_ERROR [ToSender] [Message]
                                String format = "CMD_SEND_FILE_ERROR "+sender+" Người dùng từ chối yêu cầu của bạn hoặc bị mất kết nối.!";
                                dos.writeUTF(format);
                            } catch (IOException e) {
                                System.out.println("[CMD_FILE_XD]: "+e.getMessage());
                            }
                        }                       
                        break;   
                        
                    default: 
                        main.appendMessage("[CMDException]: Không rõ lệnh "+ CMD, "CMDException", Color.RED, Color.RED);
                    break;
                }
            }
        } catch(IOException e){
            main.appendMessage(" Bị mất kết nối đến Máy chũ, vui lòng thử lại.!", "Lỗi", Color.RED, Color.RED);
        }
    }
}