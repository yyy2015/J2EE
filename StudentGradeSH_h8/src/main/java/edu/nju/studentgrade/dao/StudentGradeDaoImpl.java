package edu.nju.studentgrade.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyy on 2017/1/2.
 */
@Repository
public class StudentGradeDaoImpl implements StudentGradeDao {

    @Autowired
    private BaseDao baseDao;
    @Autowired
    private SessionFactory sessionFactory;

    public List findCourseList(String studentId) {

        ArrayList<String> list = new ArrayList<String>();

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select distinct s.courseId from ScoreEntity s where s.studentId=:studentId");
        query.setParameter("studentId",studentId);
        List<String> queryList = query.list();
        for(String id : queryList){
            list.add(id);
        }

        return list;
    }

    public String findCourseName(String courseId) {

        String name = null;

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select c.name from CourseEntity c where c.courseId=?1");
        query.setParameter(1,courseId);
        List<String> list = query.list();
        for(String cname:list){
            name = cname;
        }

        return name;
    }

    public List findScore(String studentId, String courseId) {

        ArrayList<Double> list = new ArrayList<Double>();

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select s.score from ScoreEntity s where s.studentId=?1 and s.courseId=?2 order by s.period ASC");
        query.setParameter(1,studentId);
        query.setParameter(2,courseId);
        List<Double> scoreList = query.list();
        for(Double score:scoreList){
            if(score != null){
                list.add(score);
            }else{
                list.add(-1.0);
            }
        }

        return list;
    }

    public boolean findUser(String studentId) {

        boolean isExist = false;

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from StudentEntity s where s.studentId=?1");
        query.setParameter(1,studentId);
        List list = query.list();
        if(!list.isEmpty()){
            isExist=true;
        }

        return isExist;
    }

    public boolean confirmUser(String studentId, String password) {

        boolean isCorrect = false;

        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from StudentEntity s where s.studentId=?1 and s.password=?2");
        query.setParameter(1,studentId);
        query.setParameter(2,password);
        List list = query.list();
        if(!list.isEmpty()){
            isCorrect = true;
        }

        return isCorrect;
    }

}
