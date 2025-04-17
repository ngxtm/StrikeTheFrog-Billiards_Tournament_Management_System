/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strikethefrog.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import strikethefrog.User.UserDTO;
import strikethefrog.utils.DBUtils;

/**
 *
 * @author ty307
 */
public class AdminDAO {

    public AdminDTO login(String username, String password) {

        AdminDTO admin = null;
        try {
            Connection con = DBUtils.getConnection();
            String sql = " select * from Admins  ";
            sql += " where Username = ? and Password = ? ";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            
            ResultSet rs = stmt.executeQuery();

            if (rs != null) {
                if (rs.next()) {
                    admin = new AdminDTO();

                    admin.setAdminID(rs.getString("AdminID"));
                    admin.setFullname(rs.getString("FullName"));
                    admin.setUsername(rs.getString("Username"));
                    admin.setPassword(rs.getString("Password"));
                    
                }
            }

            con.close();
        } catch (SQLException ex) {
            System.out.println("Error in servlet. Details:" + ex.getMessage());
            ex.printStackTrace();
        }

        return admin;

    }
}
