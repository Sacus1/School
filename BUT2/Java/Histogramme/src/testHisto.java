import java.io.IOException;

public class testHisto {
    public static void main(String[] args) throws IOException, OutOfBoundException, WrongSizeException {
        GreyImage im = GreyImage.loadPGM(args[0]+".pgm");
        Histogram histogram = new Histogram(im);
        histogram.saveHisto("histoAvant.csv");
        // negatif
        im.negative();
        im.writePGM(args[0] + " Negatif.pgm");
        histogram = new Histogram(im);
        histogram.saveHisto("histoNegatif.csv");
        // contraste
        im = GreyImage.loadPGM(args[0]+".pgm");
        im.adjustContrast(0, 255);
        im.writePGM(args[0] + " Contraste.pgm");
        histogram = new Histogram(im);
        histogram.saveHisto("histoContraste.csv");
        // egalisation
        im = GreyImage.loadPGM(args[0]+".pgm");
        im.equalize(0,255);
        im.writePGM(args[0] + " Equalized.pgm");
        histogram = new Histogram(im);
        histogram.saveHisto("histoEqualized.csv");
        // seiller
        im = GreyImage.loadPGM(args[0]+".pgm");
        im.seiller( 230);
        im.writePGM(args[0] + " Seiller.pgm");
        histogram = new Histogram(im);
        histogram.saveHisto("histoSeiller.csv");
        // convolution
        im = GreyImage.loadPGM(args[0]+".pgm");
        short[] w={(short)1,(short)1,(short)1,
                (short)1,(short)1,(short)1,
                (short)1,(short)1,(short)1}; // moyenne
        Mask M = new Mask(3,3,w);
        GreyImage img = im.convolve(M);
        img = img.convolve(M);
        img= img.convolve(M);
        img = img.convolve(M);
        img = img.convolve(M);
        img.writePGM(args[0] + " Convolution Moyenne*5.pgm");

        // convolution Sobel
        im = GreyImage.loadPGM(args[0]+".pgm");
        short [] w2={(short)-1,(short)-2,(short)-1,
                (short)0,(short)0,(short)0,
                (short)1,(short)2,(short)1};
        Mask M2 = new Mask(3,3,w2);
        img = im.convolve(M2);
        img.writePGM(args[0] + " Convolution Sobel.pgm");
        // convolution -1 16
        im = GreyImage.loadPGM(args[0]+".pgm");
        short [] w3={(short)-1,(short)-1,(short)-1,
                (short)-1,(short)16,(short)-1,
                (short)-1,(short)-1,(short)-1};
        Mask M3 = new Mask(3,3,w3);
        img = im.convolve(M3);
        img.writePGM(args[0] + " Convolution nette.pgm");
    }
}
