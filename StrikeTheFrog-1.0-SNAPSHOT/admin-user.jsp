<%
    if (session == null || session.getAttribute("adminsession") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<%@page import="strikethefrog.User.UserDTO"%>
<%@page import="strikethefrog.Match.MatchDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Admin Center - Games</title>
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
                <h1 class="header-title">Users</h1>
                <div action="./Dashboard" class="header-actions">
                    <form class="search-container">
                        <input type="text" class="search-input" name="searchname" placeholder="Tìm theo người dùng">
                        <input name="action" value="user" type="hidden">
                        <button  type="submit" id="search-button" class="search-button">
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
                    <h2>Management Users</h2>

                    <form method="./Dashboard" class="content-header-actions">
                        <button class="button primary-button">
                            <img src="assets/icon/admin/add-btn.png" alt="add-btn">
                            <span>Add new users</span>
                        </button>
                        <input type="hidden" name="action" value="add-user"/>
                    </form>
                </div>
                <div class="table-wrapper">
                    <table>
                        <thead>
                            <tr>
                                <th>UserID</th>
                                <th>FullName</th>
                                <th>Username</th>
                                <th>Password</th>
                                <th>Email</th>
                                <th>PhoneNumber</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<UserDTO> list = (List<UserDTO>) request.getAttribute("list");
                                if (list != null) {

                                    for (int i = 0; i < list.size(); i++) {

                            %>
                            <tr data-game-id="<%= i%>">

                                <td><%= list.get(i).getUserID()%></td>
                                <td><%= list.get(i).getFullname()%></td>
                                <td><%= list.get(i).getUsername()%></td>
                                <td><%= list.get(i).getPassword()%></td>
                                <td><%= list.get(i).getEmail()%></td>
                                <td><%= list.get(i).getPhonenumber()%></td>
                                <td>
                                    <div class="actions">
                                        <form action="./Dashboard">
                                            <input name="action" value="edit-user" type="hidden"/>

                                            <input name="user_update_id" value="<%= list.get(i).getUserID()%>" type="hidden"/>
                                            <input class="action-button edit-button" type="submit" value="Edit"/>
                                        </form>
                                       
                                        <form action="./Dashboard">
                                            <input name="action" value="delete-user" type="hidden"/>

                                            <input name="user_delete_id" value="<%= list.get(i).getUserID()%>" type="hidden"/>
                                            <input class="action-button delete-button" type="submit" value="Delete"/>
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



        <script>
            // Xử lý dropdown menu
            document.addEventListener('DOMContentLoaded', function () {
                const championshipMenuItem = document.getElementById('championship-menu-item');

                championshipMenuItem.addEventListener('click', function (e) {
                    // Toggle class 'open' để hiển thị/ẩn dropdown
                    this.classList.toggle('open');

                    // Ngăn sự kiện click lan ra các phần tử cha
                    e.stopPropagation();
                });

                // Xử lý click vào các mục con trong dropdown
                const dropdownItems = document.querySelectorAll('.dropdown-menu-item');
                dropdownItems.forEach(item => {
                    item.addEventListener('click', function (e) {
                        // Xử lý khi click vào mục con
                        console.log('Clicked on:', this.textContent);
                        // Thêm code điều hướng hoặc xử lý khác ở đây

                        // Ngăn sự kiện click lan ra các phần tử cha
                        e.stopPropagation();
                    });
                });

                // Đóng dropdown khi click ra ngoài
                document.addEventListener('click', function () {
                    championshipMenuItem.classList.remove('open');
                });
            });

            // Các hàm xử lý modal

        </script>
    </body>
</html>