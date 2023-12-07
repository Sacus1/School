package com.example.accessingdatajpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccessingDataJpaApplication {

  private static final Logger log = LoggerFactory.getLogger(AccessingDataJpaApplication.class);

  public static void main(final String[] args) {
    SpringApplication.run(AccessingDataJpaApplication.class);
  }

  @Bean
  public CommandLineRunner demo(final CustomerRepository repository) {
    return (args) -> {
      // save a few customers
      repository.save(new Customer("Jack", "Bauer"));
      repository.save(new Customer("Chloe", "O'Brian"));
      repository.save(new Customer("Kim", "Bauer"));
      repository.save(new Customer("David", "Palmer"));
      repository.save(new Customer("Michelle", "Dessler"));

      // fetch all customers
      log.info("Customers found with findAll():");
      log.info("-------------------------------");
      repository.findAll().forEach(customer -> log.info(customer.toString()));
      log.info("");

      // fetch an individual customer by ID
      final Customer customer = repository.findById(1L);
      log.info("Customer found with findById(1L):");
      log.info("--------------------------------");
      log.info(customer.toString());
      log.info("");

      // fetch customers by last name
      log.info("Customer found with findByLastName('Bauer'):");
      log.info("--------------------------------------------");
      repository.findByLastName("Bauer").forEach(bauer -> log.info(bauer.toString()));
      log.info("");
    };
  }

  private void insertFourEmployees(final EmployeeRepository repository) {
    repository.save(new Employee("Dalia", "Abo Sheasha"));
    repository.save(new Employee("Trisha", "Gee"));
    repository.save(new Employee("Helen", "Scott"));
    repository.save(new Employee("Mala", "Gupta"));
  }

  @Bean
  public CommandLineRunner run(final EmployeeRepository repository) {
    return (args) -> {
      insertFourEmployees(repository);
      log.info("Employees found with findAll():");
      log.info("-------------------------------");
      repository.findAll().forEach(employee -> log.info(employee.toString()));
    };
  }
}
