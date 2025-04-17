<%@page import="strikethefrog.Transaction.TransactionDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if (session == null || session.getAttribute("adminsession") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transaction Management - Strike The Frog</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link href="./styles/admin-lists.css" rel="stylesheet"> <!-- Using the same stylesheet as user page -->
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
            <a href="./Dashboard?action=transaction">
                <li class="sidebar-menu-item active">
                    <img src="assets/icon/admin/money-icon.png" alt="transaction-icon">
                    <p>Transactions</p>
                </li>
            </a>
            <a href="./Dashboard?action=logout">
                <li class="sidebar-menu-item">
                    <img src="assets/icon/admin/user-icon.png" alt="logout-icon">
                    <p>Logout</p>
                </li>
            </a>
        </ul>
    </div>

    <div class="main-content">
        <div class="header">
            <h1 class="header-title">Transactions</h1>
            <div class="header-actions">
                <form class="search-container" action="./Dashboard" method="get">
                    <input type="hidden" name="action" value="transaction">
                    <input type="number" class="search-input" name="playerID" placeholder="Search by Player ID" value="${param.playerID}">
                    <input type="number" class="search-input" name="tournamentID" placeholder="Search by Tournament ID" value="${param.tournamentID}">
                    <select class="search-input" name="paymentStatus">
                        <option value="">All Statuses</option>
                        <option value="Pending" ${param.paymentStatus == 'Pending' ? 'selected' : ''}>Pending</option>
                        <option value="Completed" ${param.paymentStatus == 'Completed' ? 'selected' : ''}>Completed</option>
                        <option value="Failed" ${param.paymentStatus == 'Failed' ? 'selected' : ''}>Failed</option>
                    </select>
                    <button type="submit" class="search-button">
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
                <p><%= error %></p>
            </div>
        </div>
        <%
            }
        %>

        <div class="page-content">
            <div class="content-header">
                <h2>Manage Transactions</h2>
                <form action="./Dashboard" class="content-header-actions">
                    <input type="hidden" name="action" value="add-transaction"/>
                    <button class="button primary-button">
                        <img src="assets/icon/admin/add-btn.png" alt="add-btn">
                        <span>Add new transaction</span>
                    </button>
                </form>
            </div>
            <div class="table-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>Transaction ID</th>
                            <th>Player ID</th>
                            <th>Tournament ID</th>
                            <th>Payment Status</th>
                            <th>Creation Time</th>
                            <th>Expiration Date</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="transaction" items="${list}" varStatus="loop">
                            <tr data-transaction-id="${loop.count}">
                                <td>${transaction.transactionID}</td>
                                <td>${transaction.playerID}</td>
                                <td>${transaction.tournamentID}</td>
                                <td>${transaction.paymentStatus}</td>
                                <td>${transaction.creationTime}</td>
                                <td>${transaction.expirationDate}</td>
                                <td>
                                    <div class="actions">
                                        <form action="./Dashboard">
                                            <input type="hidden" name="action" value="edit-transaction"/>
                                            <input name="transaction_edit_id" value="${transaction.transactionID}" type="hidden"/>
                                            <input class="action-button edit-button" type="submit" value="Edit"/>
                                        </form>
                                        <form action="./Dashboard">
                                            <input type="hidden" name="action" value="delete-transaction"/>
                                            <input name="transaction_delete_id" value="${transaction.transactionID}" type="hidden"/>
                                            <input class="action-button delete-button" type="submit" value="Delete"/>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script>
        // Dropdown menu handling (copied from user page)
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