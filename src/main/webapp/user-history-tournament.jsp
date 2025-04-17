<%@page import="strikethefrog.User.UserDTO"%>
<%@page import="strikethefrog.Player.PlayerDAO"%>
<%@page import="strikethefrog.Tournament.TournamentDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    UserDTO user = (UserDTO) session.getAttribute("usersession");
    System.out.println("DEBUG - Current UserID: " + user.getUserID()); // Check console
    PlayerDAO playerDAO = new PlayerDAO();
    List<TournamentDTO> tournamentData = playerDAO.getPaidTournamentsByUser(user.getUserID());
    System.out.println("DEBUG - Found tournaments: " + tournamentData.size()); // Check count
%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lịch Sử Giải Đấu</title>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="styles/uTourHistory.css">
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

        <div class="tournament-history-container">
            <h2>Lịch Sử Giải Đấu Của ${sessionScope.usersession.fullname}</h2>
            <ul class="tournament-list">
                <% if (tournamentData.isEmpty()) { %>
                <li class="tournament-item">
                    <div class="tournament-details" style="text-align: center">
                        Bạn chưa tham gia giải đấu nào
                    </div>
                </li>
                <% } else {
                    for (TournamentDTO tournament : tournamentData) {
                        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                %>
                <li class="tournament-item">
                    <a href="tournamentdetails.jsp?tournamentId=<%= tournament.getTournamentID()%>">
                        <%= tournament.getTournamentName()%>
                    </a>
                    <div class="tournament-details">
                        <p>Trạng thái: <%= tournament.getPlayerStatus()%></p>
                        <p>Ngày bắt đầu: <%= dateFormat.format(tournament.getStartTime())%></p>
                        <p>Ngày kết thúc: <%= dateFormat.format(tournament.getEndTime())%></p>
                    </div>
                </li>
                <% }
                    }%>
            </ul>
        </div>

    </body>
</html>

