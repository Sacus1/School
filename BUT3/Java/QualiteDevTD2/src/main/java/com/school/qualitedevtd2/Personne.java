package com.school.qualitedevtd2;

import java.io.Serializable;
import lombok.*;

@Builder
@Getter
@ToString
public class Personne implements Serializable {
  // champs métier
  String nom;
  String prenom;
  int age;
}
