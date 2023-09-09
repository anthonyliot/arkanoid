/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.anthony.liot.arkanoid;

/**
 *
 * @author loup
 */
public interface IObstacle {
    public double   angle   (Balle balle);
    public boolean  contact (Balle balle);
    public void     touche  ();
}
