<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>StrikeTheFrog - Sign Up</title>
        <link href="styles/signup.css" rel="stylesheet">
    </head>
    <body>

        <div class="container">
            <div class="card">
                <form action="./signup" method="POST" class="form">
                    <div class="header">
                        <h1 class="title">Sign Up</h1>
                    </div>

                    <div>
                        <input type="text" name="fullname" class="input" placeholder="Fullname" required>
                    </div>

                    <div class="input-group">
                        <input type="text" name="username" class="input" placeholder="Username" required>
                    </div>

                    <div class="input-group">
                        <input type="password" name="password" class="input" placeholder="Create password" required>
                    </div> 

                    <div class="input-group">
                        <input type="email" name="email" class="input" placeholder="Email address" required>
                    </div>

                    <div class="input-group">
                        <input type="tel" name="phonenumber" class="input" placeholder="Phone number" required>
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
                        Create Account
                    </button>

                    <div class="toggle-form">
                        <a href="login.jsp" class="toggle-button">
                            Already have an account? Login
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>