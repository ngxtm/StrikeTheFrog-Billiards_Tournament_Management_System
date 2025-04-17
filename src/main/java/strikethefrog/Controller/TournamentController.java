/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strikethefrog.Controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import strikethefrog.Player.PlayerDAO;
import strikethefrog.Player.PlayerDTO;

import strikethefrog.Tournament.TournamentDAO;
import strikethefrog.Tournament.TournamentDTO;
import strikethefrog.User.UserDAO;
import strikethefrog.User.UserDTO;

/**
 *
 * @author ASUS
 */
public class TournamentController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usersession") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        TournamentDAO tdao = new TournamentDAO();
        UserDAO udao = new UserDAO();
        PlayerDAO pdao = new PlayerDAO();

        if (action == null || action.equals("list")) {
            String searchTourName = request.getParameter("searchTourName");
            List<TournamentDTO> list = tdao.list(searchTourName);
            request.setAttribute("tourList", list);

            // Add code to find the closest tournament
            try {
                TournamentDTO closestTournament = tdao.findClosestUpcomingTournament();
                request.setAttribute("closestTournament", closestTournament);
            } catch (Exception ex) {
                log("Error finding closest tournament: " + ex.getMessage());
            }

            request.getRequestDispatcher("homepage.jsp").forward(request, response);
        } else if (action.equals("details")) {
            Integer tournamentID = null;
            try {
                tournamentID = Integer.parseInt(request.getParameter("tournamentID"));
            } catch (NumberFormatException ex) {
                log("Tournament ID is in the wrong format.");
            }

            TournamentDTO tournament = tdao.load(tournamentID);

            if (tournament != null) {

                int playerCount = tdao.countRegisteredPlayers(tournament.getTournamentID());
                String tournamentStatus = tdao.getTournamentStatus(tournament.getTournamentID());
                
                List<PlayerDTO> playerList = pdao.list(tournament.getTournamentID());

                request.setAttribute("pList", playerList);

                request.setAttribute("playerCount", playerCount);
                request.setAttribute("tournamentStatus", tournamentStatus);
                request.setAttribute("tournament", tournament);
                request.getRequestDispatcher("tournamentdetails.jsp").forward(request, response);
            } else {
                response.sendRedirect("tournamentlist.jsp");
            }
        } else if (action.equals("join")) {
            Integer userID = null;

            try {
                userID = Integer.parseInt(request.getParameter("userID"));
            } catch (NumberFormatException ex) {
                log("UserID is in wrong format.");
            }

            UserDTO user = udao.load(userID);

            int tournamentID = Integer.parseInt(request.getParameter("tournamentID"));
            TournamentDTO tournament = tdao.load(tournamentID);
            if (tournament != null) {
                LocalDateTime startTime = tournament.getStartTime().toLocalDateTime();
                LocalDateTime endTime = tournament.getEndTime().toLocalDateTime();
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMM");
                String date = dateFormat.format(startTime) + " - " + dateFormat.format(endTime) + ", " + startTime.getYear();
                //Handle Logo File Name
                String logoFileName = processImages(tournament.getTournamentName());
                request.setAttribute("logoFileName", logoFileName);
                request.setAttribute("date", date);
                request.setAttribute("tournament", tournament);
                request.setAttribute("userP", user);
                request.getRequestDispatcher("transaction.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "The tournament has no more slots available or does not exist.");
                response.sendRedirect("tournamentdetails.jsp");
            }
        } else if (action.equals("user-match")) {
            request.getRequestDispatcher("user-history-tournament.jsp").forward(request, response);
        }

    }

    public String processImages(String tournamentName) {
        String logoFileName = tournamentName.trim().toLowerCase().replace(" ", "-");
        return "assets/images/transaction/" + logoFileName + "-logo.jpg";
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
