/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthony.liot.arkanoid;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author loup
 */
public class ArkanoidJPanel extends GraphicalObjectArkanoidJPanel {

    protected static short GAME_OVER = 1;
    protected static short GAME_RUNNING = 0;
    private Raquette raket;
    protected ArkanoidJFrame pere;
    private ArkanoidTimer timer;
    private ArkanoidTimer.RafraichissementTask rafraichissementTask;
    private ArrayList<ArrayList<Brique>> briques;
    private ArrayList<Balle> balles;
    private ArrayList<IObstacle> obstacles;
    private ArrayList<ABonus> bonus;
    protected int niveau;
    protected int nbVies;
    private int MAXBrique;
    private int nbBalles;
    private int nbBriques;
    protected short gameState;
    private short vitesseBalles;
    protected int scoreCourant;
    protected int primeBonus;
    private boolean ralenti;
    private boolean acceleration;
    private boolean multiballe;
    private boolean glue;
    private boolean bouclierActive;
    private boolean mitraillette;
    private boolean ballesEnFeu;
    private Bas bouclier;
    private static int MAXNIVEL = 9;

    public ArkanoidJPanel(ArkanoidJFrame nPere) {
        super();
        pere = nPere;
        loadPartie();
    }

    public void loadPartie() {
        this.removeAll();
        URL url = pere.getClass().getResource("/images/fond.png");
        URL url2 = pere.getClass().getResource("/images/briquehaut.gif");
        URL url3 = pere.getClass().getResource("/images/briquecote.png");
        this.loadImage(url, "fond");
        this.loadImage(url2, "briqueHaut");
        this.loadImage(url3, "briqueCote");

        bouclier = this.new Bas();

        briques = new ArrayList<ArrayList<Brique>>();
        balles = new ArrayList<Balle>();
        bonus = new ArrayList<ABonus>();
        raket = new Raquette(this);
        timer = new ArkanoidTimer();

        pere.addMouseMotionListener(new RaketMouseListener());

        initialiserPartie();


        BonusRalentirBalle.rebour = new ArkanoidRebours(this.timer, 30) {

            public void action() {
                new BonusRalentirBalle(ArkanoidJPanel.this).annuler();
            }
        };

        BonusAccelerationBalle.rebour = new ArkanoidRebours(this.timer, 15) {

            public void action() {
                new BonusAccelerationBalle(ArkanoidJPanel.this).annuler();
            }
        };

        BonusGlue.rebour = new ArkanoidRebours(this.timer, 30) {

            public void action() {
                new BonusGlue(ArkanoidJPanel.this).annuler();
            }
        };

        BonusBouclier.rebour = new ArkanoidRebours(this.timer, 20) {

            public void action() {
                new BonusBouclier(ArkanoidJPanel.this).annuler();
            }
        };

        BonusFeu.rebour = new ArkanoidRebours(this.timer, 15) {

            public void action() {
                new BonusFeu(ArkanoidJPanel.this).annuler();
            }
        };

        BonusMitraillette.rebour = new ArkanoidRebours(this.timer, 3) {

            public void action() {
                new BonusMitraillette(ArkanoidJPanel.this).annuler();
            }
        };

        pere.addMouseListener(this.startBallMouseListener);
    }

    public void accelererBalles() {
        if (this.vitesseBalles == 2) {
            this.acceleration = true;
            this.vitesseBalles = 1;
        } else if (this.vitesseBalles == 3) {
            this.ralenti = false;
            this.vitesseBalles = 2;
        } else {
            return;
        }
        for (Balle b : balles) {
            b.accelerer();
        }
    }

    public void ralentirBalles() {
        if (this.vitesseBalles == 2) {
            this.ralenti = true;
            this.vitesseBalles = 3;
        } else if (this.vitesseBalles == 1) {
            this.acceleration = false;
            this.vitesseBalles = 2;
        } else {
            return;
        }
        for (Balle b : balles) {
            b.ralentir();
        }
    }

    public boolean estRalenti() {
        return this.ralenti;
    }

    public boolean estAccelere() {
        return this.acceleration;
    }

    public void setGlue(boolean glue) {
        if (this.glue == glue) {
            return;
        }

        this.glue = glue;
        this.raket.setGlue(glue);
    }

    public void annullerGlue() {
        this.setGlue(false);

        ArrayList<Balle> ballesCollees = (ArrayList<Balle>) raket.getBallesCollees().clone();
        for (Balle b : ballesCollees) {
            raket.decoller(b, false);
        }
    }

    public Raquette getRaket() {
        return raket;
    }

    public void multiballe() {
        if (this.multiballe) {
            return;
        }
        
        this.multiballe = true;

        for (int i = this.nbBalles; i < 3; i++) {
            this.lancerNouvelleBalle();
        }
    }

    public void extraballe() {
        this.nbVies++;
    }

    public void activerBouclier() {
        if (this.bouclierActive) {
            return;
        }
        this.obstacles.add(this.bouclier);
        this.bouclierActive = true;
    }

    public void enflammerBalles() {
        this.ballesEnFeu = true;
        for (Balle b : balles) {
            b.enflammer();
        }
    }

    public void eteindreBalles() {
        this.ballesEnFeu = false;
        for (Balle b : balles) {
            b.eteindre();
        }
    }

    public void setMitraillette(boolean b) {
        if (this.mitraillette == b) {
            return;
        }

        this.mitraillette = b;
        if (this.mitraillette) {
            this.addMouseListener(this.mitrailletteMouseListener);
            this.lancerBalleMitraillette();
        } else {
            this.removeMouseListener(this.mitrailletteMouseListener);
        }
    }

    public void desactiverBouclier() {
        if (!this.bouclierActive) {
            return;
        }
        this.obstacles.remove(this.bouclier);
        this.bouclierActive = false;
    }

    private void initialiserPartie() {
        this.gameState = ArkanoidJPanel.GAME_RUNNING;
        this.resetScore();
        this.nbVies = 3;
        this.niveau = 1;
        this.vitesseBalles = 2;
        this.demarrerAffichage();

        this.obstacles = new ArrayList();
        this.obstacles.add(new ArkanoidJPanel.Haut());
        this.obstacles.add(new ArkanoidJPanel.Droite());
        this.obstacles.add(new ArkanoidJPanel.Gauche());
        this.obstacles.add(this.raket);

        this.initialiserNiveau();
    }

    public void demarrerAffichage() {
        this.rafraichissementTask = this.timer.new RafraichissementTask(this);
        this.rafraichissementTask.schedule();

    }

    public void initialiserNiveau() {
        // on reintialise les balles
        effacerBalles();
        initialiserBalle();

        //ainsi que les briques
        briques.clear();
        briques = new ArrayList<ArrayList<Brique>>();

        //si on arrive au niveau final, on arrete
        if (niveau > MAXNIVEL) {
            this.niveau = 1;
        }

        this.chargerNiveau(getNextLevel());
    }

    public String getNextLevel() {

        return "/levels/level" + niveau + ".xml";
    }

    public void effacerBalles() {
        for (Balle b : balles) {
            b.stop();
        }
        balles.clear();
    }

    public void chargerNiveau(String nFile) {
        BriqueHandler loader = new BriqueHandler(this);
        loader.parse(nFile);
        briques.addAll(loader.briques());
        MAXBrique = loader.getMAX();
        nbBriques = loader.getNbBrique();
    }

    public int getMAXBrique() {
        return MAXBrique;
    }

    public int difference() {
        int tailleScreen = this.getSize().width - 2 * Brique.HAUTEUR;
        int largeurBriqueMAX = (this.getMAXBrique() - 1) * Brique.LARGEUR;
        return (int) (tailleScreen - largeurBriqueMAX) / 2;
    }

    public void initialiserBalle() {
        this.nbBalles = 0;
        this.lancerNouvelleBalle();
    }

    public synchronized void lancerNouvelleBalle() {
        Balle balle = new Balle(this, this.timer, this.vitesseBalles);
        this.raket.coller(balle);
        balle.setHautX(this.raket.getAbsisse());

        this.balles.add(balle);
        this.nbBalles++;
    }

    synchronized void ballePerdue(Balle balle) {
        balle.stop();
        this.balles.remove(balle);
        this.nbBalles--;
        
        if (multiballe && nbBalles==1){
            multiballe = false;
        }
        

        if (this.nbBalles == 0) {
            this.clearCurrentBall();

            new Thread() {

                @Override
                public void run() {
                    synchronized (this) {
                        try {
                            this.wait(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    ArkanoidJPanel.this.nbVies--;
                    if (ArkanoidJPanel.this.nbVies == 0) {
                        ArkanoidJPanel.this.gameOver();
                    } else {
                        ArkanoidJPanel.this.initialiserBalle();
                    }

                }
            }.start();
        }

    }

    synchronized void reinitialiserBalle(Balle balle) {
        balle.stop();
        this.balles.remove(balle);
        this.nbBalles--;
        this.lancerNouvelleBalle();
    }

    synchronized boolean supprimerBrique(Brique brique) {
        ArrayList<Brique> ligne = this.briques.get(brique.getJ());
        ligne.set(brique.getI(), null);
        this.nbBriques--;

        if (this.nbBriques == 0) {
            System.out.println("FELICITATIONS");
            this.effacerBalles();
            new Thread() {

                @Override
                public void run() {
                    synchronized (this) {
                        try {
                            this.wait(1000);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    niveau++;
                    ArkanoidJPanel.this.initialiserNiveau();
                }
            }.start();

            return false;
        }

        return true;
    }

    public synchronized void clearCurrentBall() {
        //balles.clear();
        bonus.clear();
        this.raket.setRatio(1.0f);
        this.raket.vider();
    }

    public void gameOver() {
        System.out.println("GAME OVER");
        this.gameState = ArkanoidJPanel.GAME_OVER;
        this.rafraichissementTask.cancel();
        this.repaint();
        arreterLaPartie(1);
    }

    private void resetScore() {
        scoreCourant = primeBonus = 0;
    }

    public ArkanoidTimer getTimer() {
        return this.timer;
    }

    public ArrayList getObstacles() {
        return obstacles;
    }

    public void ajouterPoints(int points) {
        this.scoreCourant += points;
    }

    public ArrayList<ArrayList<Brique>> getBriques() {
        return briques;
    }

    void addBonus(ABonus nBonus) {
        this.bonus.add(nBonus);
    }

    void removeBonus(ABonus nBonus) {
        this.bonus.remove(nBonus);
        nBonus.stop();
    }

    void ajouterPrime(int points) {
        this.primeBonus += points;
    }

    public void arreterLaPartie(int part) {
        int reponse;

        switch (part) {
            // Quoi qu'il en soit si tu a un scores correcte
            // tu sera enregistrer dans les scores 

            case 0:
                //tu abandonnes la partie
                //reponse = JOptionPane.showConfirmDialog(null, "Etes vous sur de vouloir abandonner?", "ABANDON DE PARTIE", JOptionPane.YES_NO_OPTION);
                HighScore.ajouterScore(pere, scoreCourant);
                //System.out.println(reponse);
                //if (reponse == 0) {
                System.exit(0);
                //}
                break;

            case 1:
                //tu as perdu la partie
                reponse = JOptionPane.showConfirmDialog(null, "voulez-vous rejouer?", "PARTIE PERDUE", JOptionPane.YES_NO_OPTION);
                HighScore.ajouterScore(pere, scoreCourant);
                if (reponse == 1) {
                    System.exit(0);
                }
                //Tu redemare une partie;
                this.loadPartie();
                break;

            case 2:
                //tu as terminer le jeu, tu as gagn�
                reponse = JOptionPane.showConfirmDialog(null, "FELICITATIONS !!!!! /n Voulez-vous rejouer?", "PARTIE GAGNE", JOptionPane.YES_NO_OPTION);
                HighScore.ajouterScore(pere, scoreCourant);
                if (reponse == 1) {
                    System.exit(0);
                }
                //Tu redemare une partie;
                this.loadPartie();
                break;

            default:
                HighScore.ajouterScore(pere, scoreCourant);
                break;
        }
    }


    // Actions Listener
    private class RaketMouseListener extends MouseMotionAdapter {

        @Override
        public void mouseMoved(MouseEvent e) {
            raket.setAbsisse(e.getX());

        }
    }

    private class MitrailletteMouseListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            ArkanoidJPanel.this.lancerBalleMitraillette();
        }
    }
    private MitrailletteMouseListener mitrailletteMouseListener = new MitrailletteMouseListener();

    private synchronized void lancerBalleMitraillette() {
        if (this.mitraillette) {
            this.lancerNouvelleBalle();
        }
    }

    private class StartBallMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            ArrayList<Balle> ballesCollees = (ArrayList<Balle>) ArkanoidJPanel.this.raket.getBallesCollees().clone();
            for (Balle b : ballesCollees) {
                ArkanoidJPanel.this.raket.decoller(b, true);
            }
        }
    }
    private StartBallMouseListener startBallMouseListener = new StartBallMouseListener();

    private class RestartMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            ArkanoidJPanel.this.removeMouseListener(this);
            ArkanoidJPanel.this.initialiserPartie();
        }
    }

    // Private Class
    private class Droite implements IObstacle {

        @Override
        public double angle(Balle balle) {
            double angle = Math.PI - balle.getAngle();
            if (angle >= Math.PI) {
                angle -= 2 * Math.PI;
            }
            return angle;
        }

        @Override
        public boolean contact(Balle balle) {
            boolean result = false;

            double angle = balle.getAngle();
            if (angle >= Math.PI / 2 || angle <= -Math.PI / 2) {
                result = false;
            } else {
                result = balle.getCentreX() + balle.getRayon() >= ArkanoidJPanel.this.getSize().width - Brique.HAUTEUR;
            }

            return result;
        }

        public void touche() {
        }
    }

    private class Gauche implements IObstacle {

        @Override
        public double angle(Balle balle) {
            double angle = Math.PI - balle.getAngle();
            if (angle >= Math.PI) {
                angle -= 2 * Math.PI;
            }
            return angle;
        }

        @Override
        public boolean contact(Balle balle) {
            boolean result = false;

            double angle = balle.getAngle();
            if (angle <= Math.PI / 2 && angle >= -Math.PI / 2) {
                result = false;
            } else {
                result = balle.getHautX() <= Brique.HAUTEUR;
            }

            return result;
        }

        public void touche() {
        }
    }

    protected class Bas implements IObstacle {

        @Override
        public double angle(Balle balle) {
            return -balle.getAngle();
        }

        @Override
        public boolean contact(Balle balle) {
            boolean result = (int) balle.getCentreY() + balle.getRayon() >= ArkanoidJPanel.this.getSize().height - Brique.HAUTEUR;

            return result;
        }

        public void touche() {
        }
    }

    private class Haut implements IObstacle {

        public double angle(Balle balle) {
            return -balle.getAngle();
        }

        public boolean contact(Balle balle) {
            boolean result = false;

            double angle = balle.getAngle();
            if (angle > Math.PI || angle < 0) {
                result = false;
            } else {
                result = balle.getHautY() <= Brique.HAUTEUR;
            }

            return result;
        }

        public void touche() {
        }
    }

    // Le Paint Component
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        pere.ajc.update();
        pere.ajc.updateInfo();
        Dimension taille = getSize();
        int x = 0, y = 0;
        // tant que l'ecran n'est pas totalement recouvert sur x et y
        while (y < taille.height) {
            while (x < taille.width) {
                // on le dessine 
                this.dessineImage(g2d, "fond", x, y);
                x += this.getDimension("fond").width;
            }
            x = 0;
            y += this.getDimension("fond").height;
        }

        x = 0;
        y = 0;
        while (x < taille.width) {
            // on le dessine 
            this.dessineImage(g2d, "briqueHaut", x, y);
            x += Brique.LARGEUR;
        }

        y = Brique.HAUTEUR;
        x = 0;
        while (y < taille.height) {
            // on le dessine 
            this.dessineImage(g2d, "briqueCote", x, y);
            y += Brique.LARGEUR;
        }

        y = Brique.HAUTEUR;
        x = taille.width - Brique.HAUTEUR;
        while (y < taille.height) {
            // on le dessine 
            this.dessineImage(g2d, "briqueCote", x, y);
            y += Brique.LARGEUR;
        }

        if (bouclierActive) {
            y = ArkanoidJPanel.this.getSize().height - Brique.HAUTEUR;
            x = Brique.HAUTEUR;
            while (x < taille.width - Brique.HAUTEUR) {
                // on le dessine 
                this.dessineImage(g2d, "briqueHaut", x, y);
                x += Brique.LARGEUR;
            }

        }

        // On dessine la raquette, balle et briques
        for (int i = 0; i < briques.size(); i++) {
            for (int j = 0; j < briques.get(i).size(); j++) {
                if (briques.get(i).get(j) != null) {
                    briques.get(i).get(j).dessineImage(g2d);
                }
            }
        }

        for (ABonus b : bonus) {
            b.dessineImage(g2d);
        }

        for (Balle b : balles) {
            b.dessineImage(g2d);
        }

        raket.dessineImage(g2d);

    }
}
