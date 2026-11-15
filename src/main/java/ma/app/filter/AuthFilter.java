
package ma.app.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/dashboard", "/produits", "/produits/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {

            // Mémoriser l'URL demandée
            String requestedURL = req.getRequestURI()
                    .substring(req.getContextPath().length());

            String query = req.getQueryString();

            if (query != null) {
                requestedURL += "?" + query;
            }

            // Créer une session pour conserver l'URL
            req.getSession().setAttribute("returnTo", requestedURL);

            resp.sendRedirect(req.getContextPath() + "/login");

        } else {

            chain.doFilter(request, response);
        }
    }
}
