package com.anthony.liot.arkanoid;
/*
 * JeuQuitter.java
 *
 * Autheurs : Lamoury jean fr�d�ric - Liot Anthony
 */

import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;


public class JeuQuitter extends Action {
    
    public JeuQuitter(ArkanoidJFrame f) {        
        super("Quitter",new ImageIcon(f.getClass().getResource("/images/quitter.png")),"Quitte la partie",81, KeyStroke.getKeyStroke(81, 2),f);
    }
    
    //Lors d'une action sur le bouton, on arrete la partie
    public void actionPerformed(ActionEvent e) {
        //On va arreter la partie 
        sujet.ajp.arreterLaPartie(0);             
    }  
}
