package com.anthony.liot.arkanoid.arkaeditor;
/*
 * JeuQuitter.java
 *
 * Autheurs : Lamoury jean fr�d�ric - Liot Anthony
 */

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class Save extends Action {

    public Save(ArkaEdit f) {
        super("Save & Quitter", "Save et Quitte", 81, KeyStroke.getKeyStroke(81, 2), f);
    }

    //Lors d'une action sur le bouton, on arrete la partie
    public void actionPerformed(ActionEvent e) {
        //On va arreter la partie 
        sujet.aep.Save();
    }
}
