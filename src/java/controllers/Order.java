package controllers;

import bl.IManage;
import bl.Manager;
import com.paypal.base.rest.PayPalRESTException;
import entities.Invoice;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.CartModel;
import models.OrderDetailModel;
import models.UserModel;
import services.IpService;
import services.PayPalPaymentService;

@WebServlet(name = "Order", urlPatterns = {"/order"})
public class Order extends HttpServlet {

    private IManage manager;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("cart");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        manager = new Manager(IpService.getClientIpAddr(request));
        HttpSession session = request.getSession(true);
        CartModel cart = (CartModel) session.getAttribute("cart");
        UserModel user = (UserModel) session.getAttribute("user");
        if (cart == null) {
            session.setAttribute("errorMessage", "Cart cannot be empty! Please put something in your cart to place an order.");
            response.sendRedirect("cart.jsp");
        } else if (user == null && !user.isLogged()) {
            session.setAttribute("errorMessage", "You must be logged in to place an order! Please first login and then try to place an order.");
            response.sendRedirect("cart.jsp");
        } else {
            String paymentOption = request.getParameter("payment").toString();
            String orderMessage = request.getParameter("message").toString();
            Invoice invoice = manager.createInvoice(cart, user, orderMessage, paymentOption);

            String redirectionLink = "";
            OrderDetailModel orderModel = new OrderDetailModel();
            switch (paymentOption.toLowerCase()) {
                case "paypal":
                    orderModel = new OrderDetailModel(invoice);
                    try {
                        int id = manager.insertInvoice(invoice);
                        redirectionLink = new PayPalPaymentService(IpService.getBaseUrl(request))
                                .authorizePayment(orderModel, invoice.getAccountId(), id);

                        session.setAttribute("invoice", invoice);
                        session.setAttribute("order", orderModel);
                        manager.log(user.getEmail() + " placed an order. STATE = PENDING");
                    } catch (PayPalRESTException ex) {
                        manager.log( user.getEmail() + " " + ex.getLocalizedMessage());
                        request.setAttribute("errorMessage", "PayPal error. Please inform admin via <a href='mailto:boren.dujnic@gmail.com' >EMAIL</a>!");
                    redirectionLink = IpService.getBaseUrl(request) + "/cart";
                    }
                    break;
                case "pay on delivery":
                    int invoiceId = manager.insertInvoice(invoice);
                    invoice = manager.getInvoice(invoiceId);
                    orderModel = new OrderDetailModel(invoice);

                    session.setAttribute("invoice", invoice);
                    session.setAttribute("order", orderModel);

                    response.addCookie(new Cookie("userEmail", user.getEmail()));
                    redirectionLink = IpService.getBaseUrl(request) + "/order/success?invoiceId=" + invoiceId;
                    break;
                default:
                    request.setAttribute("error",
                            "Please confirm everything is ok and then order products.");
                    redirectionLink = IpService.getBaseUrl(request) + "/cart";
                    break;
            }
            response.sendRedirect(redirectionLink);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
