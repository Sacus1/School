class Salle{
    public Salle(){}
    public void allume(){
        System.out.println("Lumière allumée");
    }
    public void eteint(){
        System.out.println("Lumière éteinte");
    }
}
class Ordinateur
{
    public Ordinateur(){}
    public void allume(){
        System.out.println("Ordinateur allumé");
    }
    public void eteint(){
        System.out.println("Ordinateur éteint");
    }
}
class VideoProjecteur
{
    public VideoProjecteur(){}
    public void allume(){
        System.out.println("Projecteur allumé");
    }
    public void eteint(){
        System.out.println("Projecteur éteint");
    }
    public void setFullScreenMode(){
        System.out.println("Mode plein écran activé");
    }
}
class SystemAudio
{
    protected int volume;
    public SystemAudio(){
        volume = 5;
    }
    public void allume(){
        System.out.println("Système audio allumé");
    }
    public void eteint(){
        System.out.println("Système audio éteint");
    }
    public void setVolume(int volume){
        this.volume = volume;
    }
}
class Facade{
    public Salle salle;
    public Ordinateur ordinateur;
    public VideoProjecteur videoProjecteur;
    public SystemAudio systemAudio;
    public Facade(){
        salle = new Salle();
        ordinateur = new Ordinateur();
        videoProjecteur = new VideoProjecteur();
        systemAudio = new SystemAudio();
    }
    public void allume(){
        salle.allume();
        ordinateur.allume();
        videoProjecteur.allume();
        systemAudio.allume();
        videoProjecteur.setFullScreenMode();
    }
    public void eteint(){
        salle.eteint();
        ordinateur.eteint();
        videoProjecteur.eteint();
        systemAudio.eteint();
    }
}
