package controllers;

import bl.IManage;
import bl.Manager;
import entities.PaymentGateway;
import entities.Product;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.CartItem;
import models.CartModel;
import services.IpService;

@WebServlet(name = "AddToCart", urlPatterns = {"/cart"})
public class Cart extends HttpServlet {

    private IManage manager;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        manager = new Manager(IpService.getClientIpAddr(request));
        HttpSession session = request.getSession();
        session.setAttribute("gateways", manager.getPaymentGateways(false));
        response.sendRedirect("cart.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        manager = new Manager(IpService.getClientIpAddr(request));
        HttpSession session = request.getSession();
        CartModel cart = (CartModel) session.getAttribute("cart");
        if (cart == null) {
            session.setAttribute("cart", new CartModel());
        } else {
            session.setAttribute("cart", cart);
        }
        session.setAttribute("gateways", manager.getPaymentGateways(false));
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        manager = new Manager(IpService.getClientIpAddr(request));
        HttpSession session = request.getSession(false);
        CartModel cart = (CartModel) session.getAttribute("cart");
        int productId = Integer.parseInt(request.getParameter("productId").toString());
        int amount = Integer.parseInt(request.getParameter("amount").toString());
        String action = request.getParameter("action").toString().toLowerCase();

        Product product = manager.getProduct(productId);
        CartItem item = new CartItem(productId, amount, product);

        if (cart == null) {
            cart = new CartModel();
        }
        switch (action) {
            case "add":
                cart.add(item);
                break;
            case "remove":
                cart.remove(item);
                break;
            case "apply":
                cart.changeItemAmount(item);
            case "clear":
                session.removeAttribute("cart");
                break;
            default:
                break;
        }
        if (!action.equals("clear")) {
            session.setAttribute("cart", cart);
        }
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
