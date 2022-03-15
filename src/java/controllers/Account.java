package controllers;

import bl.IManage;
import bl.Manager;
import entities.Invoice;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.UserModel;
import services.IpService;

@WebServlet(name = "Account", urlPatterns = {"/account/account"})
public class Account extends HttpServlet {

    private IManage manager;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("account/account.jsp");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        manager = new Manager(IpService.getClientIpAddr(request));
        
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");
        if (user != null && user.isLogged()) {
            Collection<Invoice> invoices = manager.getInvoicesForUser(user.getId());            
            request.setAttribute("invoices", invoices);
            response.sendRedirect("../account/account.jsp");
        } else {
            response.sendRedirect("../login");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        manager = new Manager(IpService.getClientIpAddr(request));
        processRequest(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
