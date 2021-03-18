package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
//    private Util connect;
    Connection connection = connect();

    public UserDaoJDBCImpl() throws SQLException, ClassNotFoundException {
    }

//    public UserDaoJDBCImpl(Util connect) throws SQLException, ClassNotFoundException {
//        this.connect = connect;
//    }

    public void createUsersTable() throws SQLException, ClassNotFoundException {
        Statement stmt = connection.createStatement();
        try {
            connection.setAutoCommit(false);
            stmt.execute("create table users (id MEDIUMINT NOT NULL AUTO_INCREMENT, PRIMARY KEY (id), name varchar(256), lastName varchar(256), age int (16))");
            stmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.rollback();
            connection.close();
        }
    }

    public void dropUsersTable() throws SQLException, ClassNotFoundException {
        try {
        Statement stmt = connection.createStatement();
        connection.setAutoCommit(false);
        stmt.executeUpdate("DROP TABLE IF EXISTS test.users");
        stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
          } finally {
            connection.rollback();
            connection.close();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException, ClassNotFoundException {
        int ageInt = (int)age;
        PreparedStatement statement = null;
        String sqlStr = "INSERT INTO test.users (id, name, lastName, age) VALUES (?,?,?,?)";
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sqlStr);
            statement.setLong(1, 0);
            statement.setString( 2, name);
            statement.setString( 3,lastName);
            statement.setInt( 4, ageInt);
            statement.execute();
            statement.close();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.close();
        }
    }

    public void removeUserById(long id) throws SQLException, ClassNotFoundException {
        try {
            Statement stmt = connection.createStatement();
            connection.setAutoCommit(false);
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM test.users where id ='" + id + "'");
            if (resultSet.next()){
                System.out.println("Клиент с id = " + id + " найден");
                stmt.executeUpdate("delete FROM test.users where id ='" + id + "'");
                stmt.close();
                connection.commit();
                System.out.println("Клиент с id = " + id + " из базы удален");
            }else {
                System.out.println("Клиент с таким id в базе не найден");
                resultSet.close();
                stmt.close();
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.close();
        }
    }

    public List<User> getAllUsers() throws SQLException, ClassNotFoundException {
        List<User> usersArrey = new ArrayList<>();
    try {
        connection.setAutoCommit(false);
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM test.users");
        while (resultSet.next()) {
        User user = new User();
        user.setId(resultSet.getLong(1));
//                System.out.print("  " + resultSet.getLong(1));
        user.setName(resultSet.getString(2));
//                System.out.print("  " + resultSet.getString(2));
        user.setLastName(resultSet.getString(3));
//                System.out.print("  " + resultSet.getString(3));
        byte age = (byte) resultSet.getInt(4);
//                System.out.print("  " + resultSet.getInt(4));
//                System.out.println();
        user.setAge(age);
        usersArrey.add(user);
        }
        resultSet.close();
        stmt.close();
        connection.commit();
     }catch (SQLException e){
            e.printStackTrace();
        connection.rollback();
        } finally {
        connection.close();
          }
        return usersArrey;
    }

    public void cleanUsersTable() throws SQLException, ClassNotFoundException {
        try {
            Statement stmt = connection.createStatement();
            connection.setAutoCommit(false);
            stmt.executeUpdate("delete from test.users");
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.close();
        }
    }
}
