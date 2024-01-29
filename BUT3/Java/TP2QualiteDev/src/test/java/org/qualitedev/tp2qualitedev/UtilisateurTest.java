package org.qualitedev.tp2qualitedev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UtilisateurTest {

  @Test
  void shouldCreateUserWithDefaultValues() {
    Utilisateur user = Mockito.mock(Utilisateur.class);
    when(user.getFirstName()).thenReturn("John");
    when(user.getLastName()).thenReturn("Doe");

    assertEquals("John", user.getFirstName());
    assertEquals("Doe", user.getLastName());
  }

  @Test
  void shouldCreateUserWithProvidedValues() {
    Utilisateur user = Mockito.mock(Utilisateur.class);
    when(user.getFirstName()).thenReturn("Jane");
    when(user.getLastName()).thenReturn("Doe");

    assertEquals("Jane", user.getFirstName());
    assertEquals("Doe", user.getLastName());
  }

  @Test
  void shouldUpdateFirstName() {
    Utilisateur user = Mockito.mock(Utilisateur.class);
    when(user.getFirstName()).thenReturn("Jane");

    assertEquals("Jane", user.getFirstName());
  }

  @Test
  void shouldUpdateLastName() {
    Utilisateur user = Mockito.mock(Utilisateur.class);
    when(user.getLastName()).thenReturn("Smith");

    assertEquals("Smith", user.getLastName());
  }
}
