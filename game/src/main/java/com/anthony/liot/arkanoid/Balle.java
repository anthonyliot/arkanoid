/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthony.liot.arkanoid;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author loup
 */
class Balle extends GraphicalObject {

    protected static final int DISTANCEY = 75;
    private static Random random = new Random();
    private boolean bThrown;
    private boolean bStarted;
    private boolean bInit;
    private boolean enFlamme;
    private int diametre;
    private int vitesse;
    private int rayon;
    private double x,  y;
    private double angle;
    private ArkanoidTimer timer;
    private ArkanoidTimer.BalleTask task;
    private ArkanoidRebours rebours;
    private ArkanoidJPanel pere;

    public Balle(int x, int y) {
        this.loadImage(pere.getClass().getResource(getNameFile()));
        bInit = false;
        this.x = x;
        this.y = y;
    }

    public Balle(ArkanoidJPanel nPere, ArkanoidTimer nTimer, int nVitesse) {

        pere = nPere;

        this.loadImage(pere.getClass().getResource(getNameFile()));

        timer = nTimer;
        vitesse = nVitesse;

        bInit = true;
        bThrown = false;
        bStarted = false;

        angle = (Math.PI / 2 + random.nextFloat() * Math.PI / 5);

        this.rebours = new ArkanoidRebours(this.timer, 15) {

            public void action() {
                Balle.this.pere.reinitialiserBalle(Balle.this);
            }
        };

    }

    void dessineImage(Graphics2D g2d) {

        if (bInit) {
            diametre = getDimension().width;
            rayon = diametre / 2;
            //x = pere.getSize().width / 2;
            y = pere.getSize().height - DISTANCEY + rayon;
            x = pere.getRaket().getAbsisse();
            bInit = false;
        }

        this.dessineImage(g2d, (int) getHautX(), (int) getHautY());

        if (this.estEnFlamme()) {
            Color c1 = new Color(0.8f, 0.8f, 0.0f);
            Color c2 = new Color(0.6f, 0.0f, 0.0f);

            Point p1 = new Point((int) getHautX() - 2, (int) getHautY() - 2);
            Point p2 = new Point((int) (diametre + 3), (int) ( diametre + 7));

            GradientPaint gradient = new GradientPaint(p1, c1, p2, c2, true);
            g2d.setPaint(gradient);
            g2d.drawOval((int) getHautX(), (int) getHautY(), diametre, diametre);
            g2d.drawOval((int) getHautX() - 1, (int) getHautY() - 1, diametre + 2, diametre + 2);
            g2d.drawOval((int) getHautX() - 2, (int) getHautY() - 2, diametre + 3, diametre + 3);
            g2d.drawOval((int) getHautX() - 2, (int) getHautY() - 2, diametre + 3, diametre + 4);
            g2d.drawOval((int) getHautX() - 2, (int) getHautY() - 2, diametre + 3, diametre + 5);
            g2d.drawOval((int) getHautX() - 2, (int) getHautY() - 2, diametre + 3, diametre + 7);
        }
    }

    void enflammer() {
        this.enFlamme = true;
    }

    boolean estEnFlamme() {
//        throw new UnsupportedOperationException("Not yet implemented");
        return enFlamme;
    }

    void eteindre() {
        this.enFlamme = false;
    }

    double getHautX() {
        return x - rayon;
    }

    double getHautY() {
        return y - rayon;
    }

    void setHautX(double i) {
        x = i + rayon;
    }

    void setHautY(double i) {
        y = i + rayon;
    }

    double getCentreX() {
        return x;
    }

    public double getNextCentreX(double angle){
        return x + Math.cos(angle);
    }
    
    public double getNextCentreX(){
        return x + Math.cos(this.angle);
    }
    
    double getCentreY() {
        return y;
    }

    public double getNextCentreY(double angle){
        return y - Math.sin(angle);
    }
    
    public double getNextCentreY(){
        return y - Math.sin(this.angle);
    }
    
    void setCentreX(double i) {
        x = i;
    }

    void setCentreY(double i) {
        y = i;
    }

    double getAngle() {
        return angle;
    }

    void setAngle(double angle) {
        this.angle = angle;
    }

    int getDiametre() {
        return diametre;
    }

    int getRayon() {
        return rayon;
    }

    boolean hasBeenThrown() {
        return bThrown;
    }

    public synchronized void move() {
        if (!this.testSiSortie()) {
            this.toucheObstacle();
            this.toucheBrique();
            this.x += Math.cos(this.angle);
            this.y -= Math.sin(this.angle);
        } else {
            this.pere.ballePerdue(this);
        }
    }

    public synchronized void start() {
        if (!bStarted) {
            this.bStarted = this.bThrown = true;
            this.task = this.timer.new BalleTask(this, this.vitesse);
            this.task.schedule();

            this.rebours.initialiser();
        }
    }

    public boolean testSiSortie() {
        return y >= pere.getSize().height;
    }

    void stop() {
        try {
            this.task.cancel();
        } catch (Exception e) {
        }
        this.bStarted = false;
        this.rebours.cancel();
    }

    public void accelerer() {
        if (this.vitesse == 1) {
            return;
        } else if (this.vitesse == 2) {
            this.vitesse = 1;
        } else {
            this.vitesse = 2;
        }

        if (this.bStarted) {
            this.stop();
            this.start();
        }
    }

    public void ralentir() {
        if (this.vitesse == 3) {
            return;
        } else if (this.vitesse == 2) {
            this.vitesse = 3;
        } else {
            this.vitesse = 2;
        }
        if (this.bStarted) {
            this.stop();
            this.start();
        }
    }

    private String getNameFile() {
        return "/images/balle.gif";
    }

    private boolean toucheObstacle() {
        boolean result = false;

        ArrayList<IObstacle> obstacles = this.pere.getObstacles();

        for (IObstacle obstacle : obstacles) {

            if (obstacle.contact(this)) {
                result = true;
                this.angle = obstacle.angle(this);

                if (obstacle instanceof Raquette || obstacle instanceof ArkanoidJPanel.Bas) {
                    this.rebours.initialiser();
                }
            }
        }

        //result = this.toucheBrique() || result;

        return result;
    }

    public boolean toucheBrique() {
        ArrayList<ArrayList<Brique>> briques = this.pere.getBriques();

        int _x = (int) (x - pere.difference()) / Brique.LARGEUR;
        int _y = (int) (y - Brique.DISTANCEY) / Brique.HAUTEUR;

        //System.out.println("conversion en x : "+_x+" y: "+_y);

        for (int k = _y - 1; k <= _y + 1; k++) {
            for (int l = _x - 1; l <= _x + 1; l++) {
                if (k >= 0 && k < briques.size() && l >= 0 && l < briques.get(k).size()) {
                    //System.out.println("test de contact avec la brique: "+l+" "+k);
                    if (briques.get(k).get(l) != null) {
                        if (briques.get(k).get(l).contact(this)) {
                            //System.out.println("contact avec la brique :"+k+" "+l);
                            this.angle = briques.get(k).get(l).angle(this);
                            if (this.bStarted && briques.get(k).get(l).getResistance() != Brique.RESISTANCE_INFINIE) {
                                this.rebours.initialiser();
                                briques.get(k).get(l).touche();
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}

