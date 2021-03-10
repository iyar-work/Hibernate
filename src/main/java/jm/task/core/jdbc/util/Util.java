package jm.task.core.jdbc.util;

import com.fasterxml.classmate.AnnotationConfiguration;
import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
//import org.springframework.context.annotation.Configuration;
import org.hibernate.service.Service;
import org.hibernate.service.ServiceRegistry;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;



//@PropertySource(value = { "classpath:application.properties" })
//@Configuration
@EnableTransactionManagement
public class Util {
    // реализуйте настройку соеденения с БД
    
 private static SessionFactory sessionFactory;

public SessionFactory getSessionFactory (){
    if (sessionFactory == null){
        try {
            Properties properties = new Properties();
            properties.put(Environment.URL, "jdbc:mysql://localhost:3306/test?serverTimezone=UTC");
            properties.put(Environment.USER, "root");
            properties.put(Environment.PASS, "1");
            properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            properties.put(Environment.SHOW_SQL, "thrue");

            //создаем подключение к базе данных, создаётся одно подключение на программу, но не под каждый запрос
            Configuration configuration = new Configuration().setProperties(properties);
            configuration.addAnnotatedClass(jm.task.core.jdbc.model.User.class);

            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);

//            SessionFactory sessionFactory = new Configuration().addProperties(properties).buildSessionFactory();
//            Session session = sessionFactory.getCurrentSession();
            System.out.println("Connection succes (Соединение с базой установлено!)");
//            session.beginTransaction();

              

//              session.getTransaction();
        }catch (Exception e){
            System.out.println("Connection error");
            e.printStackTrace();
        }
    }
      return sessionFactory;
}




    public Connection connect() throws ClassNotFoundException, SQLException {
        try {
            String userName = "root";
            String password = "1";
            String connectionUrl = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
            Class.forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
            System.out.println("К базе подключены!");
            return connection;
        }  catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

}
