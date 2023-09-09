/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthony.liot.arkanoid;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author loup626
 */
public class BonusGrandeRaquette extends ABonus {

    public BonusGrandeRaquette(ArkanoidJPanel pere, ArkanoidTimer timer, int x, int y) {
        super(pere, timer, x, y, 50);
    }

    protected void createImages() {
        BufferedImage imageEndroit = new BufferedImage(ABonus.DIMI,ABonus.DIMI, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imageEndroit.createGraphics();

        g2d.setPaint(new Color(0, 0, 0, 0));

        g2d.fill(new Rectangle(0, 0, 24, 24));

        Color color = new Color(150, 0, 150);

        g2d.setPaint(color);
        g2d.fill(new Ellipse2D.Double(0, 0, 24, 24));

        g2d.setPaint(Color.BLUE);
        g2d.fill(new Ellipse2D.Double(2, 2, 20, 20));

        g2d.setPaint(Color.WHITE);
        g2d.fill(new Ellipse2D.Double(4, 4, 16, 16));

        g2d.setPaint(color);
        g2d.setFont(new Font("Times New Roman", Font.BOLD, 14));
        g2d.drawString("W", 5, 18);

        BufferedImage imageEnvers = new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                imageEnvers.setRGB(i, 24 - j, imageEndroit.getRGB(i, j));
            }
        }

        this.setImages(imageEndroit, imageEnvers);

    }

    public void action() {
        this.pere.getRaket().agrandirRaquette();
    }
}
