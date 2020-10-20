package com.example.demo.configuration;


import com.example.demo.student.data.postgresSQL.StudentRepositoryPostgresSQL;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackageClasses = { StudentRepositoryPostgresSQL.class },
        entityManagerFactoryRef = "studentEntityManagerFactory",
        basePackages = {"com.example.demo.student.data.postgresSQL"},
        transactionManagerRef = "studentTransactionManager"

)
@EnableTransactionManagement
public class PostgresSqlDataSourceConfig {

    @Bean(name = "postgresDataSource")
    @ConfigurationProperties(
            prefix = "spring.postgres.datasource"
    )
    public DataSource postgresDataSource()
    {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/TaskOne");
        dataSourceBuilder.username("postgres");
        dataSourceBuilder.password("ahmed");
        return dataSourceBuilder.build();
    }

    @Primary
    @Bean(name = "studentEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    entityManagerFactory(
            @Qualifier("entityManagerFactoryBuilderPostgresSql") EntityManagerFactoryBuilder builder,
            @Qualifier("postgresDataSource") DataSource dataSource
    ) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.ddl-auto" , "update");
        properties.put("hibernate.dialect" , "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.jdbc.lob.non_contextual_creation" , "true");

        return builder
                .dataSource(dataSource)
                .properties(properties)
                .packages("com.example.demo.student.models")
                .build();
    }

    @Primary
    @Bean(name = "studentTransactionManager")
    public JpaTransactionManager customerTransactionManager(
            @Qualifier("studentEntityManagerFactory") EntityManagerFactory
                    studentEntityManagerFactory
    ) {
        return new JpaTransactionManager(studentEntityManagerFactory);
    }

    @Bean(name = "entityManagerFactoryBuilderPostgresSql")
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
    }

}
