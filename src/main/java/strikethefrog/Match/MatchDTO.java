/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strikethefrog.Match;

import java.sql.Timestamp;

/**
 *
 * @author MINH
 */
public class MatchDTO {
    private int matchID;
    private int roundID;
    private int tableID;
    private String player1Name;
    private String player2Name;
    private int player1ID;
    private int player2ID;
    private int scoreP1;
    private int scoreP2;
    private String gameStatus;
    private String winnerPlayerName;
    private int winnerPlayerID;
    private String tournamentName;
    private Timestamp matchTime;
    private int tournamentID;
    
    public int getTournamentID() {
        return tournamentID;
    }

    public void setTournamentID(int tournamentID) {
        this.tournamentID = tournamentID;
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

    public int getWinnerPlayerID() {
        return winnerPlayerID;
    }

    public void setWinnerPlayerID(int winnerPlayerID) {
        this.winnerPlayerID = winnerPlayerID;
    }
    
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

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
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

    public String getWinnerPlayerName() {
        return winnerPlayerName;
    }

    public void setWinnerPlayerName(String winnerPlayerName) {
        this.winnerPlayerName = winnerPlayerName;
    }

    
    
    public MatchDTO() {
    }

    public MatchDTO(int matchID, int roundID, int tableID, String player1Name, String player2Name, int player1ID, int player2ID, int scoreP1, int scoreP2, String gameStatus, String winnerPlayerName, int winnerPlayerID, String tournamentName, Timestamp matchTime) {
        this.matchID = matchID;
        this.roundID = roundID;
        this.tableID = tableID;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.player1ID = player1ID;
        this.player2ID = player2ID;
        this.scoreP1 = scoreP1;
        this.scoreP2 = scoreP2;
        this.gameStatus = gameStatus;
        this.winnerPlayerName = winnerPlayerName;
        this.winnerPlayerID = winnerPlayerID;
        this.tournamentName = tournamentName;
        this.matchTime = matchTime;
    }

    @Override
    public String toString() {
        return "MatchDTO{" + "matchID=" + matchID + ", roundID=" + roundID + ", tableID=" + tableID + ", player1Name=" + player1Name + ", player2Name=" + player2Name + ", player1ID=" + player1ID + ", player2ID=" + player2ID + ", scoreP1=" + scoreP1 + ", scoreP2=" + scoreP2 + ", gameStatus=" + gameStatus + ", winnerPlayerName=" + winnerPlayerName + ", winnerPlayerID=" + winnerPlayerID + ", tournamentName=" + tournamentName + ", matchTime=" + matchTime + '}';
    }

    
    
    
}
