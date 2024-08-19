/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Timestamp;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
public class DBConnection {
    private static final  String url = "jdbc:sqlserver://localhost:1433;databaseName=CHAT;encrypt=true;trustServerCertificate=true;";
    private static final  String user = "sa";
    private static final  String pass = "123456";
  public static void main(String[] args) {
      //  queryUserTable();
//        queryCustomerTable();
    }
  public static String[] getUserInfo(String username) {
        String[] userInfo = new String[5]; // Mảng để lưu thông tin người dùng

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            String query = "SELECT username, password, fullname, gender, dob FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                userInfo[0] = resultSet.getString("username");
                userInfo[1] = resultSet.getString("password");
                userInfo[2] = resultSet.getString("fullname");
                userInfo[3] = resultSet.getString("gender");
                userInfo[4] = resultSet.getString("dob");
            } else {
                // Người dùng không tồn tại trong cơ sở dữ liệu
                userInfo = null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý exception nếu có
        }

        return userInfo;
    }
public static boolean isUserVerified(String username) {
    boolean isVerified = false;
    try {
        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            String query = "SELECT status FROM users WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String status = resultSet.getString("status");
                        if (status.equals("verified")) {
                            isVerified = true;
                        }
                    }
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return isVerified;
}
public static boolean updateVerifyCode(String username, String code) {
    try {
        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            String query = "SELECT * FROM users WHERE username = ? AND verifycode = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, code);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String updateQuery = "UPDATE users SET status = ? WHERE username = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setString(1, "verified"); // Set status
                            updateStatement.setString(2, username);

                            int rowsAffected = updateStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                return true; // Mã xác thực đúng và cập nhật thành công
                            }
                        }
                    }
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return false; // Mã xác thực không đúng hoặc không cập nhật thành công
}
public static List<String> retrieveGroupMessages(String groupName) {
    List<String> messages = new ArrayList<>();
    String query = "SELECT u.username, m.message_text " +
                   "FROM GroupMessages m " +
                   "JOIN GroupList g ON m.group_id = g.group_id " +
                   "JOIN Users u ON m.sender = u.username " +
                   "WHERE g.group_name = ? AND g.group_id = (SELECT group_id FROM GroupList WHERE group_name = ?) " +
                   "ORDER BY m.sent_at ASC";

    try (Connection connection = DriverManager.getConnection(url, user, pass);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, groupName);
        statement.setString(2, groupName);

        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String senderUsername = resultSet.getString("username");
                String encryptedMessage = resultSet.getString("message_text");
                String encryptionKey =  getSecretKeyByUsername(senderUsername);

                // Giải mã tin nhắn
                String decryptedMessage = decryptMessage(encryptedMessage, encryptionKey);

                String message = senderUsername + " : " + decryptedMessage;
                messages.add(message);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return messages;
}

 public static List<String> retrieveMessages(String senderUsername, String receiverUsername) {
    List<String> messages = new ArrayList<>();
    String query = "SELECT " +
            "CASE WHEN receiver_username = ? THEN sender_username " +
            "     WHEN sender_username = ? THEN receiver_username " +
            "END AS correspondent, " +
            "message_content " +
            "FROM Messagess " +
            "WHERE (sender_username = ? AND receiver_username = ?) OR (sender_username = ? AND receiver_username = ?) " +
            "ORDER BY sent_at ASC";

    try (Connection connection = DriverManager.getConnection(url, user, pass);
         PreparedStatement statement = connection.prepareStatement(query)) {
       statement.setString(1, receiverUsername);
        statement.setString(2, senderUsername);
        statement.setString(3, senderUsername);
        statement.setString(4, receiverUsername);
        statement.setString(5, receiverUsername);
        statement.setString(6, senderUsername);
        

        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String correspondent = resultSet.getString("correspondent");
                String encryptedSecretKey = null;
                if(correspondent!=null) {
                    encryptedSecretKey = getSecretKeyByUsername(senderUsername);
                }
                else {
                    encryptedSecretKey = getSecretKeyByUsername(receiverUsername);
                   
                }
                System.out.println(encryptedSecretKey);
                String encryptedMessage = resultSet.getString("message_content");
                // Giải mã tin nhắn sử dụng secret_key
                String decryptedMessage = decryptMessage(encryptedMessage, encryptedSecretKey);

                String message = correspondent + " : " + decryptedMessage;
                messages.add(message);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return messages;
}
 public static String getSecretKeyByUsername(String username) {
    String secretKey = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
        connection = DriverManager.getConnection(url, user, pass);
        String query = "SELECT secret_key FROM Users WHERE username = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            secretKey = resultSet.getString("secret_key");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return secretKey;
}

 public static String decryptMessage(String encryptedMessage, String encryptionKey) {
    try {
        // Decode chuỗi Base64 thành mảng byte mã hóa và vectơ khởi đầu
        byte[] combinedBytes = Base64.getDecoder().decode(encryptedMessage);

        // Tách vectơ khởi đầu và dữ liệu mã hóa
        byte[] ivBytes = Arrays.copyOfRange(combinedBytes, 0, 16);
        byte[] encryptedBytes = Arrays.copyOfRange(combinedBytes, 16, combinedBytes.length);

        // Khởi tạo khóa giải mã từ encryptionKey
        byte[] keyBytes = Base64.getDecoder().decode(encryptionKey);
        SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedMessage = new String(decryptedBytes, StandardCharsets.UTF_8);

        return decryptedMessage;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}


public static void saveGroupMessage(String groupName, String senderUsername, String messageContent) {
    LocalDateTime sentAt = LocalDateTime.now(); // Lấy thời gian gửi tin nhắn hiện tại

    String query = "INSERT INTO GroupMessages (group_id, message_text, sender, sent_at) " +
                   "SELECT group_id, ?, ?, ? FROM GroupList WHERE group_name = ?";

    try (Connection connection = DriverManager.getConnection(url, user, pass);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, messageContent);
        statement.setString(2, senderUsername);
        statement.setObject(3, sentAt);
        statement.setString(4, groupName);
        statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

  public static void saveMessage(String senderUsername, String receiverUsername, String messageContent) {
        LocalDateTime sentAt = LocalDateTime.now(); // Lấy thời gian gửi tin nhắn hiện tại

        String query = "INSERT INTO Messagess (sender_username, receiver_username, message_content, sent_at) " +
                       "VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, senderUsername);
            statement.setString(2, receiverUsername);
            statement.setString(3, messageContent);
            statement.setObject(4, sentAt);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
  //user1 block user2
  public static void blockUser(String blockingUsername, String blockedUsername) {
    String query = "INSERT INTO BlockedUsers (blocking_username, blocked_username) VALUES (?, ?)";

    try (Connection connection = DriverManager.getConnection(url, user, pass);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, blockingUsername);
        statement.setString(2, blockedUsername);
        statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
  //user block group
  public static void blockGroup(String blockingUsername, int blockedGroupId) {
    String query = "INSERT INTO BlockedGroups (blocking_username, blocked_group_id) VALUES (?, ?)";

    try (Connection connection = DriverManager.getConnection(url, user, pass);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, blockingUsername);
        statement.setInt(2, blockedGroupId);
        statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

 public static void unblockUser(String blockingUsername, String blockedUsername) {
    String query = "DELETE FROM BlockedUsers WHERE blocking_username = ? AND blocked_username = ?";

    try (Connection connection = DriverManager.getConnection(url, user, pass);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, blockingUsername);
        statement.setString(2, blockedUsername);
        statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
 public static void unblockGroup(String blockingUsername, int blockedGroupId) {
    String query = "DELETE FROM BlockedGroups WHERE blocking_username = ? AND blocked_group_id = ?";

    try (Connection connection = DriverManager.getConnection(url, user, pass);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, blockingUsername);
        statement.setInt(2, blockedGroupId);
        statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
  public static boolean isBlocked(String blockingUsername, String blockedUsername) {
    String query = "SELECT COUNT(*) AS count FROM BlockedUsers WHERE blocking_username = ? AND blocked_username = ?";
    boolean isBlocked = false;

    try (Connection connection = DriverManager.getConnection(url, user, pass);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, blockingUsername);
        statement.setString(2, blockedUsername);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int count = resultSet.getInt("count");
            isBlocked = count > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return isBlocked;
}
  public static int getGroupIdByGroupNameAndUsername(String groupName, String username) {
    String query = "SELECT group_id FROM GroupList WHERE group_name = ? AND group_members LIKE ?";
    int groupId = -1;

    try (Connection connection = DriverManager.getConnection(url, user, pass);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, groupName);
        statement.setString(2, "%" + username + "%");
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            groupId = resultSet.getInt("group_id");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return groupId;
}

  public static boolean isGroupBlocked(String blockingUsername, int blockedGroupId) {
    String query = "SELECT COUNT(*) AS count FROM BlockedGroups WHERE blocking_username = ? AND blocked_group_id = ?";
    boolean isBlocked = false;

    try (Connection connection = DriverManager.getConnection(url, user, pass);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, blockingUsername);
        statement.setInt(2, blockedGroupId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int count = resultSet.getInt("count");
            isBlocked = count > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return isBlocked;
}
public static int isMessageSRS(String senderUsername, String receiverUsername) {
    String query = "SELECT isSRS FROM Messagess " +
                   "WHERE ((sender_username = ? AND receiver_username = ?) OR (sender_username = ? AND receiver_username = ?)) " +
                   "ORDER BY sent_at DESC ";

    try (Connection connection = DriverManager.getConnection(url, user, pass);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, senderUsername);
        statement.setString(2, receiverUsername);
        statement.setString(3, receiverUsername);
        statement.setString(4, senderUsername);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("isSRS");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // Trả về giá trị mặc định nếu không tìm thấy kết quả
}

 public static void updateUserStatus(int action, String username) {
        try(Connection connection = DriverManager.getConnection(url, user, pass)) {
            String query;
            switch (action) {
                case 1:
                    query = "UPDATE FriendList SET status = 'Online' WHERE username = ?";
                    break;
                case 0:
                    query = "UPDATE FriendList SET status = 'Offline' WHERE username = ?";
                    break;
                default:
                    return; // Nếu action không hợp lệ, thoát khỏi phương thức
            }

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
public static String getUserFriendStatus(String username, String selectedUsername) {
    String status = null;

    String query = "SELECT " +
            "CASE WHEN username_friend = ? THEN username " +
            "     WHEN username = ? THEN username_friend " +
            "END AS correspondent, " +
            "status " +
            "FROM FriendList " +
            "WHERE (username = ? AND username_friend = ?) OR (username = ? AND username_friend = ?) ";

    try (Connection connection = DriverManager.getConnection(url, user, pass);
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, selectedUsername);
        statement.setString(2, username);
        statement.setString(3, username);
        statement.setString(4, selectedUsername);
        statement.setString(5, selectedUsername);
        statement.setString(6, username);

        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String correspondent = resultSet.getString("correspondent");
                if (correspondent == null) {
                    status = resultSet.getString("status");
                } else if (!correspondent.equals(username)) {
                    status = resultSet.getString("status");
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return status;
}

public static List<String> getGroupsByMember(String username) {
    List<String> groupNames = new ArrayList<>();

    try (Connection connection = DriverManager.getConnection(url, user, pass)) {
        String query = "SELECT group_name FROM GroupList WHERE group_members LIKE ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "%" + username + "%");

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String groupName = resultSet.getString("group_name");
            groupNames.add(groupName);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Xử lý exception nếu có
    }

    return groupNames;
}


     public static List<String> getUserFriendsByUsername(String username) {
        List<String> friendUsernames = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            String query = "SELECT username_friend FROM FriendList WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String friendUsername = resultSet.getString("username_friend");
                friendUsernames.add(friendUsername);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý exception nếu có
        }

        return friendUsernames;
    }

   

  // Hàm lấy mật khẩu từ cơ sở dữ liệu dựa trên username
  public static String getPasswordByUsername(String username) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    String hashedPassword = null;

    try {
        // Kết nối tới cơ sở dữ liệu
        conn = DriverManager.getConnection(url,user,pass);

        // Truy vấn để lấy mật khẩu đã được mã hóa dựa trên username
        String query = "SELECT password FROM users WHERE username = ?";
        stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        rs = stmt.executeQuery();

        // Kiểm tra kết quả truy vấn
        if (rs.next()) {
            hashedPassword = rs.getString("password");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Đóng kết nối và giải phóng tài nguyên
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    return hashedPassword;
}
public static int getAllUser() {
    int count = 0;
    Statement statement = null;
    ResultSet resultSet = null;

    try {
        Connection connection = DriverManager.getConnection(url,user,pass);
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT COUNT(*) AS total FROM users");

        if (resultSet.next()) {
            count = resultSet.getInt("total");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Đóng tài nguyên
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return count;
}
//hàm cập nhật user
public static boolean updateUserInfo(String username, String password, String fullname, String gender, String dob) {
    try (Connection connection = DriverManager.getConnection(url, user, pass)) {
        String query = "UPDATE users SET password = ?, fullname = ?, gender = ?, dob = ? WHERE username = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, password);
        statement.setString(2, fullname);
        statement.setString(3, gender);
        statement.setString(4, dob);
        statement.setString(5, username);

        int rowsAffected = statement.executeUpdate();

        return rowsAffected > 0; // Trả về true nếu có ít nhất một dòng dữ liệu bị ảnh hưởng bởi câu lệnh UPDATE

    } catch (SQLException e) {
        e.printStackTrace();
        // Xử lý exception nếu có
    }

    return false; // Trả về false nếu cập nhật không thành công
}

public static void blockUser(boolean yes, String username) {
    // Thực hiện kết nối đến cơ sở dữ liệu
    Connection connection = null;
    Statement statement = null;

    try {
        connection = DriverManager.getConnection(url, user, pass);
        statement = connection.createStatement();

        // Xác định câu truy vấn SQL dựa trên biến yes
        String query;
        if (yes) {
            query = "UPDATE users SET block = 1 WHERE username = '" + username + "'";
              JOptionPane.showMessageDialog(null, "Block thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
        } else {
            query = "UPDATE users SET block = 0 WHERE username = '" + username + "'";
              JOptionPane.showMessageDialog(null, "Đã gỡ block", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
        }

        // Thực hiện câu truy vấn SQL
        statement.executeUpdate(query);
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Đóng tài nguyên
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
public static boolean isUserBlocked(String username) {
    // Thực hiện kết nối đến cơ sở dữ liệu
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    try {
        connection = DriverManager.getConnection(url, user, pass);

        // Kiểm tra trạng thái block của người dùng trong cơ sở dữ liệu
        String query = "SELECT block FROM users WHERE username = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1, username);
        resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int blockStatus = resultSet.getInt("block");
             if (blockStatus == 1) {
                JOptionPane.showMessageDialog(null, "Người dùng đã bị block từ admin!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
            return blockStatus == 1;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Đóng tài nguyên
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return false;
}


  // Hàm kiểm tra sự tồn tại của username trong cơ sở dữ liệu
public static boolean getUsername(String username) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    boolean exists = false;

    try {
        // Kết nối tới cơ sở dữ liệu
        conn = DriverManager.getConnection(url,user,pass);

        // Truy vấn để kiểm tra sự tồn tại của username
        String query = "SELECT COUNT(*) AS count FROM users WHERE username = ?";
        stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        rs = stmt.executeQuery();

        // Kiểm tra kết quả truy vấn
        if (rs.next()) {
            int count = rs.getInt("count");
            exists = count > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Đóng kết nối và giải phóng tài nguyên
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    return exists;
}
 public static boolean removeMemberFromGroup(String groupName, String memberUsername) {
     boolean success = false;
        try (Connection connection = DriverManager.getConnection(url, user, pass)){
            // Kiểm tra xem username có là thành viên của nhóm không
            String selectQuery = "SELECT group_members FROM GroupList WHERE group_name = ?";
            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                selectStatement.setString(1, groupName);
                ResultSet resultSet = selectStatement.executeQuery();
                
                if (resultSet.next()) {
                    String groupMembers = resultSet.getString("group_members");
                    
                    // Xóa memberUsername khỏi group_members nếu nó là chuỗi con
                    if (groupMembers.contains(memberUsername)) {
                        String updatedGroupMembers = groupMembers.replace(memberUsername, "");
                        
                        // Cập nhật group_members mới vào cơ sở dữ liệu
                        String updateQuery = "UPDATE GroupList SET group_members = ? WHERE group_name = ?";
                        PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                        updateStatement.setString(1, updatedGroupMembers);
                        updateStatement.setString(2, groupName);
                        int rowsUpdated = updateStatement.executeUpdate();
                        
                        if (rowsUpdated > 0) {
                           success=true;
                     
                        } else {
                            success=false;
                        
                        }
                    } else {
                        
//                        System.out.println("Thành viên không tồn tại trong nhóm.");
                    }
                } else {
//                    System.out.println("Nhóm không tồn tại.");
                }
                
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

public static boolean createGroup(String groupName, DefaultListModel<String> listModel, String createdBy, LocalDate createdDate) {
    try (Connection connection = DriverManager.getConnection(url, user, pass)) {
        // Kiểm tra xem tên nhóm đã tồn tại trong bảng GroupList hay chưa
        String checkExistQuery = "SELECT COUNT(*) AS group_count FROM GroupList WHERE group_name = ?";
        PreparedStatement checkExistStatement = connection.prepareStatement(checkExistQuery);
        checkExistStatement.setString(1, groupName);
        ResultSet resultSet = checkExistStatement.executeQuery();
        if (resultSet.next()) {
            int groupCount = resultSet.getInt("group_count");
            if (groupCount > 0) {
                return false;
            }
        }

        // Tiếp tục tạo nhóm nếu không trùng tên
        String insertQuery = "INSERT INTO GroupList (group_name, group_members, created_by, created_date) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(insertQuery);
        statement.setString(1, groupName);

        // Xây dựng danh sách thành viên từ listModel
        StringBuilder memberList = new StringBuilder();
        for (int i = 0; i < listModel.size(); i++) {
            memberList.append(listModel.getElementAt(i));
            if (i < listModel.size() - 1) {
                memberList.append(",");
            }
        }
        statement.setString(2, memberList.toString());

        statement.setString(3, createdBy);
        statement.setDate(4, java.sql.Date.valueOf(createdDate));

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Tạo nhóm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }

    } catch (SQLException e) {
        e.printStackTrace();
        // Xử lý exception nếu có
    }
    JOptionPane.showMessageDialog(null, "Tạo nhóm thất bại!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    // Nếu không thành công, trả về false
    return false;
}


public static boolean deleteGroup(String groupName) {
    try (Connection connection = DriverManager.getConnection(url, user, pass)) {
        // Xóa các dòng dữ liệu liên quan trong bảng GroupMessages
        String deleteGroupMessagesQuery = "DELETE FROM GroupMessages WHERE group_id IN (SELECT group_id FROM GroupList WHERE group_name = ?)";
        PreparedStatement deleteGroupMessagesStatement = connection.prepareStatement(deleteGroupMessagesQuery);
        deleteGroupMessagesStatement.setString(1, groupName);
        deleteGroupMessagesStatement.executeUpdate();

        // Xóa nhóm từ bảng GroupList
        String deleteGroupQuery = "DELETE FROM GroupList WHERE group_name = ?";
        PreparedStatement deleteGroupStatement = connection.prepareStatement(deleteGroupQuery);
        deleteGroupStatement.setString(1, groupName);
        int rowsAffected = deleteGroupStatement.executeUpdate();

        if (rowsAffected > 0) {
            return true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Xử lý exception nếu có
    }

    // Nếu không thành công, trả về false
    return false;
}
//public static int getMemberCount(String groupName) {
//    int memberCount = 0;
//    
//    try (Connection connection = DriverManager.getConnection(url, user, pass)){
//        // Truy vấn số lượng thành viên trong nhóm dựa trên group_name
//        String query = "SELECT group_members FROM GroupList WHERE group_name = ?";
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setString(1, groupName);
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    String groupMembers = resultSet.getString("group_members");
//                    if (groupMembers != null) {
//                        // Đếm số lượng thành viên trong group_members
//                        memberCount = groupMembers.split(",").length;
//                    }
//                }
//            }
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    
//    return memberCount;
//}
        public static boolean checkCredentials(String username, String password) {
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE username = ? AND password = ?")) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
