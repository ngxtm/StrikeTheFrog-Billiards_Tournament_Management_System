package strikethefrog.Match;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class GameMatchDTO {
    private int matchID;
    private int roundID;
    private String roundName;
    private int tableID;
    private String tableName;
    private int player1ID;
    private int player2ID;
    private String player1Name;
    private String player2Name;
    private int scoreP1;
    private int scoreP2;
    private String gameStatus;
    private int winnerPlayer;
    private int tournamentID;
    private String tournamentName;
    private Timestamp matchTime;
    
    // Default constructor
    public GameMatchDTO() {
    }
    
    // Getters and Setters
    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public int getRoundID() {
        return roundID;
    }

    public void setRoundID(int roundID) {
        this.roundID = roundID;
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getPlayer1ID() {
        return player1ID;
    }

    public void setPlayer1ID(int player1ID) {
        this.player1ID = player1ID;
    }

    public int getPlayer2ID() {
        return player2ID;
    }

    public void setPlayer2ID(int player2ID) {
        this.player2ID = player2ID;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public int getScoreP1() {
        return scoreP1;
    }

    public void setScoreP1(int scoreP1) {
        this.scoreP1 = scoreP1;
    }

    public int getScoreP2() {
        return scoreP2;
    }

    public void setScoreP2(int scoreP2) {
        this.scoreP2 = scoreP2;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getWinnerPlayer() {
        return winnerPlayer;
    }

    public void setWinnerPlayer(int winnerPlayer) {
        this.winnerPlayer = winnerPlayer;
    }

    public int getTournamentID() {
        return tournamentID;
    }

    public void setTournamentID(int tournamentID) {
        this.tournamentID = tournamentID;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public Timestamp getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(Timestamp matchTime) {
        this.matchTime = matchTime;
    }
    
    // Helper methods
    public String getFormattedMatchTime() {
        if (matchTime == null) return "TBD";
        SimpleDateFormat sdf = new SimpleDateFormat("EEE h:mm a");
        return sdf.format(matchTime);
    }
    
    public String getWinnerName() {
        if (winnerPlayer == player1ID) {
            return player1Name;
        } else if (winnerPlayer == player2ID) {
            return player2Name;
        } else {
            return "N/A";
        }
    }
    
    public boolean isPlayer1Winner() {
        return winnerPlayer == player1ID;
    }
    
    public boolean isPlayer2Winner() {
        return winnerPlayer == player2ID;
    }
} 