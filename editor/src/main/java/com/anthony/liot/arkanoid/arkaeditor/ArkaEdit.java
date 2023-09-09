/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthony.liot.arkanoid.arkaeditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

/**
 *
 * @author loup626
 */
class ArkaEdit extends JFrame {


    protected ArkaEditPane aep;
    protected ArkaEditList ael;

    class GestionEvtFenetrePrinc extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            Window win = e.getWindow();
            win.setVisible(false);
            System.exit(0);
        }
    }

    public ArkaEdit() {

        setTitle("Arkanoid Editeur");
        setMinimumSize(new Dimension(1005, 780));
        setMaximumSize(this.getMinimumSize());
        setResizable(false);
        this.setLayout(new BorderLayout());
        
        
        initToutLaPartieSuperieur();
                
        aep = new ArkaEditPane(this);
        this.getContentPane().add(aep,BorderLayout.CENTER);
        
        
        ael = new ArkaEditList(this);
        this.getContentPane().add(ael,BorderLayout.SOUTH);
        
        this.setVisible(true);
        
        
        addWindowListener(new GestionEvtFenetrePrinc());
        
        
    }

    public void initToutLaPartieSuperieur() {
        JMenuBar menubar = new JMenuBar();
        JToolBar toolbar = new JToolBar();
        initFichier(menubar, toolbar);
        setJMenuBar(menubar);
    //getContentPane().add(toolbar, "North");

    }

    private void initFichier(JMenuBar menubar, JToolBar toolbar) {
        JMenu menu = new JMenu("Menu");
        menubar.add(menu);
        menu.setMnemonic('M');


        Save quitter = new Save(this);
        menu.add(quitter);

    }
    
    public int currentIndex(){
        return ael.currentIndex();
    }
}
