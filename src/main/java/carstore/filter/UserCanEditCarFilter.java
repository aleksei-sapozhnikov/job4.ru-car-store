//package carstore.filter;
//
//import carstore.store.NewCarStore;
//import carstore.store.NewUserStore;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.hibernate.Session;
//import util.Utils;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * Checks logged user and his ability to edit given car.
// *
// * @author Aleksei Sapozhnikov (vermucht@gmail.com)
// * @version 0.1
// * @since 0.1
// */
//@WebFilter(filterName = "UserCanEditCarFilter")
//public class UserCanEditCarFilter implements Filter {
//    /**
//     * Logger.
//     */
//    @SuppressWarnings("unused")
//    private static final Logger LOG = LogManager.getLogger(UserCanEditCarFilter.class);
//
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        var context = filterConfig.getServletContext();
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        var carStore = new NewCarStore((Session) request.getAttribute("hibernateSession"));
//        var userStore = new NewUserStore((Session) request.getAttribute("hibernateSession"));
//        var req = (HttpServletRequest) request;
//        var resp = (HttpServletResponse) response;
//        var loggedUserId = (long) req.getSession(false).getAttribute("loggedUserId");
//        var carStoreId = Utils.parseLong(req.getParameter("storeId"), -1);
//        if (carStoreId == -1) {
//            throw new ServletException("Id parameter not found");
//        }
//        var user = userStore.get(loggedUserId);
//        var canEdit = user.getCars().stream().anyMatch(car -> car.getId() == carStoreId);
//        if (canEdit) {
//            chain.doFilter(req, resp);
//        } else {
//            var msg = "You are not allowed to edit this car";
//            resp.sendRedirect(String.format("%s?error=%s", req.getContextPath(), msg));
//        }
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
