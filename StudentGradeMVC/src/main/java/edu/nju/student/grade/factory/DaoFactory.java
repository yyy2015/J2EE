package edu.nju.student.grade.factory;

import edu.nju.student.grade.dao.StudentGradeDao;
import edu.nju.student.grade.dao.impl.StudentGradeDaoImpl;
import edu.nju.student.grade.service.GradeManageService;
import edu.nju.student.grade.service.impl.GradeManageImpl;

/**
 * Created by yyy on 2017/1/2.
 */
public class DaoFactory {
    public static StudentGradeDao getStudentGradeDao(){
        return StudentGradeDaoImpl.getInstance();
    }


}
