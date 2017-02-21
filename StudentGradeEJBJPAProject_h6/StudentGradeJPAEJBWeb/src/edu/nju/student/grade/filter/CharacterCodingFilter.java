package edu.nju.student.grade.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by yyy on 2016/12/20.
 */

@WebFilter("/*")
public class CharacterCodingFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        System.out.println("in filter"+response.getCharacterEncoding());
        chain.doFilter(request,response);
    }

    public void destroy() {

    }
}
