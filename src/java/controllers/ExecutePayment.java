package controllers;

import bl.IManage;
import bl.Manager;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import entities.Invoice;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.OrderDetailModel;
import services.IpService;
import services.MailingService;
import services.PayPalPaymentService;

@WebServlet(name = "ExecutePayment", urlPatterns = {"/order/execute"})
public class ExecutePayment extends HttpServlet {

    private IManage manager;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("./receipt.jsp");
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
        OrderDetailModel orderModel = (OrderDetailModel) request.getAttribute("order");
        try {
            String paymentId = request.getParameter("paymentId");
            String payerId = request.getParameter("payerId");

            PayPalPaymentService paymentService = new PayPalPaymentService(IpService.getBaseUrl(request));

            Payment payment = paymentService.executePayment(paymentId, payerId);
            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().get(0);

            Invoice invoice = manager.completePaymentForInvoice(transaction.getInvoiceNumber(), transaction.getRelatedResources().get(0).getAuthorization().getId());
            manager.log("Order successful! " + invoice.getAccountId().getEmail() + "[" + invoice.getId() + "]");
            request.setAttribute("payer", payerInfo);
            request.setAttribute("transaction", transaction);
            request.getSession().removeAttribute("cart");

            new MailingService().sendOrderSuccess(invoice.getId().toString(), payerInfo.getEmail());
            new MailingService().sendAdminOrderSuccess(invoice.getId().toString(), "boren.dujnic@gmail.com");
            processRequest(request, response);
        } catch (PayPalRESTException ex) {
            manager.log("Order FAILED! " + ex.getMessage());
            request.getSession().setAttribute("errorMessage", "There was a problem executing your payment!");
            response.sendRedirect("../cart");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
