package edu.nju.studentgrade.servlet;

import edu.nju.studentgrade.business.ScoreListBean;
import edu.nju.studentgrade.service.GradeManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by yyy on 2017/1/2.
 */
@WebServlet("/Grade")
public class ShowGradeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static ApplicationContext applicationContext;

    private static GradeManageService gradeManageService;


    public void init() throws ServletException{
        super.init();
        applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
        gradeManageService = (GradeManageService)applicationContext.getBean("GradeManageService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        processRequest(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        //用户注销
        String logout = request.getParameter("logout");
        if(null != logout){
            HttpSession session = request.getSession(false);
            session.invalidate();
            session = null;
            response.sendRedirect("/login.html");
            return;
        }

        String studentId="";
        String password="";

        HttpSession session = request.getSession(true);
//        System.out.println("studentId:" +session.getAttribute("studentId"));
        if(null != session.getAttribute("studentId")){//用户已登录，未注销
            studentId = (String)session.getAttribute("studentId");
            password = (String)session.getAttribute("password");
        }else{//用户未登录
            studentId = request.getParameter("studentId");
            password = request.getParameter("password");
        }

        ServletContext context = getServletContext();

        ScoreListBean scoreList = new ScoreListBean();

        System.out.println(studentId+" "+password);
        if(!gradeManageService.isStudentExist(studentId)){
            context.getRequestDispatcher("/jsp/idWrong.jsp").forward(request,response);
        }else if(!gradeManageService.isPasswordCorrect(studentId,password)){
            context.getRequestDispatcher("/jsp/idOrPwdWrong.jsp").forward(request,response);
        }else {
                session.setAttribute("studentId", studentId);
                scoreList.setScoreList(gradeManageService.getStudentGrade(studentId, password));
                session.setAttribute("scoreList", scoreList);
                context.getRequestDispatcher("/jsp/showGrade.jsp").forward(request, response);
            }

    }



}
