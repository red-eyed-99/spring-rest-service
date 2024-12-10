package config;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class DatasourceConfig {

    @Bean
    public DataSource dataSource() throws IOException {
        var properties = getHibernateProperties();

        var dataSource = new HikariDataSource();

        dataSource.setDriverClassName(properties.getProperty("hibernate.connection.driver_class"));
        dataSource.setJdbcUrl(properties.getProperty("hibernate.connection.url"));
        dataSource.setUsername(properties.getProperty("hibernate.connection.username"));
        dataSource.setPassword(properties.getProperty("hibernate.connection.password"));

        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() throws IOException {
        var sessionFactory = new LocalSessionFactoryBean();

        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("entities");
        sessionFactory.setHibernateProperties(getHibernateProperties());

        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Bean
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Session session(SessionFactory sessionFactory) {
        return sessionFactory.getCurrentSession();
    }

    private Properties getHibernateProperties() throws IOException {
        var properties = new Properties();

        properties.load(getClass().getResourceAsStream("/hibernate.properties"));

        return properties;
    }
}