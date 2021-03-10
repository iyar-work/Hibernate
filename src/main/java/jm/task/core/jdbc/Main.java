package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;



public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();
//        System.out.println(userService.getAllUsers());
//        userService.createUsersTable();
//        userService.dropUsersTable();
//        System.out.println(userService.getAllUsers());
//        for (int i = 0; i < 5; i++) {
//            userService.saveUser("Саня10", "Диманов20", (byte) 370);
//        }
        userService.removeUserById(9);
//        userService.cleanUsersTable();

//        conn.connect();
//        userService.createUsersTable();
//        userService.saveUser("Саня1", "Диманов2", (byte) 28);
//        userService.saveUser("Саня3", "Диманов3", (byte) 25);
//        userService.saveUser("Саня4", "Диманов4", (byte) 68);
//        userService.saveUser("Саня5", "Диманов5", (byte) 48);
//        userService.saveUser("Саня6", "Диманов6", (byte) 26);
//        userService.saveUser("Саня7", "Диманов7", (byte) 47);
//        System.out.print(userService.getAllUsers());
//        System.out.println(userService.getAllUsers());
//        userService.removeUserById(2);
//        userService.cleanUsersTable();
//        userService.dropUsersTable();
    }
}
