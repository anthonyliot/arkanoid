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
public class BonusMultiBalle extends ABonus {
   
    public static ArkanoidRebours rebour;
    
    public BonusMultiBalle(ArkanoidJPanel panel) {
        super(panel, null, 0, 0, 0);
    }
    
    /** Creates a new instance of GrandeRaquette */
    public BonusMultiBalle(ArkanoidJPanel panel, ArkanoidTimer timer, int x, int y) {
        super(panel, timer, x, y, 50);
    }
    
    @Override
    protected void createImages() {
            
        BufferedImage imageEndroit = new BufferedImage(ABonus.DIMI,ABonus.DIMI, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imageEndroit.createGraphics();

        g2d.setPaint(new Color(0, 0, 0, 0));

        g2d.fill(new Rectangle(0, 0, 24, 24));

        g2d.setPaint(Color.ORANGE);
        g2d.fill(new Ellipse2D.Double(0, 0, 24, 24));

        g2d.setPaint(Color.YELLOW);
        g2d.fill(new Ellipse2D.Double(2, 2, 20, 20));

        g2d.setPaint(Color.WHITE);
        g2d.fill(new Ellipse2D.Double(4, 4, 16, 16));

        g2d.setPaint(Color.ORANGE);
        g2d.setFont(new Font("Times New Roman", Font.BOLD, 14));
        g2d.drawString("M", 7, 18);

        BufferedImage imageEnvers = new BufferedImage(25, 25, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                imageEnvers.setRGB(i, 24 - j, imageEndroit.getRGB(i, j));
            }
        }

        this.setImages(imageEndroit, imageEnvers);

    }

    public void action() {
        this.pere.multiballe();
    }
}
    