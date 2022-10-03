// Fichier  : Saisie.java
// Auteur   : P. Even (IUT de St Die, Dpt Info)
// Contexte : BUT INFO 2021/2022 / Ressource "Developpement oriente objets"

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;


/** Utilitaire de saisie de valeurs au clavier. */
public class Saisie
{
  /** Saisie d'une valeur entiere. */
  public int entier ()
  {
    Integer emballage = null;
    // Ouverture des flots de lecture
    BufferedReader flot = new BufferedReader (
      new InputStreamReader (System.in));
    while (emballage == null)
    {
      try
      {
        // Conversion
        emballage = Integer.valueOf (flot.readLine ());
      }
      catch (NumberFormatException e)
      {
        System.out.println ("Entrer un entier SVP !!!");
      }
      // Pas de traitement des erreurs d'entree sortie
      catch (IOException e) { }
    }
    return (emballage.intValue ());
  }

  /** Saisie d'une valeur reelle. */
  public double reel ()
  {
    Double emballage = null;
    // Ouverture des flots de lecture
    BufferedReader flot = new BufferedReader (
      new InputStreamReader (System.in));
    while (emballage == null)
    {
      try
      {
        // Conversion
        emballage = Double.valueOf (flot.readLine ());
      }
      catch (NumberFormatException e)
      {
        System.out.println ("Entrer un reel SVP !!!");
      }
      // Pas de traitement des erreurs d'entree sortie
      catch (IOException e) { }
    }
    return (emballage.doubleValue ());
  }

  /** Saisie d'un caractere. */
  public char caractere ()
  {
    Character emballage = null;
    // Ouverture des flots de lecture
    BufferedReader flot = new BufferedReader (
      new InputStreamReader (System.in));
    while (emballage == null)
    {
      try
      {
        // Extraction du caractere
        String chaine = flot.readLine ();
        emballage = Character.valueOf (chaine.charAt (0));
      }
      // Pas de traitement des erreurs d'entree sortie
      catch (IOException e) { }
      catch (Exception e)
      {
        System.out.println ("Entrer un caractere SVP !!!");
      }
    }
    return (emballage.charValue ());
  }

  /** Saisie d'une chaine de caracteres. */
  public String chaine ()
  {
    String chaine = null;
    // Ouverture des flots de lecture
    BufferedReader flot = new BufferedReader (
      new InputStreamReader (System.in));
    while (chaine == null)
    {
      try
      {
        // Extraction de la chaine de caracteres
        chaine = new String (flot.readLine ());
      }
      // Pas de traitement des erreurs d'entree sortie
      catch (IOException e) { }
      catch (Exception e)
      {
        System.out.println ("Entrer une chaine SVP !!!");
      }
    }
    return (chaine);
  }
}
