/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import bl.Manager;
import entities.ProductCategory;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.IpService;

/**
 *
 * @author r3nb0
 */
@WebServlet(name = "Categories", urlPatterns = {"/categories"})
public class Categories extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("categories.jsp");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Collection<ProductCategory> categories = new Manager(IpService.getClientIpAddr(request)).getCategories();
        session.setAttribute("categories", categories);
        processRequest(request, response);
            }
        
            /**
             * Handles the HTTP <code>POST</code> method.
             *
             * @param request servlet request
             * @param response servlet response
             * @throws ServletException if a servlet-specific error occurs
             * @throws IOException if an I/O error occurs
             */
            @Override
            protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String action = session.getAttribute("action").toString();
        Manager manager = new Manager(IpService.getClientIpAddr(request));
        ProductCategory category = new ProductCategory();
        int result = 0;
        switch (action.toLowerCase()) {
            case "insert":
                category.setDescription(request.getAttribute("description").toString());
                category.setName(request.getAttribute("name").toString());
                category.setRemoved("NO");
                result = manager.insertCategory(category);
                break;
            case "edit":
                category.setId(Integer.parseInt(request.getAttribute("id").toString()));
                category.setDescription(request.getAttribute("description").toString());
                category.setName(request.getAttribute("name").toString());
                category.setRemoved(request.getAttribute("removed").toString());
                result = manager.editCategory(category).getId();
                break;
            case "remove":
                int id = Integer.parseInt(request.getAttribute("id").toString());
                result = manager.removeCategory(id) ? 1 : 0;
                break;
            default:
                result = 0;
                break;
        }
        if (result == 0 || result == -1) {
            request.getSession(false).setAttribute("message", "Action was not finished successfully!");
        }
        processRequest(request, response);
            }
        
            /**
             * Returns a short description of the servlet.
             *
             * @return a String containing servlet description
             */
            @Override
            public String getServletInfo() {
        return "Short description";
            }
        // </editor-fold>

}
