/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthony.liot.arkanoid.arkaeditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author loup626
 */
public class ArkaEditPane extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    ArrayList<Brique> briques;
    private ImageIcon fond;
    private int hautIcon;
    private int largIcon;
    private ArkaEdit ae;
    private ImageIcon brique0;
    private ImageIcon brique1;
    private ImageIcon brique2;
    private ImageIcon brique3;
    private ImageIcon brique4;
    private ImageIcon brique5;
    private ArkaEditPane.Brique currentBrique;

    public ArkaEditPane(ArkaEdit ae) {
        this.ae = ae;
        briques = new ArrayList<Brique>();
        fond = new ImageIcon(ae.getClass().getResource("/images/fond.png"));
        hautIcon = fond.getIconWidth();
        largIcon = fond.getIconHeight();
        this.setLayout(new BorderLayout());

        for (int i = 0; i < 288; i++) {
            Brique b = new Brique(this, i, 0);
            briques.add(b);
        }

        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        this.addMouseListener(this);

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);

        Dimension taille = getSize();
        //System.out.println(taille);
        int x = 0, y = 0;
        // tant que l'ecran n'est pas totalement recouvert sur x et y
        while (y < taille.height) {
            while (x < taille.width) {
                // on le dessine 
                g2d.drawImage(fond.getImage(), x, y, null);
                x += largIcon;
            }
            x = 0;
            y += hautIcon;
        }

        for (Brique b : briques) {
            g2d.drawImage(b.image.getImage(), b.getHautX(), b.getHautY(), null);
            g2d.drawString(String.valueOf(b.resistance), b.getHautX() + 15, b.getHautY() + 15);
        }
    }

    public int difference() {
        int tailleScreen = this.getSize().width - 2 * 20;
        int largeurBriqueMAX = (24 - 1) * 40;
        return (int) (tailleScreen - largeurBriqueMAX) / 2;
    }

    public void Save() {
        String s;
        do {
            s = (String) JOptionPane.showInputDialog(ae, "Nom de la sauvegarde!", "Save", 3);
        } while (s.length() == 0);

        new writerAlbum(briques, s);
        System.exit(0);
    }

    public class Brique extends JComponent {

        protected int resistance;
        protected ImageIcon image;
        protected ArrayList<ImageIcon> images;
        private ArkaEditPane aep;
        private int DISTANCEY = 40;
        private int hauteur;
        private int largeur;
        private int pos;
        protected int x;
        protected int y;

        public Brique(ArkaEditPane aep, int pos, int resistance) {
            images = new ArrayList<ImageIcon>();

            this.aep = aep;
            this.pos = pos;

            y = (pos / 24);
            x = (pos % 24);


            brique0 = new ImageIcon(ae.getClass().getResource("/images/brique0.gif"));
            brique1 = new ImageIcon(ae.getClass().getResource("/images/brique1.gif"));
            brique2 = new ImageIcon(ae.getClass().getResource("/images/brique2.gif"));
            brique3 = new ImageIcon(ae.getClass().getResource("/images/brique3.gif"));
            brique4 = new ImageIcon(ae.getClass().getResource("/images/brique4.gif"));
            brique5 = new ImageIcon(ae.getClass().getResource("/images/brique5.gif"));
            hauteur = brique0.getIconHeight();
            largeur = brique0.getIconWidth();
            images.add(brique0);
            images.add(brique1);
            images.add(brique2);
            images.add(brique3);
            images.add(brique4);
            images.add(brique5);


            this.resistance = resistance;
            this.image = brique0;

        }

        public void mouseClicked(MouseEvent e) {
            resistance =ae.currentIndex();
            this.image = images.get(resistance);
            System.out.println("clic: "+resistance);
            aep.repaint();
        }

        int getHautX() {
            return aep.difference() + x * largeur;
        }

        int getHautY() {
            return DISTANCEY + y * hauteur;

        }

        public Rectangle surface() {
            return new Rectangle(aep.difference() + x * largeur, DISTANCEY + y * hauteur, largeur, hauteur);
        }
    }

    public void mouseClicked(MouseEvent e) {
        for (Brique b : briques) {
            if (b.surface().contains(e.getPoint())) {
                b.resistance=ae.currentIndex();
                if (b.resistance > 5) {
                    b.resistance = 0;
                }
                b.image = b.images.get(b.resistance);
                //System.out.println("clic ici!!!");
                repaint();
            }
        }
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }

    public void keyTyped(KeyEvent e) {

        if (currentBrique != null) {
            if (e.getKeyCode() == KeyEvent.VK_0) {
                currentBrique.resistance = 0;
            } else if (e.getKeyCode() == KeyEvent.VK_1) {
                currentBrique.resistance = 1;
            } else if (e.getKeyCode() == KeyEvent.VK_2) {
                currentBrique.resistance = 2;
            } else if (e.getKeyCode() == KeyEvent.VK_3) {
                currentBrique.resistance = 3;
            } else if (e.getKeyCode() == KeyEvent.VK_4) {
                currentBrique.resistance = 4;
            } else if (e.getKeyCode() == KeyEvent.VK_5) {
                currentBrique.resistance = 5;
            }

            currentBrique.image = currentBrique.images.get(currentBrique.resistance);
            //System.out.println("key");
            repaint();

        }
    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }
}
