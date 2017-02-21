package edu.nju.student.grade.factory;

import edu.nju.student.grade.dao.StudentGradeDao;
import edu.nju.student.grade.dao.StudentGradeDaoImpl;

/**
 * Created by yyy on 2017/1/2.
 */
public class DaoFactory {
    public static StudentGradeDao getStudentGradeDao(){
        return StudentGradeDaoImpl.getInstance();
    }


}
