/*
 * Action.java
 *
 * Autheurs : Lamoury jean fr�d�ric - Liot Anthony
 */
package com.anthony.liot.arkanoid;
import javax.swing.*;

//AbstractAction comporte deja les services que l'on attend d'une telle classe
abstract class Action extends AbstractAction{
    
    public ArkanoidJFrame sujet;
    
    //Chaque Action sera compos� d'un nom, d'une icone, d'une chaine de caract�re,
    //d'une valeur Mnemonic et d'un raccourci clavier
    public Action(String nom,Icon icone, String s1, int i,KeyStroke keystroke, ArkanoidJFrame frame){
        
        //appel du constructeur de AbstractAction
        super(nom, icone);
        
        //putValue(String cl�,Object nouvelleValeur)
        putValue("ShortDescription", s1);
        putValue("MnemonicKey", Integer.valueOf(i));
        putValue("AcceleratorKey",keystroke);
        sujet = frame;
    }
    
}
