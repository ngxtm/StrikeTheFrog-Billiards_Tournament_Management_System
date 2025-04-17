<%-- 
    Document   : tournamentdetails
    Created on : Mar 14, 2025, 10:12:02 AM
    Author     : ASUS
--%>

<%@page import="strikethefrog.Player.PlayerDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="strikethefrog.Tournament.TournamentDAO"%>
<%@page import="strikethefrog.Tournament.TournamentDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%
    HttpSession sessionCheck = request.getSession(false); // Get session, don't create new
    if (sessionCheck == null || sessionCheck.getAttribute("usersession") == null) {
        response.sendRedirect("login.jsp?error=Please login first"); // Redirect if no session
        return;
    }

    TournamentDTO tournament = (TournamentDTO) request.getAttribute("tournament");
    int playerCount = (int) request.getAttribute("playerCount");
    String tournamentStatus = (String) request.getAttribute("tournamentStatus");

%>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><%= tournament.getTournamentName()%> - StrikeTheFrog</title>
        <link rel="stylesheet" href="styles/tournamentdetails.css">
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

        <br>

        <div class="hero-section">
            <h1 class="tournament-title"><%= tournament.getTournamentName()%></h1>
        </div>

        <div class="details-card">
            <div class="card-header">
                <div class="tournament-timing">
                    <p><strong>Start:</strong> <%= tournament.getStartTime()%></p>
                    <p><strong>End:</strong> <%= tournament.getEndTime()%></p>
                </div>
            </div>

            <div class="tournament-info">
                <div class="info-item">
                    <div class="info-label">Location</div>
                    <div class="info-value"><%= tournament.getLocation() != null ? tournament.getLocation() : "TBA"%></div>
                </div>
                <div class="info-item">
                    <div class="info-label">Players Registered</div>
                    <div class="info-value"><%= playerCount%> players</div>
                </div>
                <div class="info-item">
                    <div class="info-label">Status</div>
                    <div class="info-value status-badge <%= tournamentStatus.toLowerCase().replace(" ", "-")%>">
                        <%= tournamentStatus%>
                    </div>
                </div>
            </div>

            <div class="tournament-description">
                <h3>Tournament Description</h3>
                <p><%= tournament.getDescription() != null ? tournament.getDescription() : "Join us for an exciting billiard tournament featuring top players from around the country. This event will showcase exceptional talent and sportsmanship in a competitive yet friendly environment."%></p>
                <p>The tournament will feature 10-ball format with double elimination brackets. Players will compete for a substantial prize pool in a professional atmosphere.</p>
            </div>

            <div class="action-buttons">
                <% if (tournamentStatus.equals("Upcoming")) {%>
                <a href="TournamentController?action=join&tournamentID=<%= tournament.getTournamentID()%>&userID=${sessionScope.usersession.userID}" class="btn btn-primary">Register Now</a>
                <% } else if (tournamentStatus.equals("In Progress")) {%>
                <a href="TournamentController?action=view&tournamentID=<%= tournament.getTournamentID()%>" class="btn btn-primary">View Matches</a>
                <% } else {%>
                <a href="MatchController?action=history&tournamentID=<%= tournament.getTournamentID()%>" class="btn btn-primary">View Results</a>
                <% }%>
                <a href="rule.jsp" class="btn btn-secondary">Tournament Rules</a>
            </div>

            <div class="player-list">
                <h2><strong>PLAYERS</strong></h2>
                <ul>
                    <%
                        List<PlayerDTO> pList = (List<PlayerDTO>) request.getAttribute("pList");
                        if (pList != null && !pList.isEmpty()) {
                            for (PlayerDTO player : pList) {
                                pageContext.setAttribute("player", player);
                    %>
                    <li>${player.fullName}</li>
                        <% }
                        } else { %>
                    <p>No players signed up yet.</p>
                    <% }%>
                </ul>
            </div>

        </div>

        <footer class="footer">
            <div class="footer-links">
                <a href="TournamentController?action=list">Home</a>
                <a href="rule.jsp">Rules</a>
                <a href="login?action=logout">Logout</a>
            </div>
            <p class="copyright">&copy; 2025 StrikeTheFrog. All rights reserved.</p>
        </footer>

        <!-- Font Awesome for icons -->
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
    </body>
</html>

