package strikethefrog.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import strikethefrog.Match.GameMatchDAO;
import strikethefrog.Match.GameMatchDTO;

/**
 * Controller for match-related operations
 */
public class MatchController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usersession") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        GameMatchDAO matchDAO = new GameMatchDAO();

        if (action == null) {
            action = "history"; // Default action
        }

        if (action.equals("history")) {
            // Get tournament ID parameter if provided
            String tournamentIDParam = request.getParameter("tournamentID");
            List<GameMatchDTO> matches;

            if (tournamentIDParam != null && !tournamentIDParam.isEmpty()) {
                int tournamentID = Integer.parseInt(tournamentIDParam);
                matches = matchDAO.getMatchesByTournament(tournamentID);
                request.setAttribute("tournamentID", tournamentID);
            } else {
                matches = matchDAO.getAllMatches();
            }

            // We're no longer grouping matches by round here
            // as the JSP handles grouping by tournament and round
            request.setAttribute("matches", matches);
            request.getRequestDispatcher("matchhistory.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Match Controller Servlet";
    }
} 