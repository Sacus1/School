import outils3d.Dessinateur;

public class Boite extends Forme {
    private double largeur;
    private double hauteur;
    private double profondeur;
    
    public Boite(double largeur, double hauteur, double profondeur){
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.profondeur = profondeur;
    }

    public void instruire(Dessinateur d){
        super.instruire(d);
        d.dessinerParallelepipedeRectangle(largeur, hauteur, profondeur);
    }


    
}