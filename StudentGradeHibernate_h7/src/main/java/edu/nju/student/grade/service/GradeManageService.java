package edu.nju.student.grade.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by yyy on 2017/1/2.
 */
public interface GradeManageService {
    /**
     * 请求分派
     * @param request
     * @param response
     */
    public void forwardPage(String page, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    /**
     * 获得该学生全部课程成绩列表
     * @return
     */
    public List getStudentGrade(String studentId, String password);

    /**
     * 判断学号是否存在
     * @param studentId
     * @return
     */
    public boolean isStudentExist(String studentId);

    /**
     * 判断密码是否正确
     * @param studentId
     * @param password
     * @return
     */
    public boolean isPasswordCorrect(String studentId, String password);

}
