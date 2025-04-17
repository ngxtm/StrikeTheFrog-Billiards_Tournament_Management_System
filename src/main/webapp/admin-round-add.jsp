<%-- 
    Document   : admin-add-round
    Created on : Mar 27, 2025
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Add Round - Strike The Frog</title>
        <link href="styles/admin-detail-match.css" rel="stylesheet">
        <script src="script/sidebar.js"></script>
    </head>
    <body>
        <div class="container">
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

            <%-- Main Content --%>
            <main class="main-content">
                <section class="form-section">
                    <form id="round-details-form" 
                          action="./Dashboard" 
                          method="post">
                        <input type="hidden" name="action" value="save-round">

                        <fieldset>
                            <legend>Round Information</legend>

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="roundName">Round Name:</label>
                                    <input type="text" 
                                           id="roundName" 
                                           name="roundName" 
                                           required 
                                           maxlength="100"
                                           placeholder="Enter round name">
                                </div>
                            </div>
                        </fieldset>

                        <div class="form-actions">
                            <button type="submit" class="btn btn-save">Save</button>
                            <a href="./Dashboard?action=round"><button type="button" class="btn btn-cancel">Cancel</button></a>
                        </div>
                    </form>
                </section>
            </main>
        </div>

        <script>
            function validateRoundForm() {
                const roundName = document.getElementById('roundName');

                // Round Name validation
                if (roundName.value.trim().length < 2) {
                    alert('Please enter a valid Round Name.');
                    return false;
                }

                return true;
            }

            // Add form validation before submission
            document.getElementById('round-details-form').addEventListener('submit', function (event) {
                if (!validateRoundForm()) {
                    event.preventDefault();
                }
            });
        </script>
    </body>
</html>
