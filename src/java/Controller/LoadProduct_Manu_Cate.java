/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAL.DAO_Product;
import Models.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "LoadProduct_Manu_Cate", urlPatterns = {"/LoadProduct_Manu_Cate"})
public class LoadProduct_Manu_Cate extends HttpServlet {

    DAO_Product dao;

    @Override
    public void init() {
        dao = new DAO_Product();
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
            out.println("<title>Servlet LoadProduct_Manu_Cate</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoadProduct_Manu_Cate at " + request.getContextPath() + "</h1>");
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
//        //
//        int[] arr = {1, 2, 3, 4, 5, 6};
//        HttpSession ses = request.getSession();
//        boolean reload = true;
//        if (ses.getAttribute("reload") != null) {
//            reload = (boolean) ses.getAttribute("reload");
//        }
//        if (reload) {
//            dao.LoadProduct();
//            dao.loadCategory();
//            dao.loadManufacturer();
//            ses.setAttribute("reload", false);
//        }
//        int size = dao.getPrdList().size();
//        int cp = 0;
//        int nrpp = 1;
//
//        if (ses.getAttribute("nrpp") != null) {
//            nrpp = (int) ses.getAttribute("nrpp");
//        }
//        PageInfor page = new PageInfor(cp, nrpp, size);
//        page.calc();
//        page.setArrNrpp(arr);
//        request.setAttribute("CP", page);
//
//        //
        Object obj = request.getAttribute("deleteProduct");
        if (obj != null) {
            String productId = (obj + "").trim();
            if (productId.length() != 0) {
                dao.del(productId);
            }
        }
        dao.LoadProduct();
        dao.loadCategory();
        dao.loadManufacturer();
        request.setAttribute("DAO_Product", dao);
        request.getRequestDispatcher("View/Admin/AddProduct.jsp").forward(request, response);
//        processRequest(request, response);
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
        request.setCharacterEncoding("UTF-8");

        //
//        HttpSession ses = request.getSession();
//        boolean reload = true;
//        if (ses.getAttribute("reload") != null) {
//            reload = (boolean) ses.getAttribute("reload");
//        }
//        int np = Integer.parseInt(request.getParameter("np"));
//        int cp = Integer.parseInt(request.getParameter("cp"));
//        int nrpp = 1;//update later
//        nrpp = Integer.parseInt(request.getParameter("nrpp"));
//        ses.setAttribute("nrpp", nrpp);
//        if (reload) {
//            dao.LoadProduct();
//            dao.loadCategory();
//            dao.loadManufacturer();
//            ses.setAttribute("reload", false);
//        }
//        if (request.getParameter("home") != null) {
//            cp = 0;// click nut home
//        }
//        if (request.getParameter("pre") != null) {
//            cp = cp - 1;// click nut pre
//        }
//        if (request.getParameter("next") != null) {
//            cp = cp + 1; //click nut next
//        }
//        if (request.getParameter("end") != null) {
//            cp = np - 1;// click nut end
//        }
//        for (int i = 0; i < np; i++) {
//            if (request.getParameter("btn" + i) != null) {
//                cp = i; // click nut i 
//            }
//        }
//        PageInfor page = new PageInfor(cp, nrpp, dao.getPrdList().size());
//        page.calc();
//        request.setAttribute("DAO_Product", dao);
//        request.setAttribute("CP", page);
//        request.getRequestDispatcher("View/Admin/AddProduct.jsp").forward(request, response);
        
        //

        String productId = request.getParameter("productid");
        String name = request.getParameter("name");
        String content = request.getParameter("content");
        float price = Float.parseFloat(request.getParameter("price"));
        int amount = Integer.parseInt(request.getParameter("amount"));
        float discount = Float.parseFloat(request.getParameter("discount"));
        String imgUrl ="img/" + request.getParameter("imgUrl");
        String manufacturerId = request.getParameter("manufacturerId");
        String categoryId = request.getParameter("categoryId");
        boolean update = false;
        for (Product prd : dao.getPrdList()) {
            if (prd.getProductId().equals(productId)) {
                update = true;
                break;
            }
        }
        if(productId.length()>50){
            request.setAttribute("mess", "ProductId Không được vượt quá 50 kí tự");
            doGet(request, response);
        }else if(name.length() > 50){
            request.setAttribute("mess", "Tên sản phẩm không được vượt quá 50 kí tự");
            doGet(request, response);
        }else if(content.length() > 500){
            request.setAttribute("mess", "Content được vượt quá 500 kí tự");
            doGet(request, response);
        }else if (update) {
            dao.Update(productId, name, content, price, amount, discount, imgUrl, manufacturerId, categoryId);
        } else if (productId.trim().length() != 0) {
            dao.Insert(productId, name, content, price, amount, discount, imgUrl, manufacturerId, categoryId);
        }
        dao.Insert(productId, name, content, price, amount, discount, imgUrl, manufacturerId, categoryId);
        doGet(request, response);
//        processRequest(request, response);
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
