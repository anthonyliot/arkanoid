/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthony.liot.arkanoid;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author loup626
 */

public class ArkanoidTimer extends Timer {

    public ArkanoidTimer() {
        super(true);
    }

    public class RafraichissementTask extends TimerTask {

        private ArkanoidJPanel pere;

        public RafraichissementTask(ArkanoidJPanel panel) {
            pere = panel;
        }

        public void run() {
            try {
                pere.paintImmediately(0, 0, pere.getWidth(), pere.getHeight());
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        public void schedule() {
            ArkanoidTimer.this.schedule(this, 0, 14);
        }
    }

    public class BalleTask extends TimerTask {

        private Balle balle;
        private int period;

        public BalleTask(Balle balle, int period) {
            this.balle = balle;
            this.period = period;
        }

        public void run() {
            try {
                this.balle.move();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        public void schedule() {
            ArkanoidTimer.this.scheduleAtFixedRate(this, 0, this.period);
        }
    }
    
    public class DescenteBonusTask extends TimerTask {
        
        private ABonus bonus;
        
        public DescenteBonusTask(ABonus nBonus) {
            bonus = nBonus;
        }
        
        public void run() {
            try {
                bonus.descendre();
            }
            catch(Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        
        public void schedule() {
            ArkanoidTimer.this.scheduleAtFixedRate(this, 0, 10);
        }        
    }
    
    public class RotationBonusTask extends TimerTask {
        
        private ABonus bonus;
        
        public RotationBonusTask(ABonus nBonus) {
            bonus = nBonus;
        }
        
        public void run() {
            try {
               bonus.rotation();
             }
            catch(Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
       }
        
        public void schedule() {
            ArkanoidTimer.this.scheduleAtFixedRate(this, 0, 25);
        }
    }
    
    public class ArkanoidReboursTask extends TimerTask {

        private ArkanoidRebours car;

        public ArkanoidReboursTask(ArkanoidRebours car) {
            this.car = car;
        }

        public void run() {
            try {
                this.car.decrementer();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        public void schedule() {
            ArkanoidTimer.this.scheduleAtFixedRate(this, 1000, 1000);
        }
    }
}
