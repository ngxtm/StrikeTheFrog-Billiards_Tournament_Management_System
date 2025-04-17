<%
    if (session == null || session.getAttribute("adminsession") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<%@page import="strikethefrog.Tournament.TournamentDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Center - Tournaments</title>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
        <link href="./styles/admin-lists.css" rel="stylesheet">
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
                <a href="./Dashboard?action=logout">
                    <li class="sidebar-menu-item">
                        <img src="assets/icon/admin/user-icon.png" alt="user-icon">
                        <p>Logout</p>
                    </li>
                </a>
            </ul>
        </div>



        <div class="main-content">
            <div class="header">
                <h1 class="header-title">Tournaments</h1>
                <div class="header-actions">
                    <form class="search-container" action="Dashboard" method="GET">
                        <input type="hidden" name="action" value="tournament">
                        <input type="text" name="searchTourName" id="tournament-search" class="search-input" placeholder="Find by Tournament Name">
                        <button type="submit" id="search-button" class="search-button">
                            <img src="assets/icon/admin/search-icon.png" alt="search-icon">
                            <span>Tìm kiếm</span>
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
                    <p><%= error%></p>
                </div>
            </div>
            <%
                }
            %>
            <div class="page-content">
                <div class="content-header">
                    <h2>Management tournaments</h2>
                    <div class="content-header-actions">
                        <button class="button primary-button" id="add-tournament-btn">
                            <img src="assets/icon/admin/add-btn.png" alt="add-btn">
                            <span>Add a tournament</span>
                        </button>
                    </div>
                </div>

                <div class="table-wrapper">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Description</th>
                                <th>Location</th>
                                <th>Start Time</th>
                                <th>End Time</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                List<TournamentDTO> tournaments = (List<TournamentDTO>) request.getAttribute("tList");
                                if (tournaments != null) {
                                    for (TournamentDTO tournament : tournaments) {
                            %>
                            <tr data-tournament-id="<%= tournament.getTournamentID()%>">
                                <td><%= tournament.getTournamentID()%></td>
                                <td><%= tournament.getTournamentName()%></td>
                                <td><%= tournament.getDescription()%></td>
                                <td><%= tournament.getLocation()%></td>
                                <td><%= tournament.getStartTime()%></td>
                                <td><%= tournament.getEndTime()%></td>
                                <td>
                                    <div class="actions">
                                        <button class="action-button edit-button" onclick="openUpdateModal(<%= tournament.getTournamentID()%>)">Edit</button>
                                        <button class="action-button delete-button" onclick="deleteTournament(<%= tournament.getTournamentID()%>)">Delete</button>
                                    </div>
                                </td>
                            </tr>
                            <%
                                    }
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="modal" id="addTournamentModal" style="display: none;"> 
            <div class="modal-content"> 
                <div class="modal-header"> 
                    <h2>Add Tournament</h2> 
                    <button onclick="closeAddModal()">&times;</button> 
                </div> 

                <div class="modal-body"> 
                    <form id="addTournamentForm" enctype="multipart/form-data" method="POST" action="./Dashboard">
                        <input type="hidden" name="action" value="addTournament"> 
                        <div class="form-group"> 
                            <label for="tournamentName">Tournament Name</label> 
                            <input type="text" id="tournamentName" name="tournamentName" required> 
                        </div> 
                        <div class="form-group"> 
                            <label for="description">Description</label> 
                            <textarea id="description" name="description" required></textarea> 
                        </div> 
                        <div class="form-group"> 
                            <label for="location">Location</label> 
                            <input type="text" id="location" name="location" required> 
                        </div> 
                        <div class="form-group"> 
                            <label for="startTime">Start Time</label> 
                            <input type="datetime-local" id="startTime" name="startTime" required> 
                        </div> 
                        <div class="form-group"> 
                            <label for="endTime">End Time</label> 
                            <input type="datetime-local" id="endTime" name="endTime" required> 
                        </div> <div class="form-group"> 
                            <label for="imageUpload">Image</label> 
                            <input type="file" id="imageUpload" name="imageUpload" accept="image/*" onchange="previewImage(event)"> 
                            <div id="imagePreview" style="margin-top: 10px;"> 
                            </div> 
                        </div> 
                    </form> 
                </div>

                <div class="modal-footer"> 
                    <button class="button secondary-button" onclick="closeAddModal()">Cancel</button> 
                    <button class="button primary-button" onclick="submitTournamentForm()">Save</button> 
                </div> 

            </div> 
        </div>

        <script>
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

            function openModalForAdd() {
                const modalTitle = document.querySelector("#addTournamentModal .modal-header h2");
                modalTitle.textContent = "Add Tournament";

                const form = document.getElementById("addTournamentForm");
                form.action = "Dashboard";
                form.reset();

                const imagePreview = document.getElementById("imagePreview");
                imagePreview.innerHTML = "";

                document.getElementById("addTournamentModal").style.display = "flex";
            }

            function openModalForUpdate(tournamentData) {
                const modalTitle = document.querySelector("#addTournamentModal .modal-header h2");
                modalTitle.textContent = "Update Tournament";

                const form = document.getElementById("addTournamentForm");
                form.action = "Dashboard?action=updateTournament";

                document.getElementById("tournamentName").value = tournamentData.tournamentName;
                document.getElementById("description").value = tournamentData.description;
                document.getElementById("location").value = tournamentData.location;
                document.getElementById("startTime").value = tournamentData.startTime;
                document.getElementById("endTime").value = tournamentData.endTime;

                document.getElementById("addTournamentModal").style.display = "flex";
            }

            function closeAddModal() {
                document.getElementById("addTournamentModal").style.display = "none";
            }

        </script>
    </body>
</html>
