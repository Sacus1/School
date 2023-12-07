package com.example.accessingdatajpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Employee {
  @Id @GeneratedValue private Long id;
  private String firstName;
  private String lastName;

  public Employee() {}

  public Employee(final String firstName, final String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  @Override
  public String toString() {
    return "Employee{"
        + "id="
        + this.id
        + ", firstName='"
        + this.firstName
        + '\''
        + ", lastName='"
        + this.lastName
        + '\''
        + '}';
  }
}
