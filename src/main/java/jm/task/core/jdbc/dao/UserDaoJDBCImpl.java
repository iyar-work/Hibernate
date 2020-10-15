package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Util connect;
    
    public UserDaoJDBCImpl() {
    }
    public UserDaoJDBCImpl(Util connect){
        this.connect = connect;
    }

    public void createUsersTable() {
        try {
            Statement stmt = connect.connect().createStatement();
//            stmt.execute("create table if not exists users (id bigint auto_increment, name varchar(256), lastName varchar(256), age int(64))");
//            stmt.execute("create table users (id bigint auto_increment, name varchar(256), lastName varchar(256), age int (16))");  id MEDIUMINT NOT NULL AUTO_INCREMENT
            stmt.execute("create table users (id MEDIUMINT NOT NULL AUTO_INCREMENT, PRIMARY KEY (id), name varchar(256), lastName varchar(256), age int (16))");
            stmt.close();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() throws SQLException, ClassNotFoundException {
        Statement stmt = connect.connect().createStatement();
        stmt.executeUpdate("DROP TABLE IF EXISTS test.users");
        stmt.close();
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException, ClassNotFoundException {
        int ageInt = (int)age;
        try {
            String sqlStr = "INSERT INTO test.users (id, name, lastName, age) VALUES (?,?,?,?)";
            PreparedStatement statement = connect.connect().prepareStatement(sqlStr);
            statement.setLong(1, 0);
            statement.setString( 2, name);
            statement.setString( 3,lastName);
            statement.setInt( 4, ageInt);
            statement.execute();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            connect.connect().close();
        }
    }

    public void removeUserById(long id) {
        try {
            Statement stmt = connect.connect().createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM test.users where id ='" + id + "'");
            if (resultSet.next()){
                System.out.println("Клиент с id = " + id + " найден");
                stmt.executeUpdate("delete FROM test.users where id ='" + id + "'");
                stmt.close();
                System.out.println("Клиент с id = " + id + " из базы удален");
            }else {
                System.out.println("Клиент с таким id в базе не найден");
                resultSet.close();
                stmt.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() throws SQLException, ClassNotFoundException {
        List<User> usersArrey = new ArrayList<>();
        Statement stmt = connect.connect().createStatement();
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
        return usersArrey;
    }

    public void cleanUsersTable() throws SQLException, ClassNotFoundException {
        Statement stmt = connect.connect().createStatement();
        stmt.executeUpdate("delete from test.users");
        stmt.close();
    }
}
