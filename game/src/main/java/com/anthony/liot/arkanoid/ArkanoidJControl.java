/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthony.liot.arkanoid;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author loup626
 */
class ArkanoidJControl extends JPanel {

    private ArkanoidJFrame arkanoid;
    private ArkanoidJPanel arkapanel;
    private JLabel jl11;
    private JLabel jl22;
    private JLabel jl33;
    private JLabel jl44;
    private JLabel jl55;
    private JLabel jl66;
    private JLabel ji11;
    private JLabel ji33;
    private JLabel ji22;
    private JLabel ji44;
    private JLabel ji55;

    public ArkanoidJControl(ArkanoidJFrame nArkanoid, ArkanoidJPanel ajp) {
        arkanoid = nArkanoid;
        arkapanel = ajp;
        //this.setPreferredSize(new Dimension(arkanoid.getSize().width,100));
        this.setBorder(new BorderArrondie(Color.DARK_GRAY, 35));
        this.add(Info());
        this.add(Bonus());
        this.setVisible(true);
    }

    public JPanel Info() {
        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(3, 6));
        jp.setPreferredSize(new Dimension(400, 100));
        JLabel jl1 = new JLabel("Niveau : ");
        ji11 = new JLabel(String.valueOf(arkapanel.niveau));
        JLabel jl2 = new JLabel("Vie : ");
        ji22 = new JLabel(String.valueOf(arkapanel.nbVies));
        JLabel jl3 = new JLabel("Score : ");
        int score = arkapanel.scoreCourant + arkapanel.primeBonus;
        score = Math.max(0, score);
        ji33 = new JLabel(String.valueOf(score));
        JLabel jl4 = new JLabel("Points : ");
        ji44 = new JLabel(String.valueOf(arkapanel.scoreCourant));
        JLabel jl5 = new JLabel("Bonus : ");
        ji55 = new JLabel(String.valueOf(arkapanel.primeBonus));


        jp.add(jl3);
        jp.add(ji33);
        jp.add(jl2);
        jp.add(ji22);
        jp.add(jl4);
        jp.add(ji44);
        jp.add(jl1);
        jp.add(ji11);
        jp.add(jl5);
        jp.add(ji55);



        return jp;

    }

    public JPanel Bonus() {
        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(3, 4));
        jp.setPreferredSize(new Dimension(400, 100));


        JLabel jl1 = new JLabel(new ImageIcon(new BonusAccelerationBalle(arkapanel).getImage()));
        jl11 = new JLabel();
        JLabel jl2 = new JLabel(new ImageIcon(new BonusRalentirBalle(arkapanel).getImage()));
        jl22 = new JLabel();
        JLabel jl3 = new JLabel(new ImageIcon(new BonusGlue(arkapanel).getImage()));
        jl33 = new JLabel();
        JLabel jl4 = new JLabel(new ImageIcon(new BonusMitraillette(arkapanel).getImage()));
        jl44 = new JLabel();
        JLabel jl5 = new JLabel(new ImageIcon(new BonusBouclier(arkapanel).getImage()));
        jl55 = new JLabel();
        JLabel jl6 = new JLabel(new ImageIcon(new BonusFeu(arkapanel).getImage()));
        jl66 = new JLabel();

        jp.add(jl1);
        jp.add(jl11);
        jp.add(jl2);
        jp.add(jl22);
        jp.add(jl3);
        jp.add(jl33);
        jp.add(jl4);
        jp.add(jl44);
        jp.add(jl5);
        jp.add(jl55);
        jp.add(jl6);
        jp.add(jl66);

        return jp;
    }

    public void updateInfo() {
        ji11.setText(String.valueOf(arkapanel.niveau));
        ji22.setText(String.valueOf(arkapanel.nbVies));
        int score = arkapanel.scoreCourant + arkapanel.primeBonus;
        score = Math.max(0, score);
        ji33.setText(String.valueOf(score));
        ji44.setText(String.valueOf(arkapanel.scoreCourant));
        ji55.setText(String.valueOf(arkapanel.primeBonus));
    }

    public void update() {
        jl11.setText(String.valueOf(BonusAccelerationBalle.rebour.getTempsRestant()));
        jl22.setText(String.valueOf(BonusRalentirBalle.rebour.getTempsRestant()));
        jl33.setText(String.valueOf(BonusGlue.rebour.getTempsRestant()));
        jl44.setText(String.valueOf(BonusMitraillette.rebour.getTempsRestant()));
        jl55.setText(String.valueOf(BonusBouclier.rebour.getTempsRestant()));
        jl66.setText(String.valueOf(BonusFeu.rebour.getTempsRestant()));


    }
    /*
    public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    /*
    if (arkapanel.gameState == ArkanoidJPanel.GAME_OVER) {
    g2d.setPaint(Color.YELLOW);
    g2d.setFont(new Font("xxx", Font.BOLD, 24));
    g2d.drawString("GAME OVER", 220, 380);
    g2d.setPaint(Color.WHITE);
    }
     */
    /* Score, bonus etc...
     */
    /*
    g2d.setColor(Color.WHITE);
    g2d.draw3DRect(0, 0, this.getSize().width, this.getSize().height, true);
    g2d.setFont(new Font("Comis Sans Serif", Font.BOLD, 20));
    g2d.drawString("Lifes : ", 50, 50);
    for (int i = 0; i < arkapanel.nbVies - 1; i++) {
    new Balle(500 + 15 * i, 524).dessineImage(g2d);
    }
    g.drawString("Level : " + (this.niveau + 1), 420, 560);
    int score = this.score + this.primeBonus;
    score = Math.max(0, score);
    g.drawString("Score : " + score, 250, 590);
    g.drawString("Points : " + this.score, 250, 530);
    g.drawString("Bonus : " + this.primeBonus, 250, 560);
    g.drawImage(new Ralenti(this).getImage(), 5, 510, this);
    g.drawString(String.valueOf(Ralenti.compteARebour.getTempsRestant()), 35, 530);
     */
    /*
    g.drawImage(new BonusAccelerationBalle(arkapanel).getImage(), 5, 5, this);
    //g.drawString(String.valueOf(Acceleration.compteARebour.getTempsRestant()), 35, 560);
    /*  
    g.drawImage(new Glue(this).getImage(), 80, 510, this);
    g.drawString(String.valueOf(Glue.compteARebour.getTempsRestant()), 110, 530);
    g.drawImage(new Bouclier(this).getImage(), 80, 540, this);
    g.drawString(String.valueOf(Bouclier.compteARebour.getTempsRestant()), 110, 560);
    g.drawImage(new Mitraillette(this).getImage(), 155, 510, this);
    g.drawString(String.valueOf(Mitraillette.compteARebour.getTempsRestant()), 185, 530);
    g.drawImage(new Feu(this).getImage(), 155, 540, this);
    g.drawString(String.valueOf(Feu.compteARebour.getTempsRestant()), 185, 560);
     */
    /*}*/
}
