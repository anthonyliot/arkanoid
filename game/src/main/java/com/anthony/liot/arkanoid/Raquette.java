/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/* modif:
  - methode angle: réajustement de l'angle!

*/


package com.anthony.liot.arkanoid;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author loup
 */
public class Raquette extends GraphicalObject implements IObstacle {
    
    private final int DISTANCEY = 60;
    private final float RATIOFACTOR = 0.2f;
    private float ratio = 1.0f;
    
    private Rectangle surfaceInvalide;
       
    private int absisse;
    
    private boolean bGlue;
    private boolean bInit;
   
    private ArkanoidJPanel pere;
    private ArrayList<Balle> ballesCollees;

    public Raquette(ArkanoidJPanel nPere) {
        pere = nPere;
        ballesCollees = new ArrayList<Balle>();
        
        bGlue = false;
        bInit = true;

        this.loadImage(pere.getClass().getResource(getNameFile()));
    
    }

    public void dessineImage(Graphics2D g2d){
        if (bInit) {
            absisse = pere.getSize().width / 2;
            bInit = false;
        }
        
        this.dessineImage(g2d,absisse - (int) (getDimension().width * ratio) / 2 ,pere.getSize().height - DISTANCEY ,(int)(getDimension().width * ratio) ,getDimension().height);    
 
        ArrayList<Balle> btmp = (ArrayList<Balle>) ballesCollees.clone();
        for(Balle b : btmp) {
            b.dessineImage(g2d);
        }
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }
    
    @Override
    public double angle(Balle balle) {
        int xBalle = (int) balle.getCentreX();
        double cosAngle = ((double) xBalle - absisse) / (getDimension().width * ratio / 2);
                
        if (cosAngle <= -1 )
            cosAngle = -0.98;
        else if (cosAngle >= 1 )
            cosAngle = 0.98;
        
        cosAngle =cosAngle /1.5;
        
        double angle = Math.acos(cosAngle) ;
        
        return angle;
    }

    @Override
    public boolean contact(Balle balle) {
        double angle = balle.getAngle();
        boolean result;
        if (angle <= Math.PI && angle >= 0) {
            result = false;
        } else {
            result = (int) balle.getCentreY() + balle.getDiametre() / 2 >= pere.getSize().height - DISTANCEY && (int) balle.getCentreY() + balle.getDiametre() / 2 <= pere.getSize().height - DISTANCEY && balle.getCentreX() + balle.getDiametre() / 2 >= this.absisse - 5 -(getDimension().width * ratio) / 2 && balle.getCentreX() - balle.getDiametre() / 2 <= this.absisse + 5 +(getDimension().width * ratio) / 2;
        }

        if (result && this.bGlue) {
            this.coller(balle);
        } else if (result) {
            this.touche();
        }

        return result;
    }
    
    public void retrecirRaquette() {
        if (ratio > 0.5) {
            ratio -= RATIOFACTOR;
        }
        for (Balle b : ballesCollees) {
            if (b.getCentreX() - b.getDiametre() / 2 > absisse + (int) (getDimension().width * ratio) / 2) {
                b.setCentreX(absisse + (int) (getDimension().width * ratio) / 2);
            } else if (b.getCentreX() + b.getDiametre() / 2 < absisse - (int) (getDimension().width * ratio) / 2) {
                b.setCentreX(absisse - (int) (getDimension().width * ratio) / 2);
            }
        }
    }

    public void agrandirRaquette() {
        if (ratio < 1.5) {
            ratio += RATIOFACTOR;
        }
        setAbsisse(this.absisse);

    }
    
    public Rectangle getSurface() {
        return new Rectangle(absisse - (int) (getDimension().width * ratio) / 2, pere.getSize().height - DISTANCEY, (int)(getDimension().width * ratio) ,getDimension().height); 
    }
        
    public ArrayList<Balle> getBallesCollees() {
        return this.ballesCollees;
    }
    
    public void setGlue(boolean glue) {
        this.bGlue = glue;
    }
    
    void coller(Balle balle) {
        balle.stop();
        this.ballesCollees.add(balle);
    }

    public void decoller(Balle nBall, boolean bToStart) {
        if ((!this.ballesCollees.contains(nBall)) || ((!bToStart) && (!nBall.hasBeenThrown()))) {
            return;
        } else {
            this.ballesCollees.remove(nBall);
            nBall.start();
        }
    }

    public void vider() {
        this.ballesCollees.clear();
    }

    public int getAbsisse() {
        return absisse;
    }
   
    void setAbsisse(int absisse) {
        this.absisse = absisse;
        if (absisse + (getDimension().width * ratio / 2) > pere.getSize().width - Brique.HAUTEUR) {
            this.absisse = (int) (pere.getSize().width - (getDimension().width * ratio / 2) - Brique.HAUTEUR);
        }

        if (absisse - (getDimension().width * ratio / 2) < 0 + Brique.HAUTEUR) {
            this.absisse = (int) (getDimension().width * ratio / 2 + Brique.HAUTEUR);
        }
        
        ArrayList<Balle> btmp = (ArrayList<Balle>) ballesCollees.clone();
        for (Balle b : btmp) {
            b.setCentreX(this.absisse);
        }    
    }    
     
    public void touche() {
    }
    
    private String getNameFile() {
        return "/images/raket.png";
    }
}
