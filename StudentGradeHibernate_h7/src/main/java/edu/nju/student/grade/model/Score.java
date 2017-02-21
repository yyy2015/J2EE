package edu.nju.student.grade.model;

import java.util.ArrayList;

/**
 * Created by yyy on 2016/12/8.
 */
public class Score {
    private String courseId;
    private String courseName;



    private ArrayList<Double> scoreList;
    private int period;

    public Score(){

    }

    public Score(String courseId, String courseName, ArrayList<Double> scoreList){
        this.courseId = courseId;
        this.courseName = courseName;
        this.scoreList = scoreList;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public ArrayList<Double> getScoreList() {
        return scoreList;
    }

    public void setScoreList(ArrayList<Double> scoreList) {
        this.scoreList = scoreList;
    }


}
