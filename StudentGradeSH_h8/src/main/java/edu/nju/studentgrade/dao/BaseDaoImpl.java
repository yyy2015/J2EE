package edu.nju.studentgrade.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yyy on 2017/3/7.
 */
@Repository
public class BaseDaoImpl implements BaseDao {

    @Autowired
    protected SessionFactory sessionFactory;

    /**
     *gerCurrentSession 会自动关闭session，使用的是当前的session事务
     *@return
     */
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * openSession 需要手动关闭session 意思是打开一个新的session
     * @return
     */
    public Session getNewSession() {
        return sessionFactory.openSession();
    }

    public void flush() {
        getSession().flush();
    }

    public void clear() {
        getSession().clear();
    }

    /**
     * 根据id查询信息
     * @param c
     * @param id
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Object load(Class c, String id) {
        Session session = getSession();
        return session.get(c,id);
    }

    /**
     * 获取所有信息
     * @param c
     * @return
     */
    public List getAllList(Class c) {
        String hql = "from"+c.getName();
        Session session = getSession();
        return session.createQuery(hql).list();
    }

    /**
     * 获取总数量
     * @param c
     * @return
     */
    public Long getTotalCount(Class c) {
        Session session = getNewSession();
        String hql = "select count(*) from "+c.getName();
        Long count = (Long)session.createQuery(hql).uniqueResult();
        session.close();
        return count !=null?count.longValue():0;
    }

    /**
     * 保存
     * @param bean
     */
    public void save(Object bean) {
        try{
            Session session = getSession();
            session.save(bean);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 更新
     * @param bean
     */
    public void update(Object bean) {
        Session session = getNewSession();
        session.update(bean);
        session.flush();
        session.clear();
        session.close();
    }

    /**
     * 删除
     * @param bean
     */
    public void delete(Object bean) {
        Session session = getNewSession();
        session.delete(bean);
        session.flush();
        session.clear();
        session.close();

    }

    /**
     * 根据id删除
     * @param c
     * @param id
     */
    public void delete(Class c, String id) {
        Session session = getNewSession();
        Object obj = session.get(c,id);
        session.delete(obj);
        flush();
        clear();
        session.close();
    }

    public void delete(Class c, String[] ids) {
        for(String id:ids){
            Object obj = getSession().get(c,id);
            if(obj != null){
                getSession().delete(obj);
            }
        }
    }
}
