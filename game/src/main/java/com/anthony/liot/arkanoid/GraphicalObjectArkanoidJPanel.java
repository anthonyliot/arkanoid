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
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.plaf.DimensionUIResource;

/**
 *
 * @author loup
 */
public class GraphicalObjectArkanoidJPanel extends JPanel{
    
    private HashMap <String , BufferedImage>  images;
    private BufferedImage image;
    private Dimension     dimension;

    public GraphicalObjectArkanoidJPanel() {
        images = new HashMap<String, BufferedImage>();
    }   
    
    public void loadImage(URL nUrl, String nName) {
        
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
        
        images.put(nName, image);
    }
    
    public void dessineImage(Graphics2D g2d,String nName,int nPosX,int nPosY)
    {
        g2d.drawImage(getImage(nName),nPosX,nPosY, null);
    }
    
    public Dimension getDimension(String nName) {
        return new Dimension(images.get(nName).getWidth(),images.get(nName).getHeight());
    }
    
    public Image getImage(String nName) {
        return images.get(nName);
    }


}
