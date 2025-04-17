<%@page import="java.io.FilenameFilter"%>
<%@page import="java.io.File"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Random"%>
<%@page import="strikethefrog.Tournament.TournamentDTO"%>
<%@page import="java.util.List"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.TimeZone"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    HttpSession sessionCheck = request.getSession(false); // Get session, don't create new
    if (sessionCheck == null || sessionCheck.getAttribute("usersession") == null) {
        response.sendRedirect("login.jsp?error=Please login first"); // Redirect if no session
        return;
    }
%>
<html>
    <head>


        <meta charset="UTF-8">
        <title>StrikeTheFrog - HomePage</title>
        <link rel="stylesheet" href="styles/homepage.css">

    </head>
    <body>


        <div class="navbar">
            <div class="nav-center">
                <a href="TournamentController?action=list">Home</a>
                <div class="logo"><img src="assets/images/homepage/logo.jpg" alt="Logo" class="logo"></div>


                <a href="#">Rule</a>
            </div>


            <div class="welcome-message">
                <img src="assets/images/homepage/user.jpg" alt="User Logo" class="user-logo">
                <h4>${sessionScope.usersession.username}</h4>
                <a href="login?action=logout" class="logout-link">Logout</a>
            </div>

        </div>



        <div class="main-content">

            <h1>STRIKE THE FROG 2025</h1>

            <%
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, YYYY");
                Calendar calendar = Calendar.getInstance();
                String startDate = sdf.format(calendar.getTime());
                TimeZone timeZone = calendar.getTimeZone();

            %>
            <%= startDate%>
            <br>
            <%= timeZone.getID()%>


            <br>

            <a class="button"  href="#">DRAWS - RESULTS</a>
        </div>

        <div>
            <h1>HIGHLIGHT</h1>
            <iframe width="560" height="315" src="https://www.youtube.com/embed/G1knHnVO0mE?si=iymDVwUzkvbOuZA3" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>        
        </div>

        <div><h1>Tournaments</h1></div> 
      
        <div class="rounds-container">

            <!--            <button class="round-btn left" onclick="scrollRounds(-1)">←</button>-->
              <!-- Rounds Slider -->
            <div class="rounds-slider" id="rounds-slider">
                <%
                    List<TournamentDTO> tourList = (List<TournamentDTO>) request.getAttribute("tourList");
                    // Read images dynamically from the "assets/images/transaction" directory
                    String imagesPath = application.getRealPath("/assets/images/transaction");
                    File imagesDir = new File(imagesPath);
                    FilenameFilter imageFilter = new FilenameFilter() {
                        public boolean accept(File dir, String name) {//for filtering images png, jpg
                            return name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png");
                        }
                    };

                    String[] images = imagesDir.list(imageFilter);
                    if (tourList == null || tourList.isEmpty()) {
                %>
                <p>No tournaments available.</p>
                <%
                } else if (images == null || images.length == 0) {
                %>
                <p>No tournament images available.</p>
                <%
                } else {
                    // Ensure we have enough images for tournaments
                    List<String> shuffledImages = new ArrayList<>(Arrays.asList(images));
                    Collections.shuffle(shuffledImages); // Randomize order

                    int imageIndex = 0;
                    for (TournamentDTO tournament : tourList) {
                        // Use sequential images without duplication (with safety check)
                        String randomImage = shuffledImages.isEmpty()
                                ? "default.jpg"
                                : // Fallback to a default image
                                shuffledImages.get(imageIndex % shuffledImages.size());
                        imageIndex++;
                %>
                <div class="round">
                    <h3 style="background: #0066cc; color: white; border-radius: 10px; padding: 3px; width: 150px; text-align: center;">Invitational</h3>
                    <a href="tournamentdetails.jsp?tournamentID=<%= tournament.getTournamentID()%>" > 
                        <img src="assets/images/transaction/<%= randomImage%>" alt="Tournament Image"> 
                    </a>
                    <h1><%= tournament.getTournamentName()%></h1>
                    <h3><%= tournament.getLocation()%></h3>
                    <p> <%= tournament.getFormattedDate()%></p>


                </div>
                <%
                        }
                    }
                %>
            </div>
            <!--            <button class="round-btn right" onclick="scrollRounds(1)">→</button>-->
            <button id="loadMoreBtn" class="load-more-btn">SHOW MORE</button>
        </div>




    </body>
    <script>

        let visibleItems = 3; // Number of items to display initially
        const itemsPerPage = 3; // Number of items to load per click

        document.addEventListener("DOMContentLoaded", function () {
            const allRounds = document.querySelectorAll('.round');
            const loadMoreBtn = document.getElementById('loadMoreBtn');

            // Initially hide items beyond the first visible set
            allRounds.forEach((item, index) => {
                if (index >= visibleItems) {
                    item.style.display = 'none';
                }
            });

            // Show more items on button click
            loadMoreBtn.addEventListener("click", function () {
                let newVisibleItems = visibleItems + itemsPerPage;
                for (let i = visibleItems; i < newVisibleItems && i < allRounds.length; i++) {
                    allRounds[i].style.display = 'flex';
                }
                visibleItems = newVisibleItems;

                // Hide the button if all items are visible
                if (visibleItems >= allRounds.length) {
                    loadMoreBtn.style.display = 'none';
                }
            });
        });


    </script>

    <footer margin-top:25px style="background: linear-gradient(to right, #000, #003366); color: white; padding: 30px; text-align: center; font-size: 14px;">
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

</html>