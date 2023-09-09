/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthony.liot.arkanoid.arkaeditor;

import com.anthony.liot.arkanoid.arkaeditor.ArkaEditPane.Brique;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 *
 * @author loup626
 */
public class writerAlbum {

    public writerAlbum(ArrayList<Brique> briques, String nPath) {


        File f = new File(nPath);
        PrintWriter out = null;
        try {
            // On cr�e un PrintWriter pour pouvoir �crire des lignes de texte
            // ainsi que des entiers, flottants, bool�ens etc. dans notre fichier.
            // Le codage de caract�res utilis� est utf-8 (permet de coder n'importe
            // quel caract�re unicode).
            out = new PrintWriter(new OutputStreamWriter(
                    new FileOutputStream(f), "utf-8"));
        // Avec Java >= 1.5.0, on peut utiliser la ligne suivante :
        // out = new PrintWriter(f, "utf-8");
        } catch (FileNotFoundException fnf) {
            System.err.println("Impossible de creer le fichier XML.");
            System.err.println(fnf);
            System.exit(1);
        } catch (UnsupportedEncodingException uee) {
            System.err.println("Le codage de caracteres utf-8 n'est pas reconnu !");
            System.err.println(uee);
            System.exit(1);
        }

        out.println("<arkanoid>");
        System.out.println("------------------------------------------------");
        for (Brique b : briques) {
            System.out.println("Briques : (" + b.x + " , " + b.y + ")   Resistance : " + b.resistance);

            if (b.resistance > 0) {
                out.println("<brique posx='" + b.x + "' posy='" + b.y + "' resist='" + b.resistance + "'></brique>");
            }
        }
        //<brique posx="0" posy="0" resist="4"></brique>
        out.println("</arkanoid>");
        out.close();

    }
}
