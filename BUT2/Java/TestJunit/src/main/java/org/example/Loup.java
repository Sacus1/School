package org.example;

public class Loup implements Animal {
  private int viande;
  private int pointDeVie;

  public Loup(int viande, int pointDeVie) {
    this.viande = viande;
    this.pointDeVie = pointDeVie;
  }

  public Loup(int pointDeVie) {
    this(0, pointDeVie);
  }

  public Loup() {
    this(0, 30);
  }

  /**
   * permet de savoir si un animal est mort
   *
   * @return true si l'animal est mort
   */
  @Override
  public boolean etreMort() {
    return pointDeVie <= 0;
  }

  /**
   * fait évoluer l’animal d’un jour l’oblige à se nourrir ou à perdre des points de vie
   *
   * @return true si l’animal est vivant à la fin du jour
   */
  @Override
  public boolean passerUnJour() {
    if (viande > 0) {
      viande--;
      viande /= 2;
    } else {
      pointDeVie -= 4;
      // le loup perd la moitié de sa nourriture
      viande /= 2;
    }
    return !etreMort();
  }

  /**
   * permet d’ajouter de la nourriture à son stock
   *
   * @param nourriture quantité de nourriture stockée
   */
  @Override
  public void stockerNourriture(int nourriture) {
    viande += nourriture;
  }

  /**
   * retourne les points de vie de l’animal
   *
   * @return points de vie de l’animal
   */
  @Override
  public int getPv() {
    return pointDeVie;
  }

  /**
   * retourne-le stocker de nourriture possedé par l’animal
   *
   * @return nourriture de l’animal
   */
  @Override
  public int getStockNourriture() {
    return viande;
  }

  @Override
  public String toString() {
    return "Loup - " + "pv :" + pointDeVie + "viande :" + viande;
  }
}
