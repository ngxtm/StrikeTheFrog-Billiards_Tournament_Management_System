<%
    if (session == null || session.getAttribute("adminsession") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<%@page import="strikethefrog.Transaction.TransactionDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Transaction Details - Admin</title>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
        <link href="styles/admin.css" rel="stylesheet">
        <script src="script/sidebar.js"></script>
    </head>
    <body>
        <div class="dashboard-container">
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
                    <li class="sidebar-menu-item dropdown">
                        <img src="assets/icon/admin/tournament-icon.png" alt="Championship">
                        <span>Championship</span>
                        <div class="dropdown-arrow"></div>
                        <ul class="dropdown-menu">
                            <li><a href="./Dashboard?action=round">Round</a></li>
                            <li><a href="./Dashboard?action=match">Match</a></li>
                            <li><a href="./Dashboard?action=tournament">Tournament</a></li>
                            <li><a href="./Dashboard?action=table">Table</a></li>
                        </ul>
                    </li>
                    <a href="./Dashboard?action=transaction" class="sidebar-menu-link">
                        <li class="sidebar-menu-item active">
                            <img src="assets/icon/admin/transaction-icon.png" alt="Transaction">
                            <p>Transaction</p>
                        </li>
                    </a>
                    <a href="./Dashboard?action=logout" class="sidebar-menu-link">
                        <li class="sidebar-menu-item">
                            <img src="assets/icon/admin/logout-icon.png" alt="Logout">
                            <p>Logout</p>
                        </li>
                    </a>
                </ul>
            </div>

            <div class="main-content">
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
                        <h2>
                            <% 
                                String nextAction = (String) request.getAttribute("nextaction");
                                if (nextAction != null && nextAction.equals("create-transaction")) {
                                    out.print("Add New Transaction");
                                } else {
                                    out.print("Edit Transaction");
                                }
                            %>
                        </h2>
                    </div>
                    
                    <div class="form-container">
                        <form action="./Dashboard" method="POST">
                            <% 
                                TransactionDTO transaction = (TransactionDTO) request.getAttribute("transaction");
                                if (transaction == null) {
                                    transaction = new TransactionDTO();
                                }
                                
                                boolean readonly = request.getAttribute("readonly") != null ? 
                                    (Boolean) request.getAttribute("readonly") : false;
                                
                                if (nextAction != null) {
                            %>
                            <input type="hidden" name="action" value="<%= nextAction %>">
                            <% } %>
                            
                            <div class="form-group">
                                <label for="transactionID">Transaction ID:</label>
                                <input type="number" id="transactionID" name="transactionID" 
                                       value="<%= transaction.getTransactionID() %>" 
                                       <%= readonly ? "readonly" : "" %> required>
                            </div>
                            
                            <div class="form-group">
                                <label for="playerID">Player ID:</label>
                                <input type="number" id="playerID" name="playerID" 
                                       value="<%= transaction.getPlayerID() %>" required>
                            </div>
                            
                            <div class="form-group">
                                <label for="tournamentID">Tournament ID:</label>
                                <input type="number" id="tournamentID" name="tournamentID" 
                                       value="<%= transaction.getTournamentID() %>" required>
                            </div>
                            
                            <div class="form-group">
                                <label for="paymentStatus">Payment Status:</label>
                                <select id="paymentStatus" name="paymentStatus" required>
                                    <option value="Pending" <%= "Pending".equals(transaction.getPaymentStatus()) ? "selected" : "" %>>Pending</option>
                                    <option value="Completed" <%= "Completed".equals(transaction.getPaymentStatus()) ? "selected" : "" %>>Completed</option>
                                    <option value="Failed" <%= "Failed".equals(transaction.getPaymentStatus()) ? "selected" : "" %>>Failed</option>
                                </select>
                            </div>
                            
                            <div class="form-group">
                                <label for="creationTime">Creation Time:</label>
                                <input type="datetime-local" id="creationTime" name="creationTime" 
                                       value="<%= transaction.getCreationTime() != null ? 
                                           transaction.getCreationTime().toString().replace(" ", "T").substring(0, 16) : "" %>" 
                                       required>
                            </div>
                            
                            <div class="form-group">
                                <label for="expirationDate">Expiration Date:</label>
                                <input type="datetime-local" id="expirationDate" name="expirationDate" 
                                       value="<%= transaction.getExpirationDate() != null ? 
                                           transaction.getExpirationDate().toString().replace(" ", "T").substring(0, 16) : "" %>">
                                <small>Leave empty for completed transactions</small>
                            </div>
                            
                            <div class="form-actions">
                                <button type="submit" class="button primary-button">Save</button>
                                <a href="./Dashboard?action=transaction" class="button secondary-button">Cancel</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>