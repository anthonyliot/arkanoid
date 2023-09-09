/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthony.liot.arkanoid;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

/**
 *
 * @author loup
 */
public class ArkanoidJFrame extends JFrame {

    protected ArkanoidJPanel ajp;
    protected ArkanoidJControl ajc;
    private JeuQuitter quitter;
    private AideAPropos aideapropos;

    public ArkanoidJFrame() {

        HighScore.lire();
        setIconImage((new ImageIcon(getClass().getResource("/images/arkanoid.png"))).getImage());
        setTitle("Arkanoid");
        setMinimumSize(new Dimension(1005, 780));
        setMaximumSize(this.getMinimumSize());
        setResizable(false);
        initToutLaPartieSuperieur();
        initPartieCentrale();
        initPartieBasse();
        

        //Si la fenetre est fermer le jeu est totalement arreter
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ajp.arreterLaPartie(0);
            }
        });
    }

    public void initPartieCentrale() {
        ajp = new ArkanoidJPanel(this);
        getContentPane().add(ajp, "Center");
    }

    public void initPartieBasse() {
        ajc = new ArkanoidJControl(this, ajp);
        getContentPane().add(ajc, "South");

    }

    public void initToutLaPartieSuperieur() {
        JMenuBar menubar = new JMenuBar();
        JToolBar toolbar = new JToolBar();
        initMenuJeu(menubar, toolbar);
        initMenuAide(menubar, toolbar);
        setJMenuBar(menubar);
        //getContentPane().add(toolbar, "North");

    }

    public void initMenuAide(JMenuBar menubar, JToolBar toolbar) {
        JMenu menu = new JMenu("Aide");
        menubar.add(menu);
        menu.setMnemonic('A');


        aideapropos = new AideAPropos(this);
        menu.add(aideapropos);
        toolbar.add(aideapropos);
        toolbar.addSeparator();


    }

    public void initMenuJeu(JMenuBar menubar, JToolBar toolbar) {
        JMenu menu = new JMenu("Jeu");
        menubar.add(menu);
        menu.setMnemonic('J');

        JeuMeilleursScores best = new JeuMeilleursScores(this);
        if (HighScore.scores.isEmpty()) {
            best.setEnabled(false);
        }
        menu.add(best);
        toolbar.add(best);

        menu.addSeparator();

        quitter = new JeuQuitter(this);
        menu.add(quitter);
        toolbar.add(quitter);
        toolbar.addSeparator();
    }
}
