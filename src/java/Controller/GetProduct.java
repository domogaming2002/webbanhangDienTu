/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAL.DAO_Home;
import DAL.DAO_Order;
import Models.Cart;
import Models.Order;
import Models.OrderDetail;
import Models.Product;
import Models.ProductInCart;
import Models.ProductReview;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "GetProduct", urlPatterns = {"/GetProduct"})
public class GetProduct extends HttpServlet {

    DAO_Home dao;
    DAO_Order dao_Order;

    public void init() {
        dao = new DAO_Home();
        dao_Order = new DAO_Order();
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
            out.println("<title>Servlet GetProduct</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetProduct at " + request.getContextPath() + "</h1>");
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
        dao.LoadProduct();
        dao_Order.LoadOrder();
        dao_Order.LoadOrderDetail();
        dao.loadReview();
        String productid = request.getParameter("productid");
        request.setAttribute("userInfor", dao.getUsList());
        Product p = getProduct(productid);
        request.setAttribute("product", p);

        //Tinh sao va cmt
        int countComment = countComment(productid);
        double counStar = countStar(productid);
        request.setAttribute("countComment", countComment);
        request.setAttribute("countStar", counStar);
        //
        
        //Cart
        List<Product> list = dao.getPrdList();
        Cookie[] arr = request.getCookies();
        String txt = "";
        if (arr != null) {
            for (Cookie o : arr) {
                if (o.getName().equals("cart")) {
                    txt += o.getValue();
                }
            }
        }
        Cart cart = new Cart(txt, list);
        List<ProductInCart> listItem = cart.getItems();
        int n;
        if (listItem != null) {
            n = listItem.size();
        } else {
            n = 0;
        }
        request.setAttribute("size", n);
        //Cart

        //Check Comment
        HttpSession ses = request.getSession();
        if (ses.getAttribute("user") != null) {
            User us = (User) ses.getAttribute("user");
            boolean checkComment = checkComment1(us.getUserId(), productid);
            request.setAttribute("checkComment", checkComment);
        }

        //
        //Load Comment
        ArrayList<ProductReview> rvList = loadComment(productid);
        request.setAttribute("allreview", rvList);
        //
        request.getRequestDispatcher("View/Member/ProductContent.jsp").forward(request, response);
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
        int star = Integer.parseInt(request.getParameter("star"));
        String comment = request.getParameter("comment");
        String productId = request.getParameter("productId");
        HttpSession ses = request.getSession();
        User us = (User) ses.getAttribute("user");
        dao.insertReview(us.getUserId(), productId, star, comment);
        doGet(request, response);
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

    public Product getProduct(String productId) {
        for (Product p : dao.getPrdList()) {
            if (p.getProductId().equals(productId)) {
                return p;
            }
        }
        return null;
    }
    

    public boolean checkComment1(String UserId, String productId) {
        for (ProductReview rv : dao.getRvList()) {
            if (rv.getUserId().equals(UserId) && rv.getProductId().equals(productId)) {
                return false;
            }
        }
        for (Order ord : dao_Order.getOrdList()) {
            if (ord.getUserId().equals(UserId) && ord.getStatusId() == 4) {
                for (OrderDetail ordDetail : dao_Order.getOrdDetaiList()) {
                    if (ordDetail.getOrderId().equals(ord.getOrderId()) && ordDetail.getProductId().equals(productId)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public ArrayList<ProductReview> loadComment(String productId) {
        ArrayList<ProductReview> rvList = new ArrayList<>();
        for (ProductReview rv : dao.getRvList()) {
            if (rv.getProductId().equals(productId)) {
                rvList.add(rv);
            }
        }
        return rvList;
    }
    
    public int countComment(String productId){
        int count = 0;
        for (ProductReview rv : dao.getRvList()) {
            if (rv.getProductId().equals(productId)) {
                count++;
            }
        }
        return count;
    }
    
    public double countStar(String productId){
        int count = 0;
        double star = 0;
        for (ProductReview rv : dao.getRvList()) {
            if (rv.getProductId().equals(productId)) {
                star = star+ rv.getStar();
                count++;
            }
        }
        if(star == 0){
            return 0;
        }
        star = star / count;
        return star;
    }
}
