package edu.nju.student.grade.dao;

import edu.nju.student.grade.model.CourseEntity;
import edu.nju.student.grade.model.ScoreEntity;
import edu.nju.student.grade.model.StudentEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyy on 2017/1/2.
 */
public class StudentGradeDaoImpl implements StudentGradeDao {

    private Configuration config;
    private ServiceRegistry serviceRegistry;
    private SessionFactory sessionFactory;
    private Session session;
    private static StudentGradeDaoImpl studentGradeDao = new StudentGradeDaoImpl();


    private StudentGradeDaoImpl(){

    }

    public static StudentGradeDaoImpl getInstance(){
        return studentGradeDao;
    }

    public List findCourseList(String studentId) {

        ArrayList<String> list = new ArrayList<String>();

        config = new Configuration().configure();
        config.addAnnotatedClass(ScoreEntity.class);
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        sessionFactory = config.buildSessionFactory(serviceRegistry);
        session = sessionFactory.openSession();
        Query query = session.createQuery("select distinct s.courseId from ScoreEntity s where s.studentId=:studentId");
        query.setParameter("studentId",studentId);
        List<String> idList = query.list();
        for(String id: idList){
            list.add(id);
        }
        session.close();
        sessionFactory.close();

        return list;
    }

    public String findCourseName(String courseId) {

        String name = null;

        config = new Configuration().configure();
        config.addAnnotatedClass(CourseEntity.class);
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        sessionFactory = config.buildSessionFactory(serviceRegistry);
        session = sessionFactory.openSession();
        Query query = session.createQuery("select c.name from CourseEntity c where c.courseId=?1");
        query.setParameter(1,courseId);
        List<String> list = query.list();
        for(String cname:list){
            name = cname;
        }
        session.close();
        sessionFactory.close();

        return name;
    }

    public List findScore(String studentId, String courseId) {

        ArrayList<Double> list = new ArrayList<Double>();

        config = new Configuration().configure();
        config.addAnnotatedClass(ScoreEntity.class);
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        sessionFactory = config.buildSessionFactory(serviceRegistry);
        session = sessionFactory.openSession();
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
        session.close();
        sessionFactory.close();

        return list;
    }

    public boolean findUser(String studentId) {

        boolean isExist = false;

        config = new Configuration().configure();
        config.addAnnotatedClass(StudentEntity.class);
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        sessionFactory = config.buildSessionFactory(serviceRegistry);
        session = sessionFactory.openSession();
        Query query = session.createQuery("from StudentEntity s where s.studentId=?1");
        query.setParameter(1,studentId);
        List list = query.list();
        if(!list.isEmpty()){
            isExist=true;
        }
        session.close();
        sessionFactory.close();

        return isExist;
    }

    public boolean confirmUser(String studentId, String password) {

        boolean isCorrect = false;

        config = new Configuration().configure();
        config.addAnnotatedClass(StudentEntity.class);
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        sessionFactory = config.buildSessionFactory(serviceRegistry);
        session = sessionFactory.openSession();
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
