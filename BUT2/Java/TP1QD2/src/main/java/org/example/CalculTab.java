package org.example;

import java.util.Collections;
import java.util.List;

// Somme des éléments d'un tableau d'entiers
public class CalculTab {
  private final List<Float> tab;

  public CalculTab(List<Float> tab) {
    this.tab = tab;
  }

  public float calculMoyenne() throws EmptyException {
    if (tab == null || tab.size() == 0) throw new EmptyException();
    float moyenne = tab.stream().reduce(0f, Float::sum);
    moyenne /= tab.size();
    return moyenne;
  }

  public float calculMediane() throws EmptyException {
    if (tab == null || tab.size() == 0) throw new EmptyException();
    return tab.get(tab.size() / 2);
  }

  public void trierTableau() {
    Collections.sort(tab);
  }

  @Override
  public String toString() {
    return tab.toString();
  }
}
