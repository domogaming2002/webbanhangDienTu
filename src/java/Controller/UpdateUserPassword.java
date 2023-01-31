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
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "UpdateUserPassword", urlPatterns = {"/UpdateUserPassword"})
public class UpdateUserPassword extends HttpServlet {

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
            out.println("<title>Servlet UpdateUserPassword</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateUserPassword at " + request.getContextPath() + "</h1>");
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
//        processRequest(request, response);dao.LoadUser();
        dao.loadQuestion();
        request.setAttribute("DAO_Login", dao);
        request.getRequestDispatcher("View/Member/UpdatePassword.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        User us = (User) session.getAttribute("user");
        String answer = request.getParameter("answer");
        String oldpassword = request.getParameter("oldpassword");
        String newpassword = request.getParameter("newpassword");
        String renewpassword = request.getParameter("renewpassword");
        String check = "false";
        if (request.getParameter("update") != null) {
            check = request.getParameter("update");
        }
        if (answer != null) {
            if (!answer.equals(us.getAnswer())) {
                request.setAttribute("msg", "Câu trả lời sai");
                check = "false";
            } else if (!newpassword.equals(renewpassword) || !newpassword.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$")) {
                request.setAttribute("msg", "New password và re-enter new password không giống nhau. Mật khẩu cần có từ 8-20 kí tự, có ít nhất 1 chữ cái thường, 1 chữ cái in hoa, 1 số");
                check = "false";
            } else if (!oldpassword.equals(us.getPass())) {
                request.setAttribute("msg", "Old password incorrect");
                check = "false";
            }
        }
        if (check.equals("true")) {
            request.setAttribute("msg", "Update succesfully");
            dao.updateUserPass(us.getUserId(), newpassword);
        }
        request.getRequestDispatcher("View/Member/UpdatePassword.jsp").forward(request, response);

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
