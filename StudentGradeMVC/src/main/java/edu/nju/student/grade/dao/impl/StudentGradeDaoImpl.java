package edu.nju.student.grade.dao.impl;

import edu.nju.student.grade.dao.DaoHelper;
import edu.nju.student.grade.dao.StudentGradeDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yyy on 2017/1/2.
 */
public class StudentGradeDaoImpl implements StudentGradeDao {

    private static StudentGradeDaoImpl studentGradeDao = new StudentGradeDaoImpl();
    private static DaoHelper daoHelper = DaoHelpImpl.getBaseDaoInstance();

    private StudentGradeDaoImpl(){

    }

    public static StudentGradeDaoImpl getInstance(){
        return studentGradeDao;
    }

    public List findCourseList(String studentId) {

        Connection con = daoHelper.getConnection();
        PreparedStatement stmt = null;
        ResultSet result = null;
        ArrayList<String> list = new ArrayList<String>();
        try{
            stmt = con.prepareStatement("select DISTINCT courseId from score where studentId = ?");
            stmt.setString(1,studentId);
            result = stmt.executeQuery();

            while(result.next()){
                list.add(result.getString("courseId"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

        }
        return list;
    }

    public String findCourseName(String courseId) {

        Connection con = daoHelper.getConnection();
        PreparedStatement stmt = null;
        ResultSet result = null;
        String name = null;

        try{
            stmt = con.prepareStatement("select * from course where courseId = ?");
            stmt.setString(1,courseId);
            result = stmt.executeQuery();
            result.next();
            name = result.getString("name");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(con,stmt,result);
        }

        return name;
    }

    public List findScore(String studentId, String courseId) {

        Connection con = daoHelper.getConnection();
        PreparedStatement stmt = null;
        ResultSet result = null;
        ArrayList<Double> list = new ArrayList<Double>();

        try{
            stmt = con.prepareStatement("select * from score where studentId = ? and courseId = ? ORDER BY period ASC ");
            stmt.setString(1,studentId);
            stmt.setString(2,courseId);
            result = stmt.executeQuery();

            while(result.next()){
                if(null != result.getString("score")){
                    list.add(result.getDouble("score"));
                }else{
                    list.add(-1.0);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            closeAll(con,stmt,result);
        }

        return list;
    }

    public boolean findUser(String studentId) {
        Connection con = daoHelper.getConnection();
        PreparedStatement stmt = null;
        ResultSet result = null;
        boolean isExist = false;

        try{
            stmt = con.prepareStatement("select count(*) as cnt from student where studentId = ?");
            stmt.setString(1,studentId);
            result = stmt.executeQuery();
            result.next();
            if(result.getInt("cnt")>0){
                isExist = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(con,stmt,result);
        }


        return isExist;
    }

    public boolean confirmUser(String studentId, String password) {

        Connection con = daoHelper.getConnection();
        PreparedStatement stmt = null;
        ResultSet result = null;
        boolean isCorrect = false;

        try {
            stmt = con.prepareStatement("select count(*) as cnt from student where studentId =? and password =?");
            stmt.setString(1,studentId);
            stmt.setString(2,password);
            result = stmt.executeQuery();
            result.next();
            if(result.getInt("cnt")>0){
                isCorrect = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(con,stmt,result);
        }

        return isCorrect;
    }

    private void closeAll(Connection con, PreparedStatement stmt, ResultSet result){
        daoHelper.closeConnection(con);
        daoHelper.closePreparedStatement(stmt);
        daoHelper.closeResult(result);
    }
}
