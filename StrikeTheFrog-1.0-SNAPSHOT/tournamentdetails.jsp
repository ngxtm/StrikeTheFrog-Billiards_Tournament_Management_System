<%-- 
    Document   : tournamentdetails
    Created on : Mar 14, 2025, 10:12:02 AM
    Author     : ASUS
--%>

<%@page import="strikethefrog.Tournament.TournamentDAO"%>
<%@page import="strikethefrog.Tournament.TournamentDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tournament Details</title>
    </head>
    <body>

        <h1>Tournament Details</h1>

        <p>Login user: ${sessionScope.usersession.fullname}</p>

        <%
            String tournamentID = request.getParameter("tournamentID");
            TournamentDTO tournament = null;

            // Assuming you have a method to get tournament details by ID
            if (tournamentID != null) {
                tournament = TournamentDAO.load(Integer.parseInt(tournamentID)); // Replace with actual method
            }

            if (tournament != null) {
        %>
        <p><strong>ID:</strong> <%= tournament.getTournamentID()%></p>
        <p><strong>Name:</strong> <%= tournament.getTournamentName()%></p>
        <p><strong>Description:</strong> <%= tournament.getDescription()%></p>
        <p><strong>Location:</strong> <%= tournament.getLocation()%></p>
        <p><strong>Start Time:</strong> <%= tournament.getStartTime()%></p>
        <p><strong>End Time:</strong> <%= tournament.getEndTime()%></p>
        <%
        } else {
        %>
        <p>Sorry, tournament details could not be found.</p>
        <%
            }
        %>

        <%
            if (tournament != null) {
        %>
        <!-- Join Button -->
        <form action="TournamentController" method="GET">
            <input name="action" value="join" type="hidden">
            <input name="tournamentID" value="<%= tournament.getTournamentID()%>" type="hidden">
            <input name="userID" value="${sessionScope.usersession.userID}" type="hidden">
            <input type="submit" value="Join Tournament">
        </form>
        <%
            }
        %>
        <br>
        <a href="tournamentlist.jsp">Back to List</a>

    </body>
</html>

