<%-- 
    Document   : admin-detail-tournament
    Created on : Mar 26, 2025, 8:43:01 PM
    Author     : ASUS
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List"%>
<%@page import="strikethefrog.Tournament.TournamentDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>${requestScope.pageHeading} - Strike The Frog</title>
        <link href="styles/admin-detail.css" rel="stylesheet">
        <script src="script/sidebar.js"></script>
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
                            <img src="assets/icon/admin/logout-icon.png" alt="logout-icon">
                            <p>Logout</p>
                        </li>
                    </a>
                </ul>
            </aside>
            <main class="main-content">
                <header>
                    <h1>${requestScope.pageHeading}</h1>
                </header>

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


                <%
                    TournamentDTO tournament = (TournamentDTO) request.getAttribute("tournament");
                    if (tournament != null) {
                %>
                <section class="form-section">
                    <form id="tournament-details-form" action="./Dashboard" method="POST" enctype="multipart/form-data">
                        <input type="hidden" name="action" value="${requestScope.nextaction}">

                        <fieldset>
                            <legend>Tournament Information</legend>
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="tournamentID">Tournament ID:</label>
                                    <input id="tournamentID" name="tournamentID" 
                                           value="${requestScope.tournament.tournamentID != 0 ? requestScope.tournament.tournamentID : '[ID will be auto-generated]'}" readonly>
                                </div>
                                <div class="form-group">
                                    <label for="tournamentName">Tournament Name:</label>
                                    <input type="text" id="tournamentName" name="tournamentName" value="${requestScope.tournament.tournamentName}" required>
                                </div>
                            </div>
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="description">Description:</label>
                                    <textarea id="description" name="description" rows="5" cols="50" required
                                              style="width: 100%; height: 120px; resize: none;">${requestScope.tournament.description}</textarea>
                                </div>

                            </div>
                        </fieldset>

                        <fieldset>
                            <legend>Schedule</legend>
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="location">Location:</label>
                                    <input type="text" id="location" name="location" value="${requestScope.tournament.location}" required>
                                </div>
                                <div class="form-group">
                                    <label for="startDate">Start Date:</label>
                                    <input type="datetime-local" id="startDate" name="startTime" value="${requestScope.tournament.startTime}" required>
                                </div>
                                <div class="form-group">
                                    <label for="endDate">End Date:</label>
                                    <input type="datetime-local" id="endDate" name="endTime" value="${requestScope.tournament.endTime}" required>
                                </div>
                            </div>
                        </fieldset>

                        <fieldset>
                            <legend>Tournament Logo</legend>

                            <div class="form-row">
                                <div class="form-group">
                                    <label>Current Logo:</label>
                                    <c:if test="${not empty tournament.imgFile}">
                                        <img src="${tournament.imgFile}" alt="${tournament.tournamentName}'s Logo" style="max-width: 200px; max-height: 200px">
                                    </c:if>
                                    <c:if test="${empty tournament.imgFile}">
                                        <p>No logo uploaded</p>
                                    </c:if>
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="logo">Upload New Logo:</label>
                                    <input type="file" id="logo" name="logo" accept="image/png">
                                </div>
                            </div>
                        </fieldset>

                        <div class="form-actions">
                            <button type="submit" class="btn btn-save">Save</button>
                            <a href="./Dashboard?action=tournament"><button type="button" class="btn btn-cancel">Cancel</button></a>
                        </div>
                    </form>
                </section>
                <%
                    }
                %>
            </main>
        </div>
    </body>
</html>