package edu.nju.student.grade.service;

import edu.nju.student.grade.dao.StudentGradeDao;
import edu.nju.student.grade.model.Score;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyy on 2017/2/14.
 */
@Stateless(name = "GradeManageServiceEJB")
public class GradeManageServiceBean implements GradeManageService {

    @EJB(beanName="StudentGradeDaoEJB")
    StudentGradeDao studentGradeDao;

    public GradeManageServiceBean() {
    }


    @Override
    public List getStudentGrade(String studentId, String password) {
        ArrayList<Score> scoreList = new ArrayList<Score>();

        if(isStudentExit(studentId)&&isPasswordCorrect(studentId,password)){
            ArrayList<String> courseList =
                    (ArrayList<String>)studentGradeDao.findCourseList(studentId);
            for(String courseId: courseList){
                ArrayList<Double> markList =
                        (ArrayList<Double>)studentGradeDao.findScore(studentId,courseId);
                String courseName = studentGradeDao.findCourseName(courseId);
                Score score = new Score(courseId,courseName,markList);
                scoreList.add(score);
            }

            return scoreList;
        }

        return null;
    }

    @Override
    public boolean isStudentExit(String studentId) {
        return studentGradeDao.findUser(studentId);
    }

    @Override
    public boolean isPasswordCorrect(String studentId, String password) {
        return studentGradeDao.confirmUser(studentId, password);
    }
}
