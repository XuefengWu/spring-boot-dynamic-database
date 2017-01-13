package hello;

import dynamic.CustomerContextHolder;
import dynamic.CustomerRoutingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager",
        basePackages = {"hello"})
public class RepositoryConfig {
    @Autowired
    JpaVendorAdapter jpaVendorAdapter;

    @Autowired
    private Environment environment;

    @Bean(name = "entityManager")
    public EntityManager entityManager() {
        return entityManagerFactory().createEntityManager();
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setJpaVendorAdapter(jpaVendorAdapter);
        emf.setJpaProperties(hibernateProperties());
        emf.setPackagesToScan("hello");
        emf.setPersistenceUnitName("default");   // <- giving 'default' as name
        emf.afterPropertiesSet();
        return emf.getObject();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(entityManagerFactory());
        return tm;
    }


    private Properties hibernateProperties() {
        Properties properties= new Properties();
        String hibernateDialect = "org.hibernate.dialect.H2Dialect";
        properties.setProperty("hibernate.dialect",hibernateDialect);
        properties.setProperty("spring.jpa.hibernate.ddl-auto","update");
        return properties;
    }

    public DataSource dataSource() {

        //TODO load properties files by activeProfiles. --spring.profiles.active=dev,sit,uat,prod
        //String[] activeProfiles = this.environment.getActiveProfiles();
        //File[] files = Paths.get("tenants").toFile().listFiles();
        //Properties tenantProperties = new Properties();
        //tenantProperties.load(new FileInputStream(propertyFile));
                
        AbstractRoutingDataSource dataSource = new CustomerRoutingDataSource();
        Map<Object,Object>  targetDataSources = new HashMap<>();
        targetDataSources.put(CustomerContextHolder.CustomerType.GOLD,goldDataSource());
        targetDataSources.put(CustomerContextHolder.CustomerType.SILVER,silverDataSource());
        targetDataSources.put(CustomerContextHolder.CustomerType.BRONZE,bronzeDataSource());
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(bronzeDataSource());
        dataSource.afterPropertiesSet();
        return dataSource;
    }

    public DataSource goldDataSource() {
        //String databaseUrl = "jdbc:h2:mem:gold";
        String databaseUrl = "jdbc:h2:~/temp/db/gold;MODE=PostgreSQL";
        String username = "sa";
        String password = "";
        String driverClassName = "org.h2.Driver";
        DriverManagerDataSource dataSource = new DriverManagerDataSource(databaseUrl, username, password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

    public DataSource silverDataSource() {
        //String databaseUrl = "jdbc:h2:mem:silver";
        String databaseUrl = "jdbc:h2:~/temp/db/silver;MODE=PostgreSQL";
        String username = "sa";
        String password = "";
        String driverClassName = "org.h2.Driver";
        DriverManagerDataSource dataSource = new DriverManagerDataSource(databaseUrl, username, password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

    public DataSource bronzeDataSource() {
        //String databaseUrl = "jdbc:h2:mem:bronze";
        String databaseUrl = "jdbc:h2:~/temp/db/bronze;MODE=PostgreSQL";
        String username = "sa";
        String password = "";
        String driverClassName = "org.h2.Driver";
        DriverManagerDataSource dataSource = new DriverManagerDataSource(databaseUrl, username, password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

}