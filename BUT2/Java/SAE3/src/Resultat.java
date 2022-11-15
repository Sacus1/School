public class Resultat {
	private final int[] score = new int[2];
  private final Membre[] membres = new Membre[2];
  private String videoPath;
  private String[] imagesPath;

  public Resultat(Membre membre1, Membre membre2, int score1, int score2, int nbImages) {
    this.membres[0] = membre1;
    this.membres[1] = membre2;
    this.score[0] = score1;
    this.score[1] = score2;
    this.imagesPath = new String[nbImages];
  }

  public void setVideoPath(String videoPath) {
    this.videoPath = videoPath;
  }
  public void addImagePath(String imagePath) {
    // TODO
  }

  public Membre GetGagnant(){
      // TODO
      return null;
  }

  public int GetScore(Membre membre){
      // TODO
      return 0;
  }

  public Membre[] GetMembres(){
      // TODO
      return null;
  }

  public String GetVideoPath(){
      // TODO
      return null;
  }

  public String[] GetImagesPath(){
      // TODO
      return null;
  }
}
