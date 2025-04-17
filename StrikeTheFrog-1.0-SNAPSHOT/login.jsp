<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>StrikeTheFrog - Login</title>
        <link href="styles/login.css" rel="stylesheet">
    </head>
    <body>

        <div class="container">
            <div class="card">
                <form action="./login" method="GET" class="form">
                    <div class="header">
                        <h1 class="title">Login</h1>
                    </div>

                    <div>
                        <input type="text" name="user" class="input" placeholder="Username" required>
                    </div>

                    <div>
                        <input type="password" name="password" class="input" placeholder="Password" required>
                    </div>

                    <%
                        String errorMessage = (String) request.getAttribute("error");
                        if (errorMessage != null) {
                    %>
                    <div class="error-message"><%= errorMessage%></div>
                    <%
                        }
                    %>

                    <button type="submit" class="submit-button">
                        Login
                    </button>

                    <div class="toggle-form">
                        <a href="signup.jsp" class="toggle-button">
                            New here? Sign Up
                        </a>
                    </div>
                </form>
            </div>
        </div>

    </body>
</html>