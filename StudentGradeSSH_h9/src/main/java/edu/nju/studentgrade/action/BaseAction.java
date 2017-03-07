package edu.nju.studentgrade.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by yyy on 2017/3/7.
 */
public class BaseAction extends ActionSupport implements
        SessionAware,ServletRequestAware,ServletResponseAware{

    private static final long serialVersionUID = 1L;

    public HttpServletRequest request;
    public HttpServletResponse response;

    @SuppressWarnings("unchecked")
    public Map session;

    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.request = httpServletRequest;
    }

    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.response = httpServletResponse;
    }

    public void setSession(Map session) {
        this.session = session;
    }
}
