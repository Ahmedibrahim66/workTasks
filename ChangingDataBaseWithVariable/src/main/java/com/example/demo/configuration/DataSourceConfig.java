package com.example.demo.configuration;


import com.example.demo.student.data.StudentRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
        basePackages = {"com.example.demo.student.data"},
        basePackageClasses = {StudentRepository.class},
        entityManagerFactoryRef = "studentEntityManagerFactory",
        transactionManagerRef = "studentTransactionManager"

)
public class DataSourceConfig {

    @Value("${database-type}")
    private String database;

    @Bean(name = "DataSource")
    public DataSource mysqlDataSource() {

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

        if(database.equals("MYSQL")){

            dataSourceBuilder.url("jdbc:mysql://localhost:3306/taskone?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
            dataSourceBuilder.username("root");
            dataSourceBuilder.password("ahmed");
            return dataSourceBuilder.build();

        }else if(database.equals("POSTGRES")){

            dataSourceBuilder.url("jdbc:postgresql://localhost:5432/TaskOne");
            dataSourceBuilder.username("postgres");
            dataSourceBuilder.password("ahmed");
            return dataSourceBuilder.build();
        }
        return dataSourceBuilder.build();
    }

    @Primary
    @Bean(name = "studentEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    entityManagerFactory(
            @Qualifier("entityManagerFactoryBuilder") EntityManagerFactoryBuilder builder,
            @Qualifier("DataSource") DataSource dataSource
    ) {
        HashMap<String, Object> properties = new HashMap<>();

        if(database.equals("MYSQL")){
            properties.put("hibernate.ddl-auto", "update");
            properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
            properties.put("hibernate.jdbc.lob.non_contextual_creation", "true");
        }else if( database.equals("POSTGRES") ){
            properties.put("hibernate.ddl-auto", "update");
            properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            properties.put("hibernate.jdbc.lob.non_contextual_creation", "true");
        }

        return builder
                .dataSource(dataSource)
                .properties(properties)
                .packages("com.example.demo.student.models")
                .persistenceUnit("Student")
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

    @Bean(name = "entityManagerFactoryBuilder")
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
    }


}
