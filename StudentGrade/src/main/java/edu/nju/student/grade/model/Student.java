package edu.nju.student.grade.model;

/**
 * Created by yyy on 2016/12/8.
 */
public class Student {
    private String studentId;
    private String password;


    public Student() {

    }
    public Student(String studentId, String password) {

        this.studentId = studentId;
        this.password = password;
    }
    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
