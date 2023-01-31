/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAL.DAO_Home;
import DAL.DAO_Login;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ManageUser", urlPatterns = {"/ManageUser"})
public class ManageUser extends HttpServlet {

    DAO_Login dao;
    DAO_Home daoHome;

    public void init() {
        dao = new DAO_Login();
        daoHome = new DAO_Home();
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
            out.println("<title>Servlet ManageUser</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageUser at " + request.getContextPath() + "</h1>");
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
        dao.LoadUser();
        dao.loadQuestion();
        daoHome.loadThread();
        ArrayList<User> memList = getMemberList();
        request.setAttribute("member", memList);
        request.setAttribute("DAO_Login", dao);
        request.setAttribute("thread", daoHome.getThreadList());
        request.setAttribute("questHm", dao.getQuestHm());
        request.getRequestDispatcher("View/Admin/ManageMember.jsp").forward(request, response);
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
        String userId = request.getParameter("userid");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String qid = request.getParameter("qid");
        String answer = request.getParameter("answer");
        String dob = request.getParameter("dob");
        String address = request.getParameter("address");
        if (!phone.matches("(84|0[3|5|7|8|9])+([0-9]{8})\\b")) {
            request.setAttribute("mess", "Số điện thoại cần có 9 số và đầu là 03,05,07,08,09,84");
            doGet(request, response);
        } else if (!email.matches("[\\w+]+[\\@]+[\\w+]+(\\.\\w+)+$")) {
            request.setAttribute("mess", "Email sai dạng");
            doGet(request, response);
        } else {
            dao.UpdateUserAdmin(userId, firstname, lastname, phone, email, qid, answer, dob, address);
            doGet(request, response);
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

    public ArrayList<User> getMemberList() {
        ArrayList<User> usList = new ArrayList<>();
        for (User user : dao.getUsList()) {
            if (user.getAdmin() == 1) {
                usList.add(user);
            }
        }
        return usList;
    }
}
