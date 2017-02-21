package edu.nju.student.grade.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by yyy on 2017/2/14.
 */
public interface DaoHelper {
    /**
     * 获取连接对象
     * @return
     */
    public Connection getConnection();

    /**
     * 关闭连接对象
     * @param con
     */
    public void closeConnection(Connection con);

    /**
     * 关闭PreparedStatement对象
     * @param stmt
     */
    public void closePreparedStatement(PreparedStatement stmt);

    /**
     * 关闭result对象
     * @param result
     */
    public void closeResult(ResultSet result);
}
