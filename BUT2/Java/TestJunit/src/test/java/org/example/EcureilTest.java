package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EcureilTest {

	/**
	 * test constructeur vide
	 */
	@Test
	void test_constructeurVide() {
		Animal animal = new Ecureil();
		assertEquals(5, animal.getPv(),"Un écureuil devrait avoir 5 points de vie");
		assertEquals(0, animal.getStockNourriture(),"Un écureuil devrait avoir 0 noisettes");
	}
	@Test
	void test_etreMort(){
		Animal animal = new Ecureil();
		assertFalse(animal.etreMort(),"Un écureuil devrait être en vie");
		animal.passerUnJour();
		assertFalse(animal.etreMort(),"Un écureuil devrait être en vie");
		animal.passerUnJour();
		assertFalse(animal.etreMort(),"Un écureuil devrait être en mort");
	}
	@Test
	void test_passerUnJour() {
		Animal animal = new Ecureil();
		animal.stockerNourriture(1);
		animal.passerUnJour();
		assertEquals(5, animal.getPv(),"Un écureuil devrait avoir 5 points de vie");
		assertEquals(0, animal.getStockNourriture(),"Un écureuil devrait avoir 0 noisettes");
		animal.passerUnJour();
		assertEquals(3, animal.getPv(),"Un écureuil devrait avoir 3 points de vie");
		assertEquals(0, animal.getStockNourriture(),"Un écureuil devrait avoir 0 noisettes");
		assertTrue(animal.passerUnJour(),"Un écureuil devrait être vivant");
		assertFalse(animal.passerUnJour(),"Un écureuil devrait être mort");
	}

	@Test
	void test_stockerNourriture() {
		Animal animal = new Ecureil();
		animal.stockerNourriture(1);
		assertEquals(1, animal.getStockNourriture(),"Un écureuil devrait avoir 1 noisette");
	}
}
