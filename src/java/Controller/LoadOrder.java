/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAL.DAO_Home;
import DAL.DAO_Order;
import Models.Order;
import Models.OrderDetail;
import Models.Product;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "LoadOrder", urlPatterns = {"/LoadOrder"})
public class LoadOrder extends HttpServlet {

    DAO_Order dao;

    public void init() {
        dao = new DAO_Order();
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
            out.println("<title>Servlet LoadOrder</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoadOrder at " + request.getContextPath() + "</h1>");
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
        dao.LoadOrder();
        dao.loadProduct();
        dao.loadOrderStatus();
        HttpSession session = request.getSession();
        User us =(User) session.getAttribute("user");
         ArrayList<Order> ord = dao.getOrdList();
        if(us.getAdmin()==1){
            ord = getOrderByUserId(us.getUserId());
        }
        request.setAttribute("DAO_Order", dao);
        request.setAttribute("Order", ord);
        request.getRequestDispatcher("View/Member/Order.jsp").forward(request, response);
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
        dao.LoadOrder();
        dao.LoadOrderDetail();
        dao.loadProduct();
        dao.loadOrderStatus();
        request.setAttribute("DAO_Order", dao);
        HttpSession session = request.getSession();
        User us =(User) session.getAttribute("user");
        String orderId = request.getParameter("orderId");
        String statusid = request.getParameter("statusid");
        request.setAttribute("statusid", statusid);
        request.setAttribute("orderId", orderId);
        ArrayList<OrderDetail> ordDetail = getOrderDetailByOrderId(orderId);
        request.setAttribute("OrderDetail", ordDetail);
        request.getRequestDispatcher("View/Member/OrderDetail.jsp").forward(request, response);
        
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

    public ArrayList<Order> getOrderByUserId(String userId) {
        ArrayList<Order> ordList = new ArrayList<>();
        for (Order ord : dao.getOrdList()) {
            if (ord.getUserId().equals(userId)) {
                ordList.add(ord);
            }
        }
        return ordList;
    }
    
    public ArrayList<OrderDetail> getOrderDetailByOrderId(String orderId) {
        ArrayList<OrderDetail> ordDetailList = new ArrayList<>();
        for (OrderDetail ord : dao.getOrdDetaiList()) {
            if (ord.getOrderId().equals(orderId)) {
                ordDetailList.add(ord);
            }
        }
        return ordDetailList;
    }
    
}
