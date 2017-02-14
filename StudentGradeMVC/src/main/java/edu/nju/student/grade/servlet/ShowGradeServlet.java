package edu.nju.student.grade.servlet;

import edu.nju.student.grade.business.ScoreListBean;
import edu.nju.student.grade.factory.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by yyy on 2017/1/2.
 */
@WebServlet("/Grade")
public class ShowGradeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ShowGradeServlet(){
        super();
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
            response.sendRedirect("/StudentGrade/login.html");
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
        PrintWriter out = response.getWriter();
        ServletContext context = getServletContext();

        ScoreListBean scoreList = new ScoreListBean();

        System.out.println(studentId+" "+password);
        if(!ServiceFactory.getGradeManageService().isStudentExist(studentId)){
            out.print("学号不存在<br><br>");
            out.print("<a href='login.html'>登录</a><br><br>");
        }else if(!ServiceFactory.getGradeManageService().isPasswordCorrect(studentId,password)){
            out.print("学号或者密码错误<br><br>");
            out.print("<a href='login.html'>登录</a><br><br>");
        }else {
                session.setAttribute("studentId", studentId);
                scoreList.setScoreList(ServiceFactory.getGradeManageService().getStudentGrade(studentId, password));
                session.setAttribute("scoreList", scoreList);
                context.getRequestDispatcher("/jsp/showGrade.jsp").forward(request, response);
            }

    }



}
