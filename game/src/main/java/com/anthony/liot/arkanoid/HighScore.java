package com.anthony.liot.arkanoid;
/*
 * HighScore.java
 *
 * Autheurs : Liot Anthony
 */

import java.awt.Component;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/** Cette classe s'occupe de garder � jour un fichier contenant les meilleurs scores. */

public class HighScore {
    // Nombre de scores � conserver.
    protected static final int NBMAX = 10;
    // Sert de s�parateur dans le fichier des High Scores. 
    protected static final String SEPARATOR = "-";
    // le nom du fichier gardant les scores sera high.scores
    private static final String FILE = "high.scores";
    // creer une liste de scores
    protected static ArrayList<String[]> scores;
    
    /** Copier FILE dans l'ArrayList scores */        	
    public static void lire() {
        // scores sera une nouvelle liste
        scores = new ArrayList<String[]>();
        try {
            // prend en entr�e un fichier creer de nom high.scores
            BufferedReader in = new BufferedReader(new FileReader(FILE));
            String s;
            int i=0;
            // on lit ligne par ligne 
            while ((s = in.readLine()) != null && i++ < NBMAX){
                // split retourne un tableau de 2 partie separer par le SEPARATOR
                // il les ajoutes a la liste scores
                scores.add(s.split(SEPARATOR));                
            }
            // close ferme le flux
            in.close();
        } 
        catch (IOException e) {
            //.................................
        }
    }
      
    /** Copier l'ArrayList scores dans FILE. */
    private static void ecrire() {
        try {
            // prend en sortie un fichier creer de nom high.scores
            PrintWriter out = new PrintWriter(new FileWriter(FILE));
            String[] s;
            // flush vide le tampon s'il existe
            out.flush();
            for(int j=0;j<NBMAX && j<scores.size();j++) {
                s=(String [])scores.get(j);
                //formatage du flux texte de sortie
                out.println(s[0]+SEPARATOR+s[1]);
            }
            // close ferme le flux
            out.close();
        }
        catch (IOException e) {
            //.................................
        }
    }

    /** Affiche un JOptionPane listant les High Scores. */
    public static void afficherScore(Component c) {
        // on lit le fichier
        lire();
        // StringBuffer permettra de modifier les objets present                
        StringBuffer str = new StringBuffer("");
        for(int i=0;i<scores.size();i++) {
            String[] s = (String[]) scores.get(i);
            str.append((i+1)+" . "+s[0]+" - "+s[1]+" \n"); 
        }
        JOptionPane.showMessageDialog(c,str,"High Scores",JOptionPane.PLAIN_MESSAGE);
    }
	
    /** V�rifie si l'entier score est dans le top et l'ins�re .*/
	 
    public static boolean ajouterScore(ArkanoidJFrame ajf, int score) {
        if (score <= 0){
            return false;
        }
        // on lit le fichier
        lire();
        String[] s;
        Object[] obj=null;
        Object[] obj1=null;
        ImageIcon icone = new ImageIcon(ajf.getClass().getResource("/images/hof.png"));
        int size = scores.size();
        int i = 0;
        while(i < NBMAX && i < size && score < Integer.parseInt((s=(String [])scores.get(i))[1])){
            i++; // on se d�cale jusqu'� la position de ce score (si il est trop faible on d�passe NBMAX
        }
        if(i<NBMAX || size==0) { // si le joueur a gagn� sa place
            s = new String[2];
            do {
                s[0] = (String)JOptionPane.showInputDialog(ajf,"Veuillez entrez votre nom!", "Hall Of Famous" ,3, icone, obj, obj1);
                if (s[0] == null){ // on lui demande d'entrer son nom
                    return false;
                }
            } while (s[0].length() == 0);
            s[1] = ""+score;
            scores.add(i,s);
            ecrire(); // on ecrit ca dans le fichier
            return true;
        }
        return false;
    }
}