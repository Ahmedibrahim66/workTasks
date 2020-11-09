//package com.example.demo.configuration;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//
//import javax.persistence.EntityManagerFactory;
//import java.util.HashMap;
//
//@Configuration
//public class DataSource {
//
//
//    @Bean(name = "DataSource")
//    public javax.sql.DataSource mysqlDataSource() {
//
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//
//
//
//            dataSourceBuilder.url("jdbc:postgresql://localhost:5432/TaskTwo");
//            dataSourceBuilder.username("postgres");
//            dataSourceBuilder.password("ahmed");
//            return dataSourceBuilder.build();
//
//    }
//
//    @Primary
//    @Bean(name = "studentEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean
//    entityManagerFactory(
//            @Qualifier("entityManagerFactoryBuilder") EntityManagerFactoryBuilder builder,
//            @Qualifier("DataSource") javax.sql.DataSource dataSource
//    ) {
//        HashMap<String, Object> properties = new HashMap<>();
//
//
//            properties.put("hibernate.ddl-auto", "create");
//            properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//            properties.put("hibernate.jdbc.lob.non_contextual_creation", "true");
//
//        return builder
//                .dataSource(dataSource)
//                .properties(properties)
//                .packages("com.example.demo.models")
//                .persistenceUnit("User")
//                .build();
//
//    }
//
//    @Primary
//    @Bean(name = "studentTransactionManager")
//    public JpaTransactionManager customerTransactionManager(
//            @Qualifier("studentEntityManagerFactory") EntityManagerFactory
//                    studentEntityManagerFactory
//    ) {
//        return new JpaTransactionManager(studentEntityManagerFactory);
//    }
//
//    @Bean(name = "entityManagerFactoryBuilder")
//    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
//        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
//    }
//
//}
