package Aeroport;

public class Aeroport{
    private boolean piste_libre;
    private static Aeroport instance;
    public static Aeroport getInstance(){
        if(instance==null){
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (Aeroport.class){
                if(instance==null){
                    instance=new Aeroport();
                }
            }
        }
        return instance;
    }
    private Aeroport(){
        piste_libre=true;
    }
    public synchronized boolean autoriserADecoller(){
        if(piste_libre){
            piste_libre=false;
            return true;
        }
        return false;
    }
    public synchronized boolean liberer_piste(){
        if(!piste_libre){
            piste_libre=true;
            return true;
        }
        return false;
    }

}
