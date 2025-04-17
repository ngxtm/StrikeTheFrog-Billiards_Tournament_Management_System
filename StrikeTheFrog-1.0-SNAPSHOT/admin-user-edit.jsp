<%-- 
    Document   : admin-edit-user
    Created on : Mar 26, 2025
    Author     : Administrator
--%>

<%@page import="strikethefrog.User.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User Management - Strike The Frog</title>
        <link href="styles/admin-detail-match.css" rel="stylesheet">
        <script src="script/sidebar.js"></script>
    </head>
    <body>
        <div class="container">
            <%-- Sidebar Component --%>
            <aside class="sidebar">
                <div class="sidebar-logo">Strike The Frog</div>
                <ul class="sidebar-menu">
                    <a href="./Dashboard?action=home" class="sidebar-menu-link">
                        <li class="sidebar-menu-item">
                            <img src="assets/icon/admin/home-icon.png" alt="Home">
                            <p>Home</p>
                        </li>
                    </a>
                    <a href="./Dashboard?action=user" class="sidebar-menu-link">
                        <li class="sidebar-menu-item active">
                            <img src="assets/icon/admin/user-icon.png" alt="Users">
                            <p>Users</p>
                        </li>
                    </a>
                    <li class="sidebar-menu-item dropdown" id="championship-menu-item">
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
                    <a href="./Dashboard?action=logout" class="sidebar-menu-link">
                        <li class="sidebar-menu-item">
                            <img src="assets/icon/admin/logout-icon.png" alt="Logout">
                            <p>Logout</p>
                        </li>
                    </a>
                </ul>
            </aside>

            <%-- Main Content --%>
            <main class="main-content">



                <section class="form-section">
                    <form id="user-details-form" 
                          action="./Dashboard" 
                          method="get" >
                        <input type="hidden" name="action" value="save-user">
                        <fieldset>
                            <legend>Personal Information</legend>

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="fullName">Full Name:</label>
                                    <input type="text" 
                                           id="fullName" 
                                           name="fullName"
                                           required 
                                           maxlength="100"
                                           placeholder="Enter full name">
                                </div>
                            </div>

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="email">Email Address:</label>
                                    <input type="email" 
                                           id="email" 
                                           name="email" 

                                           required 
                                           maxlength="100"
                                           placeholder="example@domain.com">
                                </div>

                                <div class="form-group">
                                    <label for="phoneNumber">Phone Number:</label>
                                    <input type="tel" 
                                           id="phoneNumber" 
                                           name="phoneNumber" 

                                           pattern="[0-9]{10,15}"
                                           placeholder="10-15 digits"
                                           title="Phone number should be 10-15 digits">
                                </div>
                            </div>
                        </fieldset>

                        <fieldset>
                            <legend>Account Credentials</legend>

                            <div class="form-row">
                                <div class="form-group">
                                    <label for="username">Username:</label>
                                    <input type="text" 
                                           id="username" 
                                           name="username" 

                                           required 
                                           minlength="4" 
                                           maxlength="50"
                                           placeholder="Choose a username">
                                </div>

                                <div class="form-group">
                                    <label for="password">Password </label>
                                    <input type="password" 
                                           id="password" 
                                           name="password" 

                                           minlength="8" 
                                           maxlength="50"
                                           placeholder="Choose a password">
                                </div>
                            </div>
                        </fieldset>

                        <div class="form-actions">
                            <button type="submit" class="btn btn-save">Save</button>
                            <a href="./Dashboard?action=match"><button type="button" class="btn btn-cancel">Cancel</button></a>
                        </div>
                    </form>
                </section>
            </main>
        </div>

        <script>
            function validateUserForm() {
                const fullName = document.getElementById('fullName');
                const email = document.getElementById('email');
                const username = document.getElementById('username');
                const password = document.getElementById('password');

                // Full name validation
                if (fullName.value.trim().length < 2) {
                    alert('Please enter a valid full name.');
                    return false;
                }

                // Email validation
                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                if (!emailRegex.test(email.value)) {
                    alert('Please enter a valid email address.');
                    return false;
                }

                // Username validation
                if (username.value.length < 4) {
                    alert('Username must be at least 4 characters long.');
                    return false;
                }

        </script>
    </body>
</html>