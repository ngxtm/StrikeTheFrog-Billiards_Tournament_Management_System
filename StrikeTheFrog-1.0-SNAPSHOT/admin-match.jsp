
<%
    if (session == null || session.getAttribute("adminsession") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<%@page import="strikethefrog.Match.MatchDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Center - Match</title>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link href="styles/admin-lists.css" rel="stylesheet" type="text/css">
        <script src="script/sidebar.js"></script>
        <style>
            .error-message-container {
                width: 100%;
                margin: 10px 0;
                display: flex;
                justify-content: center;
            }

            .error-message {
                background-color: #fee2e2; 
                border: 1px solid #ef4444; 
                border-radius: 6px;
                padding: 10px 15px;
                color: #b91c1c;
                font-size: 14px;
                max-width: 800px;
                width: 100%;
            }

            .error-message p {
                margin: 0;
                font-weight: 500;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <div class="sidebar">
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
                    Championship
                    <div class="dropdown-arrow"></div>
                    <ul class="dropdown-menu">
                        <a href="./Dashboard?action=round"><li class="dropdown-menu-item">Round</li></a>
                        <a href="./Dashboard?action=match"><li class="dropdown-menu-item">Match</li></a>
                        <a href="./Dashboard?action=tournament"><li class="dropdown-menu-item">Tournament</li></a>
                        <a href="./Dashboard?action=table"><li class="dropdown-menu-item">Table</li></a>
                    </ul>
                </li>
                <a href="./Dashboard?action=logout">
                    <li class="sidebar-menu-item">
                        <img src="assets/icon/admin/user-icon.png" alt="user-icon">
                        <p>Logout</p>
                    </li>
                </a>
            </ul>
        </div>

        <div class="main-content">
            <div class="header">
                <h1 class="header-title">Match</h1>
                <div class="header-actions">
                    <form action="./Dashboard" class="search-container">
                        <input type="text" id="tournament-search" name="tournamentName" class="search-input" placeholder="Tìm theo giải đấu...">
                        <input type="text" id="player-search" name="matchID" class="search-input" placeholder="Tìm theo ID trận đấu...">
                        <button type="submit" id="search-button" class="search-button">
                            <img src="assets/icon/admin/search-icon.png" alt="search-icon">
                            <span>Tìm kiếm</span>
                        </button>
                        <input name="action" value="search-admin-match" type="hidden"/>
                    </form>
                </div>
            </div>

            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
            <div class="error-message-container">
                <div class="error-message">
                    <p><%= error%></p>
                </div>
            </div>
            <%
                }
            %>

            <div class="page-content">
                <div class="content-header">
                    <h2>Management matches of a tournament</h2>
                    <!-- Remove the original error message from here -->
                    <form method="./Dashboard" class="content-header-actions">
                        <button class="button primary-button">
                            <img src="assets/icon/admin/add-btn.png" alt="add-btn">
                            <span>Add a match</span>
                        </button>
                        <input type="hidden" name="action" value="navigate-to-create-match"/>
                    </form>
                </div>
                <div class="table-wrapper">
                    <table>
                        <thead>
                            <tr>
                                <th>Tournament</th>
                                <th>MatchbID</th>
                                <th>RoundID</th>
                                <th>TableID</th>
                                <th>Player 1</th>
                                <th>Player 2</th>
                                <th>Score P1</th>
                                <th>Score P2</th>
                                <th>Winner Player</th>
                                <th>Game Status</th>
                                <th>Match Time</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<MatchDTO> list = (List<MatchDTO>) request.getAttribute("list");
                                if (list != null) {

                                    for (int i = 0; i < list.size(); i++) {
                            %>
                            <tr data-game-id="<%= i%>">
                                <td id="tournament"><%= list.get(i).getTournamentName()%></td>
                                <td><%= list.get(i).getMatchID()%></td>
                                <td><%= list.get(i).getRoundID()%></td>
                                <td><%= list.get(i).getTableID()%></td>
                                <td><%= list.get(i).getPlayer1Name()%></td>
                                <td><%= list.get(i).getPlayer2Name()%></td>
                                <td><%= list.get(i).getScoreP1()%></td>
                                <td><%= list.get(i).getScoreP2()%></td>
                                <td><%= list.get(i).getWinnerPlayerName()%></td>
                                <td><%= list.get(i).getGameStatus()%></td>
                                <td><%= list.get(i).getMatchTime()%></td>
                                <td>
                                    <div class="actions">
                                        <form action="./Dashboard">
                                            <input name="action" value="edit-match" type="hidden"/>
                                            <input name="tournamentName" value="<%= list.get(i).getTournamentName()%>" type="hidden"/>
                                            <input name="matchID" value="<%= list.get(i).getMatchID()%>" type="hidden"/>
                                            <input class="action-button edit-button" type="submit" value="Edit"/>
                                        </form>
                                        <form action="./Dashboard">
                                            <input name="action" value="delete-match" type="hidden"/>
                                            <input name="tournamentName" value="<%= list.get(i).getTournamentName()%>" type="hidden"/>
                                            <input name="matchID" value="<%= list.get(i).getMatchID()%>" type="hidden"/>
                                            <input class="action-button delete-button" type="submit" value="Delete"/>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                            <%
                                    }
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>