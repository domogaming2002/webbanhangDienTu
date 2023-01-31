/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAL.DAO_LoadManu_Cate;
import Models.*;
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
@WebServlet(name = "LoadManu_Cate", urlPatterns = {"/LoadManu_Cate"})
public class LoadManu_Cate extends HttpServlet {

    DAO_LoadManu_Cate dao;

    public void init() {
        dao = new DAO_LoadManu_Cate();
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
            out.println("<title>Servlet LoadManu_Cate</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoadManu_Cate at " + request.getContextPath() + "</h1>");
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
        Object obj = request.getAttribute("deleteManu");
        if (obj != null) {
            String manuid = (obj + "").trim();
            if (manuid.length() != 0) {
                dao.delManu(manuid);
            }
        }
        Object obj2 = request.getAttribute("deleteCate");
        if (obj2 != null) {
            String cateId = (obj2 + "").trim();
            if (cateId.length() != 0) {
                dao.delCate(cateId);
            }
        }

        dao.loadCategory();
        dao.loadManufacturer();
        request.setAttribute("DAO_LoadManu_Cate", dao);
        request.getRequestDispatcher("View/Admin/AddManu_Cate.jsp").forward(request, response);
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
        request.setCharacterEncoding("UTF-8");
        String manufacturerId = request.getParameter("manufacturerId");
        if (manufacturerId.length() != 0) {
            String name = request.getParameter("manuName");
            String address = request.getParameter("address");
            boolean update = false;
            for (Manufacturer mn : dao.getManuList()) {
                if (mn.getManufacturerId().equals(manufacturerId)) {
                    update = true;
                    break;
                }
            }
            if (update) {
                dao.updateManu(manufacturerId, name, address);
            } else if (manufacturerId.trim().length() != 0) {
                dao.insertManu(manufacturerId, name, address);
            }
            dao.insertManu(manufacturerId, name, address);
        }
        
        String categoryId = request.getParameter("categoryId");
        if (categoryId.length() != 0) {
            String name = request.getParameter("cateName");
            String content = request.getParameter("content");
            boolean update = false;
            for (Category mn : dao.getCateList()) {
                if (mn.getCategoryId().equals(categoryId)) {
                    update = true;
                    break;
                }
            }
            if (update) {
                dao.updateCate(categoryId, name, content);
            } else if (categoryId.trim().length() != 0) {
                dao.insertCate(categoryId, name, content);
            }
            
            dao.insertCate(categoryId, name, content);
        }
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

}
