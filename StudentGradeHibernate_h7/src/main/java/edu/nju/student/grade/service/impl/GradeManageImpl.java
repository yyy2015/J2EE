package edu.nju.student.grade.service.impl;

import edu.nju.student.grade.factory.DaoFactory;
import edu.nju.student.grade.model.Score;
import edu.nju.student.grade.service.GradeManageService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyy on 2017/1/2.
 */
public class GradeManageImpl implements GradeManageService {
    private static GradeManageImpl gradeManageService = new GradeManageImpl();

    public static GradeManageImpl getInstance(){
        return gradeManageService;
    }

    public void forwardPage(String page, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(response.encodeURL(page));
        dispatcher.forward(request,response);
    }

    public List getStudentGrade(String studentId, String password) {

        ArrayList<Score> scoreList = new ArrayList<Score>();

        if(isStudentExist(studentId) && isPasswordCorrect(studentId, password)){
            ArrayList<String> courseList =
                    (ArrayList<String>) DaoFactory.getStudentGradeDao().findCourseList(studentId);
            for(String courseId : courseList){
                ArrayList<Double> markList =
                        (ArrayList<Double>)DaoFactory.getStudentGradeDao().findScore(studentId,courseId);
                String courseName = DaoFactory.getStudentGradeDao().findCourseName(courseId);

                Score score = new Score();
                score.setCourseId(courseId);
                score.setCourseName(courseName);
                score.setScoreList(markList);

                scoreList.add(score);
            }

            return scoreList;
        }
        return null;
    }

    public boolean isStudentExist(String studentId){
        return DaoFactory.getStudentGradeDao().findUser(studentId);
    }

    public boolean isPasswordCorrect(String studentId, String password){
        return DaoFactory.getStudentGradeDao().confirmUser(studentId,password);
    }
}
