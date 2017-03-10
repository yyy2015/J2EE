package edu.nju.studentgrade.service;

import edu.nju.studentgrade.dao.StudentGradeDao;
import edu.nju.studentgrade.model.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class GradeManageServiceImpl implements GradeManageService {

    @Autowired
    private StudentGradeDao studentGradeDao;

    public void forwardPage(String page, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(response.encodeURL(page));
        dispatcher.forward(request,response);
    }

    public List getStudentGrade(String studentId, String password) {

        ArrayList<Score> scoreList = new ArrayList<Score>();

        if(isStudentExist(studentId) && isPasswordCorrect(studentId, password)){
            ArrayList<String> courseList =
                    (ArrayList<String>) studentGradeDao.findCourseList(studentId);
            for(String courseId : courseList){
                ArrayList<Double> markList =
                        (ArrayList<Double>)studentGradeDao.findScore(studentId,courseId);
                String courseName = studentGradeDao.findCourseName(courseId);

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
        return studentGradeDao.findUser(studentId);
    }

    public boolean isPasswordCorrect(String studentId, String password){
        return studentGradeDao.confirmUser(studentId,password);
    }
}
