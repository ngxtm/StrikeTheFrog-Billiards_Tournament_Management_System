/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package strikethefrog.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import strikethefrog.Admin.AdminDAO;
import strikethefrog.Admin.AdminDTO;
import strikethefrog.User.UserDAO;
import strikethefrog.User.UserDTO;

/**
 *
 * @author ASUS
 */
public class LoginController extends HttpServlet {

    private static final String LOGIN_PAGE = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = LOGIN_PAGE;
        try {
            String action = request.getParameter("action");

            String username = request.getParameter("user");
            String password = request.getParameter("password");

            if (action == null || action.equals("") || action.equals("login")) {
                UserDAO dao = new UserDAO();
                UserDTO user = dao.login(username, password);

                AdminDAO adao = new AdminDAO();
                AdminDTO admin = adao.login(username, password);

                if (admin != null) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("adminsession", admin);
                    url = "Dashboard?action=home";
                } else if (user != null) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("usersession", user);
                    url = "TournamentController?action=list";

                } else {
                    request.setAttribute("error", "Username or password is incorrect");
                }
            } else if (action.equals("logout")) {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
                request.setAttribute("error", "logout successfully");

            }
        } catch (Exception e) {
            log("Error at MainController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
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
