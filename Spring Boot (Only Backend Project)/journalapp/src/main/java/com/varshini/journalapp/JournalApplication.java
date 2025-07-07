package com.varshini.journalapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;

@SpringBootApplication
@EnableTransactionManagement // Spring annotation used to enable Spring's annotation-driven transaction
								// management (like @Transactional) in your application, searches for the method
								// with @Transcational
public class JournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);

	}

    // We have to create a bean to say that all work of PlatformTransactionManager(interface) is actually done by JpaTransactionManager
    // The @Bean method creates and registers a JpaTransactionManager for transaction management in a Spring Boot application using JPA. 
    // It takes an  EntityManagerFactory(MongoDatabaseFactory in MongoDB) as input to manage transactions specifically forJPA-based operations, ensuring proper handling of commit and rollback operations.
    @Bean
    PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}

}
//PlatformTransactionManager -  is a Spring Framework interface used for managing transactions across different types of data sources. implemneted by
//JpaTransactionManager which  is a Spring-provided class that manages transactions when you're using JPA (like Hibernate) with a relational database (e.g., MySQL, PostgreSQL).

//PlatformTransactionManager - starts , mamages transaction and all rolling back is done by this.