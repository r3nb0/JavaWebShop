package controllers;

import bl.IManage;
import bl.Manager;
import entities.Product;
import entities.ProductCategory;
import java.io.IOException;
import java.util.Collection;
import services.IpService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.ProductFilter;
import services.FilterService;
import services.FormService;

/**
 *
 * @author r3nb0
 */
@WebServlet(name = "Products", urlPatterns = {"/products"})
public class Products extends HttpServlet {

    private IManage manager;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("products.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        manager = new Manager(IpService.getClientIpAddr(request));

        HttpSession session = request.getSession();
        session.setAttribute("products", manager.getProducts());
        session.setAttribute("categories", manager.getCategories());
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        manager = new Manager(IpService.getClientIpAddr(request));
        HttpSession session = request.getSession();
        Collection<Product> unfiltered = manager.getProducts();
        Collection<ProductCategory> categories = manager.getCategories();

        session.setAttribute("categories", categories);

        //actions - apply & reset filter
        if (request.getParameter("action").toLowerCase().equals("apply")) {
            ProductFilter filter = FormService.getProductFilterFromParamMap(request);
            Collection<Product> filtered = FilterService.filterProducts(unfiltered, filter);
            session.setAttribute("products", filtered);
        } else {
            session.setAttribute("products", unfiltered);
        }
        
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
