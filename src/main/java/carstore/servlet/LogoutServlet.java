package carstore.servlet;

import carstore.constants.Attributes;
import carstore.constants.WebApp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Invalidates current logged user session.
 *
 * @author Aleksei Sapozhnikov (vermucht@gmail.com)
 * @version 0.1
 * @since 0.1
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    /**
     * Logger.
     */
    @SuppressWarnings("unused")
    private static final Logger LOG = LogManager.getLogger(LogoutServlet.class);

    /**
     * Invalidates current active session.
     *
     * @param req  Request object.
     * @param resp Response object.
     * @throws IOException In case of problems.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession(false).invalidate();
        var resultMsg = "Successfully logged out";
        var codedMsg = URLEncoder.encode(resultMsg, StandardCharsets.UTF_8);
        var redirectPath = new StringBuilder()
                .append((String) req.getServletContext().getAttribute(Attributes.ATR_CONTEXT_PATH.v()))
                .append("?")
                .append(WebApp.MSG_SUCCESS.v()).append("=").append(codedMsg)
                .toString();
        resp.sendRedirect(redirectPath);
    }
}
