package edu.nju.studentgrade.action;

import edu.nju.studentgrade.business.ScoreListBean;
import edu.nju.studentgrade.service.GradeManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by yyy on 2017/3/7.
 */
@Controller
public class LoginAction extends BaseAction {
    private static final long serialVersionUID = 1L;
    @Autowired
    private GradeManageService gradeManageService;

    public String execute() throws ServletException,IOException{

        String studentId="";
        String password="";

        if(request == null){
            System.out.println("request is null !!!");
        }
        HttpSession session = request.getSession(true);
//        System.out.println("studentId:" +session.getAttribute("studentId"));
        if(null != session.getAttribute("studentId")){//用户已登录，未注销
            studentId = (String)session.getAttribute("studentId");
            password = (String)session.getAttribute("password");
        }else{//用户未登录
            studentId = request.getParameter("studentId");
            password = request.getParameter("password");
        }

        ScoreListBean scoreList = new ScoreListBean();

        System.out.println(studentId+" "+password);

        if(!gradeManageService.isStudentExist(studentId)){
            return "idWrong";
        }else if(!gradeManageService.isPasswordCorrect(studentId,password)){
            return "idOrPwdWrong";
        }else {
            session.setAttribute("studentId", studentId);
            scoreList.setScoreList(gradeManageService.getStudentGrade(studentId, password));
            session.setAttribute("scoreList", scoreList);

            return "success";
        }

    }

    public String logout()throws ServletException,IOException {
        //用户注销
        String logout = request.getParameter("logout");
        if (null != logout) {
            HttpSession session = request.getSession(false);
            session.invalidate();
            session = null;
            return "logout";
        }
        return null;
    }
}
