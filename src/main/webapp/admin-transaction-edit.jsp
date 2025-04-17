<%-- 
    Document   : admin-transaction-edit
    Created on : Mar 27, 2025
    Author     : ty307
--%>

<%@page import="strikethefrog.Transaction.TransactionDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    <title>Edit Transaction - Strike The Frog</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link href="./styles/admin-lists.css" rel="stylesheet">
    <style>
        .form-section {
            padding: 20px;
            max-width: 600px;
            margin: 0 auto;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: 500;
        }
        .form-group input, .form-group select {
            width: 100%;
            padding: 8px;
            box-sizing: border-box;
        }
        .readonly-field {
            background-color: #f0f0f0;
            cursor: not-allowed;
        }
        .form-actions {
            margin-top: 20px;
            display: flex;
            gap: 10px;
        }
        .btn {
            padding: 10px 20px;
            border: none;
            cursor: pointer;
        }
        .btn-save {
            background-color: #4CAF50;
            color: white;
        }
        .btn-cancel {
            background-color: #f44336;
            color: white;
        }
        .error-message {
            color: red;
            margin-bottom: 15px;
        }
    </style>
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
                    <img src="assets/icon/admin/transaction-icon.png" alt="transaction-icon">
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
    </div>

    <div class="main-content">
        <section class="form-section">
            <h1>Edit Transaction</h1>

            <%-- Error Message --%>
            <%
                String error = (String) request.getAttribute("error");
                if (error != null) {
            %>
            <p class="error-message"><%= error %></p>
            <%
                }
            %>

            <form id="transaction-edit-form" action="./Dashboard" method="post">
                <input type="hidden" name="action" value="save-edit-transaction">
                <input type="hidden" name="transactionID" value="${transaction.transactionID}">

                <fieldset>
                    <legend>Transaction Information</legend>

                    <div class="form-group">
                        <label for="transactionID">Transaction ID:</label>
                        <input type="text" 
                               id="transactionID" 
                               name="transactionIDDisplay" 
                               value="${transaction.transactionID}" 
                               readonly 
                               class="readonly-field">
                    </div>

                    <div class="form-group">
                        <label for="playerID">Player ID:</label>
                        <input type="number" 
                               id="playerID" 
                               name="playerID" 
                               min="1" 
                               required 
                               value="${transaction.playerID}" 
                               placeholder="Enter Player ID">
                    </div>

                    <div class="form-group">
                        <label for="tournamentID">Tournament ID:</label>
                        <input type="number" 
                               id="tournamentID" 
                               name="tournamentID" 
                               min="1" 
                               required 
                               value="${transaction.tournamentID}" 
                               placeholder="Enter Tournament ID">
                    </div>

                    <div class="form-group">
                        <label for="paymentStatus">Payment Status:</label>
                        <select id="paymentStatus" name="paymentStatus" required>
                            <option value="Pending" ${transaction.paymentStatus == 'Pending' ? 'selected' : ''}>Pending</option>
                            <option value="Completed" ${transaction.paymentStatus == 'Completed' ? 'selected' : ''}>Completed</option>
                            <option value="Failed" ${transaction.paymentStatus == 'Failed' ? 'selected' : ''}>Failed</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="creationTime">Creation Time:</label>
                        <input type="datetime-local" 
                               id="creationTime" 
                               name="creationTime" 
                               required 
                               value="${transaction.creationTime != null ? transaction.creationTime.toString().substring(0, 16) : ''}">
                    </div>

                    <div class="form-group">
                        <label for="expirationDate">Expiration Date:</label>
                        <input type="datetime-local" 
                               id="expirationDate" 
                               name="expirationDate" 
                               required 
                               value="${transaction.expirationDate != null ? transaction.expirationDate.toString().substring(0, 16) : ''}">
                    </div>
                </fieldset>

                <div class="form-actions">
                    <button type="submit" class="btn btn-save">Save Changes</button>
                    <a href="./Dashboard?action=transaction"><button type="button" class="btn btn-cancel">Cancel</button></a>
                </div>
            </form>
        </section>
    </div>

    <script>
        // Dropdown menu handling
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

        // Form validation
        document.getElementById('transaction-edit-form').addEventListener('submit', function (e) {
            const playerID = document.getElementById('playerID').value;
            const tournamentID = document.getElementById('tournamentID').value;
            const creationTime = new Date(document.getElementById('creationTime').value);
            const expirationDate = new Date(document.getElementById('expirationDate').value);

            if (playerID < 1) {
                alert('Player ID must be a positive number.');
                e.preventDefault();
                return;
            }
            if (tournamentID < 1) {
                alert('Tournament ID must be a positive number.');
                e.preventDefault();
                return;
            }
            if (expirationDate <= creationTime) {
                alert('Expiration Date must be after Creation Time.');
                e.preventDefault();
                return;
            }
        });
    </script>
</body>
</html>