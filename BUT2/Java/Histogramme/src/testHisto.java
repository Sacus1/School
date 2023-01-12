import java.io.IOException;

public class testHisto {
  public static void main(String[] args)
      throws IOException, OutOfBoundException, WrongSizeException {
    GreyImage im = GreyImage.loadPGM(args[0] + ".pgm");
    Histogram histogram = new Histogram(im);
    histogram.saveHisto("histoAvant.csv");
    // negatif
    im.negative();
    im.writePGM(args[0] + " Negatif.pgm");
    histogram = new Histogram(im);
    histogram.saveHisto("histoNegatif.csv");
    // contraste
    im = GreyImage.loadPGM(args[0] + ".pgm");
    im.adjustContrast(0, 255);
    im.writePGM(args[0] + " Contraste.pgm");
    histogram = new Histogram(im);
    histogram.saveHisto("histoContraste.csv");
    // egalisation
    im = GreyImage.loadPGM(args[0] + ".pgm");
    im.equalize(0, 255);
    im.writePGM(args[0] + " Equalized.pgm");
    histogram = new Histogram(im);
    histogram.saveHisto("histoEqualized.csv");
    // seiller
    im = GreyImage.loadPGM(args[0] + ".pgm");
    im.seiller(230);
    im.writePGM(args[0] + " Seiller.pgm");
    histogram = new Histogram(im);
    histogram.saveHisto("histoSeiller.csv");
  }
}
