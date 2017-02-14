package edu.nju.student.grade.dao;

import java.util.List;

/**
 * Created by yyy on 2017/1/2.
 */
public interface StudentGradeDao {

    /**
     * 获得对应学生的全部课程
     * @param studentId
     */
    public List findCourseList(String studentId);

    /**
     * 获得对应编号的课程名称
     * @param courseId
     */
    public String findCourseName(String courseId);

    /**
     * 获得该学生该课程的成绩列表
     * @param studentId
     * @param courseId
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
