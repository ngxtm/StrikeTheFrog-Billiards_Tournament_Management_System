<%
    if (session == null || session.getAttribute("adminsession") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<%@page import="strikethefrog.Round.RoundDTO"%>
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
                <h1 class="header-title">Rounds Management</h1>
                <div class="header-actions">
                    <form class="search-container" action="Dashboard" method="get">
                        <input type="text" class="search-input" name="searchRoundName" placeholder="Search by Round Name">
                        <input name="action" value="round" type="hidden">
                        <button type="submit" id="search-button" class="search-button">
                            <img src="assets/icon/admin/search-icon.png" alt="search-icon">
                            <span>Search</span>
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
                    <h2>Round List</h2>
                    <div class="content-header-actions">
                        <button class="button primary-button">
                            <img src="assets/icon/admin/add-btn.png" alt="add-btn">
                            <span>Add New Round</span>
                        </button>
                    </div>
                </div>
                <div class="table-wrapper">
                    <table>
                        <thead>
                            <tr>
                                <th>Round ID</th>
                                <th>Round Name</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<RoundDTO> list = (List<RoundDTO>) request.getAttribute("list");
                                if (list != null) {
                                    for (int i = 0; i < list.size(); i++) {
                            %>
                            <tr data-round-id="<%= list.get(i).getRoundid()%>">
                                <td><%= list.get(i).getRoundid()%></td>
                                <td><%= list.get(i).getRoundname()%></td>
                                <td>
                                    <div class="actions">
                                        <button class="action-button edit-button" onclick="openUpdateModal(<%= i%>)">Edit</button>
                                        <button class="action-button delete-button" onclick="deleteRound(<%= list.get(i).getRoundid()%>)">Delete</button>
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
            // Existing dropdown menu script remains the same as in the previous version

            function openUpdateModal(index) {
                // Placeholder for update modal functionality
                console.log('Open update modal for round at index:', index);
            }

            function deleteRound(roundId) {
                // Placeholder for delete functionality
                if (confirm('Are you sure you want to delete this round?')) {
                    console.log('Delete round with ID:', roundId);
                    // Add AJAX call or form submission to delete the round
                }
            }
        </script>
    </body>


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