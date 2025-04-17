/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strikethefrog.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import strikethefrog.User.UserDAO;
import strikethefrog.User.UserDTO;

/**
 *
 * @author ty307
 */
@WebServlet(name = "SignupController", urlPatterns = {"/SignupController"})
public class SignupController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        System.out.println("SignupController called with action: " + action);

        String fullname = request.getParameter("fullname");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phonenumber = request.getParameter("phonenumber");

        System.out.println("Received signup request with:");
        System.out.println("Fullname: " + fullname);
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phonenumber);

        if (action == null || action.equals("") || action.equals("signup")) {
            UserDAO dao = new UserDAO();

            int c = 0;
            if (dao.isUsernameTaken(username)) {
                System.out.println("Username is taken: " + username);
                request.setAttribute("usernameError", "This username - " + username + " - already exists.");
                c++;
            }
            if (dao.isEmailTaken(email)) {
                System.out.println("Email is taken: " + email);
                request.setAttribute("emailError", "This email - " + email + " - is already used.");
                c++;
            }
            if (dao.isPhoneNumberTaken(phonenumber)) {
                System.out.println("Phone number is taken: " + phonenumber);
                request.setAttribute("phoneError", "This phone number - " + phonenumber + " - is already used.");
                c++;
            }

            if (c > 0) {
                RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");
                rd.forward(request, response);
            } else {
                UserDTO user = dao.signup(fullname, username, password, email, phonenumber);
                if (user != null) {
                    request.setAttribute("message", "Account created. Please log in.");
                    RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                    rd.forward(request, response);
                } else {
                    request.setAttribute("error", "Failed to create new account.");
                    RequestDispatcher rd = request.getRequestDispatcher("signup.jsp");
                    rd.forward(request, response);
                }

            }
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
