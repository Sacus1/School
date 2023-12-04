package com.school.qualitedevtd2;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Builder
@Getter
@ToString
public class Personne implements Serializable {
	// champs métier
	String nom;
	String prenom;
	int age;
}
