package edu.nju.studentgrade.dao;

import org.hibernate.Session;

import java.util.List;

/**
 * Created by yyy on 2017/3/7.
 */
public interface BaseDao {
    public Session getSession();

    public Session getNewSession();

    public void flush();

    public void clear();

    public Object load(Class c, String id);

    public List getAllList(Class c);

    public Long getTotalCount(Class c);

    public void save(Object bean);

    public void update(Object bean);

    public void delete(Object bean);

    public void delete(Class c, String id);

    public void delete(Class c, String[] ids) ;

}
