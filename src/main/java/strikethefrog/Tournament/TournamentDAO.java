/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strikethefrog.Tournament;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import strikethefrog.utils.DBUtils;
import java.io.InputStream;

public class TournamentDAO {

    public List<TournamentDTO> list(String searchTourName) {
        List<TournamentDTO> list = new ArrayList<>();

        try {
            Connection con = DBUtils.getConnection();
            String sql = " SELECT TournamentID, TournamentName, Description, Location, StartTime, EndTime, ImgFile FROM Tournaments WHERE 1=1 ";

            if (searchTourName != null && !searchTourName.isEmpty()) {
                sql += " AND TournamentName LIKE ? ";
            }

            PreparedStatement stmt = con.prepareStatement(sql);

            if (searchTourName != null && !searchTourName.isEmpty()) {
                stmt.setString(1, "%" + searchTourName + "%");
            }

            ResultSet rs = stmt.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    TournamentDTO tournament = new TournamentDTO();
                    tournament.setTournamentID(rs.getInt("TournamentID"));
                    tournament.setTournamentName(rs.getString("TournamentName"));
                    tournament.setDescription(rs.getString("Description"));
                    tournament.setLocation(rs.getString("Location"));
                    tournament.setStartTime(rs.getTimestamp("StartTime"));
                    tournament.setEndTime(rs.getTimestamp("EndTime"));
                    tournament.setImgFile(rs.getString("ImgFile"));
                    list.add(tournament);
                }
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(TournamentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public int getIDByName(String name) throws SQLException {
        String sql = " SELECT TournamentID FROM Tournaments WHERE TournamentName = ? ";
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt("TournamentID") : -1;
        }
    }

    public int getTotalEndTournament() throws SQLException {
        String sql = " SELECT COUNT(*) AS Total FROM Tournaments WHERE EndTime < CURRENT_TIMESTAMP; ";
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public TournamentDTO load(int tournamentID) {
        TournamentDTO tournament = null;

        try {
            Connection con = DBUtils.getConnection();
            String sql = " SELECT TournamentID, TournamentName, Description, Location, StartTime, Fee, EndTime, ImgFile FROM Tournaments WHERE TournamentID = ? ";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, tournamentID);

            ResultSet rs = stmt.executeQuery();
            if (rs != null && rs.next()) {
                tournament = new TournamentDTO();
                tournament.setTournamentID(rs.getInt("TournamentID"));
                tournament.setTournamentName(rs.getString("TournamentName"));
                tournament.setDescription(rs.getString("Description"));
                tournament.setLocation(rs.getString("Location"));
                tournament.setStartTime(rs.getTimestamp("StartTime"));
                tournament.setEndTime(rs.getTimestamp("EndTime"));
                tournament.setImgFile(rs.getString("ImgFile"));
                tournament.setFee(rs.getBigDecimal("Fee"));
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(TournamentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tournament;
    }

    public int getTotalUpcomingTournaments() throws SQLException {
        String sql = " SELECT COUNT(*) AS Total FROM Tournaments WHERE StartTime > CURRENT_TIMESTAMP; ";
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public int getTotalTournaments() throws SQLException {
        String sql = " SELECT COUNT(*) AS Total FROM Tournaments; ";
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
    
    public boolean update(TournamentDTO tournament) {
        String sql = " UPDATE Tournaments SET TournamentName = ?, Description = ?, Location = ?, StartTime = ?, EndTime = ?, ImgFile = ? "
                + " WHERE TournamentID = ? ";
        try {
            Connection con = DBUtils.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, tournament.getTournamentName());
            stmt.setString(2, tournament.getDescription());
            stmt.setString(3, tournament.getLocation());
            stmt.setTimestamp(4, tournament.getStartTime());
            stmt.setTimestamp(5, tournament.getEndTime());
            stmt.setString(6, tournament.getImgFile());
            stmt.setInt(7, tournament.getTournamentID());

            stmt.executeUpdate();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(TournamentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean insert(TournamentDTO tournament) {
        String sql = " INSERT INTO Tournaments(TournamentName, Description, Location, StartTime, EndTime, ImgFile) "
                + " VALUES(?, ?, ?, ?, ?, ?) ";
        try {
            Connection con = DBUtils.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, tournament.getTournamentName());
            stmt.setString(2, tournament.getDescription());
            stmt.setString(3, tournament.getLocation());
            stmt.setTimestamp(4, tournament.getStartTime());
            stmt.setTimestamp(5, tournament.getEndTime());
            stmt.setString(6, tournament.getImgFile());

            stmt.executeUpdate();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(TournamentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean delete(int tournamentID) {
        String sql = " DELETE FROM Tournaments WHERE TournamentID = ? ";
        try {
            Connection con = DBUtils.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, tournamentID);

            stmt.executeUpdate();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(TournamentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void clearImgFile(int tournamentID) {
        String sql = " UPDATE Tournaments SET ImgFile = NULL WHERE TournamentID = ? ";
        try {
            Connection con = DBUtils.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, tournamentID);
            stmt.executeUpdate();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(TournamentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Validates if a match time is within the tournament's start and end time
     *
     * @param tournamentID The ID of the tournament
     * @param matchTime The match time to validate
     * @return A validation result object containing success status and message
     * @throws SQLException If a database error occurs
     */
    public ValidationResult validateMatchTime(int tournamentID, Timestamp matchTime) throws SQLException {
        String sql = "SELECT StartTime, EndTime FROM Tournaments WHERE TournamentID = ?";
        try (Connection conn = DBUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tournamentID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Timestamp startTime = rs.getTimestamp("StartTime");
                Timestamp endTime = rs.getTimestamp("EndTime");

                if (matchTime == null) {
                    return new ValidationResult(false, "Match time cannot be empty");
                }

                if (matchTime.before(startTime)) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
                    String formattedStart = startTime.toLocalDateTime().format(formatter);
                    String formattedEnd = endTime.toLocalDateTime().format(formatter);
                    return new ValidationResult(false,
                            "Match time must be after tournament start time. Valid range: "
                            + formattedStart + " and " + formattedEnd);
                }

                if (matchTime.after(endTime)) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
                    String formattedStart = startTime.toLocalDateTime().format(formatter);
                    String formattedEnd = endTime.toLocalDateTime().format(formatter);
                    return new ValidationResult(false,
                            "Match time must be before tournament end time. Valid range: "
                            + formattedStart + " and " + formattedEnd);
                }

                return new ValidationResult(true, "Match time is valid");
            } else {
                return new ValidationResult(false, "Tournament not found");
            }
        }
    }

    /**
     * Inner class to represent validation results
     */
    public class ValidationResult {

        private boolean valid;
        private String message;

        public ValidationResult(boolean valid, String message) {
            this.valid = valid;
            this.message = message;
        }

        public boolean isValid() {
            return valid;
        }

        public String getMessage() {
            return message;
        }
    }

    public static void main(String[] args) throws SQLException {
        TournamentDAO dao = new TournamentDAO();
        System.out.println(dao.getTotalUpcomingTournaments());
    }

    /**
     * Get list of ongoing tournaments (started but not ended)
     *
     * @return List of ongoing tournaments
     * @throws SQLException If a database error occurs
     */
    public List<TournamentDTO> getOngoingTournaments() throws SQLException {
        List<TournamentDTO> list = new ArrayList<>();
        String sql = "SELECT TournamentID, TournamentName, Description, Location, StartTime, EndTime "
                + "FROM Tournaments "
                + "WHERE StartTime <= CURRENT_TIMESTAMP AND EndTime >= CURRENT_TIMESTAMP "
                + "ORDER BY StartTime ASC";

        try (Connection conn = DBUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TournamentDTO tournament = new TournamentDTO();
                tournament.setTournamentID(rs.getInt("TournamentID"));
                tournament.setTournamentName(rs.getString("TournamentName"));
                tournament.setDescription(rs.getString("Description"));
                tournament.setLocation(rs.getString("Location"));
                tournament.setStartTime(rs.getTimestamp("StartTime"));
                tournament.setEndTime(rs.getTimestamp("EndTime"));
                list.add(tournament);
            }
        }
        return list;
    }

    public TournamentDTO findClosestUpcomingTournament() throws SQLException {
        String sql = " SELECT TOP 1 * FROM Tournaments WHERE EndTime > GETDATE() ORDER BY StartTime ASC ";

        try (Connection conn = DBUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                TournamentDTO tournament = new TournamentDTO();
                tournament.setTournamentID(rs.getInt("TournamentID"));
                tournament.setTournamentName(rs.getString("TournamentName"));
                tournament.setDescription(rs.getString("Description"));
                tournament.setLocation(rs.getString("Location"));
                tournament.setStartTime(rs.getTimestamp("StartTime"));
                tournament.setEndTime(rs.getTimestamp("EndTime"));
                return tournament;
            }
        }
        return null; // No upcoming tournament found
    }

    // Count players registered for a tournament
    public int countRegisteredPlayers(int tournamentID) {
        int count = 0;
        try {
            Connection con = DBUtils.getConnection();
            String sql = "SELECT COUNT(*) AS playerCount FROM Players WHERE TournamentID = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, tournamentID);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("playerCount");
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(TournamentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return count;
    }

    // Get tournament status based on current time
    public String getTournamentStatus(int tournamentID) {
        String status = "Unknown";
        try {
            Connection con = DBUtils.getConnection();
            String sql = "SELECT StartTime, EndTime FROM Tournaments WHERE TournamentID = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, tournamentID);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                java.sql.Timestamp startTime = rs.getTimestamp("StartTime");
                java.sql.Timestamp endTime = rs.getTimestamp("EndTime");
                java.sql.Timestamp currentTime = new java.sql.Timestamp(System.currentTimeMillis());

                if (currentTime.before(startTime)) {
                    status = "Upcoming";
                } else if (currentTime.after(startTime) && currentTime.before(endTime)) {
                    status = "In Progress";
                } else {
                    status = "Completed";
                }
            }

            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(TournamentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }
}
