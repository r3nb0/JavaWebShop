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
import entities.Product;
import java.math.BigDecimal;
import models.UserModel;
import services.IpService;

@WebServlet(name = "ProductServlet", urlPatterns = {"/product"})
public class ProductServlet extends HttpServlet {

    private IManage manager;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("product.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        manager = new Manager(IpService.getClientIpAddr(request));
        String productId = request.getParameter("id");
        int id = Integer.valueOf(productId);
        session.setAttribute("product", manager.getProduct(id));
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        manager = new Manager(IpService.getClientIpAddr(request));
        HttpSession session = request.getSession();
        UserModel user = (UserModel) session.getAttribute("user");

        try {
            if (user != null && user.isLogged() && user.hasRole("Admin")) {
                String action = request.getParameter("action");
                Product pro;
                String prodId, name, description, givenPrice, image, categoryId;
                int id, category;
                BigDecimal price;
                switch (action.toLowerCase()) {
                    case "add":

                        name = request.getParameter("name");
                        description = request.getParameter("description");
                        givenPrice = request.getParameter("price");
                        image = request.getParameter("image");
                        categoryId = request.getParameter("category");
                        category = Integer.parseInt(String.valueOf(categoryId));
                        givenPrice = givenPrice.trim();
                        if (givenPrice.contains(",")) {
                            givenPrice.replace(',', '.');
                        }
                        price = new BigDecimal(givenPrice);

                        pro = new Product();
                        price = new BigDecimal(givenPrice);
                        pro.setId(1);
                        pro.setName(name);
                        pro.setDescription(description);
                        pro.setPrice(price);
                        pro.setImage(image);
                        pro.setProductCategoryId(manager.getCategory(Integer.valueOf(category)));
                        int result = manager.insertProduct(pro);
                        if (result == 0) {
                            throw new Exception("Product can not be added! Please check all the fields and try again.");
                        } else {
                            session.setAttribute("infoMessage", "Product added sucessfully!");
                        }
                        break;
                    case "edit":
                        prodId = request.getParameter("id");
                        name = request.getParameter("name");
                        description = request.getParameter("description");
                        givenPrice = request.getParameter("price");
                        image = request.getParameter("image");
                        categoryId = request.getParameter("category");
                        category = Integer.parseInt(String.valueOf(categoryId));
                        givenPrice = givenPrice.trim();
                        if (givenPrice.contains(",")) {
                            givenPrice.replace(',', '.');
                        }
                        price = new BigDecimal(givenPrice);

                        id = Integer.parseInt(prodId);
                        pro = manager.getProduct(id);
                        //
                        if (pro != null) {
                            pro.setName(name);
                            pro.setDescription(description);
                            pro.setPrice(price);
                            pro.setImage(image);
                            pro.setProductCategoryId(manager.getCategory(category));
                            Product dummy = manager.editProduct(pro);
                            if (dummy == null) {
                                throw new Exception("Product can not be edited! Please check all the fields and try again.");
                            } else {
                                session.setAttribute("infoMessage", "Product edited sucessfully!");
                            }
                        }
                        break;
                    case "remove":
                        prodId = request.getParameter("id");
                        id = Integer.parseInt(prodId);
                        boolean success = manager.removeProduct(id);
                        if (!success) {
                            throw new Exception("This product can't be removed!");
                        }
                        break;
                    default:
                        throw new Exception("Failed to understand what are you trying to do. Hmm..");
                }
                response.sendRedirect("admin");
            } else {
                session.setAttribute("errorMessage", "You are not authorized to do this action!");
                response.sendRedirect("login");
            }
        } catch (Exception e) {
            session.setAttribute("errorMessage", e.getMessage());
            response.sendRedirect("./admin/admin.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
