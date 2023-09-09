/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.anthony.liot.arkanoid;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author loup
 */
class GraphicalObject implements IElement{
    
    private BufferedImage image;
    private Dimension     dimension;
    
    public void loadImage(URL nUrl) {
        
        BufferedImage bufImage = null;

        try {
            bufImage = ImageIO.read(nUrl);
        } catch (IOException ioe) {
            System.out.println("Erreur de chargement! " + ioe);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
        dimension = new Dimension ( bufImage.getWidth() , bufImage.getHeight() );

        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        image = gc.createCompatibleImage( (int)dimension.getWidth(), (int)dimension.getHeight(), Transparency.BITMASK);
        image.getGraphics().drawImage(bufImage, 0, 0, null);
    }
        
    public void dessineImage(Graphics2D g2d,int nPosX,int nPosY,int nWidth,int nHeight)
    {
        g2d.drawImage(image,nPosX,nPosY, nWidth , nHeight, null );
    }

    public void dessineImage(Graphics2D g2d,int nPosX,int nPosY)
    {
        g2d.drawImage(image,nPosX,nPosY, null);
    }
    
    public Dimension getDimension() {
        return dimension;
    }

    public Image getImage() {
        return image;
    }
   
}
