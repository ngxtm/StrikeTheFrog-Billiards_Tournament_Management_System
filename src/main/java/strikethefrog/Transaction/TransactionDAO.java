package strikethefrog.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import strikethefrog.utils.DBUtils;

/**
 * Data Access Object for Transaction-related operations
 */
public class TransactionDAO {

    /**
     * Inserts a new transaction into the database
     *
     * @param transaction The TransactionDTO object to insert
     * @return true if insertion is successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean insertTransaction(TransactionDTO transaction) throws SQLException {
        String sql = "INSERT INTO Transactions (PlayerID, TournamentID, PaymentStatus, CreationTime, ExpirationDate) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, transaction.getPlayerID());
            stmt.setInt(2, transaction.getTournamentID());
            stmt.setString(3, transaction.getPaymentStatus());
            stmt.setTimestamp(4, transaction.getCreationTime());
            stmt.setTimestamp(5, transaction.getExpirationDate());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        transaction.setTransactionID(generatedKeys.getInt(1));
                    }
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error inserting transaction: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Saves a new transaction into the database (alias for insertTransaction)
     *
     * @param transaction The TransactionDTO object to save
     * @return true if saving is successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean saveTransaction(TransactionDTO transaction) throws SQLException {
        String sql = "INSERT INTO Transactions (PlayerID, TournamentID, PaymentStatus, CreationTime, ExpirationDate) " +
                    "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, transaction.getPlayerID());
            stmt.setInt(2, transaction.getTournamentID());
            stmt.setString(3, transaction.getPaymentStatus());
            stmt.setTimestamp(4, transaction.getCreationTime());
            stmt.setTimestamp(5, transaction.getExpirationDate());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        transaction.setTransactionID(generatedKeys.getInt(1));
                    }
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error saving transaction: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Retrieves a transaction by its ID
     *
     * @param transactionID The ID of the transaction to retrieve
     * @return TransactionDTO object if found, null otherwise
     */
    public TransactionDTO getTransactionById(int transactionID) {
        TransactionDTO transaction = null;
        String sql = "SELECT * FROM Transactions WHERE TransactionID = ?";

        try (Connection conn = DBUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transactionID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                transaction = new TransactionDTO();
                transaction.setTransactionID(rs.getInt("TransactionID"));
                transaction.setPlayerID(rs.getInt("PlayerID"));
                transaction.setTournamentID(rs.getInt("TournamentID"));
                transaction.setPaymentStatus(rs.getString("PaymentStatus"));
                transaction.setCreationTime(rs.getTimestamp("CreationTime"));
                transaction.setExpirationDate(rs.getTimestamp("ExpirationDate"));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving transaction: " + e.getMessage());
            e.printStackTrace();
        }
        return transaction;
    }

    /**
     * Updates an existing transaction
     *
     * @param transaction The TransactionDTO object with updated values
     * @return true if update is successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean updateTransaction(TransactionDTO transaction) throws SQLException {
        String sql = "UPDATE Transactions SET PlayerID = ?, TournamentID = ?, PaymentStatus = ?, "
                + "CreationTime = ?, ExpirationDate = ? WHERE TransactionID = ?";

        try (Connection conn = DBUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transaction.getPlayerID());
            stmt.setInt(2, transaction.getTournamentID());
            stmt.setString(3, transaction.getPaymentStatus());
            stmt.setTimestamp(4, transaction.getCreationTime());
            stmt.setTimestamp(5, transaction.getExpirationDate());
            stmt.setInt(6, transaction.getTransactionID());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating transaction: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Deletes a transaction by its ID
     *
     * @param transactionID The ID of the transaction to delete
     * @return true if deletion is successful, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean deleteTransaction(int transactionID) throws SQLException {
        String sql = "DELETE FROM Transactions WHERE TransactionID = ?";

        try (Connection conn = DBUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transactionID);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting transaction: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Lists all transactions, optionally filtered by player ID or tournament ID
     *
     * @param playerID Player ID to filter by (optional, use null or -1 for no
     * filter)
     * @param tournamentID Tournament ID to filter by (optional, use null or -1
     * for no filter)
     * @return List of TransactionDTO objects
     */
    public List<TransactionDTO> listTransactions(Integer playerID, Integer tournamentID) {
        List<TransactionDTO> transactionList = new ArrayList<>();
        String sql = "SELECT * FROM Transactions";
        boolean hasFilter = (playerID != null && playerID != -1) || (tournamentID != null && tournamentID != -1);

        if (hasFilter) {
            sql += " WHERE ";
            if (playerID != null && playerID != -1) {
                sql += "PlayerID = ?";
                if (tournamentID != null && tournamentID != -1) {
                    sql += " AND ";
                }
            }
            if (tournamentID != null && tournamentID != -1) {
                sql += "TournamentID = ?";
            }
        }

        try (Connection conn = DBUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            int paramIndex = 1;
            if (playerID != null && playerID != -1) {
                stmt.setInt(paramIndex++, playerID);
            }
            if (tournamentID != null && tournamentID != -1) {
                stmt.setInt(paramIndex, tournamentID);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TransactionDTO transaction = new TransactionDTO();
                transaction.setTransactionID(rs.getInt("TransactionID"));
                transaction.setPlayerID(rs.getInt("PlayerID"));
                transaction.setTournamentID(rs.getInt("TournamentID"));
                transaction.setPaymentStatus(rs.getString("PaymentStatus"));
                transaction.setCreationTime(rs.getTimestamp("CreationTime"));
                transaction.setExpirationDate(rs.getTimestamp("ExpirationDate"));
                transactionList.add(transaction);
            }

        } catch (SQLException e) {
            System.err.println("Error listing transactions: " + e.getMessage());
            e.printStackTrace();
        }
        return transactionList;
    }

    /**
     * Gets all transactions for a specific player
     *
     * @param playerID The ID of the player
     * @return List of TransactionDTO objects for the player
     */
    public List<TransactionDTO> getTransactionsByPlayer(int playerID) {
        return listTransactions(playerID, -1);
    }

    /**
     * Gets all transactions for a specific tournament
     *
     * @param tournamentID The ID of the tournament
     * @return List of TransactionDTO objects for the tournament
     */
    public List<TransactionDTO> getTransactionsByTournament(int tournamentID) {
        return listTransactions(-1, tournamentID);
    }

    /**
     * Checks if a transaction exists for a player and tournament combination
     *
     * @param playerID The ID of the player
     * @param tournamentID The ID of the tournament
     * @return true if a transaction exists, false otherwise
     * @throws SQLException if a database error occurs
     */
    public boolean transactionExists(int playerID, int tournamentID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Transactions WHERE PlayerID = ? AND TournamentID = ?";

        try (Connection conn = DBUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerID);
            stmt.setInt(2, tournamentID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            return false;
        }
    }

    /**
     * Lists all transactions filtered by payment status
     *
     * @param paymentStatus The payment status to filter by
     * @return List of TransactionDTO objects matching the payment status
     */
    public List<TransactionDTO> listTransactionsByPaymentStatus(String paymentStatus) {
        List<TransactionDTO> transactionList = new ArrayList<>();
        String sql = "SELECT * FROM Transactions WHERE PaymentStatus = ?";

        try (Connection conn = DBUtils.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, paymentStatus);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TransactionDTO transaction = new TransactionDTO();
                transaction.setTransactionID(rs.getInt("TransactionID"));
                transaction.setPlayerID(rs.getInt("PlayerID"));
                transaction.setTournamentID(rs.getInt("TournamentID"));
                transaction.setPaymentStatus(rs.getString("PaymentStatus"));
                transaction.setCreationTime(rs.getTimestamp("CreationTime"));
                transaction.setExpirationDate(rs.getTimestamp("ExpirationDate"));
                transactionList.add(transaction);
            }

        } catch (SQLException e) {
            System.err.println("Error listing transactions by payment status: " + e.getMessage());
            e.printStackTrace();
        }
        return transactionList;
    }
}