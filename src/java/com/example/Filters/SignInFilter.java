package com.example.Filters;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebFilter;

@WebFilter("/*")   // Filter all URL
public class SignInFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("X-XSS-Protection", "1; mode=block");
        response.setHeader("X-Content-Type-Options", "nosniff");

        String path = request.getRequestURI();

        //Check for Suspecious pattern
        String query = request.getQueryString();
        if (query != null && query.matches(".*([';]+|--).*")) {
            throw new ServletException("Possible Injection Detected!");
        }

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // Set Do Dont save the Page details
        response.setHeader("Pragma", "no-cache"); // For old Systems
        response.setDateHeader("Expires", 0); // me Set this to Expires

        HttpSession session = request.getSession(false);
//|| path.contains("home")
        if (path.contains("Error")) {
            chain.doFilter(req, res);
            return;
        }

        if ((path.endsWith("signin") || path.endsWith("signup") || path.contains("Error") || path.contains("test") )) {
            if(!(session == null || session.getAttribute("User") == null)){
                response.sendRedirect("home");
                return;
            }
            chain.doFilter(req, res);
            return;
        }

        if (session == null || session.getAttribute("User") == null) {
            response.sendRedirect("signin");
            return;
        }

        chain.doFilter(req, res);
    }
}
