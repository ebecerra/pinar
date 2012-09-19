package com.befasoft.common.servlet;

import com.befasoft.common.tools.HibernateUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Permite cerrar la session de hibernate cuando se termina la peticion http
 */
public class HibernateCloseFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        // Nothing
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        long time = System.currentTimeMillis();
        boolean isAction = ((HttpServletRequest) request).getRequestURI().indexOf(".action") >= 0;
        try {
            if (isAction) {
                System.out.println("--- OPEN REQUEST --- " + ((HttpServletRequest) request).getRequestURI() + "?" + ((HttpServletRequest) request).getQueryString());
            }

            filterChain.doFilter(request, response);
            double transcurredTime = ((System.currentTimeMillis() - time) / 1000.0);
            if (isAction) {
                System.out.println("--- CLOSE REQUEST --- " + transcurredTime + " segs :" + ((HttpServletRequest) request).getRequestURI() + "?" + ((HttpServletRequest) request).getQueryString());
            }

        }
        finally {
            HibernateUtil.closeSession();
        }
    }

    public void destroy() {
        // Nothing
    }
}
