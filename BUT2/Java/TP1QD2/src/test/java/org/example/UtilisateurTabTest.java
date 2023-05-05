package org.example;

import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class UtilisateurTabTest {
	@Test
	void testConstructor() {
		UtilisateurTab utilisateur = new UtilisateurTab("John", "Doe", "John.doe@mail.com", "20-11-2023", 10, 10, 10);
		assertThat(utilisateur.getPrenom()).isEqualTo("John");
		assertThat(utilisateur.getNom()).isEqualTo("Doe");
		assertThat(utilisateur.getEmail()).isEqualTo("John.doe@mail.com");
		assertThat(utilisateur.getDate()).isEqualTo("20-11-2023");
		assertThat(utilisateur.getNombreEtudiants()).isEqualTo(10);
		assertThat(utilisateur.getMoyenne()).isEqualTo(10);
		assertThat(utilisateur.getMediane()).isEqualTo(10);
	}


	@Test
	void testConstructorDateInvalid() {
		assertThatThrownBy(() -> new UtilisateurTab("John", "Doe", "John.doe@mail.com",
						"2020-11-32", 10, 10, 10)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void testConstructorStringOnly() {
		assertThatThrownBy(() -> new UtilisateurTab("1", "Doe", "John.doe@mail.com",
						"20-11-2023", 10, 10, 10)).isInstanceOf(IllegalArgumentException.class);
	}
	@Test
	void testConstructorStringOnly2() {
		assertThatThrownBy(() -> new UtilisateurTab("John", "1", "John.doe@mail.com",
						"20-11-2023", 10, 10, 10)).isInstanceOf(IllegalArgumentException.class);
	}

}
