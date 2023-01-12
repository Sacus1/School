import java.awt.*;
import java.io.*;
import javax.swing.*;

class Histogram {
  int[] data;
  short minValue;
  private GreyImage im;

  Histogram(GreyImage im) {
    minValue = im.getMin();
    data = new int[im.getMax() - minValue + 1];
    data[0] = im.getMin();
    data[data.length - 1] = im.getMax();
    this.im = im;
    for (int i = 0; i < im.getSizeData(); i++) {
      try {
        data[im.getPixel(i) - minValue]++;
      } catch (OutOfBoundException e) {
        System.out.println("Error: " + e.getMessage());
      }
    }
  }

  Histogram(GreyImage im, int size) {
    minValue = im.getMin();
    data = new int[size];
    data[0] = im.getMin();
    data[data.length - 1] = im.getMax();
    this.im = im;
    for (int i = 0; i < im.getSizeData(); i++) {
      try {
        data[im.getPixel(i) - minValue]++;
      } catch (OutOfBoundException e) {
        System.out.println("Error: " + e.getMessage());
      }
    }
  }

  int getValue(short v) {
    return data[v - minValue];
  }

  short getPeak() {
    short peak = minValue;
    for (int i = 0; i < data.length; i++) {
      if (data[i] > data[peak - minValue]) {
        peak = (short) (i + minValue);
      }
    }
    return peak;
  }

  void saveHisto(String filename) throws FileNotFoundException, IOException {
    FileOutputStream fileout = new FileOutputStream(filename);
    for (int i = 0; i < data.length; i++) {
      String tmp = minValue + i + " " + data[i] + "\n";
      fileout.write(tmp.getBytes());
    }

    fileout.close();
  }

  void draw() {
    JFrame frame = new JFrame("Histogram");
    JPanel panel =
        new JPanel() {
          @Override
          public void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();
            int max = 0;
            for (int datum : data) {
              if (datum > max) {
                max = datum;
              }
            }
            for (int i = 0; i < data.length; i++) {
              // write value
              g.drawLine(
                  i * width / data.length,
                  height - 50,
                  i * width / data.length,
                  height - 50 - data[i] * height / max);
              if (i % 5 == 0) g.drawString(i + "", i * width / data.length, height);
            }
          }
        };
    frame.add(panel);
    frame.setSize(500, 500);
    frame.setVisible(true);
  }
}
