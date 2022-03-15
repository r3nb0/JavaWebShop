package controllers;

import bl.IManage;
import bl.Manager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.UserModel;
import services.IpService;

@WebServlet(name = "Admin", urlPatterns = {"/admin"})
public class Admin extends HttpServlet {

    private IManage manager;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("admin/admin.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.manager = new Manager(IpService.getClientIpAddr(request));
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");
        if (user != null && user.isLogged() && user.hasRole("admin")) {
            session.setAttribute("categories", manager.getCategoriesForAdmin());
            session.setAttribute("products", manager.getProductsForAdmin());
            session.setAttribute("orders", manager.getInvoices(true));
            session.setAttribute("accounts", manager.getAccounts());
            session.setAttribute("gateways", manager.getPaymentGateways(true));
            session.setAttribute("logs", manager.getLogs());
            processRequest(request, response);
        } else {
            session.removeAttribute("categories");
            session.removeAttribute("products");
            session.removeAttribute("orders");
            session.removeAttribute("accounts");
            session.removeAttribute("gateways");
            request.setAttribute("errorMessage", "You cannot pretend to be an admin.");
            response.sendRedirect("loginError.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
