package org.example;

public class Ecureil implements Animal {

  int noisette;
  int pointDeVie;

  public Ecureil() {
    this.pointDeVie = 5;
    this.noisette = 0;
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
   * fait evoluer l'animal d'un jour l'oblige a se nourrir ou a perdre des points de vie
   *
   * @return true si l'animal est vivant a la fin du jour
   */
  @Override
  public boolean passerUnJour() {
    if (noisette == 0) {
      pointDeVie -= 2;
    } else {
      noisette -= 1;
    }
    if (etreMort()) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * permet d'ajouter de la nourriture a son stock
   *
   * @param nourriture quantite de nourriture stockee
   */
  @Override
  public void stockerNourriture(int nourriture) {
    noisette += nourriture;
  }

  /**
   * retourne les points de vie de l'animal
   *
   * @return points de vie de l'animal
   */
  @Override
  public int getPv() {
    return pointDeVie;
  }

  /**
   * retourne le stocke de nourriture possede par l'animal
   *
   * @return nourriture de l'animal
   */
  @Override
  public int getStockNourriture() {
    return noisette;
  }

  @Override
  public String toString() {
    return "Écureuil - pv: " + pointDeVie + " noisettes :" + pointDeVie;
  }
}
