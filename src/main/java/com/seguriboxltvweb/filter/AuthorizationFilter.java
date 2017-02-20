package com.seguriboxltvweb.filter;

import com.seguriboxltvweb.jsf.util.JsfUtil;
import java.io.IOException;
import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.jsf"})
public class AuthorizationFilter implements Filter {

    public AuthorizationFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain){
        try {
            HttpServletRequest reqt = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession ses = reqt.getSession(false);

            String reqURI = reqt.getRequestURI();
            if (reqURI.contains("/login.jsf")
                    || reqURI.contains("/logout.jsf")
                    || reqURI.contains("/login_2.jsf")
                    || (ses != null && ses.getAttribute("username") != null)
                    || reqURI.contains("/public/")
                    || reqURI.contains("javax.faces.resource")) {
                
                // Agregado por miguel 11/3/2016
                boolean resourceRequest = reqt.getRequestURI().startsWith(reqt.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER + "/");
                if (!resourceRequest) { // Prevent browser from caching restricted resources
                    resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
                    resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
                    resp.setDateHeader("Expires", 0); // Proxies.
                }
                //Agregado por miguel 11/3/2016
                chain.doFilter(request, response);
            } else {
                resp.sendRedirect(reqt.getContextPath() + "/login.jsf");
            }
        } catch (IOException | ServletException e) {
            
        }
    }

    @Override
    public void destroy() {

    }
}
