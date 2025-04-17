<%@page import="java.io.FilenameFilter"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Random"%>
<%@page import="strikethefrog.Tournament.TournamentDTO"%>
<%@page import="java.util.List"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.TimeZone"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="strikethefrog.User.UserDTO"%>
<!DOCTYPE html>
<%
    HttpSession sessionCheck = request.getSession(false); // Get session, don't create new
    if (sessionCheck == null || sessionCheck.getAttribute("usersession") == null) {
        response.sendRedirect("login.jsp?error=Please login first"); // Redirect if no session
        return;
    }
%>
<html>
    <head>
        <meta charset="UTF-8">
        <title>StrikeTheFrog - HomePage</title>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="styles/homepage.css">
    </head>
    <body>
        <%
            TournamentDTO closestTournament = (TournamentDTO) request.getAttribute("closestTournament");
            UserDTO user = (UserDTO) session.getAttribute("usersession");
        %>
        <div class="container">

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

            <div class="main-content">
                <h1>STRIKE THE FROG 2025</h1>
                <%
                    SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, YYYY");
                    Calendar calendar = Calendar.getInstance();
                    String startDate = sdf.format(calendar.getTime());
                    TimeZone timeZone = calendar.getTimeZone();
                %>
                <%= startDate%>
                <br>
                <%= timeZone.getID()%>
                <br>
                <h2>Latest Tournament</h2>

                <c:if test="${not empty error}">
                    <div class="error-message">
                        ${error}
                    </div>
                </c:if>

                <c:if test="${not empty message}">
                    <div class="info-message">
                        ${message}
                    </div>
                </c:if>

                <% if (closestTournament != null) {%>
                <div class="tournament-card">
                    <div class="tournament-header">
                        <h3><%= closestTournament.getTournamentName()%></h3>
                        <span class="tournament-id">#<%= closestTournament.getTournamentID()%></span>
                    </div>
                    <div class="tournament-details">
                        <p><strong>Start Time:</strong> <%= closestTournament.getStartTime()%></p>
                        <p><strong>End Time:</strong> <%= closestTournament.getEndTime()%></p>
                        <p><strong>Location:</strong> <%= closestTournament.getLocation()%></p>
                        <a class="button register" href="TournamentController?action=join&tournamentID=<%= closestTournament.getTournamentID()%>&userID=<%= user.getUserID()%>">REGISTER NOW</a>

                    </div>
                </div>
                <% } else { %>
                <p>No upcoming tournaments available.</p>
                <a class="button" href="TournamentController?action=list">VIEW ALL TOURNAMENTS</a>
                <% } %>
            </div>

            <div class="video-container">
                <h1>HIGHLIGHT</h1>
                <iframe width="560" height="315" src="https://www.youtube.com/embed/G1knHnVO0mE?si=iymDVwUzkvbOuZA3" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>        
            </div>

            <div><h1>Tournaments</h1></div> 

            <div class="rounds-container">
                <div class="rounds-slider" id="rounds-slider">
                    <%
                        List<TournamentDTO> tourList = (List<TournamentDTO>) request.getAttribute("tourList");
                        if (tourList == null || tourList.isEmpty()) {
                    %>
                    <p>No tournaments available.</p>
                    <%
                    } else {
                        for (TournamentDTO tournament : tourList) {
                            pageContext.setAttribute("tournament", tournament);
                    %>
                    <div class="round">
                        <form action="TournamentController" method="get">
                            <input type="hidden" name="action" value="details">
                            <input type="hidden" name="tournamentID" value="<%= tournament.getTournamentID()%>">
                            <button type="submit" style="border: none; background: none; padding: 0;">
                                <img src="${tournament.imgFile}" alt="${tournament.tournamentName}'s Logo" style="max-width: 100px; max-height: 100px">
                            </button>
                        </form>
                        <h1><%= tournament.getTournamentName()%></h1>
                        <h3><%= tournament.getLocation()%></h3>
                        <p>Time: <%= tournament.getFormattedDate()%></p>
                    </div>
                    <%
                            }
                        }
                    %>
                </div>
                <button id="loadMoreBtn" class="load-more-btn">SHOW MORE</button>
            </div>
        </div>
    </body>
    <script>
        let visibleItems = 6;
        const itemsPerPage = 6;

        document.addEventListener("DOMContentLoaded", function () {
            const allRounds = document.querySelectorAll('.round');
            const loadMoreBtn = document.getElementById('loadMoreBtn');

            // Initially hide items beyond the first 6
            allRounds.forEach((item, index) => {
                if (index >= visibleItems) {
                    item.style.display = 'none';
                }
            });

            // Hide the button if there are 6 or fewer items
            if (allRounds.length <= visibleItems) {
                loadMoreBtn.style.display = 'none';
            }

            loadMoreBtn.addEventListener("click", function () {
                let newVisibleItems = visibleItems + itemsPerPage;

                // Show the next 6 items
                for (let i = visibleItems; i < newVisibleItems && i < allRounds.length; i++) {
                    allRounds[i].style.display = 'flex';
                }

                visibleItems = newVisibleItems;

                // Hide the button if we've shown all items
                if (visibleItems >= allRounds.length) {
                    loadMoreBtn.style.display = 'none';
                }
            });
        });
    </script>

    <footer margin-top:25px style="background: linear-gradient(to right, #000, #003366); color: white; padding: 30px; text-align: center; font-size: 14px;">
        <div>
            <a href="TournamentController?action=list" style="color: white; text-decoration: none; margin: 0 15px;">Home</a> | 
            <a href="rule.jsp" style="color: white; text-decoration: none; margin: 0 15px;">Rule</a> | 
            <a href="login?action=logout" style="color: white; text-decoration: none; margin: 0 15px;">Logout</a>
        </div>
        <p style="margin-top: 30px;">
            <strong>Home:</strong> Navigate to the homepage. <br>
            <strong>Rule:</strong> View legislation structure for awards.<br>
            <strong>Log Out:</strong> Securely sign out of your account. 
        </p>
        <p style="margin-top: 25px;">&copy; 2025 StrikeTheFrog. All rights reserved.</p>
    </footer>
</html>