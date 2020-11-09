package com.example.demo.configuration;


import com.example.demo.student.data.mysql.StudentRepositoryMySQL;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = {"com.example.demo.student.data.mysql"},
        basePackageClasses = {StudentRepositoryMySQL.class},
        entityManagerFactoryRef = "studentEntityManagerFactormymysql",
        transactionManagerRef = "studentTransactionManagermysql"

)
public class MySQLDataSourceConfig {

    @Bean(name = "mysqlDataSource")
    public DataSource mysqlDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/taskone?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("ahmed");
        return dataSourceBuilder.build();
    }

    @Primary
    @Bean(name = "studentEntityManagerFactormymysql")
    public LocalContainerEntityManagerFactoryBean
    entityManagerFactory(
            @Qualifier("entityManagerFactoryBuilderMySQL") EntityManagerFactoryBuilder builder,
            @Qualifier("mysqlDataSource") DataSource dataSource
    ) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.ddl-auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.put("hibernate.jdbc.lob.non_contextual_creation", "true");


        return builder
                .dataSource(dataSource)
                .properties(properties)
                .packages("com.example.demo.student.models")
                .persistenceUnit("Student")
                .build();
    }

    @Primary
    @Bean(name = "studentTransactionManagermysql")
    public JpaTransactionManager customerTransactionManager(
            @Qualifier("studentEntityManagerFactormymysql") EntityManagerFactory
                    studentEntityManagerFactory
    ) {
        return new JpaTransactionManager(studentEntityManagerFactory);
    }

    @Bean(name = "entityManagerFactoryBuilderMySQL")
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
    }


}
