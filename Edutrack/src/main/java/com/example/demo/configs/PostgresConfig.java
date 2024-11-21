// package com.example.demo.configs;

// import org.springframework.beans.factory.annotation.Qualifier;
// import org.springframework.boot.jdbc.DataSourceBuilder;
// import org.springframework.boot.autoconfigure.domain.EntityScan;
// import org.springframework.boot.context.properties.ConfigurationProperties;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Primary;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
// import org.springframework.orm.jpa.JpaTransactionManager;
// import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
// import org.springframework.transaction.PlatformTransactionManager;
// import org.springframework.transaction.annotation.EnableTransactionManagement;

// import jakarta.persistence.EntityManagerFactory;
// import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
// import javax.sql.DataSource;

// @Configuration
// @EnableTransactionManagement
// @EnableJpaRepositories(
//     basePackages = "com.example.demo.repositories.postgres",
//     entityManagerFactoryRef = "postgresEntityManagerFactory",
//     transactionManagerRef = "postgresTransactionManager"
// )
// @EntityScan(basePackages = "com.example.demo.model.postgres")
// public class PostgresConfig {

//     @Primary
//     @Bean(name = "postgresDataSource")
//     @ConfigurationProperties(prefix = "spring.datasource")
//     public DataSource dataSource() {
//         return DataSourceBuilder.create().build();
//     }

//     @Primary
//     @Bean(name = "postgresEntityManagerFactory")
//     public LocalContainerEntityManagerFactoryBean entityManagerFactory(
//             EntityManagerFactoryBuilder builder,
//             @Qualifier("postgresDataSource") DataSource dataSource) {
//         return builder
//                 .dataSource(dataSource)
//                 .packages("com.example.demo.model.postgres")
//                 .persistenceUnit("postgres")
//                 .build();
//     }

//     @Primary
//     @Bean(name = "postgresTransactionManager")
//     public PlatformTransactionManager transactionManager(
//             @Qualifier("postgresEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//         return new JpaTransactionManager(entityManagerFactory);
//     }
// }
 