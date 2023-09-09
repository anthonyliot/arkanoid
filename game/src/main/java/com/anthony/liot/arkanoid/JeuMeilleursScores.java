package com.anthony.liot.arkanoid;
/*
 * JeuMeilleursScores.java
 *
 * Autheurs : Lamoury jean fr�d�ric - Liot Anthony
 */

import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

public class JeuMeilleursScores extends Action{
    
    public JeuMeilleursScores(ArkanoidJFrame f) {
        super("Meilleurs Scores",new ImageIcon(f.getClass().getResource("/images/hof.png")),"les dix meilleurs joueurs",77, KeyStroke.getKeyStroke(77, 2),f);
    }
    
    //Lors d'une action sur le bouton, on affiche le top ten
    @SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent e) {
        // affiche les scores
        HighScore.afficherScore(sujet);
        
    }
}
