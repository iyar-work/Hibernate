package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from test.users").list();
//        return getSession().createQuery("from test.users").list();
//        return sessionFactory.getCurrentSession().createQuery("from test").list();
//        return session.createQuery("from test.users").list();
    }


//    public void setSessionFactory(org.hibernate.SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }


//    public UserDaoHibernateImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    @Override
    public void createUsersTable() {

    }

    @Override
    public void dropUsersTable() {

    }

    @Override
    @Transactional
    public void saveUser(String name, String lastName, byte age) {
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        sessionFactory.getCurrentSession().save(user);
//        getSession().save(user);
//        sessionFactory.getCurrentSession().saveOrUpdate(user);
//        Transaction transaction = null;

//        Session session = sessionFactory.getCurrentSession();
//        session.persist();
//        transaction = session.beginTransaction();
//        session.save(user);
//        transaction.commit();
//        session.close();
    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public void cleanUsersTable() {

    }
}
