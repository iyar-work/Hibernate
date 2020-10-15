package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;



@PropertySource(value = { "classpath:application.properties" })
@Configuration
@EnableTransactionManagement
public class Util {
    // реализуйте настройку соеденения с БД


    @Value("${jdbc.driverClassName}")
    private String driverClass;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    @Value("${hibernate.dialect}")
    private String dialect;

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, username, password);
        dataSource.setDriverClassName(driverClass);
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
        factory.setDataSource(getDataSource());
        factory.setHibernateProperties(hibernateProperties());
        factory.setPackagesToScan(new String[] { "com.javatechie.spring.orm.api.model" });
        return factory;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", dialect);
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        return properties;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory factory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(factory);
        return transactionManager;
    }



//    // Свойства источника данных
//    @Value("${com.mysql.jdbc.Driver}")
//    private String driverClassName;
//    @Value("${jdbc:mysql://localhost:3306/test?serverTimezone=UTC}")
//    private String url;
//    @Value("${root}")
//    private String username;
//    @Value("${1}")
//    private String password;
//
//    /**
//     * Компонент источника данных
//     */
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(driverClassName);
//        dataSource.setUrl(url);
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        return dataSource;
//    }
//
//    // Свойства Hibernate
//    @Value("${hibernate.dialect}")
//    private String hibernateDialect;
//    @Value("${hibernate.show_sql}")
//    private String hibernateShowSql;
//    @Value("${hibernate.hbm2ddl.auto}")
//    private String hibernateHBM2DDLAuto;
//
//    /**
//     * Свойства Hibernate в виде объекта класса Properties
//     */
//    @Bean
//    public Properties hibernateProperties() {
//        Properties properties = new Properties();
//        properties.put("hibernate.dialect", hibernateDialect);
//        properties.put("hibernate.show_sql", hibernateShowSql);
//        properties.put("hibernate.hbm2ddl.auto", hibernateHBM2DDLAuto);
//        return properties;
//    }
//
//    /**
//     * Фабрика сессий Hibernate: sessionFactory — для создания сессий, с помощью которых осуществляются операции
//     * с объектами-сущностями. Здесь мы устанавливаем источник данных, свойства Hibernate
//     * и в каком пакете нужно искать классы-сущности.
//     */
//    @Bean
//    @SuppressWarnings("deprecation")
//    public SessionFactory sessionFactory() {
//        return new LocalSessionFactoryBuilder(dataSource())
//                .scanPackages("jm.task.core.jdbc.util")
//                .addProperties(hibernateProperties())
//                // используем устаревший метод, так как Spring не оставляет нам выбора
//                .buildSessionFactory();
//    }
//
//    /**
//     * Менеджер транзакций: transactionManager — для настройки менеджера транзакций.
//     */
//    @Bean
//    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
//        HibernateTransactionManager hiberTranzMng = new HibernateTransactionManager();
//        hiberTranzMng.setSessionFactory(sessionFactory);
//        return hiberTranzMng;
//    }



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
