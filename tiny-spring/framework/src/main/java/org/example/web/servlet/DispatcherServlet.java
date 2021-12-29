package org.example.web.servlet;



import org.example.web.handler.HandlerMgr;
import org.example.web.handler.MappingHandler;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class DispatcherServlet implements Servlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        System.out.println(request.getMethod()+" "+request.getRequestURI());
        for (MappingHandler mappingHandler : HandlerMgr.mappingHandlerList){
            try {
                if (mappingHandler.handle(servletRequest,servletResponse)){
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        servletResponse.getWriter().write("<h1>404</h1>");

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
