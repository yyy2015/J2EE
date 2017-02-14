package edu.nju.student.grade.servlet;

import edu.nju.student.grade.model.Score;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by yyy on 2016/12/7.
 */

@WebServlet("/Grade")
public class GradeServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private DataSource dataSource = null;

    public GradeServlet(){
        super();
    }

    public void init(){
        InitialContext jndiContext = null;

        Properties properties = new Properties();
        properties.put(Context.PROVIDER_URL,"jnp:///");
        properties.put(Context.INITIAL_CONTEXT_FACTORY,"org.apache.naming.java.javaURLContextFactory");
        try{
            jndiContext = new InitialContext(properties);
            dataSource = (DataSource) jndiContext.lookup("java:comp/env/jdbc/studentGrade");
            System.out.println("got context");
            System.out.println("About to get student grade info...");
        }catch(NamingException e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
//        request.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html;charset=UTF-8");

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
        if(null != session.getAttribute("studentId")){//未注销
            studentId = (String)session.getAttribute("studentId");
            password = (String)session.getAttribute("password");
        }else{
            studentId = request.getParameter("studentId");
            password = request.getParameter("password");
        }

        PrintWriter out = response.getWriter();

        UserConfirm isConfirmed = confirmUser(studentId,password,request,response);

        System.out.println("user confirm: " + isConfirmed.toString());

        if(isConfirmed == UserConfirm.ID_WRONG){
            out.println("学号不存在");
        }
        if(isConfirmed == UserConfirm.PASSWORD_WRONG){
            out.println("学号或者密码错误");
        }
        if(isConfirmed == UserConfirm.CONFIRMED){
            displayGrade(request, response);
            displayLogout(request, response);
        }

    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        doGet(request,response);
    }

    //验证学生身份并在身份验证成功后获得对应的成绩
    public UserConfirm confirmUser(String studentId,String password,HttpServletRequest request,HttpServletResponse response){

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        ArrayList list = new ArrayList();

        try{
            connection = dataSource.getConnection();
        }catch(SQLException e){
            e.printStackTrace();
        }

        try{
            //获得数据库中所有学生的学号和密码
            statement = connection.prepareStatement("select * from student");
            result = statement.executeQuery();

            while(result.next()){

                if(studentId.equals(result.getString("studentId")) ){

                    if(password.equals(result.getString("password")) ){//学号与密码均正确
                        //设置session中的学号和学生姓名
                        HttpSession session = request.getSession();
                        session.setAttribute("studentId",studentId);
                        session.setAttribute("studentName",result.getString("name"));
                        session.setAttribute("password",result.getString("password"));

//                        //获取对应学号学生的全部成绩信息
//                        ResultSet gradeResult = null;
//                        PreparedStatement gradeStatement = connection.prepareStatement("select * from score where studentId=? ");
//                        gradeStatement.setString(1,studentId);
//                        gradeResult = gradeStatement.executeQuery();

                        //获得对应学号学生的全部课程
                        ResultSet courseListResult = null;
                        PreparedStatement courseListStat = connection.prepareStatement("select DISTINCT courseId from score where studentId=?");
                        courseListStat.setString(1,studentId);
                        courseListResult = courseListStat.executeQuery();

                        while(courseListResult.next()){
                            ResultSet scoreResult = null;
                            String courseId = courseListResult.getString("courseId");
                            PreparedStatement scoreStat = connection.prepareStatement("select * from score where studentId = ? and courseId = ? order by period ASC ");
                            scoreStat.setString(1,studentId);
                            scoreStat.setString(2,courseId);
                            scoreResult = scoreStat.executeQuery();
                            //获得每门课程每次测试的分数，并保存在scoreList中
                            ArrayList<Double> scoreList = new ArrayList<Double>();
                            while(scoreResult.next()){
                                //该学生有考试未参加
                                if(null==scoreResult.getString("score")){
                                    System.out.println("score string: null");
                                    scoreList.add(-1.0);
                                }else {
                                    scoreList.add(scoreResult.getDouble("score"));
                                }
                            }

                            Score score = new Score();
                            score.setCourseId(courseId);
                            score.setScoreList(scoreList);

                            ResultSet courseResult = null;
                            PreparedStatement courseStatement = connection.prepareStatement("select * from course where courseId=?");
                            courseStatement.setString(1,courseId);
                            courseResult = courseStatement.executeQuery();

                            //设置分数对应的课程名
                            while(courseResult.next()){
                                score.setCourseName(courseResult.getString("name"));
                            }

                            list.add(score);


                        }
                        request.setAttribute("list",list);


                        return UserConfirm.CONFIRMED;

                    }else{//学号存在，密码错误
                        return UserConfirm.PASSWORD_WRONG;
                    }
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }


        //学号不存在
        return UserConfirm.ID_WRONG;
    }

    public void displayGrade(HttpServletRequest request,HttpServletResponse response) throws IOException{
        ArrayList list = (ArrayList) request.getAttribute("list");
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html lang='en'> <head> <meta charset='UTF-8'> <title>score</title> </head> <body><h2>欢迎您,"
        + session.getAttribute("studentId")+"</h2>\n" +
                "<h4>课程号：课程名：各次测验得分</h4>");
        for(int i=0;i<list.size();i++){
            Score score = (Score) list.get(i);
            out.println("<span>"+score.getCourseId()+"&nbsp&nbsp&nbsp&nbsp"+score.getCourseName());
            ArrayList<Double> scoreList = score.getScoreList();
            for(double thisScore:scoreList){
                if(thisScore<0){
                    out.print("&nbsp&nbsp&nbsp&nbsp未参加");
                }else {
                    out.print("&nbsp&nbsp&nbsp&nbsp" + thisScore);
                }
            }
            out.print("</span><br>");
        }
        out.println("<br><a href='online.jsp'>查看在线用户</a><br>");
        out.println("<br>");

        // 点击here，刷新该页面，会话有效
//        System.out.println(request.getRequestURI());
        out.println("Click <a href='" + response.encodeURL(request.getRequestURI()) + "'>here</a> to reload this page.<br>");
    }

    public void displayLogout(HttpServletRequest request,HttpServletResponse response) throws IOException{
        PrintWriter out = response.getWriter();
        // 注销Logout
        out.println("<form method='GET' action='" + response.encodeURL(request.getContextPath() + "/Grade") + "'>");
        out.println("</p>");
        out.println("<input type='submit' name='logout' value='logout'>");
        out.println("</form>");
        out.println("</body></html>");
    }


}
