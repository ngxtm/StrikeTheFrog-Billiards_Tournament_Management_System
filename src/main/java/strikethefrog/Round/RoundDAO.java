/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strikethefrog.Round;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import strikethefrog.utils.DBUtils;

public class RoundDAO {

    public List<RoundDTO> searchRoundsByName(String searchname) {
        List<RoundDTO> roundList = new ArrayList<>();
        try {
            Connection con = DBUtils.getConnection();
            String sql = "SELECT * FROM Rounds";

            if (searchname != null && !searchname.isEmpty()) {
                sql += " WHERE RoundName LIKE ?";
            }

            PreparedStatement stmt = con.prepareStatement(sql);

            if (searchname != null && !searchname.isEmpty()) {
                String searchPattern = "%" + searchname + "%";
                stmt.setString(1, searchPattern);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RoundDTO round = new RoundDTO();
                round.setRoundid(rs.getString("RoundID"));
                round.setRoundname(rs.getString("RoundName"));

                roundList.add(round);
            }

            con.close();
        } catch (Exception e) {
            System.out.println("Error searching rounds: " + e.getMessage());
            e.printStackTrace();
        }
        return roundList;
    }

    public boolean deleteRound(String roundID) {

        try {
            Connection con = DBUtils.getConnection();
            String sql = "DELETE FROM Rounds WHERE RoundID = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, roundID);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            }

            con.close();
        } catch (Exception e) {
            System.out.println("Error deleting round: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public boolean addRound(RoundDTO round) {
        boolean success = false;
        try {
            Connection con = DBUtils.getConnection();
            String sql = "INSERT INTO Rounds (RoundName) VALUES (?)";

            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, round.getRoundname());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        round.setRoundid(generatedKeys.getString(1));
                        success = true;
                    }
                }
            }

            con.close();
        } catch (Exception e) {
            System.out.println("Error adding round: " + e.getMessage());
            e.printStackTrace();
        }
        return success;
    }

    public boolean updateRound(RoundDTO round) {
        boolean success = false;
        try {
            Connection con = DBUtils.getConnection();
            String sql = "UPDATE Rounds SET RoundName = ? WHERE RoundID = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, round.getRoundname());
            stmt.setString(2, round.getRoundid());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                success = true;
            }

            con.close();
        } catch (Exception e) {
            System.out.println("Error updating round: " + e.getMessage());
            e.printStackTrace();
        }
        return success;
    }

    public RoundDTO loadRound(String roundID) {
        RoundDTO round = null;
        try {
            Connection con = DBUtils.getConnection();
            String sql = "SELECT * FROM Rounds WHERE RoundID = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, roundID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                round = new RoundDTO();
                round.setRoundid(rs.getString("RoundID"));
                round.setRoundname(rs.getString("RoundName"));
            }

            con.close();
        } catch (Exception e) {
            System.out.println("Error loading round: " + e.getMessage());
            e.printStackTrace();
        }
        return round;
    }

    public boolean isRoundNameTaken(String roundName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Rounds WHERE RoundName = ?";

        try (Connection conn = DBUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, roundName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

            return false;
        }
    }

}
