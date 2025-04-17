/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strikethefrog.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import strikethefrog.utils.DBUtils;

/**
 *
 * @author ASUS
 */
public class TableDAO {

    public List<TableDTO> list(String searchTableName) {
        List<TableDTO> list = new ArrayList<>();
        try {
            String sql = " SELECT TableID, TableName FROM GameTable WHERE 1=1 ";
            Connection con = DBUtils.getConnection();

            if (searchTableName != null && !searchTableName.isEmpty()) {
                sql += " AND TableName LIKE ? ";
            }

            PreparedStatement stmt = con.prepareStatement(sql);

            if (searchTableName != null && !searchTableName.isEmpty()) {
                stmt.setString(1, "%" + searchTableName + "%");
            }

            ResultSet rs = stmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    TableDTO table = new TableDTO();

                    table.setTableID(rs.getInt("TableID"));
                    table.setTableName(rs.getString("TableName"));

                    list.add(table);
                }
            }

            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(TableDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public boolean insert(TableDTO table) {
        String sql = " INSERT INTO GameTable(TableName) "
                + "VALUES(?)";
        try {
            Connection con = DBUtils.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, table.getTableName());

            stmt.executeUpdate();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Cannot insert new table. Details: " + ex.getMessage());
        }

        return false;
    }

    public TableDTO load(int tableID) {
        TableDTO table = null;
        try {
            String sql = " SELECT TableID, TableName FROM GameTable WHERE TableID = ? ";
            Connection con = DBUtils.getConnection();

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, tableID);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                table = new TableDTO();
                
                table.setTableID(tableID);
                table.setTableName(rs.getString("TableName"));
            }
        } catch (SQLException ex) {
            System.out.println("Cannot insert new table. Details: " + ex.getMessage());
        }
        
        return table;
    }
    
    public boolean update(TableDTO table){
        String sql = " UPDATE GameTable SET TableName = ? WHERE TableID = ? ";
        try{
            Connection con = DBUtils.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, table.getTableName());
            stmt.setInt(2, table.getTableID());
            
            stmt.executeUpdate();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Cannot insert new table. Details: " + ex.getMessage());
        }
        
        return false;
    }
    
    public boolean delete(int tableID){
        String sql = " DELETE FROM GameTable WHERE TableID = ? ";
        try{
            Connection con = DBUtils.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, tableID);
            
            stmt.executeUpdate();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Cannot insert new table. Details: " + ex.getMessage());
        }
        
        return false;
    }
}
