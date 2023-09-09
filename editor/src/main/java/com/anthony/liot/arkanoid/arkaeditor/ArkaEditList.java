/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.anthony.liot.arkanoid.arkaeditor;

import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author Administrateur
 */
public class ArkaEditList extends JPanel {
    
    private JList _liste;
    private ArkaEdit _parent;
    
    public ArkaEditList(ArkaEdit parent){
        _parent = parent;
        String[] n = {"Vide", "Brique 1","Brique 2", "Brique 3" ,"Brique 4" ,"Brique 5"};
        _liste = new JList(n);
        _liste.setLayoutOrientation(1);
        _liste.setSelectedIndex(0);
        this.setLayout(new BorderLayout());
        this.add(_liste);
    }
    
    public int currentIndex()
    {
        return _liste.getSelectedIndex();
    }

}
