package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LoupTest {

  @Test
  public void test_constructeurVide() {
    Animal animal = new Loup(0, 30);
    assertEquals(30, animal.getPv(), "Le loup est censé avoir 30 pv");
    assertEquals(0, animal.getStockNourriture(), "Le loup est censé avoir 0 viande");
  }

  @Test
  void etreMort() {
    Animal animal = new Loup(0, 4);
    assertFalse(animal.etreMort(), "Le loup est censé être en vie");
    animal.passerUnJour();
    assertTrue(animal.etreMort(), "Le loup est censé être mort");
  }

  @Test
  void passerUnJour() {
    Animal animal = new Loup(2, 30);
    assertEquals(2, animal.getStockNourriture(), "L'animal est censé avoir 2 viandes");
    animal.passerUnJour();
    assertEquals(0, animal.getStockNourriture(), "L'animal est censé avoir 0 viande");
    animal.passerUnJour();
    assertEquals(26, animal.getPv(), "L'animal est censé avoir 26 pv");
  }

  @Test
  void stockerNourriture() {
    Animal animal = new Loup(0, 30);
    animal.stockerNourriture(5);
    assertEquals(5, animal.getStockNourriture(), "L'animal est censé avoir 5 viandes");
  }
}
