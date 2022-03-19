// J'appose ici mon en-tete de fichiers ...

import outils3d.Fenetre3D;

/** Application graphique. */
public class MonAppli {
  /** Construit et dessine une scene 3D. */
  public static void main(String[] args) {
    Boite boite = new Boite(0.5, 0.25, 0.5);
    boite.colorier(0, .5, .5);
    new Fenetre3D(boite);
  }
}
