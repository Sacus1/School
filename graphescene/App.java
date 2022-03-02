import outils3d.Fenetre3D;

public class App {
    public static void main(String[] args) throws Exception {
        Scene b = new Boite(0.2, 0.2, 0.2);
        ((Boite) b).colorier(.5, 0, 0);
        Scene c = new Cercle(.3);
        ((Cercle) c).colorier(0, 0.5, 0);
        Decalage s = new Decalage(c, b, 0, -0.5, 0);
        Bascule basc = new Bascule(c, b, 0, 0, 0, 45);
        AssemblageCompose ac = new AssemblageCompose(s, basc);
        new Fenetre3D(ac);
    }
}
