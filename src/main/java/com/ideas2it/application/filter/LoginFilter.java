package com.ideas2it.application.filter;

import java.io.IOException;

import com.ideas2it.application.logger.ApplicationLogger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

 /**
  * @author Latheeshwar Raj
  */
public class LoginFilter implements Filter {

    private ServletContext context;

    public void init(final FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        HttpSession session = req.getSession(false);
        boolean newTabCheck = (req.getRequestURI().equals("/PEC/index.jsp") || req.getRequestURI().equals("/PEC/"));
        String path= req.getRequestURI();
        if(path.endsWith(".css")){
          chain.doFilter(request,response);
          return;
        }
        if(null == session) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else if(null != session && null == session.getAttribute("status") && req.getRequestURI().equals("/PEC/UserManagement")) {
            chain.doFilter(request, response);
        } else if(null != session && null != session.getAttribute("status") && newTabCheck) {
            request.getRequestDispatcher("menu.jsp").forward(request, response);
        } else if(null != session && null == session.getAttribute("status") && req.getRequestURI() != "/PEC/UserManagement") {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {

    }
}
