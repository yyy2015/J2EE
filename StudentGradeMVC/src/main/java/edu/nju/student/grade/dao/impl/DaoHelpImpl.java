package edu.nju.student.grade.dao.impl;

import edu.nju.student.grade.dao.DaoHelper;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by yyy on 2017/1/2.
 */
public class DaoHelpImpl implements DaoHelper {

    private static DaoHelpImpl baseDao = new DaoHelpImpl();

    private InitialContext jndiContext = null;
    private Connection connection = null;
    private DataSource dataSource = null;

    private DaoHelpImpl(){
        Properties properties = new Properties();
        properties.put(Context.PROVIDER_URL, "jnp:///");
        properties.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.naming.java.javaURLContextFactory");

        try{
            jndiContext = new InitialContext(properties);
            dataSource = (DataSource) jndiContext.lookup("java:comp/env/jdbc/studentGrade");
        }catch(NamingException e){
            e.printStackTrace();
        }

        System.out.println("got context");
        System.out.println("About to get ds---DaoHelper");
    }

    public static DaoHelpImpl getBaseDaoInstance(){
        return baseDao;
    }

    public Connection getConnection() {

        try{
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public void closeConnection(Connection con) {
        if(con!=null){
            try{
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closePreparedStatement(PreparedStatement stmt) {
        if(stmt!=null){
            try{
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void closeResult(ResultSet result) {
        if(result!=null){
            try{
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
