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

        // convolution Sobel horizontal
        GreyImage imH = GreyImage.loadPGM(args[0]+".pgm");
        short [] w2={(short)-1,(short)-2,(short)-1,
                (short)0,(short)0,(short)0,
                (short)1,(short)2,(short)1};
        Mask M2 = new Mask(3,3,w2);
        imH = imH.convolve(M2);
        imH.writePGM(args[0] + " Convolution Sobel horizontal.pgm");
        // convolution Sobel vertical
        GreyImage imV = GreyImage.loadPGM(args[0]+".pgm");
        short [] w3={(short)-1,(short)0,(short)1,
                (short)-2,(short)0,(short)2,
                (short)-1,(short)0,(short)1};
        Mask M3 = new Mask(3,3,w3);
        imV = imV.convolve(M3);
        imV.writePGM(args[0] + " Convolution Sobel vertical.pgm");
        // seuillage Sobel norme
        im.seiller( 100);
        im.writePGM(args[0] + " Convolution Sobel norme seuillé.pgm");
        // convolution -1 16
        im = GreyImage.loadPGM(args[0]+".pgm");
        short [] w5={(short)-1,(short)-1,(short)-1,
                (short)-1,(short)16,(short)-1,
                (short)-1,(short)-1,(short)-1};
        Mask M5 = new Mask(3,3,w5);
        img = im.convolve(M5);
        img.writePGM(args[0] + " Convolution nette.pgm");
        // bruit aleatoire
        im = GreyImage.loadPGM(args[0]+".pgm");
        im = im.addRandomNoise(.2);
        im.writePGM(args[0] + " Bruit aleatoire.pgm");
        // bruit gaussien
        im = GreyImage.loadPGM(args[0]+".pgm");
        im = im.addGaussianNoise(0, 25);
        im.writePGM(args[0] + " Bruit gaussien.pgm");
        // gradient
        im = GreyImage.loadPGM(args[0]+".pgm");
        im = im.gradient(imV,imH);
        im.writePGM(args[0] + " Gradient.pgm");
// seuillage gradient
        im.seiller( 100);
        im.writePGM(args[0] + " Gradient seuillé.pgm");
        // test filtre
        im = GreyImage.loadPGM(args[0]+".pgm");
        System.out.println("Original :" + im.computeNMSE(im));
        GreyImage bruit = im.addGaussianNoise(0, 15);
        System.out.println("bruit : " + im.computeNMSE(bruit));
        // Filtre passe bas 3x3
        short[] Fw1={(short)1,(short)1,(short)1,
                (short)1,(short)1,(short)1,
                (short)1,(short)1,(short)1}; // moyenne
        Mask F1 = new Mask(3,3,Fw1);
        GreyImage imgF1 = bruit.convolve(F1);
        System.out.println("Filtre passe bas 3x3 gaussien :"+im.computeNMSE(imgF1));
        // Filtre passe bas 5x5
        short[] Fw2={(short)1,(short)1,(short)1,(short)1,(short)1,
                (short)1,(short)1,(short)1,(short)1,(short)1,
                (short)1,(short)1,(short)1,(short)1,(short)1,
                (short)1,(short)1,(short)1,(short)1,(short)1,
                (short)1,(short)1,(short)1,(short)1,(short)1}; // moyenne
        Mask F2 = new Mask(5,5,Fw2);
        GreyImage imgF2 = bruit.convolve(F2);
        System.out.println("Filtre passe bas 5x5 gaussien :"+im.computeNMSE(imgF2));
        // filtre median 3x3
        GreyImage imgF3 = bruit.medianFilter(3);
        System.out.println("Filtre median 3x3 gaussien :"+im.computeNMSE(imgF3));
        // filtre median 5x5
        GreyImage imgF4 = bruit.medianFilter(5);
        System.out.println("Filtre median 5x5 gaussien :"+im.computeNMSE(imgF4));
        // bruit gaussien 0;30
        bruit = im.addGaussianNoise(0, 30);
        System.out.println("bruit 0;30 : " + im.computeNMSE(bruit));
        // Filtre passe bas 3x3
        imgF1 = bruit.convolve(F1);
        System.out.println("Filtre passe bas 3x3 gaussien 0;30 :"+im.computeNMSE(imgF1));
        // Filtre passe bas 5x5
        imgF2 = bruit.convolve(F2);
        System.out.println("Filtre passe bas 5x5 gaussien 0;30 :"+im.computeNMSE(imgF2));
        // filtre median 3x3
        imgF3 = bruit.medianFilter(3);
        System.out.println("Filtre median 3x3 gaussien 0;30 :"+im.computeNMSE(imgF3));
        // filtre median 5x5
        imgF4 = bruit.medianFilter(5);
        System.out.println("Filtre median 5x5 gaussien 0;30 :"+im.computeNMSE(imgF4));
        // bruit aleatoire 5%
        bruit = im.addRandomNoise(.05);
        System.out.println("bruit 5% : " + im.computeNMSE(bruit));
        // Filtre passe bas 3x3
        imgF1 = bruit.convolve(F1);
        System.out.println("Filtre passe bas 3x3 aleatoire 5% :"+im.computeNMSE(imgF1));
        // Filtre passe bas 5x5
        imgF2 = bruit.convolve(F2);
        System.out.println("Filtre passe bas 5x5 aleatoire 5% :"+im.computeNMSE(imgF2));
        // filtre median 3x3
        imgF3 = bruit.medianFilter(3);
        System.out.println("Filtre median 3x3 aleatoire 5% :"+im.computeNMSE(imgF3));
        // filtre median 5x5
        imgF4 = bruit.medianFilter(5);
        System.out.println("Filtre median 5x5 aleatoire 5% :"+im.computeNMSE(imgF4));
        // bruit aleatoire 15%
        bruit = im.addRandomNoise(.15);
        System.out.println("bruit 15% : " + im.computeNMSE(bruit));
        // Filtre passe bas 3x3
        imgF1 = bruit.convolve(F1);
        System.out.println("Filtre passe bas 3x3 aleatoire 15% :"+im.computeNMSE(imgF1));
        // Filtre passe bas 5x5
        imgF2 = bruit.convolve(F2);
        System.out.println("Filtre passe bas 5x5 aleatoire 15% :"+im.computeNMSE(imgF2));
        // filtre median 3x3
        imgF3 = bruit.medianFilter(3);
        System.out.println("Filtre median 3x3 aleatoire 15% :"+im.computeNMSE(imgF3));
        // filtre median 5x5
        imgF4 = bruit.medianFilter(5);
        System.out.println("Filtre median 5x5 aleatoire 15% :"+im.computeNMSE(imgF4));

    }
}
