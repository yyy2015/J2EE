package edu.nju.student.grade.tag;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * Created by yyy on 2017/1/3.
 */
public class CheckSessionHandler extends BodyTagSupport {
    @Override
    public int doEndTag() throws JspException {
        PageContext pageContext =this.pageContext;
        HttpSession session = pageContext.getSession();
        if(null == session.getAttribute("studentId")){
            HttpServletResponse response= (HttpServletResponse) pageContext.getResponse();
            try {
                response.sendRedirect("/StudentGrade/login.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this.SKIP_PAGE;
        }else{
            return this.EVAL_PAGE;
        }
    }
}
