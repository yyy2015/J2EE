package edu.nju.student.grade.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;

@WebListener
public class CounterListener implements ServletContextListener,HttpSessionAttributeListener,HttpSessionListener {

    private ServletContext application =null;
    private int total;

    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("context init....");

        //初始化总人数
        total = 0;
        //初始化一个application对象
        application = sce.getServletContext();
        //设置一个列表属性，用于保存在线用户名
        this.application.setAttribute("online",new ArrayList<String>());

        this.application.setAttribute("total",total);
    }

    //向会话中添加属性时的回调方法
    public void attributeAdded(HttpSessionBindingEvent event) {
        //取得用户列表
        ArrayList<String> online = (ArrayList<String>)this.application.getAttribute("online");
        if("studentId".equals(event.getName())){
            online.add((String)event.getValue());
        }
        //将添加后的列表重新设置到application属性中
        this.application.setAttribute("online",online);

    }

    //会话销毁时回调的方法
    public void sessionDestroyed(HttpSessionEvent se) {
        //取得用户列表
        ArrayList<String> online = (ArrayList<String>) this.application.getAttribute("online");
        //取得当前学号
        String studentId = (String) se.getSession().getAttribute("studentId");
        //将此用户从列表中删除
        online.remove(studentId);
        this.application.setAttribute("online",online);

        int totalNum = (Integer)this.application.getAttribute("total");
        totalNum-=1;
        this.application.setAttribute("total",totalNum);
    }

    //每当jsp接到一个新的访问,jsp自动为其创建session,利用这一点来统计总的在线人数
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("session created!"+ " " +se.getSession().getId());
        int totalNum = (Integer)this.application.getAttribute("total");
        totalNum+=1;
        this.application.setAttribute("total",totalNum);
    }


    public void contextDestroyed(ServletContextEvent sce) {

    }

    public void attributeRemoved(HttpSessionBindingEvent event) {

    }

    public void attributeReplaced(HttpSessionBindingEvent event) {

    }



}