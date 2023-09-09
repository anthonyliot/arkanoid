package com.anthony.liot.arkanoid;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author loup626
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.border.Border;


public class BorderArrondie implements Border{

    private Color couleur;
    private int   angle;
    
    public BorderArrondie(Color nCouleur,int nAngle) {
        couleur = nCouleur;
        angle   = nAngle;
    }   

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 0, 0);//ou autre chose cela depend de si tu veux rendre parametrable
    }

    @Override
    public boolean isBorderOpaque() {
        return true;//ou autre chose cela d�pend de si tu veux rendre parametrable
    }
       

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(couleur);//ou une autre couleur que tu peux rendre parametrable
        int arc = angle;//tu peux aussi le rendre configurable
        int adjustXY = 1;//pour ajuster le dessin en x et y
        int adjustWH = 3;//idem pour width et height
        //pour eviter les escalier sur l'arrondi
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawRoundRect(x+adjustXY, y+adjustXY, width-adjustWH, height-adjustWH, arc, arc);
    }
}