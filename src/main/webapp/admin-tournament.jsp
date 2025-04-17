<%
    if (session == null || session.getAttribute("adminsession") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<%@page import="strikethefrog.Tournament.TournamentDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Center - Tournaments</title>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link href="./styles/admin-lists.css" rel="stylesheet">
        <script src="script/sidebar.js"></script>
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
                <li class="sidebar-menu-item active" id="championship-menu-item">
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
        </div>



        <div class="main-content">
            <div class="header">
                <h1 class="header-title">Tournaments</h1>
                <div class="header-actions">
                    <form class="search-container" action="Dashboard" method="GET">
                        <input type="hidden" name="action" value="tournament">
                        <input type="text" name="searchTourName" id="tournament-search" class="search-input" placeholder="Find by Tournament Name">
                        <button type="submit" id="search-button" class="search-button">
                            <img src="assets/icon/admin/search-icon.png" alt="search-icon">
                            <span>Search</span>
                        </button>
                    </form>
                </div>
            </div>
            <%
                String error = (String) request.getAttribute("error");
                String message = (String) request.getAttribute("message");
                if (error != null || message != null) {
            %>
            <div class="message-container">
                <div class="<%= error != null ? "error-message" : "success-message"%>">
                    <p><%= error != null ? error : message%></p>
                </div>
            </div>
            <%
                }
            %>
            <div class="page-content">
                <div class="content-header">
                    <h2>Management tournaments</h2>

                    <form action="./Dashboard" method="GET" class="content-header-actions" >
                        <button class="button primary-button" id="add-tournament-btn" type="submit">
                            <img src="assets/icon/admin/add-btn.png" alt="add-btn">
                            <span>Add a tournament</span>
                            <input name="action" type="hidden" value="create-tournament">
                        </button>
                    </form>

                </div>

                <div class="table-wrapper">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Description</th>
                                <th>Location</th>
                                <th>Start Time</th>
                                <th>End Time</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<TournamentDTO> tournaments = (List<TournamentDTO>) request.getAttribute("tList");
                                if (tournaments != null) {
                                    for (TournamentDTO tournament : tournaments) {
                            %>
                            <tr data-tournament-id="<%= tournament.getTournamentID()%>">
                                <td><%= tournament.getTournamentID()%></td>
                                <td><%= tournament.getTournamentName()%></td>
                                <td><%= tournament.getDescription()%></td>
                                <td><%= tournament.getLocation()%></td>
                                <td><%= tournament.getStartTime()%></td>
                                <td><%= tournament.getEndTime()%></td>
                                <td>
                                    <div class="actions">
                                        <form action="./Dashboard" method="GET">
                                            <input name="action" type="hidden" value="edit-tournament">
                                            <input name="tournamentID" value="<%= tournament.getTournamentID()%>" type="hidden">
                                            <input class="action-button edit-button" type="submit" value="Edit">
                                        </form>

                                        <form action="./Dashboard" method="GET">
                                            <input name="action" type="hidden" value="delete-tournament">
                                            <input name="tournamentID" value="<%= tournament.getTournamentID()%>" type="hidden">
                                            <input class="action-button delete-button" type="submit" value="Delete">
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

    </script>
</body>
</html>
