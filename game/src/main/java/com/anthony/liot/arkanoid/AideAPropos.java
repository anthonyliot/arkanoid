package com.anthony.liot.arkanoid;
/*
 * AideAPropos.java
 *
 * Autheurs : Lamoury jean fr�d�ric - Liot Anthony
 */


import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import javax.swing.JOptionPane;

//AideAPropos herite de la classe Action

public class AideAPropos extends Action{    
    
    public AideAPropos(ArkanoidJFrame f) {
        super("A-propos",new ImageIcon(f.getClass().getResource("/images/a-propos.png")),"Information sur les auteurs",65, KeyStroke.getKeyStroke(65, 2),f);
    }

    //Lors d'une action sur le bouton, on declenche un message
    public void actionPerformed(ActionEvent e) {
        
        //On cree un string, jeu, auteur, date, mail...
        String s = "ARKANOID 1.0\nLe Casse Brique\n\nAnthony LIOT et Cedric Lesoeur\n" +
                    "Projet IHM 2007/2008 Master 1 Informatique.\nUniversité de Montpellier\n\n" +
                    "anthony.liot@gmail.com\nbacayaourtine@msn.com";
        
        //Que l'on insert ensuite dans une boite de dialogue
        JOptionPane.showMessageDialog(sujet, s, "A-propos", 1, new ImageIcon(getClass().getResource("/images/a-propos.png")));
    
    }
    
}
