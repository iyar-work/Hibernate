package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public List<User> getAllUsers(){
        Session session = new Util().getSessionFactory().openSession();
        session.beginTransaction();
//        return session.createQuery("from jm.task.core.jdbc.model.User").list();
        return session.createQuery("from User").list();
    }

    @Override
    public void createUsersTable() {
        NativeQuery query;
        Session session = new Util().getSessionFactory().openSession();
            session.beginTransaction();
            query = session.createNativeQuery("create table test.User5 (id MEDIUMINT NOT NULL AUTO_INCREMENT, PRIMARY KEY (id), name varchar(256), lastName varchar(256), age int (16))");
        query.executeUpdate();
        System.out.println("Таблица готова!!!");
        session.disconnect();
        session.close();
    }

    @Override
    public void dropUsersTable() {
//        DROP DATABASE your_database;
        NativeQuery query;
        Session session = new Util().getSessionFactory().openSession();
            session.beginTransaction();
            query = session.createNativeQuery("DROP TABLE IF EXISTS test.users");
//        Необходимо обновить запрос: query.executeUpdate(). При каких-то изменениях в таблице запрос
//        следует обновлять, а, скажем, при получении данных из таблицы без её изменения
//        запрос обновлять не нужно
        query.executeUpdate();
        System.out.println("таблица удалена");
        session.disconnect();
        session.close();
    }

    @Override
//    @Transactional
    public void saveUser(String name, String lastName, byte age) {
        //а для сохранения лучше использовать метод persist вместо save
        //неочевидный такой момент)
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        Session session = new Util().getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if (transaction == null) {
                // откат транзакции
                transaction.rollback();
            }
        }finally {
            session.disconnect();
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = new Util().getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }catch (RuntimeException e){
            if (transaction == null) {
                // откат транзакции
                transaction.rollback();
            }
        }finally {
            session.disconnect();
            session.close();
        }
    }

    @Override
    public void cleanUsersTable() {
        Session session = new Util().getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.createNativeQuery("DELETE FROM test.User5").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Произведено удаление данных из таблицы");
        } catch (RuntimeException e){
            if (transaction == null) {
                // откат транзакции
                transaction.rollback();
            }
        }finally {
            session.disconnect();
            session.close();
        }
    }
}
