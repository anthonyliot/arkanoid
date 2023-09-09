/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthony.liot.arkanoid;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author loup
 */
public abstract class ABonus {

    public static final int DIMI = 25;
    
    protected ArkanoidJPanel pere;
    private int xImage;
    private int yImage;
    private int points;
    private int epaisseurImage = 30;
    protected ArkanoidTimer timer;
    private ArkanoidTimer.DescenteBonusTask descenteTask;
    private ArkanoidTimer.RotationBonusTask rotationTask;
    private BufferedImage imageEndroit;
    private BufferedImage imageEnvers;
    private boolean sens,  sensEvolution = false;

    public ABonus(ArkanoidJPanel nPere, ArkanoidTimer nTimer, int x, int y, int nPoints) {
        pere = nPere;
        xImage = x;
        yImage = y;
        points = nPoints;

        if (nTimer != null) {
            this.timer = nTimer;
            this.descenteTask = this.timer.new DescenteBonusTask(this);
            this.rotationTask = this.timer.new RotationBonusTask(this);
        }

        createImages();

    }

    public void start() {
        this.pere.addBonus(this);
        this.descenteTask = this.timer.new DescenteBonusTask(this);
        this.descenteTask.schedule();
        this.rotationTask = this.timer.new RotationBonusTask(this);
        this.rotationTask.schedule();
    }

    public void stop() {
        this.descenteTask.cancel();
        this.rotationTask.cancel();
    }

    public void setImages(BufferedImage imageEndroit, BufferedImage imageEnvers) {
        this.imageEndroit = imageEndroit;
        this.imageEnvers = imageEnvers;
    }

    void descendre() {
        this.yImage++;
        this.SiAttrappe();
        this.SiPerdu();
    }

    void rotation() {
        if (sensEvolution) {
            this.epaisseurImage += 2;
            if (this.epaisseurImage == 30) {
                this.sens = !this.sens;
                this.sensEvolution = !this.sensEvolution;
            }
        } else {
            this.epaisseurImage -= 2;
            if (this.epaisseurImage == 0) {
                this.sens = !this.sens;
                this.sensEvolution = !this.sensEvolution;
            }
        }
    }

    public Image getImage() {
        return this.imageEndroit;
    }

    private void SiAttrappe() {
        Rectangle r1 = new Rectangle(this.xImage , this.yImage , 30, this.epaisseurImage);
        Rectangle r2 = pere.getRaket().getSurface();
        Rectangle r3 = r1.intersection(r2);

        if (!r3.isEmpty()) {
            pere.removeBonus(this);
            this.action();
            pere.ajouterPrime(this.points);
        }
    }

    private void SiPerdu() {
        if (this.yImage >= pere.getSize().height) {
            pere.removeBonus(this);
        }
    }

    public void dessineImage(Graphics2D g2d) {
        if (sens && this.imageEndroit != null) {
            g2d.drawImage(this.imageEndroit,this.xImage , this.yImage , 30, this.epaisseurImage, pere);
        } else if ((!sens) && this.imageEnvers != null) {
            g2d.drawImage(this.imageEnvers, this.xImage , this.yImage , 30, this.epaisseurImage, pere);
        }
    }

    protected abstract void createImages();

    public abstract void action();
}
