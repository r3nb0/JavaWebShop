/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import bl.IManage;
import bl.Manager;
import dao.RepoFactory;
import entities.Account;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.UserModel;
import services.IpService;

/**
 *
 * @author r3nb0
 */
@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

    private IManage manager;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            HttpSession session = request.getSession();
        manager = new Manager(IpService.getClientIpAddr(request));
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Account account = manager.login(email, password);
        if (account != null) {
            UserModel user = new UserModel(account.getId(), account.getEmail(), account.getUsername(), account.getRole(), true);
            session.setAttribute("user", user);
            response.sendRedirect("account/account.jsp");
        } else {
            session.setAttribute("errorMessage", "Incorrect credentials! Try again with correct ones.");
            response.sendRedirect("loginError.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
