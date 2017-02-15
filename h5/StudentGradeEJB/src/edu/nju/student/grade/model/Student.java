package edu.nju.student.grade.model;

import java.io.Serializable;

/**
 * Created by yyy on 2017/2/14.
 */
public class Student implements Serializable {
    private String studentId;
    private String password;

    public Student(){

    }
    public Student(String studentId, String password){
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
