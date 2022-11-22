package Aeroport;

class Avion extends Thread {
    private String nom;
    private Aeroport a;
    final int TEMPS_DECOLLAGE = 1000;
    public Avion(String s){
        nom=s;
    }
    public void run(){
        a=Aeroport.getInstance();
        try {
            System.out.println("Je suis avion " + nom + " sur aeroport " + a + " en attente de decollage");
            while (!a.autoriserADecoller())
                Thread.sleep(100);
            System.out.println("Je suis avion " + nom + " sur aeroport " + a + " en train de decoller");
            Thread.sleep(TEMPS_DECOLLAGE);
            System.out.println("Je suis avion " + nom + " sur aeroport " + a + " a fini de decoller");
            a.liberer_piste();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
