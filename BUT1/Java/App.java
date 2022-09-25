import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class App {

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrameSquelette f = new FrameSquelette("TP3");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.pack();
                f.setVisible(true);
            }
        });
    }
}

class FrameSquelette extends JFrame implements MouseInputListener {

    private Border raisedbevel, loweredbevel, compound;
    private Pan p1;
    private JPanel p2, p3, p4;
    private JLabel CoupL, NBCoupL, PairsL, NBPairsL;
    private Image[] im = new Image[10];
    private int posX = -1, posY = -1, numclic = 0, nbpairs = 0;
    private int[] order = { 10, 9, 1, 2, 4, 2, 7, 6, 5, 10, 3, 3, 8, 9, 1, 8, 6, 5, 4, 7 };
    private boolean[] trouve = { false, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false };

    public boolean isFull() {
        for (boolean t : trouve) {
            if (!t)
                return false;
        }
        return true;
    }

    public void buildGui() {
        p1 = new Pan();
        p1.setPreferredSize(new Dimension(120 * 5 + 5, 120 * 4 + 5));
        add(p1, BorderLayout.CENTER);
        p1.repaint();
        p2 = new JPanel(new BorderLayout(2, 1));
        p3 = new JPanel();
        p4 = new JPanel();
        CoupL = new JLabel("Nombre de coup : ");
        CoupL.setBorder(compound);
        NBCoupL = new JLabel("0");
        NBCoupL.setBorder(compound);

        p3.add(CoupL);
        p3.add(NBCoupL);
        PairsL = new JLabel("Nombre de pairs decouverts : ");
        PairsL.setBorder(compound);
        NBPairsL = new JLabel("0");
        NBPairsL.setBorder(compound);
        p4.add(PairsL);
        p4.add(NBPairsL);
        p2.add(p3, BorderLayout.NORTH);
        p2.add(p4, BorderLayout.SOUTH);
        add(p2, BorderLayout.SOUTH);
        setFocusable(true);
        p1.addMouseListener(this);
    }

    public FrameSquelette(String titre) {
        super(titre);
        System.out.println(order.length + " " + trouve.length);
        raisedbevel = BorderFactory.createRaisedBevelBorder();
        loweredbevel = BorderFactory.createLoweredBevelBorder();
        compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
        buildGui();
    }

    public int[] shuffle(int[] o) {
        int[] ord = new int[o.length];
        for (int i = 2; i < o.length; i++) {
            int r = (int) Math.random() * 3;
            int n = r == 2 ? o[i + 1] : (r == 1 ? o[i] : o[i - 1]);
            ord[i] = n;
        }
        return ord;
    }

    public void EndMenu() {
        remove(p2);
        p2 = new JPanel(new BorderLayout(1, 2));
        p3 = new JPanel();
        p4 = new JPanel(new BorderLayout(2, 1));
        JButton Oui = new JButton("Oui");
        Oui.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                remove(p2);
                remove(p1);
                validate();
                repaint();
                buildGui();
                posX = -1;
                posY = -1;
                numclic = 0;
                nbpairs = 0;
                for (int i = 0; i < trouve.length; i++) {
                    trouve[i] = false;
                }
                order = shuffle(order);
                validate();
                repaint();
                requestFocus();
            }

        });
        p4.add(Oui, BorderLayout.NORTH);
        JButton Non = new JButton("Non");
        Non.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }

        });
        p4.add(Non, BorderLayout.SOUTH);
        JLabel ScoreFinal = new JLabel("Vous avez gagner en " + numclic + " coup voulez vous rejouer");
        p3.add(ScoreFinal);
        p2.add(p3, BorderLayout.WEST);
        p2.add(p4, BorderLayout.EAST);
        add(p2, BorderLayout.SOUTH);
        validate();
        repaint();
    }

    class Pan extends JPanel {
        Image fond;

        public Pan() {
            try {
                fond = (ImageIO.read(new File("/home/sacus/Cours/Java/IHM TP3/Image/fond.png")));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            for (int i = 0; i < im.length; i++) {
                try {
                    im[i] = (ImageIO.read(new File("/home/sacus/Cours/Java/IHM TP3/Image/im" + (i + 1) + ".png")));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        public void paintComponent(Graphics g) {
            super.paintComponents(g);
            for (int j = 0; j < 5; j++) {
                for (int i = 0; i < 4; i++) {
                    if ((j == posX && i == posY) || trouve[i * 5 + j])
                        g.drawImage(im[order[i * 5 + j] - 1], j * 120 + 5, i * 120 + 5, null);
                    else
                        g.drawImage(fond, j * 120 + 5, i * 120 + 5, null);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Image img1 = null;
        int ancienPosX = posX;
        int ancienPosY = posY;
        if (posX >= 0 && posY > -1)
            img1 = im[order[posY * 5 + posX] - 1];
        posX = e.getX() / 120;
        posY = e.getY() / 120;
        if (posX >= 0 && posY > -1 && trouve[posY * 5 + posX])
            return;
        if (img1 != null && img1.equals(im[order[posY * 5 + posX] - 1])) {
            trouve[posY * 5 + posX] = true;
            trouve[ancienPosY * 5 + ancienPosX] = true;
            nbpairs += 1;
            NBPairsL.setText("" + nbpairs);
        }
        numclic += 1;
        NBCoupL.setText("" + numclic);
        if (isFull()) {
            EndMenu();
        }
        p1.repaint();

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}