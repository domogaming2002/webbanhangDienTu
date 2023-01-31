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
@WebServlet(name = "UpdateUserDetail", urlPatterns = {"/UpdateUserDetail"})
public class UpdateUserDetail extends HttpServlet {

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
            out.println("<title>Servlet UpdateUserDetail</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateUserDetail at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("View/Member/UserDetail.jsp").forward(request, response);
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
        HttpSession session = request.getSession();
        User us = (User) session.getAttribute("user");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String dob = request.getParameter("dateofbirth");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String check = request.getParameter("update");
        if (!phone.matches("(84|0[3|5|7|8|9])+([0-9]{8})\\b")) {
            request.setAttribute("mess", "Số điện thoại cần có 9 số và đầu là 03,05,07,08,09,84");
            check = "false";
        } else if (!email.matches("[\\w+]+[\\@]+[\\w+]+(\\.\\w+)+$")) {
            request.setAttribute("mess", "Email không hợp lệ");
            check = "false";
        }
        if (check.equals("true")) {
            dao.updateUserDetail(firstname, lastname, dob, phone, address, email, us.getUserId());
            dao.LoadUser();
            session.removeAttribute("user");
            session.setAttribute("user", getUser(us.getUserId()));
            request.setAttribute("mess", "Update thành công");
        }
        request.getRequestDispatcher("View/Member/UserDetail.jsp").forward(request, response);

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

}
