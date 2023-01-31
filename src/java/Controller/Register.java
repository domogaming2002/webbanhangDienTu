/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAL.DAO_Login;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "Register", urlPatterns = {"/Register"})
public class Register extends HttpServlet {

    DAO_Login dao;

    public void init() {
        dao = new DAO_Login();
    }

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
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Register</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Register at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
//        processRequest(request, response);
        dao.loadQuestion();
        request.setAttribute("DAO_Login", dao);
        request.getRequestDispatcher("View/Member/Register.jsp").forward(request, response);
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
//        processRequest(request, response);
        dao.LoadUser();
        dao.loadQuestion();
        request.setAttribute("DAO_Login", dao);
        String userId = request.getParameter("userId");
        String pass = request.getParameter("pass");
        String repass = request.getParameter("repass");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String date = request.getParameter("dateofbirth");
        String gmail = request.getParameter("gmail");
        String qId = request.getParameter("qid");
        String answer = request.getParameter("answer");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        if (!pass.equals(repass)) {
            request.setAttribute("mess", "Pass need equal repass");
            request.getRequestDispatcher("View/Member/Register.jsp").forward(request, response);
        } else {
            User us = checkUserAndEmail(userId, gmail);
            if (us == null) {
                if (!userId.matches("^[a-zA-Z0-9](_(?!(\\.|_))|\\.(?!(_|\\.))|[a-zA-Z0-9]){6,18}[a-zA-Z0-9]$")) {
                    request.setAttribute("mess", "UserId có từ 6-18 kí tự và bắt đầu với chữ cái ");
                    request.getRequestDispatcher("View/Member/Register.jsp").forward(request, response);
                } else if (!pass.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$")) {
                    request.setAttribute("mess", "Password cần có từ 8-20 kí tự, có ít nhất 1 chữ cái thường, 1 chữ cái in hoa, 1 số");
                    request.getRequestDispatcher("View/Member/Register.jsp").forward(request, response);
                } else if (!phone.matches("(84|0[3|5|7|8|9])+([0-9]{8})\\b")) {
                    request.setAttribute("mess", "Số điện thoại cần có 9 số và đầu là 03,05,07,08,09,84");
                    request.getRequestDispatcher("View/Member/Register.jsp").forward(request, response);
                } else if (!gmail.matches("[\\w+]+[\\@]+[\\w+]+(\\.\\w+)+$")) {
                    request.setAttribute("mess", "Email sai dạng");
                    request.getRequestDispatcher("View/Member/Register.jsp").forward(request, response);
                } else {
                    dao.insertUser(userId, pass, firstName, lastName, phone, gmail, 1, false, qId, answer, date, address);
                    dao.LoadUser();
                    String token = SendEmail.RandomToken();
                    dao.insertVerirfyToken(userId, token);
                    request.getSession().setAttribute("userVerify", getUser(userId));
                    try {
                        SendEmail.sendEmail(gmail, token);
                    } catch (Exception e) {
                    }
                    response.sendRedirect("VerifyServlet");
                }
            } else {
                request.setAttribute("mess", "This userId have been used");
                doGet(request, response);
            }
        }
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

    public User getUser(String userId) {
        for (User us : dao.getUsList()) {
            if (us.getUserId().equals(userId)) {
                return us;
            }
        }
        return null;
    }
    
    public User checkUserAndEmail(String userId, String email) {
        for (User us : dao.getUsList()) {
            if (us.getUserId().equals(userId) && us.getEmail().equals(email)) {
                return us;
            }
        }
        return null;
    }


}
