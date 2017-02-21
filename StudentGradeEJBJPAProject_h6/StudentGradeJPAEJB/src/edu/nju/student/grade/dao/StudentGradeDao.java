package edu.nju.student.grade.dao;

import javax.ejb.Remote;
import java.util.List;

/**
 * Created by yyy on 2017/2/14.
 */

@Remote
public interface StudentGradeDao {
    /**
     * 获得对应学生的全部课程
     * @param studentId
     * @return
     */
    public List findCourseList(String studentId);

    /**
     * 获得对应编号的课程名称
     * @param courseId
     * @return
     */
    public String findCourseName(String courseId);

    /**
     * 获得该学生对应课程的成绩列表
     * @param studentId
     * @param courseId
     * @return
     */
    public List findScore(String studentId, String courseId);

    /**
     * 查询学生学号是否存在
     * @param studentId
     * @return
     */
    public boolean findUser(String studentId);

    /**
     * 验证学生学号密码是否正确
     * @param studentId
     * @param password
     * @return
     */
    public boolean confirmUser(String studentId, String password);
}
