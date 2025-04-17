<%-- 
    Document   : admin-edit
    Created on : Mar 24, 2025, 7:18:58 PM
    Author     : MINH
--%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="strikethefrog.Tournament.TournamentDTO"%>
<%@page import="strikethefrog.Tournament.TournamentDAO"%>
<%@page import="strikethefrog.Match.MatchDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit Game Details - Strike The Frog</title>
        <link href="styles/admin-detail-match.css" rel="stylesheet">
        <script src="script/sidebar.js"></script>
        <script src="script/admin-edit.js"></script>
    </head>
    <body>
        <div class="container">
            <aside class="sidebar">
                <div class="sidebar-logo">
                    Strike The Frog
                </div>
                <ul class="sidebar-menu">
                    <a href="./Dashboard?action=home">
                        <li class="sidebar-menu-item">
                            <img src="assets/icon/admin/home-icon.png" alt="home-icon">
                            <p>Home</p>
                        </li>
                    </a>
                    <a href="./Dashboard?action=user">
                        <li class="sidebar-menu-item">
                            <img src="assets/icon/admin/user-icon.png" alt="user-icon">
                            <p>User</p>
                        </li>
                    </a>
                    <li class="sidebar-menu-item active" id="championship-menu-item">
                        <img src="assets/icon/admin/tournament-icon.png" alt="tournament-icon">
                        <span>Championship</span>
                        <div class="dropdown-arrow"></div>
                        <ul class="dropdown-menu">
                            <li class="dropdown-menu-item"><a href="./Dashboard?action=round">Round</a></li>
                            <li class="dropdown-menu-item"><a href="./Dashboard?action=match">Match</a></li>
                            <li class="dropdown-menu-item"><a href="./Dashboard?action=tournament">Tournament</a></li>
                            <li class="dropdown-menu-item"><a href="./Dashboard?action=table">Table</a></li>
                        </ul>
                    </li>
                    <a href="./Dashboard?action=transaction">
                        <li class="sidebar-menu-item">
                            <img src="assets/icon/admin/money-icon.png" alt="transaction-icon">
                            <p>Transactions</p>
                        </li>
                    </a>
                    <a href="./Dashboard?action=logout">
                        <li class="sidebar-menu-item">
                            <img src="assets/icon/admin/user-icon.png" alt="user-icon">
                            <p>Logout</p>
                        </li>
                    </a>
                </ul>
            </aside>
            <main class="main-content">
                <header>
                    <h1>Edit Game Details</h1>
                </header>

                <% if (request.getAttribute("error") != null) {%>
                <div class="error-message-container">
                    <div class="error-message">
                        <p><%= request.getAttribute("error")%></p>
                    </div>
                </div>
                <% } %>

                <%
                    MatchDTO match = (MatchDTO) request.getAttribute("match");
                    Boolean readonlyObj = (Boolean) request.getAttribute("readonly");
                    boolean readonly = (readonlyObj != null) ? readonlyObj : true;
                    boolean isNewMatch = (match == null || match.getMatchID() == 0);

                    if (match == null) {
                        match = new MatchDTO();
                    }
                %>
                <section class="form-section">
                    <form id="game-details-form" action="./Dashboard" method="post">
                        <input type="hidden" name="action" value="${requestScope.nextaction}">
                        <fieldset>
                            <legend>Match Information</legend>
                            <div class="form-row">
                                <div class="form-group"> 
                                    <label for="tournament">Tournament:</label>
                                    <% if (isNewMatch) {%>
                                    <input id="tournament" name="tournamentName" value="" <%= isNewMatch ? "" : "readonly"%>>
                                    <% } else {%>
                                    <input id="tournament" name="tournamentName" value="<%= match.getTournamentName()%>" readonly>
                                    <% }%>
                                </div>
                                <div class="form-group">
                                    <label for="matchid">Match ID:</label>
                                    <% if (isNewMatch) {%>
                                    <input type="text" id="matchid" name="matchID" value="" <%= isNewMatch ? "" : "readonly"%>>
                                    <% } else {%>
                                    <input type="text" id="matchid" name="matchID" value="<%= match.getMatchID()%>" readonly>
                                    <% }%>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="roundid">Round ID:</label>
                                    <input type="number" id="roundid" name="roundID" min="1" value="<%= isNewMatch ? "" : match.getRoundID()%>">
                                </div>
                                <div class="form-group">
                                    <label for="tableid">Table ID:</label>
                                    <input type="number" id="tableid" name="tableID" min="1" value="<%= isNewMatch ? "" : match.getTableID()%>">
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <legend>Player</legend>
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="player1ID">Player 1 ID:</label>
                                    <input type="text" id="player1ID" name="player1ID" 
                                           value="<%= isNewMatch ? "" : match.getPlayer1ID()%>"
                                           onblur="updatePlayerName(this.value, 'player1Name')">
                                </div>
                                <div class="form-group">
                                    <label for="player2ID">Player 2 ID:</label>
                                    <input type="text" id="player2ID" name="player2ID" 
                                           value="<%= isNewMatch ? "" : match.getPlayer2ID()%>"
                                           onblur="updatePlayerName(this.value, 'player2Name')">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="player1Name">Player 1 Name:</label>
                                    <input type="text" id="player1Name" name="player1Name" 
                                           value="<%= isNewMatch ? "" : match.getPlayer1Name()%>" readonly />
                                </div>
                                <div class="form-group">
                                    <label for="player2Name">Player 2 Name:</label>
                                    <input type="text" id="player2Name" name="player2Name" 
                                           value="<%= isNewMatch ? "" : match.getPlayer2Name()%>" readonly/>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <legend>Score</legend>
                            <!-- Update the score input fields to call updateWinner on change -->
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="scorep1">Score P1:</label>
                                    <input type="number" id="scorep1" name="scoreP1" min="0" 
                                           value="<%= match.getScoreP1()%>" 
                                           onchange="updateWinner()" onkeyup="updateWinner()">
                                </div>
                                <div class="form-group">
                                    <label for="scorep2">Score P2:</label>
                                    <input type="number" id="scorep2" name="scoreP2" min="0" 
                                           value="<%= match.getScoreP2()%>"
                                           onchange="updateWinner()" onkeyup="updateWinner()">
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <legend>Result</legend>
                            <div class="form-row">
                                <!-- Thay đổi phần select winner để thêm data-player-type attribute -->
                                <div class="form-group">
                                    <label for="winnerPlayerName">Winner Player:</label>
                                    <input type="text" id="winnerPlayerName" name="winnerPlayerName" 
                                           value="<%= match.getWinnerPlayerName() != null ? match.getWinnerPlayerName() : "The winner has not been found"%>" 
                                           readonly />
                                </div>
                                <div class="form-group">
                                    <label for="status">Status:</label>
                                    <select id="status" name="status">
                                        <option value="Completed" <%= "Completed".equals(match.getGameStatus()) ? "selected" : ""%>>Completed</option>
                                        <option value="Pending" <%= "Pending".equals(match.getGameStatus()) || match.getGameStatus() == null ? "selected" : ""%>>Pending</option>
                                        <option value="InProgress" <%= "InProgress".equals(match.getGameStatus()) ? "selected" : ""%>>In Progress</option>
                                        <option value="Cancelled" <%= "Cancelled".equals(match.getGameStatus()) ? "selected" : ""%>>Cancelled</option>
                                    </select>
                                </div>  
                        </fieldset>
                        <fieldset>
                            <legend>Schedule</legend>
                            <div class="form-group">
                                <label for="matchtime">Match Time:</label>
                                <input type="datetime-local" id="matchtime" name="matchtime" 
                                       value="<%= match.getMatchTime() != null ? match.getMatchTime().toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : ""%>"
                                       required>
                                <small class="form-text text-muted">
                                    Match time must be within the tournament's start and end time.
                                    <% if (match.getTournamentID() > 0) {
                                            TournamentDAO tournamentDAO = new TournamentDAO();
                                            TournamentDTO tournament = tournamentDAO.load(match.getTournamentID());
                                            if (tournament != null) {
                                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
                                                String startTime = tournament.getStartTime().toLocalDateTime().format(formatter);
                                                String endTime = tournament.getEndTime().toLocalDateTime().format(formatter);

                                                // Add hidden inputs to store tournament start and end times
                                                String startTimeISO = tournament.getStartTime().toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                                                String endTimeISO = tournament.getEndTime().toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                                    %>
                                    Valid range: <%= startTime%> to <%= endTime%>
                                    <input type="hidden" id="tournament-start-time" value="<%= startTimeISO%>">
                                    <input type="hidden" id="tournament-end-time" value="<%= endTimeISO%>">
                                    <% }
                                        } %>
                                </small>
                            </div>
                        </fieldset>
                        <div class="form-actions">
                            <button type="submit" class="btn btn-save">Save</button>
                            <a href="./Dashboard?action=match"><button type="button" class="btn btn-cancel">Cancel</button></a>
                        </div>
                    </form>
                </section>
                <% if (match != null) {%>
                <input type="hidden" id="player1IDHidden" name="player1IDHidden" value="<%= match.getPlayer1ID()%>">
                <input type="hidden" id="player2IDHidden" name="player2IDHidden" value="<%= match.getPlayer2ID()%>">
                <% }%>
            </main>
        </div>
    </body>
    <!-- Add this script at the end of the body to initialize the winner when the page loads -->
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            validateMatchTime();

            updateWinner();
        });
    </script>
</html>
