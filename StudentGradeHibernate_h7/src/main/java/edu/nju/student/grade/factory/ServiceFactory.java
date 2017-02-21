package edu.nju.student.grade.factory;

import edu.nju.student.grade.service.GradeManageService;
import edu.nju.student.grade.service.impl.GradeManageImpl;

/**
 * Created by yyy on 2017/1/2.
 */
public class ServiceFactory {
    public static GradeManageService getGradeManageService(){
        return GradeManageImpl.getInstance();
    }
}
