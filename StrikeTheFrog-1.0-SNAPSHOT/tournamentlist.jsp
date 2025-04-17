<%@page import="Tournament.TournamentDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tournament List</title>
    </head>
    <body>

        <h1>Tournament List</h1>
        
        <p>Login user: ${sessionScope.usersession.fullname}</p>

        <br>

        <form action="TournamentController" method="GET">
            <input name="action" value="create" type="hidden">
            <input type="submit" value="Create">
        </form>
        
        <br>

        <table border="1">
            <tr>
                <td>Tournament ID</td>
                <td>Tournament Name</td>
                <td>Description</td>
                <td>Location</td>
                <td>Start Time</td>
                <td>End Time</td>
                <td>Delete</td>
            </tr>
            <%
                List<TournamentDTO> list = (List<TournamentDTO>) request.getAttribute("tList");
                if (list != null && !list.isEmpty()) {
                    for (TournamentDTO tournament : list) {
                        pageContext.setAttribute("tournament", tournament);
            %>
            <tr>

                <%
                    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedStartTime = formatter.format(tournament.getStartTime());
                    String formattedEndTime = formatter.format(tournament.getEndTime());
                %>

                <td><a href="TournamentController?action=details&id=${tournament.tournamentID}">${tournament.tournamentID}</a></td>
                <td>${tournament.tournamentName}</td>
                <td>${tournament.description}</td>
                <td>${tournament.location}</td>
                <td><%=formattedStartTime%></td>
                <td><%=formattedEndTime%></td>

                <td>
                    <form action="TournamentController" method="GET">
                        <input name="action" value="delete" type="hidden">
                        <input name="id" value="${tournament.tournamentID}" type="hidden">
                        <input type="submit" value="Delete">
                    </form>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="8">No tournaments available</td>
            </tr>
            <%
                }
            %>
        </table>
    </body>
</html>
