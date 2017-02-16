package edu.nju.student.grade.model;

import javax.persistence.*;
import java.util.ArrayList;

/**
 * Created by yyy on 2017/2/15.
 */
@Entity
@IdClass(ScorePK.class)
public class Score {
    private String studentId;
    private String courseId;
    private Double score;
    private int period;


    @Id
    @Column(name = "studentId")
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Id
    @Column(name = "courseId")
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "score")
    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Id
    @Column(name = "period")
    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Score score1 = (Score) o;

        if (period != score1.period) return false;
        if (studentId != null ? !studentId.equals(score1.studentId) : score1.studentId != null) return false;
        if (courseId != null ? !courseId.equals(score1.courseId) : score1.courseId != null) return false;
        if (score != null ? !score.equals(score1.score) : score1.score != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studentId != null ? studentId.hashCode() : 0;
        result = 31 * result + (courseId != null ? courseId.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + period;
        return result;
    }

}
