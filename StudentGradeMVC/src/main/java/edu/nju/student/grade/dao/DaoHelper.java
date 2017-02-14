package edu.nju.student.grade.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by yyy on 2017/1/2.
 */
public interface DaoHelper {
    /**
     *用tomcat数据源得到连接对象
     */
    public Connection getConnection();

    /**
     * 关闭connection对象，把数据库连接放回到连接池中
     */
    public void closeConnection(Connection con);

    /**
     * 关闭PreparedStatement对象
     */
    public void closePreparedStatement(PreparedStatement stmt);

    /**
     * 关闭resultSet对象
     */
    public void closeResult(ResultSet result);

}
