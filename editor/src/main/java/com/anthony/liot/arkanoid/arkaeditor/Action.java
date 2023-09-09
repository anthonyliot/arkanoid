/*
 * Action.java
 *
 * Autheurs : Lamoury jean fr�d�ric - Liot Anthony
 */
package com.anthony.liot.arkanoid.arkaeditor;
import javax.swing.*;

//AbstractAction comporte deja les services que l'on attend d'une telle classe
abstract class Action extends AbstractAction{
    
    public ArkaEdit sujet;
    
    //Chaque Action sera compos� d'un nom, d'une icone, d'une chaine de caract�re,
    //d'une valeur Mnemonic et d'un raccourci clavier
    public Action(String nom, String s1, int i,KeyStroke keystroke, ArkaEdit frame){
        
        //appel du constructeur de AbstractAction
        super(nom);
        
        //putValue(String cl�,Object nouvelleValeur)
        putValue("ShortDescription", s1);
        putValue("MnemonicKey", Integer.valueOf(i));
        putValue("AcceleratorKey",keystroke);
        sujet = frame;
    }
    
}
