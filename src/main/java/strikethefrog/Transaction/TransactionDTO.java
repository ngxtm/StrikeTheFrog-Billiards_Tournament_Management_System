/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strikethefrog.Transaction;

import java.sql.Timestamp;
/**
 *
 * @author MINH
 */
public class TransactionDTO {
    private int transactionID;
    private int playerID;
    private int tournamentID;
    private String paymentStatus;
    private Timestamp creationTime;
    private Timestamp ExpirationDate;

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getTournamentID() {
        return tournamentID;
    }

    public void setTournamentID(int tournamentID) {
        this.tournamentID = tournamentID;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public Timestamp getExpirationDate() {
        return ExpirationDate;
    }

    public void setExpirationDate(Timestamp ExpirationDate) {
        this.ExpirationDate = ExpirationDate;
    }

   
}
