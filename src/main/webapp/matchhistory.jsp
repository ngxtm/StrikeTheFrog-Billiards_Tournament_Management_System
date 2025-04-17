<%@page import="java.util.List"%>
<%@page import="strikethefrog.Match.GameMatchDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%
    HttpSession sessionCheck = request.getSession(false);
    if (sessionCheck == null || sessionCheck.getAttribute("usersession") == null) {
        response.sendRedirect("login.jsp?error=Please login first");
        return;
    }

    List<GameMatchDTO> matches = (List<GameMatchDTO>) request.getAttribute("matches");
%>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Match History - StrikeTheFrog</title>
        <link rel="stylesheet" href="styles/matchhistory.css">
    </head>
    <body>

        <div class="navbar">
            <div class="nav-left">
                <div class="logo">
                    <img src="assets/images/homepage/logo.jpg" alt="Logo">
                </div>
            </div>

            <div class="nav-center">
                <a href="TournamentController?action=list">Home</a>
                <a href="rule.jsp">Rule</a>
            </div>

            <div class="welcome-message">
                <img src="assets/images/homepage/user.jpg" alt="User Logo" class="user-logo">
                <a href="TournamentController?action=user-match"><h4>${sessionScope.usersession.username}</h4></a>
                <a href="login?action=logout" class="logout-link">Logout</a>
            </div>
        </div>

        <div class="container">
            <h1 class="page-title">Match History</h1>

            <a href="TournamentController?action=list" class="back-button">← Back to Home</a>

            <% if (matches == null || matches.isEmpty()) { %>
            <div style="text-align: center; margin-top: 3rem;">
                <h2>No matches found</h2>
                <p>There are no matches to display at this time.</p>
            </div>
            <% } else { %>
            <div class="matches-container">
                <% for (GameMatchDTO match : matches) {%>
                <div class="match-card">
                    <div class="match-header">
                        <span class="round-name"><%= match.getRoundName()%></span>

                    </div>

                    <div class="match-info">
                        <span>Match #<%= match.getMatchID()%></span>
                        <span><%= match.getTableName()%></span>

                        <span class="tournament-name"><%= match.getTournamentName()%></span>

                        <span class="match-time"><%= match.getFormattedMatchTime()%></span>
                    </div>

                    <div class="player <%= match.isPlayer1Winner() ? "winner" : ""%>">
                        <span class="player-name"><%= match.getPlayer1Name()%></span>
                        <span class="player-score"><%= match.getScoreP1()%></span>
                    </div>

                    <div class="player <%= match.isPlayer2Winner() ? "winner" : ""%>">
                        <span class="player-name"><%= match.getPlayer2Name()%></span>
                        <span class="player-score"><%= match.getScoreP2()%></span>
                    </div>

                    <% if (match.getGameStatus() != null) {%>
                    <div class="match-status status-<%= match.getGameStatus().toLowerCase()%>">
                        <%= match.getGameStatus()%>
                    </div>
                    <% } %>
                </div>
                <% } %>
            </div>
            <% }%>
        </div>

        <footer style="background: linear-gradient(to right, #000, #003366); color: white; padding: 30px; text-align: center; font-size: 14px; margin-top: 3rem;">
            <div>
                <a href="TournamentController?action=list" style="color: white; text-decoration: none; margin: 0 15px;">Home</a> | 
                <a href="rule.jsp" style="color: white; text-decoration: none; margin: 0 15px;">Rules</a> | 
                <a href="login?action=logout" style="color: white; text-decoration: none; margin: 0 15px;">Logout</a>
            </div>
            <p style="margin-top: 25px;">&copy; 2025 StrikeTheFrog. All rights reserved.</p>
        </footer>
    </body>
</html> 