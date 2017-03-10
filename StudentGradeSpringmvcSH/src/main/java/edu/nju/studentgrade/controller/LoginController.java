package edu.nju.studentgrade.controller;

import edu.nju.studentgrade.business.ScoreListBean;
import edu.nju.studentgrade.model.Score;
import edu.nju.studentgrade.service.GradeManageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyy on 2017/3/9.
 */
@Controller
@RequestMapping("")
public class LoginController {
    @Resource
    private GradeManageService gradeManageService;

    @RequestMapping(value="/Grade")
    public String getGrade(@RequestParam("studentId") String studentId,
                           @RequestParam("password") String password, HttpServletRequest request){
        HttpSession session = request.getSession(true);

        if(null != session.getAttribute("studentId")){//用户已登录，未注销
            studentId = (String)session.getAttribute("studentId");
            password = (String)session.getAttribute("password");
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

            return "showGrade";
        }


    }

    @RequestMapping("/logout")
    public String logout(@RequestParam("logout") String logout,HttpServletRequest request){

        if (null != logout) {
            HttpSession session = request.getSession(false);
            session.invalidate();
            session = null;
            return "login";
        }
        return null;
    }

    @RequestMapping("/json")
    @ResponseBody
    public List<Score> getString(){
        List<Score> scoreList = gradeManageService.getStudentGrade("1001","1001");
        return scoreList;
    }

}
