package org.example;

/** modelise un animal independamment de son type */
public interface Animal {

  /**
   * permet de savoir si un animal est mort
   *
   * @return true si l'animal est mort
   */
  boolean etreMort();

  /**
   * fait evoluer l'animal d'un jour l'oblige a se nourrir ou a perdre des points de vie
   *
   * @return true si l'animal est vivant a la fin du jour
   */
  boolean passerUnJour();

  /**
   * permet d'ajouter de la nourriture a son stock
   *
   * @param nourriture quantite de nourriture stockee
   */
  void stockerNourriture(int nourriture);

  /**
   * retourne les points de vie de l'animal
   *
   * @return points de vie de l'animal
   */
  public int getPv();

  /**
   * retourne le stocke de nourriture possede par l'animal
   *
   * @return nourriture de l'animal
   */
  public int getStockNourriture();
}
