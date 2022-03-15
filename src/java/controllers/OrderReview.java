package controllers;

import bl.IManage;
import bl.Manager;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import entities.Invoice;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.IpService;
import services.PayPalPaymentService;

@WebServlet(name = "OrderReview", urlPatterns = {"/order/review"})
public class OrderReview extends HttpServlet {

    private IManage manager;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("review.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        manager = new Manager(IpService.getClientIpAddr(request));
        String gateway = "";
        gateway = request.getParameter("gateway");
        if (gateway != null && gateway.equalsIgnoreCase("pay on delivery")) {
            String orderId = request.getParameter("orderId");
            manager.log(orderId);

        } else {
            String paymentId = request.getParameter("paymentId");
            String payerId = request.getParameter("PayerID");
            try {
                PayPalPaymentService paymentService = new PayPalPaymentService(IpService.getBaseUrl(request));
                Payment payment = paymentService.getPaymentDetails(paymentId);
                PayerInfo payerInfo = payment.getPayer().getPayerInfo();
                Transaction transaction = payment.getTransactions().get(0);
                ShippingAddress shippingAddress = transaction.getItemList().getShippingAddress();


                request.getSession().setAttribute("payer", payerInfo);
                request.getSession().setAttribute("transaction", transaction);
                request.getSession().setAttribute("shippingAddress", shippingAddress);

                System.out.println("");
                String url = "review.jsp?paymentId=" + paymentId + "&payerId=" + payerId;
                response.sendRedirect(url);

            } catch (PayPalRESTException e) {
                request.getSession().setAttribute("errorMessage", "Could not get payment details.");
                response.sendRedirect("error.jsp");
            }
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
    }
}
