package com.sq.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sq.model.vo.SqUserTab;

/**
 *
 *@autor whai
 */
public class BirtFilter implements Filter{
	 /** *//**
     * 容器，封装birt相关功能的uri和所对应Servlet名的键值对
     */
    Map<String, String> map = new HashMap<String, String>();

    /** *//**
     * Context.
     */
    ServletContext context;

    /** *//**
     * debug开关
     */
    static boolean debug = false;

    /** *//**
     */
    public void destroy() {
        map = null;
    }

    /** *//**
     * 过滤birt请求，转发到对应的servlet，以绕过其他过滤器，e.g. struts
     *
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain fc) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
       HttpSession session =  req.getSession();
       SqUserTab sqUserTab = (SqUserTab)session.getAttribute("sqUserTab");
       
        String uri = req.getRequestURI();
        if (debug) {
            System.out.println(">>>Requesting " + uri + "?"
                    + req.getQueryString());
        }
        if(!uri.equals("/sq_project/system/loginout.shtml")){
	        if(!(uri.equals("/sq_project/") || uri.equals("/sq_project/prof_login.shtml") || uri.startsWith("/sq_project/css")||
	        		uri.startsWith("/sq_project/images")||uri.startsWith("/sq_project/js")||uri.equals("/sq_project/system/login.shtml") ||
	        		uri.equals("/sq_project/index.jsp"))
	        	&& sqUserTab == null){
	     	   res.sendRedirect("index.jsp");
	        }
        }
        
        Set<String> keys = map.keySet();

        for (String key : keys) {
            /**//*
             * TODO:这里的判断只是简单地调用contains方法，这样就带来较多限制。
             * 比如工程子目录的命名、struts命名空间等都受到birtViewer的约束。待改进。
             */
            if (uri.contains(key)) {
                RequestDispatcher rd = this.context.getNamedDispatcher(map
                        .get(key));
                if (rd != null) {
                    if (debug) {
                        System.out.println(">>>Redirect successfully executed");
                    }
                    // 跳过其他过滤器，跳转到对应的servlet
                    rd.forward(request, response);
                } else {
                    if (debug) {
                        System.out
                                .println(">>>Redirect unsuccessfully executed");
                    }
                }
                return;
            }
        }
        // 将请求交给下一个过滤器
        fc.doFilter(request, response);
    }

    /** *//**
     * @description
     * @author Shoru
     * @date 2009-8-21
     * @version 1.0.0
     * @param fc
     * @throws ServletException
     */
    public void init(FilterConfig fc) throws ServletException {

        this.context = fc.getServletContext();
        /**//*
         * 这里注意，在项目目录的命名时，不要取和birt内置的一些servlet名重复。 请根据项目的web.xml自行配置。
         * （包括frameset、run、preview、download、parameter、document、output）
         */
        map.put("frameset", "ViewerServlet");
    	map.put("preview", "EngineServlet");
    	map.put("download", "EngineServlet");
    	map.put("document", "EngineServlet");
    	map.put("output", "EngineServlet");
    	map.put("extract", "EngineServlet");

    }

}
