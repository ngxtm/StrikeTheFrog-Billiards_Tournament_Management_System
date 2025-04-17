package strikethefrog.Match;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import strikethefrog.utils.DBUtils;

public class GameMatchDAO {
    
    /**
     * Get all matches
     * @return List of matches
     */
    public List<GameMatchDTO> getAllMatches() {
        List<GameMatchDTO> matches = new ArrayList<>();
        
        try {
            Connection con = DBUtils.getConnection();
            String sql = "SELECT m.MatchID, m.RoundID, r.RoundName, m.TableID, " +
                         "m.Player1ID, m.Player2ID, m.ScoreP1, m.ScoreP2, " +
                         "m.GameStatus, m.WinnerPlayer, m.TournamentID, m.MatchTime, " +
                         "t.TournamentName, " +
                         "p1.UserID as p1UserID, u1.FullName as Player1Name, " +
                         "p2.UserID as p2UserID, u2.FullName as Player2Name, " +
                         "tbl.TableName " +
                         "FROM GameMatch m " +
                         "JOIN Tournaments t ON m.TournamentID = t.TournamentID " +
                         "JOIN Rounds r ON m.RoundID = r.RoundID " +
                         "JOIN Players p1 ON m.Player1ID = p1.PlayerID " +
                         "JOIN Users u1 ON p1.UserID = u1.UserID " +
                         "JOIN Players p2 ON m.Player2ID = p2.PlayerID " +
                         "JOIN Users u2 ON p2.UserID = u2.UserID " +
                         "JOIN GameTable tbl ON m.TableID = tbl.TableID " +
                         "ORDER BY t.StartTime DESC, r.RoundID ASC, m.MatchTime ASC";
            
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                GameMatchDTO match = new GameMatchDTO();
                match.setMatchID(rs.getInt("MatchID"));
                match.setRoundID(rs.getInt("RoundID"));
                match.setRoundName(rs.getString("RoundName"));
                match.setTableID(rs.getInt("TableID"));
                match.setTableName(rs.getString("TableName"));
                match.setPlayer1ID(rs.getInt("Player1ID"));
                match.setPlayer2ID(rs.getInt("Player2ID"));
                match.setPlayer1Name(rs.getString("Player1Name"));
                match.setPlayer2Name(rs.getString("Player2Name"));
                match.setScoreP1(rs.getInt("ScoreP1"));
                match.setScoreP2(rs.getInt("ScoreP2"));
                match.setGameStatus(rs.getString("GameStatus"));
                match.setWinnerPlayer(rs.getInt("WinnerPlayer"));
                match.setTournamentID(rs.getInt("TournamentID"));
                match.setTournamentName(rs.getString("TournamentName"));
                match.setMatchTime(rs.getTimestamp("MatchTime"));
                
                matches.add(match);
            }
            
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(GameMatchDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return matches;
    }
    
    /**
     * Get matches by tournament ID
     * @param tournamentID The tournament ID
     * @return List of matches for the tournament
     */
    public List<GameMatchDTO> getMatchesByTournament(int tournamentID) {
        List<GameMatchDTO> matches = new ArrayList<>();
        
        try {
            Connection con = DBUtils.getConnection();
            String sql = "SELECT m.MatchID, m.RoundID, r.RoundName, m.TableID, " +
                         "m.Player1ID, m.Player2ID, m.ScoreP1, m.ScoreP2, " +
                         "m.GameStatus, m.WinnerPlayer, m.TournamentID, m.MatchTime, " +
                         "t.TournamentName, " +
                         "p1.UserID as p1UserID, u1.FullName as Player1Name, " +
                         "p2.UserID as p2UserID, u2.FullName as Player2Name, " +
                         "tbl.TableName " +
                         "FROM GameMatch m " +
                         "JOIN Tournaments t ON m.TournamentID = t.TournamentID " +
                         "JOIN Rounds r ON m.RoundID = r.RoundID " +
                         "JOIN Players p1 ON m.Player1ID = p1.PlayerID " +
                         "JOIN Users u1 ON p1.UserID = u1.UserID " +
                         "JOIN Players p2 ON m.Player2ID = p2.PlayerID " +
                         "JOIN Users u2 ON p2.UserID = u2.UserID " +
                         "JOIN GameTable tbl ON m.TableID = tbl.TableID " +
                         "WHERE m.TournamentID = ? " +
                         "ORDER BY r.RoundID ASC, m.MatchTime ASC";
            
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, tournamentID);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                GameMatchDTO match = new GameMatchDTO();
                match.setMatchID(rs.getInt("MatchID"));
                match.setRoundID(rs.getInt("RoundID"));
                match.setRoundName(rs.getString("RoundName"));
                match.setTableID(rs.getInt("TableID"));
                match.setTableName(rs.getString("TableName"));
                match.setPlayer1ID(rs.getInt("Player1ID"));
                match.setPlayer2ID(rs.getInt("Player2ID"));
                match.setPlayer1Name(rs.getString("Player1Name"));
                match.setPlayer2Name(rs.getString("Player2Name"));
                match.setScoreP1(rs.getInt("ScoreP1"));
                match.setScoreP2(rs.getInt("ScoreP2"));
                match.setGameStatus(rs.getString("GameStatus"));
                match.setWinnerPlayer(rs.getInt("WinnerPlayer"));
                match.setTournamentID(rs.getInt("TournamentID"));
                match.setTournamentName(rs.getString("TournamentName"));
                match.setMatchTime(rs.getTimestamp("MatchTime"));
                
                matches.add(match);
            }
            
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(GameMatchDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return matches;
    }
} 