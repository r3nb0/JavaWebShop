package controllers;

import bl.IManage;
import bl.Manager;
import entities.ProductCategory;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.UserModel;
import services.IpService;

@WebServlet(name = "Category", urlPatterns = {"/category"})
public class Category extends HttpServlet {

    private IManage manager;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("admin");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        manager = new Manager(IpService.getClientIpAddr(request));
        UserModel user = (UserModel) session.getAttribute("user");
        try {
            if (user != null && user.isLogged()
                    && user.hasRole("Admin")) {
                String action = request.getParameter("action");
                String Id = request.getParameter("id");
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                int catId = Integer.parseInt(Id);
                ProductCategory cat;
                switch (action.toLowerCase()) {
                    case "add":
                        cat = new ProductCategory();
                        cat.setId(catId);
                        cat.setName(name);
                        cat.setDescription(description);
                        int result = manager.insertCategory(cat);
                        if (result == 0) {
                            throw new Exception("Category can not be added! Please check all the fields and try again.");
                        } else {
                            session.setAttribute("infoMessage", "Category added sucessfully!");
                        }
                        break;
                    case "edit":
                        cat = manager.getCategory(catId);
                        if (cat != null) {
                            cat.setName(name);
                            cat.setDescription(description);
                            ProductCategory dummy = manager.editCategory(cat);
                            if (dummy == null) {
                                throw new Exception("Category can not be edited! Please check all the fields and try again.");
                            } else {
                                session.setAttribute("infoMessage", "Category edited sucessfully!");
                            }
                        }
                        break;
                    case "remove":
                        boolean success = manager.removeCategory(catId);
                        if (!success) {
                            throw new Exception("This category can't be removed!");
                        } else {
                            session.setAttribute("infoMessage", "Category removed sucessfully!");
                        }
                        break;
                    default:
                        throw new Exception("Failed to understand what are you trying to do. Hmm..");
                }
                response.sendRedirect("admin");
            } else {
                session.setAttribute("errorMessage", "You are not authorized to do this action!");
                response.sendRedirect("error.jsp");
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
