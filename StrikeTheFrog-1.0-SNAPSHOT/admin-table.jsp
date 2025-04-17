<%
    if (session == null || session.getAttribute("adminsession") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<%@page import="strikethefrog.Table.TableDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Center - Tables</title>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link href="./styles/admin-lists.css" rel="stylesheet">
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
                <h1 class="header-title">Tables</h1>
                <div class="header-actions">
                    <form class="search-container" action="Dashboard" method="GET">
                        <input type="hidden" name="action" value="table">
                        <input type="text" name="searchTableName" id="search" class="search-input" placeholder="Find by Table Name">
                        <button type="submit" id="search-button" class="search-button">
                            <img src="assets/icon/admin/search-icon.png" alt="search-icon">
                            <span>Tìm kiếm</span>
                        </button>
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
                    <h2>Management tables</h2>
                    <div class="content-header-actions">
                        <button class="button primary-button" id="add-btn">
                            <img src="assets/icon/admin/add-btn.png" alt="add-btn">
                            <span>Add a table</span>
                        </button>
                    </div>
                </div>

                <div class="table-wrapper">
                    <table>
                        <thead>
                            <tr>
                                <th>Table ID</th>
                                <th>Table Name</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<TableDTO> tables = (List<TableDTO>) request.getAttribute("taList");
                                if (tables != null) {
                                    for (TableDTO table : tables) {
                            %>
                            <tr>
                                <td><%= table.getTableID()%></td>
                                <td><%= table.getTableName()%></td>
                                <td>
                                    <button class="action-button edit-button" onclick="openEditRoundModal(<%= table.getTableID()%>, '<%= table.getTableName()%>')">Edit</button>
                                    <button class="action-button delete-button" onclick="deleteRound(<%= table.getTableID()%>)">Delete</button>
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


            <script>
                document.addEventListener('DOMContentLoaded', function () {
                    const championshipMenuItem = document.getElementById('championship-menu-item');

                    championshipMenuItem.addEventListener('click', function (e) {
                        this.classList.toggle('open');

                        e.stopPropagation();
                    });

                    const dropdownItems = document.querySelectorAll('.dropdown-menu-item');
                    dropdownItems.forEach(item => {
                        item.addEventListener('click', function (e) {
                            console.log('Clicked on:', this.textContent);

                            e.stopPropagation();
                        });
                    });

                    document.addEventListener('click', function () {
                        championshipMenuItem.classList.remove('open');
                    });
                });
            </script>
    </body>
</html>