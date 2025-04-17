<%-- 
    Document   : admin-edit
    Created on : Mar 24, 2025, 7:18:58 PM
    Author     : MINH
--%>
<%@page import="strikethefrog.Match.MatchDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                    <li class="sidebar-menu-item" id="championship-menu-item">
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
                <%
                    MatchDTO match = (MatchDTO) request.getAttribute("match");
                    if (match != null) {
                %>
                <section class="form-section">
                    <form id="game-details-form" action="./Dashboard" method="post">
                        <input type="hidden" name="action" value="update-match">
                        <fieldset>
                            <legend>Match Information</legend>
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="tournament">Tournament:</label>
                                    <input id="tournament" name="tournamentName" value="<%= match.getTournamentName()%>" readonly>
                                </div>
                                <div class="form-group">
                                    <label for="matchid">Match ID:</label>
                                    <input type="text" id="matchid" name="matchID" value="<%= match.getMatchID()%>" readonly>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="roundid">Round ID:</label>
                                    <input type="number" id="roundid" name="roundID" min="1" value="<%= match.getRoundID()%>">
                                </div>
                                <div class="form-group">
                                    <label for="tableid">Table ID:</label>
                                    <input type="number" id="tableid" name="tableID" min="1" value="<%= match.getTableID()%>">
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <legend>Player</legend>
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="player1ID">Player 1 ID:</label>
                                    <input type="text" id="player1ID" name="player1ID" 
                                           value="<%= match.getPlayer1ID()%>"
                                           onblur="updatePlayerName(this.value, 'player1Name')">
                                </div>
                                <div class="form-group">
                                    <label for="player2ID">Player 2 ID:</label>
                                    <input type="text" id="player2ID" name="player2ID" 
                                           value="<%= match.getPlayer2ID()%>"
                                           onblur="updatePlayerName(this.value, 'player2Name')">
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="player1Name">Player 1 Name:</label>
                                    <input type="text" id="player1Name" name="player1Name" 
                                           value="<%= match.getPlayer1Name()%>" readonly />
                                </div>
                                <div class="form-group">
                                    <label for="player2Name">Player 2 Name:</label>
                                    <input type="text" id="player2Name" name="player2Name" value="<%= match.getPlayer2Name()%>" readonly/>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <legend>Score</legend>
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="scorep1">Score P1:</label>
                                    <input type="number" id="scorep1" name="scoreP1" min="0" value="<%= match.getScoreP1()%>">
                                </div>
                                <div class="form-group">
                                    <label for="scorep2">Score P2:</label>
                                    <input type="number" id="scorep2" name="scoreP2" min="0" value="<%= match.getScoreP2()%>">
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
                                        <option value="Completed" <%= match.getGameStatus().equals("Completed") ? "selected" : ""%>>Completed</option>
                                        <option value="Pending" <%= match.getGameStatus().equals("Pending") ? "selected" : ""%>>Pending</option>
                                        <option value="InProgress" <%= match.getGameStatus().equals("InProgress") ? "selected" : ""%>>In Progress</option>
                                        <option value="Cancelled" <%= match.getGameStatus().equals("Cancelled") ? "selected" : ""%>>In Progress</option>
                                    </select>
                                </div>  
                        </fieldset>
                        <fieldset>
                            <legend>Schedule</legend>
                            <div class="form-group">
                                <label for="matchtime">Match Time:</label>
                                <input type="datetime-local" id="matchtime" name="matchtime" value="<%= match.getMatchTime() != null ? match.getMatchTime() : "2024-11-15T10:00"%>">
                            </div>
                        </fieldset>
                        <div class="form-actions">
                            <button type="submit" class="btn btn-save">Save</button>
                            <a href="./Dashboard?action=match"><button type="button" class="btn btn-cancel">Cancel</button></a>
                        </div>
                    </form>
                </section>
                <%
                    }
                %>
            </main>
        </div>
    </body>
    <!-- Thêm hidden fields để lưu playerID -->
    <input type="hidden" id="player1IDHidden" name="player1IDHidden" value="<%= match.getPlayer1ID()%>">
    <input type="hidden" id="player2IDHidden" name="player2IDHidden" value="<%= match.getPlayer2ID()%>">
</html>
