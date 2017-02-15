package edu.nju.student.grade.service;

import javax.ejb.Remote;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by yyy on 2017/2/14.
 */

@Remote
public interface GradeManageService {
    /**
     * 请求分派
     * @param page
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void forwordPage(String page, HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException;

    /**
     * 获得该学生的全部课程的全部成绩列表
     * @param studentId
     * @param password
     * @return
     */
    public List getStudentGrade(String studentId, String password);

    /**
     * 判断学号是否存在
     * @param studentId
     * @return
     */
    public boolean isStudentExit(String studentId);

    /**
     * 判断密码是否正确
     * @param studentId
     * @param password
     * @return
     */
    public boolean isPasswordCorrect(String studentId, String password);

}
