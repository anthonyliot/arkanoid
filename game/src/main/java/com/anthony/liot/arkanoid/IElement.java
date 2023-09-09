/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.anthony.liot.arkanoid;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.net.URL;

/**
 *
 * @author loup
 */
public interface IElement {
    public void loadImage(URL nUrl);
    public Dimension getDimension();
    public Image getImage();
    public void dessineImage(Graphics2D g2d,int nPosX,int nPosY);
    public void dessineImage(Graphics2D g2d,int nPosX,int nPosY,int nWidth, int nHeight);
}
