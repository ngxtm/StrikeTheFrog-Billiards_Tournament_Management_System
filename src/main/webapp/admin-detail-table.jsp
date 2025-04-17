<%-- 
    Document   : admin-detail-table
    Created on : Mar 27, 2025, 2:46:22 AM
    Author     : ASUS
--%>

<%@page import="strikethefrog.Table.TableDTO"%>
<%@page import="java.util.List"%>
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
                    TableDTO table = (TableDTO) request.getAttribute("table");
                    if (table != null) {
                %>
                <section class="form-section">
                    <form id="tournament-details-form" action="./Dashboard" method="GET">
                        <input type="hidden" name="action" value="${requestScope.nextaction}">

                        <fieldset>
                            <legend>Table Information</legend>
                            <div class="form-row">
                                <div class="form-group">
                                    <label for="tableID">Table ID:</label>
                                    <input id="tableID" name="tableID" 
                                           value="${requestScope.table.tableID != 0 ? requestScope.table.tableID : '[ID will be auto-generated]'}" readonly>
                                </div>
                                <div class="form-group">
                                    <label for="tableName">Table Name:</label>
                                    <input type="text" id="tableName" name="tableName" value="${requestScope.table.tableName}" required>
                                </div>
                            </div>
                        </fieldset>


                        <div class="form-actions">
                            <button type="submit" class="btn btn-save">Save</button>
                            <a href="./Dashboard?action=table"><button type="button" class="btn btn-cancel">Cancel</button></a>
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