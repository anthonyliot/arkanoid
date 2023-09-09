/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.anthony.liot.arkanoid;

/**
 *
 * @author loup626
 */

public abstract class ArkanoidRebours {
    
    private int duree;
    private int tempsRestant;
    
    private ArkanoidTimer timer;
    private ArkanoidTimer.ArkanoidReboursTask task;
    
    /** Creates a new instance of CompteARebours */
    public ArkanoidRebours(ArkanoidTimer timer, int duree) {
        this.timer = timer;
        this.tempsRestant = 0;
        this.duree = duree;
    }
    
    
    public int getTempsRestant() {
        return this.tempsRestant;
    }
    
    
    public synchronized void initialiser() {
        this.cancel();
            
        this.task = this.timer.new ArkanoidReboursTask(this);
        this.tempsRestant = duree;
        this.task.schedule();
    }
    
    
    public void decrementer() {
        this.tempsRestant --;
        if(this.tempsRestant == 0) {
            this.cancel();
            this.action();
        }
    }
    
    public void annulerTempsRestant() {
        this.tempsRestant = 0;
    }

    public void cancel() {
        this.annulerTempsRestant();
        try {
            this.task.cancel();
        }
        catch(Exception e) {
        }
    }
    
    public abstract void action();
}

