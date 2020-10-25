package com.configs.database;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Properties;

@Data
@NoArgsConstructor
@Service
public class DatabaseConfig {

    @Value("${database.spring.datasource.url}")
    private String url;

    @Value("${database.spring.datasource.username}")
    private String username;

    @Value("${database.spring.datasource.password}")
    private String password;

    @Bean(name="mysqlDataSource")
    public DataSource mysqlDataSource() {
        MysqlDataSource mysqlDS = new MysqlDataSource();
        mysqlDS.setURL(url);
        mysqlDS.setUser(username);
        mysqlDS.setPassword(password);
        return mysqlDS;
    }

    @Bean(name="mysqlJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("mysqlDataSource") DataSource dsMySQL) {
        return new JdbcTemplate(dsMySQL);
    }

    @Bean(name="mysqlEntityManager")
    public LocalContainerEntityManagerFactoryBean mysqlEntityManager(@Autowired @Qualifier("mysqlDataSource") DataSource dataSource,
                                                                     @Autowired Properties hibernateProperties,
                                                                     @Autowired JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setPersistenceUnitName("persist-transactions");
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setJpaProperties(hibernateProperties);
        entityManagerFactoryBean.setPackagesToScan("com.publisher_service");
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        return entityManagerFactoryBean;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        vendorAdapter.setShowSql(true);
        vendorAdapter.setGenerateDdl(true);
        return vendorAdapter;
    }

    @Bean(name="hibernateProperties")
    public Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return properties;
    }

    @Bean
    public PlatformTransactionManager mysqlTransactionManager(@Autowired @Qualifier("mysqlEntityManager") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean.getObject());
        return transactionManager;
    }
}
