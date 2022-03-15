package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.UserModel;

/**
 *
 * @author r3nb0
 */
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String url = req.getRequestURI();
        UserModel user = (UserModel) req.getSession().getAttribute("user");
        HttpSession session = req.getSession(false);

        String loginURI = req.getContextPath() + "/login.jsp";

        boolean loggedIn = user != null || user.isLogged();
        boolean loginRequest = req.getRequestURI().equals(loginURI);

        if (loggedIn || loginRequest) {
            chain.doFilter(request, response);
        } else {
            res.sendRedirect(loginURI);
        }
//        //if user is not logged and tries to access account, admin of logout - redirect to login
//        //elae proceed
//        if (user == null || !user.isLogged()) {
//            if (url.indexOf("account/account.jsp") >= 0
//                    || url.indexOf("admin/admin.jsp") >= 0
//                    || url.indexOf("logout.jsp") >= 0) {
//                res.sendRedirect(req.getServletContext().getRealPath("")+ "/login.jsp");
//            } else {
//                chain.doFilter(request, response);
//            }
//        } 
//        //if user is logged and tries to access admin and role is admin, proceed
//        //if user is logged and tried to access account, proceed
//        else {
//            if (url.indexOf("admin/admin.jsp") >= 0) {
//                if (user.hasRole("Admin")) {
//                    res.sendRedirect(req.getServletContext().getRealPath("") + "/admin/admin.jsp");
//                } else {
//                res.sendRedirect(req.getServletContext().getRealPath("") + "/account/account.jsp");
//                }
//            } else if (url.indexOf("account/account.jsp") >= 0) {
//                res.sendRedirect(req.getServletContext().getRealPath("") + "/account/account.jsp");
//            } else {
//                chain.doFilter(request, response);
//            }
//        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
