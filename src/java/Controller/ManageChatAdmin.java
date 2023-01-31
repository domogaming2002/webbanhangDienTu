/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAL.DAO_Home;
import Models.Chat;
import Models.ThreadChat;
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
@WebServlet(name = "ManageChatAdmin", urlPatterns = {"/ManageChatAdmin"})
public class ManageChatAdmin extends HttpServlet {

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
            out.println("<title>Servlet ManageChatAdmin</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageChatAdmin at " + request.getContextPath() + "</h1>");
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
        dao.loadChat();
        dao.loadThread();
        HttpSession ses = request.getSession();
        int threadId = 0;
        User us = (User) ses.getAttribute("user");
        String userId_Member = request.getParameter("userId");
        if (!checkThread(userId_Member)) {
            dao.insertThread(userId_Member,true);
            dao.loadThread();
        }
        request.setAttribute("userID_Member", userId_Member);
        threadId = getThreadId(userId_Member);
        request.setAttribute("threadId", threadId);
        ArrayList<Chat> chList = getChatList(threadId);
        request.setAttribute("chatList", chList);
        request.getRequestDispatcher("View/Member/Chat.jsp").forward(request, response);
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
        dao.loadThread();
        int threadId = Integer.parseInt(request.getParameter("threadId"));
        HttpSession ses = request.getSession();
        User us = (User) ses.getAttribute("user");
        String userIdsend = us.getUserId();
        String chat = request.getParameter("chatMessage");
        
        String userIdto = request.getParameter("userID_Member");
        
        dao.insertChat(threadId, userIdsend, chat, userIdto);
        dao.updateThread(threadId, true);
        
        request.setAttribute("userId", userIdto);
        
        dao.loadChat();
        dao.loadThread();

        request.setAttribute("userID_Member", userIdto);
        threadId = getThreadId(userIdto);
        ArrayList<Chat> chList = getChatList(threadId);
        request.setAttribute("chatList", chList);
        request.getRequestDispatcher("View/Member/Chat.jsp").forward(request, response);;
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

    public boolean checkThread(String userID) {
        for (ThreadChat th : dao.getThreadList()) {
            if (th.getUserId().equals(userID)) {
                return true;
            }
        }
        return false;
    }

    public int getThreadId(String userID) {
        for (ThreadChat th : dao.getThreadList()) {
            if (th.getUserId().equals(userID)) {
                return th.getThreadId();
            }
        }
        return 0;
    }

    public ArrayList<Chat> getChatList(int threadId) {
        ArrayList<Chat> chatList = new ArrayList<>();
        for (Chat ch : dao.getChatList()) {
            if (ch.getThreadId() == threadId) {
                chatList.add(ch);
            }
        }
        return chatList;
    }
}
