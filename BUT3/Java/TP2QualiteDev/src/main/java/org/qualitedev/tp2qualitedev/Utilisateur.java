package org.qualitedev.tp2qualitedev;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

@Entity
public class Utilisateur {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @RestResource(exported = false)
  private long id;

  @Setter @Getter private String firstName;
  @Setter @Getter private String lastName;

  public Utilisateur(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Utilisateur() {
    this.firstName = "John";
    this.lastName = "Doe";
  }
}
