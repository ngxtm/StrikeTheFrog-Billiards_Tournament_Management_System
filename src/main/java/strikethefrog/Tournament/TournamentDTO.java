/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strikethefrog.Tournament;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.math.BigDecimal;

public class TournamentDTO {

    private int tournamentID;
    private String tournamentName;
    private String description;
    private String location;
    private Timestamp startTime;
    private Timestamp endTime;
    private String imgFile;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getFormattedDate() {
        LocalDateTime startTimeLocal = getStartTime().toLocalDateTime();
        LocalDateTime endTimeLocal = getEndTime().toLocalDateTime();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMM");
        String date = dateFormat.format(startTimeLocal) + " - " + dateFormat.format(endTimeLocal) + ", " + startTime.getYear();
        return date;
    }
    // Add these fields to TournamentDTO
    private BigDecimal fee;
    private String playerStatus;
    
    // Add getters and setters
    public BigDecimal getFee() {
        return fee;
    }
    
    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
    
    public String getPlayerStatus() {
        return playerStatus;
    }
    
    public void setPlayerStatus(String playerStatus) {
        this.playerStatus = playerStatus;
    }
    public String getImgFile() {
        return imgFile;
    }

    public void setImgFile(String imgFile) {
        this.imgFile = imgFile;
    }

}
