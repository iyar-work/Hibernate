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


/Java/IdeaProjects/web/CoreTaskTemplate-master3/src/main/java/resources/application.properties

jdbc.driverClassName = com.mysql.jdbc.Driver
jdbc.url = jdbc:mysql://localhost:3306/test?serverTimezone=UTC
jdbc.username = root
jdbc.password = 1
hibernate.dialect = org.hibernate.dialect.MySQLDialect

______________________________________________________________________________________________

было:
       <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.38</version>
        </dependency>

_____________________________________________________________________________________________

(https://www.cyberforum.ru/java-database/thread2432611.html)

@Configuration
@ComponentScan(basePackages = { "config","controller","dao","entity","service"})
@EnableWebMvc
@EnableTransactionManagement
//@EnableJpaRepositories("") //включаем возможность использования JPARepository и говорим, где их искать
public class HibernateConfig {
    private static Logger logger=LoggerFactory.getLogger(HibernateConfig.class);

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = null;
        try {
            dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl("jdbc:mysql://localhost:3306/dbfastwater?useSSL=false");
            dataSource.setUsername("root");
            dataSource.setPassword("root");
        } catch (Exception e) {
            logger.error("MysqlDataSource bean cannot be created!", e);
        }
        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties hibernateProp = new Properties();
        hibernateProp.put("hibernate.dialect", "org.hibernate.spatial.dialect.mysql.MySQL56SpatialDialect");
        hibernateProp.put("connection.driver_class", "com.mysql.jdbc.Driver");
        hibernateProp.put("hibernate.format_sql", true);
        hibernateProp.put("hibernate.use_sql_comments", true);
        hibernateProp.put("hibernate.show_sql", true);
        hibernateProp.put("hibernate.max_fetch_depth", 3);
        hibernateProp.put("hibernate.jdbc.batch_size", 10);
        hibernateProp.put("hibernate.jdbc.fetch_size", 50);
        hibernateProp.put("hibernate.hbm2ddl.auto","update");
        return hibernateProp;
    }

    @Bean
    public SessionFactory sessionFactory() throws IOException {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(getDataSource());
        sessionFactoryBean.setPackagesToScan("entity","dao","service");
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        sessionFactoryBean.afterPropertiesSet();
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        return sessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws IOException {
        HibernateTransactionManager transactionManager=new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory());
        return transactionManager;
    }
}



https://www.youtube.com/watch?v=emg94BI2Jao&t=4317s