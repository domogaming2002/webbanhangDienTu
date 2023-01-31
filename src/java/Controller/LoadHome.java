/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAL.DAO_Home;
import Models.Cart;
import Models.Category;
import Models.PageInfor;
import Models.Product;
import Models.ProductInCart;
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
@WebServlet(name = "LoadHome", urlPatterns = {"/LoadHome", "/Home"})
public class LoadHome extends HttpServlet {

    DAO_Home dao;

    public void init() {
        dao = new DAO_Home();
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
            out.println("<title>Servlet LoadHome</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoadHome at " + request.getContextPath() + "</h1>");
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
        //
        int[] arr1 = {3, 9, 21, 27};
        ArrayList<Product> prdList;
        HttpSession ses = request.getSession();
        boolean reload = true;
        if (ses.getAttribute("reload") != null) {
            reload = (boolean) ses.getAttribute("reload");
        }
        if (reload) {
            dao.LoadProduct();
            dao.loadCategory();
            ses.setAttribute("reload", false);
        }
        request.setAttribute("DAO_Home", dao);
        String categoryId = request.getParameter("categoryId");
        prdList = dao.getPrdList();

        //Cart size
        if (ses.getAttribute("user") != null) {
            User us = (User) ses.getAttribute("user");
            Cookie[] arr = request.getCookies();
            String txt = "";
            if (arr != null) {
                for (Cookie o : arr) {
                    if (o.getName().equals("cart"+us.getUserId())) {
                        txt += o.getValue();
                    }
                }
            }
            Cart cart = new Cart(txt, prdList);
            List<ProductInCart> listItem = cart.getItems();
            int n;
            if (listItem != null) {
                n = listItem.size();
            } else {
                n = 0;
            }
            request.setAttribute("size", n);
        }

        //
        if (request.getParameter("categoryId") != null) {
            prdList = getProductByCategory(categoryId);
        }

        String search = request.getParameter("search");
        if (request.getParameter("search") != null) {
            prdList = getProductByName(search);
        }
        request.setAttribute("productList", prdList);

        int size = dao.getPrdList().size();
        int cp = 0;
        int nrpp = 1;

        if (ses.getAttribute("nrpp") != null) {
            nrpp = (int) ses.getAttribute("nrpp");
        }

        PageInfor page = new PageInfor(cp, nrpp, size);
        page.calc();
        page.setArrNrpp(arr1);
        request.setAttribute("CP", page);
        request.getRequestDispatcher("View/Member/Home.jsp").forward(request, response);
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
        HttpSession ses = request.getSession();
        ArrayList<Product> prdList;
        boolean reload = true;
        if (ses.getAttribute("reload") != null) {
            reload = (boolean) ses.getAttribute("reload");
        }
        int np = Integer.parseInt(request.getParameter("np"));
        int cp = Integer.parseInt(request.getParameter("cp"));
        int nrpp = Integer.parseInt(request.getParameter("nrpp"));
        ses.setAttribute("nrpp", nrpp);
        if (reload) {
            dao.LoadProduct();
            dao.loadCategory();
            dao.loadManufacturer();
            ses.setAttribute("reload", false);
        }
        if (request.getParameter("home") != null) {
            cp = 0;// click nut home
        }
        if (request.getParameter("pre") != null) {
            cp = cp - 1;// click nut pre
        }
        if (request.getParameter("next") != null) {
            cp = cp + 1; //click nut next
        }
        if (request.getParameter("end") != null) {
            cp = np - 1;// click nut end
        }
        for (int i = 0; i < np; i++) {
            if (request.getParameter("btn" + i) != null) {
                cp = i; // click nut i 
            }
        }
        prdList = dao.getPrdList();

        request.setAttribute("productList", prdList);
        PageInfor page = new PageInfor(cp, nrpp, prdList.size());
        page.calc();
        request.setAttribute("DAO_Home", dao);
        request.setAttribute("CP", page);
        request.getRequestDispatcher("View/Member/Home.jsp").forward(request, response);
//        doGet(request, response);

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

    public ArrayList<Product> getProductByCategory(String categoryId) {
        ArrayList<Product> productList = new ArrayList<>();
        for (Product prd : dao.getPrdList()) {
            if (prd.getCategoryId().equals(categoryId)) {
                productList.add(prd);
            }
        }
        return productList;
    }

    public ArrayList<Product> getProductByName(String name) {
        name = name.replaceAll("\\s+", " ");
        name = name.toUpperCase().trim();
        ArrayList<Product> productList = new ArrayList<>();
        for (Product prd : dao.getPrdList()) {
            if (prd.getName().toUpperCase().contains(name)) {
                productList.add(prd);
            }
        }
        return productList;
    }
}
