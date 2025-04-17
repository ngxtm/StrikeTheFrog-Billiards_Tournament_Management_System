<%-- 
    Document   : jointournament
    Created on : Mar 14, 2025, 10:03:39 AM
    Author     : ASUS
--%>

<%@page import="strikethefrog.Tournament.TournamentDTO"%>
<%@page import="strikethefrog.User.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Join Tournament</title>
    </head>
    <body>
        <h1>Join Tournament</h1>
        
        <p>Login user: ${sessionScope.usersession.fullname}</p>
        
        <%
            TournamentDTO tournament = (TournamentDTO) request.getAttribute("tournament");
            UserDTO user = (UserDTO) request.getAttribute("userP");
            boolean buttonDisabled = request.getAttribute("buttonDisabled") != null;
            if (tournament != null) {
        %>
            <h2>Tournament Details</h2>
            <p><strong>ID:</strong> <%= tournament.getTournamentID() %></p>
            <p><strong>Name:</strong> <%= tournament.getTournamentName() %></p>
            <p><strong>Description:</strong> <%= tournament.getDescription() %></p>
            <p><strong>Location:</strong> <%= tournament.getLocation() %></p>
            <p><strong>Start Time:</strong> <%= tournament.getStartTime() %></p>
            <p><strong>End Time:</strong> <%= tournament.getEndTime() %></p>

            <h2>Player Information</h2>
            <% if (!buttonDisabled) { %>
            
            <form action="./TransactionController" method="GET">
                <input type="hidden" name="action" value="confirm">
                <input type="hidden" name="tournamentID" value="<%= tournament.getTournamentID() %>">
                
                <label for="playerID">Player ID:</label>
                <input type="text" id="playerID" name="playerID" value="<%= user.getUserID() %>" readonly>
                <br><br>

                <label for="playerName">Name:</label>
                <input type="text" id="playerName" name="playerName" value="<%= user.getFullname() %>" required>
                <br><br>

                <label for="playerEmail">Email:</label>
                <input type="email" id="playerEmail" name="playerEmail" value="<%= user.getEmail() %>" required>
                <br><br>

                <label for="playerPhone">Phone Number:</label>
                <input type="text" id="playerPhone" name="playerPhone" value="<%= user.getPhonenumber() %>" required>
                <br><br>

                <h2>Payment Information</h2>
                <label for="paymentMethod">Payment Method:</label>
                <select id="paymentMethod" name="paymentMethod" required>
                    <option value="creditCard">Credit Card</option>
                    <option value="paypal">PayPal</option>
                </select>
                <br><br>

                <label for="cardDetails">Card Details:</label>
                <input type="text" id="cardDetails" name="cardDetails" placeholder="Enter your card details" required>
                <br><br>

                <input type="submit" value="Confirm and Join">
            </form>
            <% } else { %>
                <p style="color: green; font-weight: bold;">You have successfully joined the tournament!</p>
            <% } %>
        <%
            } 
        %>

        <%
            String message = (String) request.getAttribute("message");
            String error = (String) request.getAttribute("error");
        %>
        <%
            if (message != null) {
        %>
            <p style="color: green; font-weight: bold;"><%= message %></p>
        <%
            } else if (error != null) {
        %>
            <p style="color: red; font-weight: bold;">Error: <%= error %></p>
        <%
            }
        %>
        
        <br>
        <a href="TournamentController?action=list">Back to List</a>
    </body>
</html>




