/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strikethefrog.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import strikethefrog.Tournament.TournamentDTO;
import strikethefrog.utils.DBUtils;

public class PlayerDAO {

    public PlayerDTO load(int playerID) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = DBUtils.getConnection();

            String sql = "SELECT p.PlayerID, p.UserID, p.PlayerStatus, p.TournamentID, u.FullName " +
                         "FROM Players p " +
                         "JOIN Users u ON p.UserID = u.UserID " +
                         "WHERE p.PlayerID = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, playerID);

            rs = stmt.executeQuery();

            if (rs.next()) {
                PlayerDTO player = new PlayerDTO();
                player.setPlayerID(rs.getInt("PlayerID"));
                player.setUserID(rs.getInt("UserID"));
                player.setPlayerStatus(rs.getString("PlayerStatus"));
                player.setTournamentID(rs.getInt("TournamentID"));
                player.setFullName(rs.getString("FullName"));

                return player;
            }
        } catch (Exception ex) {
            System.err.println("Error loading player: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }

        return null;
    }
    
    public boolean create(PlayerDTO player){
        try{
            Connection con = DBUtils.getConnection();
            
            String sql = " INSERT INTO Players (UserID, PlayerStatus) VALUES (?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, player.getUserID());
            stmt.setString(2, player.getPlayerStatus());
            
            stmt.executeUpdate();
            con.close();
            
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return false;
    }
    
    public List<PlayerDTO> list(int tournamentID){
        List<PlayerDTO> list = new ArrayList<>();
        try{
            String sql = " SELECT p.PlayerID, u.UserID, u.FullName, p.PlayerStatus, p.TournamentID FROM Players p "
                    + " JOIN Users u ON p.UserID = u.UserID "
                    + " WHERE p.TournamentID = ? ";
            
            Connection con = DBUtils.getConnection();
            
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, tournamentID);
            
            ResultSet rs = stmt.executeQuery();
            if(rs != null){
                while(rs.next()){
                    PlayerDTO player = new PlayerDTO();
                    
                    player.setPlayerID(rs.getInt("PlayerID"));
                    player.setUserID(rs.getInt("UserID"));
                    player.setFullName(rs.getString("FullName"));
                    player.setPlayerStatus(rs.getString("PlayerStatus"));
                    player.setTournamentID(rs.getInt("TournamentID"));
                    
                    list.add(player);
                }
            }
            
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(PlayerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
    
    public int getTotalPlayer() throws SQLException {
        int total = 0;
        try (Connection con = DBUtils.getConnection();
                PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) AS Total FROM Players")) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getInt("Total");
            }
        }
        return total;
    }
    
    // Add this method to your PlayerDAO class
    public int getPlayerCountByTournamentID(int tournamentID) throws SQLException {
        int count = 0;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            con = DBUtils.getConnection();
            String sql = "SELECT COUNT(*) AS PlayerCount FROM Players WHERE TournamentID = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, tournamentID);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                count = rs.getInt("PlayerCount");
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (con != null) con.close();
        }
        
        return count;
    }
    
    // Add this method to PlayerDAO class
    public List<TournamentDTO> getPaidTournamentsByUser(int userID) throws SQLException {
        List<TournamentDTO> tournaments = new ArrayList<>();
        // String sql = "SELECT T.TournamentID, T.TournamentName, CAST(T.Description AS VARCHAR(MAX)) AS Description, " 
        //            + "T.Location, T.StartTime, T.EndTime, T.Fee, P.PlayerStatus "
        //            + "FROM Tournaments T "
        //            + "INNER JOIN Players P ON T.TournamentID = P.TournamentID "
        //            + "INNER JOIN Transactions Tr ON P.PlayerID = Tr.PlayerID AND P.TournamentID = Tr.TournamentID "
        //            + "WHERE P.UserID = ? AND Tr.PaymentStatus = 'Completed' "
        //            + "GROUP BY T.TournamentID, T.TournamentName, T.Description, T.Location, " 
        //            + "T.StartTime, T.EndTime, T.Fee, P.PlayerStatus";

        String sql = "SELECT T.TournamentID, T.TournamentName, CAST(T.Description AS VARCHAR(MAX)) AS Description, " 
           + "T.Location, T.StartTime, T.EndTime, T.Fee, P.PlayerStatus "
           + "FROM Tournaments T "
           + "INNER JOIN Players P ON T.TournamentID = P.TournamentID "
           + "INNER JOIN Transactions Tr ON P.PlayerID = Tr.PlayerID "
           + "WHERE P.UserID = ? AND Tr.PaymentStatus = 'Completed'";
        
        try (Connection con = DBUtils.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                TournamentDTO tournament = new TournamentDTO();
                tournament.setTournamentID(rs.getInt("TournamentID"));
                tournament.setTournamentName(rs.getString("TournamentName"));
                tournament.setDescription(rs.getString("Description"));
                tournament.setLocation(rs.getString("Location"));
                tournament.setStartTime(rs.getTimestamp("StartTime"));
                tournament.setEndTime(rs.getTimestamp("EndTime"));
                tournament.setFee(rs.getBigDecimal("Fee"));
                tournament.setPlayerStatus(rs.getString("PlayerStatus"));
                tournaments.add(tournament);
            }
        }
        return tournaments;
    }
    
    public static void main(String[] args) throws SQLException {
        PlayerDAO dao = new PlayerDAO();
//        PlayerDTO p = dao.load(2);
        System.out.println(dao.getTotalPlayer());
    }
}
