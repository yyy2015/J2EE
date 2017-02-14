package edu.nju.student.grade.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

/**
 * Created by yyy on 2017/2/14.
 */
public class DaoHelperImpl implements DaoHelper {

    private static DaoHelperImpl baseDao = new DaoHelperImpl();

    private InitialContext jndiContext = null;
    private Connection connection = null;
    private DataSource dataSource = null;

    private DaoHelperImpl(){

    }

    public static DaoHelperImpl getBaseDaoInstance(){
        return baseDao;
    }

    @Override
    public Connection getConnection() {
        try{
            final Hashtable properties = new Hashtable();
            properties.put(Context.URL_PKG_PREFIXES,"org.jboss.ejb.client.naming");

            try {
                jndiContext = new InitialContext(properties);
                dataSource = (DataSource) jndiContext.lookup("java:jboss/datasources/mysqlDS");
                if (dataSource == null) {
                    System.out.println("dataSource is null");
                }
            }catch(NamingException e){
                e.printStackTrace();
            }
            System.out.println("got context");
            System.out.println("About to get ds---DaoHelper");
            connection = dataSource.getConnection();
        }catch(SQLException e){
            e.printStackTrace();
        }

        return connection;
    }

    @Override
    public void closeConnection(Connection con) {
        if(con != null){
            try{
                con.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void closePreparedStatement(PreparedStatement stmt) {
        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void closeResult(ResultSet result) {
        if(result != null){
            try {
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
