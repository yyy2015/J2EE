package edu.nju.student.grade.dao;

import edu.nju.student.grade.model.Course;
import edu.nju.student.grade.model.Score;
import edu.nju.student.grade.model.Student;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yyy on 2017/2/14.
 */
@Stateless(name = "StudentGradeDaoEJB")
public class StudentGradeDaoBean implements StudentGradeDao {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public List findCourseList(String studentId) {

        ArrayList<String> list = new ArrayList<String>();

        Query query = em.createQuery("select s from Score s where s.studentId = ?1 ",Score.class);
        query.setParameter(1,studentId);
        List result = query.getResultList();
        Iterator iterator = result.iterator();
        while(iterator.hasNext()){
            Score score = (Score)iterator.next();
            if(list.indexOf(score.getCourseId()) == -1) {
                list.add(score.getCourseId());
            }
        }
        em.clear();

        return list;
    }

    @Override
    public String findCourseName(String courseId) {

        String name = null;

        Course course = em.find(Course.class,courseId);
        name = course.getName();

        return name;
    }

    @Override
    public List findScore(String studentId, String courseId) {
        ArrayList<Double> scoreList = new ArrayList<Double>();

        Query query = em.createQuery("select s from Score s where s.studentId = ?1 and s.courseId = ?2 ORDER BY s.period ASC",Score.class);
        query.setParameter(1,studentId);
        query.setParameter(2,courseId);
        List result = query.getResultList();
        Iterator iterator = result.iterator();
        while(iterator.hasNext()){
            Score score = (Score)iterator.next();
            if(score.getScore() == null){
                scoreList.add(-1.0);
            }else {
                scoreList.add(score.getScore());
                System.out.println(studentId+" "+courseId+" "+score.getScore());
            }
        }

        return scoreList;
    }


    @Override
    public boolean findUser(String studentId) {

        boolean isExist = false;

        Student student = em.find(Student.class,studentId);
        if(student != null){
            isExist = true;
        }

        return isExist;
    }

    @Override
    public boolean confirmUser(String studentId, String password) {

        boolean isCorrect = false;

        Query query = em.createQuery("select s from Student s where s.studentId=?1 and s.password=?2");
        query.setParameter(1,studentId);
        query.setParameter(2,password);
        List result = query.getResultList();
        Iterator iterator = result.iterator();
        if(iterator.hasNext()){
            isCorrect = true;
        }

        return isCorrect;
    }


}
