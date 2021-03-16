package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
//import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import org.springframework.transaction.annotation.Transactional;


//import java.sql.SQLException;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {

    public UserServiceImpl() throws SQLException, ClassNotFoundException {
    }

    UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();

    public void createUsersTable() {
        userDaoHibernate.createUsersTable();
    }

    public void dropUsersTable() {
        userDaoHibernate.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDaoHibernate.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        userDaoHibernate.removeUserById(id);
    }

    @Transactional
    public List<User> getAllUsers() {
        return userDaoHibernate.getAllUsers();
    }

    public void cleanUsersTable() {
        userDaoHibernate.cleanUsersTable();
    }

//
//
//    UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
//
//    public void createUsersTable() throws SQLException, ClassNotFoundException {
//        userDaoJDBC.createUsersTable();
//    }
//
//    public void dropUsersTable() throws SQLException, ClassNotFoundException {
//        userDaoJDBC.dropUsersTable();
//    }
//
//    public void saveUser(String name, String lastName, byte age) throws SQLException, ClassNotFoundException {
//        userDaoJDBC.saveUser(name, lastName, age);
//    }
//
//    public void removeUserById(long id) throws SQLException, ClassNotFoundException {
//        userDaoJDBC.removeUserById(id);
//    }
//
//    public List<User> getAllUsers() throws SQLException, ClassNotFoundException {
//        return userDaoJDBC.getAllUsers();
//    }
//
//    public void cleanUsersTable() throws SQLException, ClassNotFoundException {
//        userDaoJDBC.cleanUsersTable();
//    }
}
