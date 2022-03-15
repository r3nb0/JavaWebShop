package controllers;

import bl.IManage;
import bl.Manager;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.IpService;
import services.MailingService;

@WebServlet(name = "OrderSuccess", urlPatterns = {"/order/success"})
public class OrderSuccess extends HttpServlet {

    private IManage manager;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("success.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        manager = new Manager(IpService.getClientIpAddr(request));
        String invoiceId = request.getParameter("invoiceId");
        if (invoiceId != null) {
            if (!invoiceId.isEmpty() && !invoiceId.trim().isEmpty()) {
                int id = Integer.valueOf(invoiceId);
                session.setAttribute("invoice", manager.getInvoice(id));
                session.removeAttribute("cart");
                String email = "test@test.com";
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equalsIgnoreCase("userEmail")) {
                        email = cookie.getValue();
                    }
                }
                
                new MailingService().sendOrderSuccess(invoiceId, email);
                new MailingService().sendAdminOrderSuccess(invoiceId, "boren.dujnic@gmail.com");

                processRequest(request, response);
            }
        } else {
            session.setAttribute("errorMessage", "Your cannot make order complete this way. You have to pay for it to get the items you want to buy. Nice try tho! =)");
            response.sendRedirect("cart.jsp");
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
