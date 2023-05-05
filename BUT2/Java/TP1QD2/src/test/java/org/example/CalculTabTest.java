package org.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CalculTabTest {

  @Test
  void calculMoyenne() throws EmptyException {
    CalculTab calculTab = new CalculTab(List.of(1f, 2f, 3f, 4f, 5f));
    assertThat(calculTab.calculMoyenne()).isEqualTo(3);
  }

  @Test
  void calculMoyenneVide() {
    CalculTab calculTab = new CalculTab(List.of());
    assertThatThrownBy(calculTab::calculMoyenne).isInstanceOf(EmptyException.class);
  }

  @Test
  void calculMoyenneNull() {
    CalculTab calculTab = new CalculTab(null);
    assertThatThrownBy(calculTab::calculMoyenne).isInstanceOf(EmptyException.class);
  }

  @Test
  void calculMoyenneUnElement() throws EmptyException {
    CalculTab calculTab = new CalculTab(List.of(1f));
    assertThat(calculTab.calculMoyenne()).isEqualTo(1);
  }

  @Test
  void calculMediane() throws EmptyException {
    CalculTab calculTab = new CalculTab(List.of(1f, 2f, 3f, 4f, 5f));
    assertThat(calculTab.calculMediane()).isEqualTo(3);
  }

  @Test
  void calculMedianeVide() {
    CalculTab calculTab = new CalculTab(List.of());
    assertThatThrownBy(calculTab::calculMediane).isInstanceOf(EmptyException.class);
  }

  @Test
  void calculMedianeNull() {
    CalculTab calculTab = new CalculTab(null);
    assertThatThrownBy(calculTab::calculMediane).isInstanceOf(EmptyException.class);
  }

  @Test
  void calculMedianeUnElement() throws EmptyException {
    CalculTab calculTab = new CalculTab(List.of(1f));
    assertThat(calculTab.calculMediane()).isEqualTo(1);
  }

  @Test
  void trierTableau() {
    CalculTab calculTab = new CalculTab(new ArrayList<>(List.of(5f, 1f, 6f, 3f, 4f)));
    calculTab.trierTableau();
    assertThat(calculTab.toString()).isEqualTo("[1.0, 3.0, 4.0, 5.0, 6.0]");
  }
}
