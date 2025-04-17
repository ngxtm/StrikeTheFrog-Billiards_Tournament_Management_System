package strikethefrog.Match;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import strikethefrog.Tournament.TournamentDAO;
import strikethefrog.User.UserDAO;
import strikethefrog.utils.DBUtils;

/**
 *
 * @author MINH
 */
public class MatchDAO {

    public List<MatchDTO> list(String mIDStr, String tName) throws SQLException {
        List<MatchDTO> list = new ArrayList<>();
        Connection con = DBUtils.getConnection();
        String sql = "SELECT \n"
                + "    gm.MatchID,\n"
                + "    gm.RoundID,\n"
                + "    gm.TableID,\n"
                + "    u1.FullName AS Player1Name,\n"
                + "    u2.FullName AS Player2Name,\n"
                + "    gm.Player1ID, \n"
                + "    gm.Player2ID, \n"
                + "    gm.ScoreP1,\n"
                + "    gm.ScoreP2,\n"
                + "    gm.GameStatus,\n"
                + "    u3.FullName AS WinnerPlayerName,\n"
                + "    gm.WinnerPlayer AS WinnerPlayerID, \n"
                + "    t.TournamentName,\n"
                + "    gm.MatchTime\n"
                + "FROM \n"
                + "    GameMatch gm\n"
                + "INNER JOIN \n"
                + "    Players p1 ON gm.Player1ID = p1.PlayerID\n"
                + "INNER JOIN \n"
                + "    Users u1 ON p1.UserID = u1.UserID\n"
                + "INNER JOIN \n"
                + "    Players p2 ON gm.Player2ID = p2.PlayerID\n"
                + "INNER JOIN \n"
                + "    Users u2 ON p2.UserID = u2.UserID\n"
                + "LEFT JOIN \n"
                + "    Players p3 ON gm.WinnerPlayer = p3.PlayerID\n"
                + "LEFT JOIN \n"
                + "    Users u3 ON p3.UserID = u3.UserID\n"
                + "INNER JOIN \n"
                + "    Tournaments t ON gm.TournamentID = t.TournamentID"
                + " WHERE 1=1 ";

        if (mIDStr != null && !mIDStr.isEmpty()) {
            sql += " AND gm.MatchID = ? ";
        }

        if (tName != null && !tName.isEmpty()) {
            sql += " AND TournamentName like ? ";
        }

        sql += " ORDER BY t.TournamentName DESC, gm.MatchID ASC";

        PreparedStatement stmt = con.prepareStatement(sql);
        int i = 1;
        if (mIDStr != null && !mIDStr.isEmpty()) {
            Integer mID = null;
            try {
                mID = Integer.parseInt(mIDStr);
            } catch (NumberFormatException e) {
                System.out.println("Error converting mID");
            }
            if (mID > 0) {
                stmt.setInt(i, mID);
                ++i;
            }
        }

        if (tName != null && !tName.isEmpty()) {
            stmt.setString(i, "%" + tName + "%");
        }

        ResultSet rs = stmt.executeQuery();
        if (rs != null) {
            while (rs.next()) {
                MatchDTO match = new MatchDTO();
                match.setMatchID(rs.getInt("MatchID"));
                match.setTournamentName(rs.getString("TournamentName"));
                match.setRoundID(rs.getInt("RoundID"));
                match.setTableID(rs.getInt("TableID"));
                match.setPlayer1Name(rs.getString("Player1Name"));
                match.setPlayer2Name(rs.getString("Player2Name"));
                match.setPlayer1ID(rs.getInt("Player1ID"));
                match.setPlayer2ID(rs.getInt("Player2ID"));
                match.setScoreP1(rs.getInt("ScoreP1"));
                match.setScoreP2(rs.getInt("ScoreP2"));
                match.setGameStatus(rs.getString("GameStatus"));
                match.setWinnerPlayerName(rs.getString("WinnerPlayerName"));
                match.setWinnerPlayerID(rs.getInt("WinnerPlayerID"));
                match.setMatchTime(rs.getTimestamp("MatchTime"));
                list.add(match);
            }
        }
        return list;
    }

    public MatchDTO getByMatchIDAndTournamentName(int mID, String tName) throws SQLException {
        MatchDTO match = new MatchDTO();
        Connection con = DBUtils.getConnection();
        String sql = "SELECT \n"
                + "    gm.MatchID,\n"
                + "    gm.RoundID,\n"
                + "    gm.TableID,\n"
                + "    u1.FullName AS Player1Name,\n"
                + "    u2.FullName AS Player2Name,\n"
                + "    gm.Player1ID, \n"
                + "    gm.Player2ID, \n"
                + "    gm.ScoreP1,\n"
                + "    gm.ScoreP2,\n"
                + "    gm.GameStatus,\n"
                + "    u3.FullName AS WinnerPlayerName,\n"
                + "    gm.WinnerPlayer AS WinnerPlayerID, \n"
                + "    t.TournamentName,\n"
                + "    gm.MatchTime\n"
                + "FROM \n"
                + "    GameMatch gm\n"
                + "INNER JOIN \n"
                + "    Players p1 ON gm.Player1ID = p1.PlayerID\n"
                + "INNER JOIN \n"
                + "    Users u1 ON p1.UserID = u1.UserID\n"
                + "INNER JOIN \n"
                + "    Players p2 ON gm.Player2ID = p2.PlayerID\n"
                + "INNER JOIN \n"
                + "    Users u2 ON p2.UserID = u2.UserID\n"
                + "LEFT JOIN \n"
                + "    Players p3 ON gm.WinnerPlayer = p3.PlayerID\n"
                + "LEFT JOIN \n"
                + "    Users u3 ON p3.UserID = u3.UserID\n"
                + "INNER JOIN \n"
                + "    Tournaments t ON gm.TournamentID = t.TournamentID"
                + " WHERE MatchID = ? AND TournamentName = ? ";

        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, mID);
        stmt.setString(2, tName);

        ResultSet rs = stmt.executeQuery();
        if (rs != null) {
            while (rs.next()) {
                match.setMatchID(rs.getInt("MatchID"));
                match.setTournamentName(rs.getString("TournamentName"));
                match.setRoundID(rs.getInt("RoundID"));
                match.setTableID(rs.getInt("TableID"));
                match.setPlayer1Name(rs.getString("Player1Name"));
                match.setPlayer2Name(rs.getString("Player2Name"));
                match.setPlayer1ID(rs.getInt("Player1ID"));
                match.setPlayer2ID(rs.getInt("Player2ID"));
                match.setScoreP1(rs.getInt("ScoreP1"));
                match.setScoreP2(rs.getInt("ScoreP2"));
                match.setGameStatus(rs.getString("GameStatus"));
                match.setWinnerPlayerName(rs.getString("WinnerPlayerName"));
                match.setWinnerPlayerID(rs.getInt("WinnerPlayerID"));
                match.setMatchTime(rs.getTimestamp("MatchTime"));
            }
        }
        return match;
    }

    public boolean update(MatchDTO match) throws SQLException {

        Integer winnerPlayerID = null;

        if (match.getScoreP1() > match.getScoreP2()) {
            winnerPlayerID = match.getPlayer1ID();
        } else if (match.getScoreP2() > match.getScoreP1()) {
            winnerPlayerID = match.getPlayer2ID();
        }

        String sql = " UPDATE GameMatch SET RoundID = ?, TableID = ?, Player1ID = ?, Player2ID = ?, ScoreP1 = ?, \n"
                + " ScoreP2 = ?, GameStatus = ?, WinnerPlayer = ?, MatchTime = ? WHERE MatchID = ? ";
        try (Connection con = DBUtils.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, match.getRoundID());
            stmt.setInt(2, match.getTableID());
            stmt.setInt(3, match.getPlayer1ID());
            stmt.setInt(4, match.getPlayer2ID());
            stmt.setInt(5, match.getScoreP1());
            stmt.setInt(6, match.getScoreP2());
            stmt.setString(7, match.getGameStatus());

            // Xử lý trường hợp winnerPlayerID là null
            if (winnerPlayerID == null) {
                stmt.setNull(8, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(8, winnerPlayerID);
            }

            stmt.setTimestamp(9, match.getMatchTime());
            stmt.setInt(10, match.getMatchID());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int matchID, int tournamentID) throws SQLException {
        String sql = " DELETE FROM GameMatch WHERE MatchID = ? AND TournamentID = ? ";
        try (Connection con = DBUtils.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, matchID);
            stmt.setInt(2, tournamentID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error in delete func of MatchDAO. Details: " + e.getMessage());
        }
        return false;
    }

    public boolean insert(MatchDTO match) throws SQLException {
        // The SQL statement should match the exact structure of your GameMatch table
        String sql = " INSERT INTO GameMatch (MatchID, RoundID, TableID, Player1ID, Player2ID, ScoreP1, ScoreP2, GameStatus, WinnerPlayer, TournamentID, MatchTime) "
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Determine winner based on scores if not already set
        Integer winnerPlayerID = match.getWinnerPlayerID();
        if (winnerPlayerID <= 0) {
            if (match.getScoreP1() > match.getScoreP2()) {
                winnerPlayerID = match.getPlayer1ID();
            } else if (match.getScoreP2() > match.getScoreP1()) {
                winnerPlayerID = match.getPlayer2ID();
            } else {
                winnerPlayerID = null; // Tie or no winner yet
            }
        }

        try (Connection con = DBUtils.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, match.getMatchID());
            stmt.setInt(2, match.getRoundID());
            stmt.setInt(3, match.getTableID());
            stmt.setInt(4, match.getPlayer1ID());
            stmt.setInt(5, match.getPlayer2ID());
            stmt.setInt(6, match.getScoreP1());
            stmt.setInt(7, match.getScoreP2());
            stmt.setString(8, match.getGameStatus());

            // Handle the winner player ID - FIXED INDEX FROM 8 TO 9
            if (winnerPlayerID == null) {
                stmt.setNull(9, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(9, winnerPlayerID);
            }

            stmt.setInt(10, match.getTournamentID());
            stmt.setTimestamp(11, match.getMatchTime());

            System.out.println("Executing SQL: " + sql);
            System.out.println("With values: " + match.toString());

            int result = stmt.executeUpdate();
            System.out.println("Insert result: " + result);
            return result > 0;
        } catch (SQLException e) {
            System.out.println("Error in insert func of MatchDAO. Details: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for better debugging
            throw e; // Re-throw to be handled by caller
        }
    }

    public int getTotalTournament() throws SQLException {
        int total = 0;
        try (Connection con = DBUtils.getConnection();
                PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) AS Total FROM Tournaments")) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getInt("Total");
            }
        }
        return total;
    }

    public static void main(String[] args) throws SQLException {
        MatchDAO dao = new MatchDAO();
        String mID = "";
        String tName = "";
        List<MatchDTO> list = dao.list(mID, tName);
        for (MatchDTO m : list) {
            System.out.println(m.toString());
        }
//
//        MatchDTO match = dao.getByMatchIDAndTournamentName(Integer.parseInt(mID), tName);
//        System.out.println(match.toString());
    }
}
