<%-- 
    Document   : rule
    Created on : Mar 25, 2025, 12:59:34 PM
    Author     : Duy
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%
    HttpSession sessionCheck = request.getSession(false);
    if (sessionCheck == null || sessionCheck.getAttribute("usersession") == null) {
        response.sendRedirect("login.jsp?error=Please login first");
        return;
    }

%>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Rules - StrikeTheFrog</title>
        <link rel="stylesheet" href="styles/rules.css">
    </head>
    <body>
        <div class="navbar">
            <div class="nav-left">
                <div class="logo">
                    <img src="assets/images/homepage/logo.jpg" alt="Logo">
                </div>
            </div>

            <div class="nav-center">
                <a href="TournamentController?action=list">Home</a>
                <a href="rule.jsp">Rule</a>
            </div>

            <div class="welcome-message">
                <img src="assets/images/homepage/user.jpg" alt="User Logo" class="user-logo">
                <a href="TournamentController?action=user-match"><h4>${sessionScope.usersession.username}</h4></a>
                <a href="login?action=logout" class="logout-link">Logout</a>
            </div>
        </div>

        <div class="container">
            <h1 class="page-title">RULES - Strike The Frog 2025</h1>

            <h2 class="section-title">WPA SANCTIONED EVENTS</h2>
            <div class="rule-event">VN Open Tour</div>
            <div class="rule-event">Hanoi Open</div>
            <div class="rule-event">Hochiminh Open</div>

            <div class="rule-buttons">
                <a href="https://www.bing.com/ck/a?!&&p=117e9c986c1c243249b0d9c224bd6cda1bbe0231d26211a14d98cda1783a36e6JmltdHM9MTc0Mjk0NzIwMA&ptn=3&ver=2&hsh=4&fclid=26b76cce-f4ad-6a22-1817-79ebf5ac6b7d&psq=WPA+SANCTIONED+EVENTS+protocol%2c&u=a1aHR0cHM6Ly9lcGJmcmVmLmxpbmsvcC9XUEEyNE5aLnBkZg&ntb=1" target="_blank" class="rule-button">PROTOCOL AND INFO (PDF)</a>
                <a href="https://www.bing.com/ck/a?!&&p=117e9c986c1c243249b0d9c224bd6cda1bbe0231d26211a14d98cda1783a36e6JmltdHM9MTc0Mjk0NzIwMA&ptn=3&ver=2&hsh=4&fclid=26b76cce-f4ad-6a22-1817-79ebf5ac6b7d&psq=WPA+SANCTIONED+EVENTS+protocol%2c&u=a1aHR0cHM6Ly9lcGJmcmVmLmxpbmsvcC9XUEEyNE5aLnBkZg&ntb=1" target="_blank" class="rule-button">SHOOTOUT RULES</a>
            </div>

            <h2 class="section-title">PBS INVITATIONAL EVENTS</h2>
            <div class="rule-event">Women Challenge of Champions</div>
            <div class="rule-event">American Junior Showdown</div>
            <div class="rule-event">Wheelchair Exhibition</div>
            <div class="rule-event">Bank Pool Showdown</div>
            <div class="rule-event">One Pocket Face off</div>

            <div class="rule-buttons">
                <a href="https://www.bing.com/ck/a?!&&p=117e9c986c1c243249b0d9c224bd6cda1bbe0231d26211a14d98cda1783a36e6JmltdHM9MTc0Mjk0NzIwMA&ptn=3&ver=2&hsh=4&fclid=26b76cce-f4ad-6a22-1817-79ebf5ac6b7d&psq=WPA+SANCTIONED+EVENTS+protocol%2c&u=a1aHR0cHM6Ly9lcGJmcmVmLmxpbmsvcC9XUEEyNE5aLnBkZg&ntb=1" target="_blank" class="rule-button">PROTOCOL AND INFO (PDF)</a>
                <a href="https://www.bing.com/ck/a?!&&p=117e9c986c1c243249b0d9c224bd6cda1bbe0231d26211a14d98cda1783a36e6JmltdHM9MTc0Mjk0NzIwMA&ptn=3&ver=2&hsh=4&fclid=26b76cce-f4ad-6a22-1817-79ebf5ac6b7d&psq=WPA+SANCTIONED+EVENTS+protocol%2c&u=a1aHR0cHM6Ly9lcGJmcmVmLmxpbmsvcC9XUEEyNE5aLnBkZg&ntb=1" target="_blank" class="rule-button">ONE POCKET RULES (PDF)</a>
                <a href="https://www.bing.com/ck/a?!&&p=117e9c986c1c243249b0d9c224bd6cda1bbe0231d26211a14d98cda1783a36e6JmltdHM9MTc0Mjk0NzIwMA&ptn=3&ver=2&hsh=4&fclid=26b76cce-f4ad-6a22-1817-79ebf5ac6b7d&psq=WPA+SANCTIONED+EVENTS+protocol%2c&u=a1aHR0cHM6Ly9lcGJmcmVmLmxpbmsvcC9XUEEyNE5aLnBkZg&ntb=1" target="_blank" class="rule-button">BANK POOL RULES (PDF)</a>
            </div>
        </div>

        <footer style="background: linear-gradient(to right, #000, #003366); color: white; padding: 30px; text-align: center; font-size: 14px; margin-top: 3rem;">
            <div>
                <a href="TournamentController?action=list" style="color: white; text-decoration: none; margin: 0 15px;">Home</a> | 
                <a href="rule.jsp" style="color: white; text-decoration: none; margin: 0 15px;">Rules</a> | 
                <a href="login?action=logout" style="color: white; text-decoration: none; margin: 0 15px;">Logout</a>
            </div>
            <p style="margin-top: 25px;">&copy; 2025 StrikeTheFrog. All rights reserved.</p>
        </footer>
    </body>
</html>
