<%-- 
    Document   : rule
    Created on : Mar 25, 2025, 12:59:34 PM
    Author     : Duy
--%>
<%
    HttpSession sessionCheck = request.getSession(false); // Get session, don't create new
    if (sessionCheck == null || sessionCheck.getAttribute("usersession") == null) {
        response.sendRedirect("login.jsp?error=Please login first"); // Redirect if no session
        return;
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>

    <head>
        <title>Rules - Strike The Frog 2025</title>
        <link rel="stylesheet" href="styles.css">
    </head>

    <body>
        <style>

            body, html {
                margin: 0;
                padding: 0;
                overflow-x: hidden;
            }

            .welcome-message {
                margin-left: auto; /* Push to the right */
                display: flex;
                align-items: center;
                gap: 10px; 
            }
            .welcome-message h4 {


                padding: 10px;
                /*    margin-right: 10px; */
                color: white;
                font-size: 15px;

            }
            .logout-link:hover {
                background: none; 
                color: red; 
            }
            .user-logo {
                width: 20px; 
                height: 20px; 
                border-radius: 50%; 
            }
            h4{
                font-size: 25px;
            }
            /* Rounds Slider */

            .nav-center {
                display: flex;
                align-items: center;
                gap: 20px;
                position: absolute;
                left: 50%;
                transform: translateX(-50%);
            }


            header {
                background: linear-gradient(to bottom, #000, #0066cc);
                color: white;
                text-align: center;
                padding: 30px 0;
            }

            header h1 {
                margin: 0;
                font-size: 3rem;
            }

            .container {
                padding: 40px 10%;
            }

            h2 {
                color: #003366;
            }

            ul {
                list-style-type: none;
                padding: 0;
            }

            li {
                margin-bottom: 10px;
            }

            button {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 12px 20px;
                border-radius: 5px;
                cursor: pointer;
                margin-right: 10px;
            }

            button:hover {
                background-color: #0056b3;
            }

            button.yellow {
                background-color: #ffc107;
                color: black;
            }

            button.yellow:hover {
                background-color: #e0a800;
            }

            footer {
                background-color: #003366;
                color: white;
                text-align: center;
                padding: 20px;
            }

            footer p {
                margin: 0;
            }

            footer a {
                color: #ffc107;
                text-decoration: none;
            }

            footer a:hover {
                text-decoration: underline;
            }
            .logo img {
                height: 60px; /* Adjust size as needed */
                width: 70px;
                border-radius: 50px;
            }
            .navbar {
                display: flex;
                /*   align-items: center;*/
                justify-content: space-between; 
                background: rgba(0, 0, 0, 0.8);
                padding: 15px;
                flex-wrap: wrap; 
            }

            .navbar a {
                color: white;
                text-decoration: none;
                padding: 14px 20px;
                font-size: 18px;
            }

            .navbar a:hover {
                background: #ffcc00;
                color: black;
            }
        </style>
        <header>
            <div class="navbar">
                <div class="nav-center">
                    <a href="TournamentController?action=list">Home</a>
                    <div class="logo"><img src="assets/images/homepage/logo.jpg" alt="Logo" class="logo"></div>


                    <a href="rule.jsp">Rule</a>
                </div>


                <div class="welcome-message">
                    <img src="assets/images/homepage/user.jpg" alt="User Logo" class="user-logo">
                    <h4>${sessionScope.usersession.username}</h4>
                    <a href="login?action=logout" class="logout-link">Logout</a>
                </div>

            </div>
        </header>

        <section class="rules-section">
            <h1>RULES - Strike The Frog 2025</h1>

            <div class="event-category">
                <h2>WPA SANCTIONED EVENTS</h2>
                <ul>
                    <li>VN Open Tour</li>
                    <li>Hanoi Open</li>
                    <li>Hochiminh Open</li>
                </ul>
                <button class="blue-btn">PROTOCOL AND INFO (PDF)</button>
                <button class="blue-btn">SHOOTOUT RULES</button>
            </div>

            <div class="event-category">
                <h2>PBS INVITATIONAL EVENTS</h2>
                <ul>
                    <li>Women Challenge of Champions</li>
                    <li>American Junior Showdown</li>
                    <li>Wheelchair Exhibition</li>
                    <li>Bank Pool Showdown</li>
                    <li>One Pocket Face off</li>
                </ul>
                <button class="blue-btn">PROTOCOL AND INFO (PDF)</button>
                <button class="yellow-btn">ONE POCKET RULES (PDF)</button>
                <button class="yellow-btn">BANK POOL RULES (PDF)</button>
            </div>
        </section>


        <footer margin-top:25px style="background: linear-gradient(to right, #000, #003366); color: white; padding: 30px; text-align: center; font-size: 14px;">

            <div class="footer-content">
                <p>The #1 Pro Billiard tour in the world, 15+ events per year with the best players in the world and $2M+ Prize Fund</p>

            </div>
            <div>
                <a href="TournamentController?action=list" style="color: white; text-decoration: none; margin: 0 15px;">Home</a> | 
                <a href="#" style="color: white; text-decoration: none; margin: 0 15px;">Rule</a> | 
                <a href="login?action=logout" style="color: white; text-decoration: none; margin: 0 15px;">Logout</a>

            </div>
            <p style="margin-top: 30px;">
                <strong>Home:</strong> Navigate to the homepage. <br>

                <strong>Rule:</strong> View legislation structure for awards.<br>
                <strong>Log Out:</strong> Securely sign out of your account. 
            </p>
            <p style="margin-top: 25px;">&copy; 2025 StrikeTheFrog. All rights reserved.</p>

        </footer>

    </body>
</html>
