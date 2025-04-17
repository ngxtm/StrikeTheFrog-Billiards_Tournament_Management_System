/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strikethefrog.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import strikethefrog.utils.DBUtils;

/**
 *
 * @author DUNGHUYNH
 */
public class UserDAO {

    public UserDTO login(String username, String password) {

        UserDTO user = null;
        try {
            Connection con = DBUtils.getConnection();
            String sql = " SELECT UserID, FullName, Username, Email, PhoneNumber FROM Users ";
            sql += " WHERE Username = ? AND Password = ? ";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs != null) {
                if (rs.next()) {
                    user = new UserDTO();

                    user.setUserID(rs.getInt("UserID"));
                    user.setUsername(rs.getString("Username"));
                    user.setFullname(rs.getString("FullName"));
                    user.setEmail(rs.getString("Email"));
                    user.setPhonenumber(rs.getString("PhoneNumber"));
                }
            }

            con.close();
        } catch (SQLException ex) {
            System.out.println("Error in servlet. Details:" + ex.getMessage());
            ex.printStackTrace();
        }

        return user;

    }

    public UserDTO signup(String fullname, String username, String password, String email, String phonenumber) {
    UserDTO user = null;
    try {
        Connection con = DBUtils.getConnection();

        String insertSql = " INSERT INTO Users (FullName, Username, Password, Email, PhoneNumber) VALUES (?, ?, ?, ?, ?) ";
        PreparedStatement insertStmt = con.prepareStatement(insertSql);
        insertStmt.setString(1, fullname);
        insertStmt.setString(2, username);
        insertStmt.setString(3, password);
        insertStmt.setString(4, email);
        insertStmt.setString(5, phonenumber);
        insertStmt.executeUpdate(); 

        String fetchSql = " SELECT * FROM Users WHERE username = ? ";
        PreparedStatement fetchStmt = con.prepareStatement(fetchSql);
        fetchStmt.setString(1, username);
        ResultSet rs = fetchStmt.executeQuery();

        if (rs.next()) {
            user = new UserDTO(); 
            
            user.setUserID(rs.getInt("UserID")); 
            user.setFullname(rs.getString("FullName"));
            user.setUsername(rs.getString("Username"));
            user.setEmail(rs.getString("Email"));
            user.setPhonenumber(rs.getString("PhoneNumber"));
        }

        con.close();
    } catch (SQLException ex) {
        System.out.println("Error in signup. Details: " + ex.getMessage());
        ex.printStackTrace();
    }
    return user;
}


    public UserDTO load(int userID) {
        UserDTO user = null;

        try {
            Connection con = DBUtils.getConnection();
            String sql = "SELECT UserID, Username, FullName, Email, PhoneNumber, Password FROM Users WHERE UserID = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, userID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new UserDTO();

                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setFullname(rs.getString("FullName"));
                user.setEmail(rs.getString("Email"));
                user.setPhonenumber(rs.getString("PhoneNumber"));
                user.setPassword(rs.getString("Password"));
            }

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return user;
    }

    public int getIDByFullName(String fullName) throws SQLException {
        String sql = " SELECT UserID FROM Users WHERE FullName = ? ";
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fullName);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt("UserID") : -1;
        }
    }

    public List listUsers(String searchTerm) {
        List userList = new ArrayList<>();
        try {
            Connection con = DBUtils.getConnection();
            String sql = "SELECT * FROM Users";
            if (searchTerm != null && !searchTerm.isEmpty()) {
                sql += " WHERE FullName LIKE ?";
            }
            PreparedStatement stmt = con.prepareStatement(sql);
            if (searchTerm != null && !searchTerm.isEmpty()) {
                String searchPattern = "%" + searchTerm + "%";
                stmt.setString(1, searchPattern);
            }
            ResultSet rs = stmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    UserDTO user = new UserDTO();
                    user.setUserID(rs.getInt("UserID"));
                    user.setFullname(rs.getString("FullName"));
                    user.setUsername(rs.getString("Username"));
                    user.setPassword(rs.getString("Password"));
                    user.setEmail(rs.getString("Email"));
                    user.setPhonenumber(rs.getString("PhoneNumber"));
                    userList.add(user);
                }
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Error listing users: " + e.getMessage());
        }
        return userList;
    }

    public boolean canDeleteUser(int userID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Players WHERE UserID = ?";

        try (Connection conn = DBUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int playerCount = rs.getInt(1);
                    return playerCount == 0;
                }
            }
        }

        return false;
    }

    public boolean deleteUser(int userID) throws SQLException {
        if (!canDeleteUser(userID)) {
            throw new SQLException("User has associated player records and cannot be deleted.");
        }

        String sql = "DELETE FROM Users WHERE UserID = ?";

        try (Connection conn = DBUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean insertUser(UserDTO user) throws SQLException {
        String sql = "INSERT INTO Users(FullName, Username, Password, Email, PhoneNumber) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getFullname());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPhonenumber());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setUserID(generatedKeys.getInt(1));
                    }
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error inserting user: " + e.getMessage());
            e.printStackTrace(); // In stack trace để debug chi tiết hơn
        }
        return false;
    }

    public boolean isUsernameTaken(String username) {
        boolean taken = false;
        try {
            Connection con = DBUtils.getConnection();
            String sql = " SELECT * FROM Users WHERE Username = ? ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                taken = true;
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return taken;
    }

    public boolean isEmailTaken(String email) {
        boolean taken = false;
        try {
            Connection con = DBUtils.getConnection();
            String sql = " SELECT * FROM Users WHERE Email = ? ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                taken = true;
            }

            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return taken;
    }

    public boolean isPhoneNumberTaken(String phonenumber) {
        boolean taken = false;
        try {
            Connection con = DBUtils.getConnection();
            String sql = " SELECT * FROM Users WHERE PhoneNumber = ? ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, phonenumber);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                taken = true;
            }
            
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return taken;
    }

    public boolean updateUser(UserDTO user) throws SQLException {
        String sql = " UPDATE Users SET FullName = ?, Username = ?, Password = ?, Email = ?, PhoneNumber = ? WHERE UserID = ? ";

        try (Connection conn = DBUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getFullname());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPhonenumber());
            stmt.setInt(6, user.getUserID());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
