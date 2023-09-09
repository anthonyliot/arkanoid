/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthony.liot.arkanoid;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author loup
 */
public class Brique extends GraphicalObject implements IObstacle {

    public static final int RESISTANCE_INFINIE = 5;
    public static final int DISTANCEY = 40;
    public static double COSLIMIT;
    public static double SINLIMIT;
    public static int LARGEUR;
    public static int HAUTEUR;
    private static Random random = new Random();
    private int i;
    private int j;
    private int centreX;
    private int centreY;
    private int resistance;
    private int points;
    private BufferedImage image;
    private ABonus bonus;
    private ArkanoidJPanel pere;

    
    public Brique(int nI, int nJ, int nResistance, ArkanoidJPanel nPere) {
        pere = nPere;
        resistance = nResistance;

        this.loadImage(pere.getClass().getResource(getNameFile()));

        LARGEUR = getDimension().width;
        HAUTEUR = getDimension().height;

        COSLIMIT = (Brique.LARGEUR / 2) / (Math.hypot(Brique.LARGEUR / 2, Brique.HAUTEUR / 2));
        SINLIMIT = (Brique.HAUTEUR / 2) / (Math.hypot(Brique.LARGEUR / 2, Brique.HAUTEUR / 2));

        i = nI;
        j = nJ;
        
        centreX = getCentreX();
        centreY = getCentreY();

        points = getPoints();
        bonus=null;

    }

    public void dessineImage(Graphics2D g2d) {
        this.dessineImage(g2d, getHautX(), getHautY());
    }

    public void touche() {
        if (resistance != Brique.RESISTANCE_INFINIE) {
            resistance--;
            if (this.resistance == 0) {
                detruire();
            }
        }
    }

    public void detruire() {
        pere.ajouterPoints(this.points);
        bonus = getBonus();
        if (pere.supprimerBrique(this) && this.bonus != null) {
            System.out.println("BONUS");
            this.bonus.start();
        }
    }
    
    private ABonus getBonus() {
        int nBonus = random.nextInt(100);
        //nBonus = 6;
        switch (nBonus) {
            case 0: {
                return new BonusGlue(pere, pere.getTimer(), this.getHautX(), this.getHautY());
            }
            case 1: {
                return new BonusGrandeRaquette(pere, pere.getTimer(), this.getHautX(), this.getHautY());
            }
            case 2: {
                return new BonusPetiteRaquette(pere, pere.getTimer(), this.getHautX(), this.getHautY());
            }
            case 3: {
                return new BonusAccelerationBalle(pere, pere.getTimer(), this.getHautX(), this.getHautY());
            }
            case 4: {
                return new BonusRalentirBalle(pere, pere.getTimer(), this.getHautX(), this.getHautY());
            }
            case 5: {
                return new BonusAnnuleGlue(pere, pere.getTimer(), this.getHautX(), this.getHautY());
            }
            case 6: {
                return new BonusMultiBalle(pere, pere.getTimer(), this.getHautX(), this.getHautY());
            }
            case 7: {
                int chance = random.nextInt(3);
                if (chance == 0) {
                    return new BonusExtraBalle(pere, pere.getTimer(), this.getHautX(), this.getHautY());
                } else {
                    return null;
                }
            }
            case 8: {
                return new BonusBouclier(pere, pere.getTimer(), this.getHautX(), this.getHautY());
            }
            case 9: {
                int chance = random.nextInt(2);
                if (chance == 0) {
                    return new BonusFeu(pere, pere.getTimer(), this.getHautX(), this.getHautY());
                } else {
                    return null;
                }

            }
            case 10: {
                int chance = random.nextInt(3);
                if (chance == 0) {
                    return new BonusMitraillette(pere, pere.getTimer(), this.getHautX(), this.getHautY());
                }

            }
            default:
                return null;
        }

    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getResistance() {
        return resistance;
    }

    int getHautX() {
        return pere.difference() + getI() * getDimension().width;
    }

    int getHautY() {
        return DISTANCEY + getJ() * getDimension().height;
    }

    int getCentreX() {
        //return pere.difference() + getI() * getDimension().width - getDimension().width / 2;
        //return pere.difference() + getI() * getDimension().width + getDimension().width / 2;
        return getHautX() + (Brique.LARGEUR / 2);
    }

    int getCentreY() {
        //return DISTANCEY + getJ() * getDimension().height - getDimension().height / 2;
        //return DISTANCEY + getJ() * getDimension().height + getDimension().height / 2;
        return getHautY() + (Brique.HAUTEUR / 2);
    }

    int getPoints() {
        if (resistance > 0 && resistance != RESISTANCE_INFINIE) {
            return (resistance * 5) + (resistance - 1) * 10;
        } else {
            return 0;
        }
    }

    private Rectangle getSurface() {
        return new Rectangle(getHautX(), getHautY(), Brique.LARGEUR, Brique.HAUTEUR);
    }

    private String getNameFile() {
        return "/images/brique" + resistance + ".gif";
    }
    
    
    public boolean ballePasEncorePartie(double angle, Balle balle)
    {
        double distance = Math.hypot(balle.getNextCentreX() - centreX, balle.getNextCentreY() - centreY);
        double nextDistance =  Math.hypot(balle.getNextCentreX(angle) - centreX, balle.getNextCentreY(angle) - centreY);
        if( nextDistance <= distance )
            return false;
        return true;
    }
    
    public double angle(Balle balle) {
        
        if (balle.estEnFlamme() && this.resistance != RESISTANCE_INFINIE)
            return balle.getAngle();
        
        if (Math.abs(cos(balle)) > COSLIMIT )
        {
            double angle = Math.PI - balle.getAngle();
            if (angle > Math.PI) {
                angle -= 2 * Math.PI;
            }
            if( ballePasEncorePartie(angle,balle) )
                return angle;
            return balle.getAngle();
        }
        else if(Math.abs(sin(balle)) >= SINLIMIT )
        {
            double angle = - balle.getAngle();
            if( ballePasEncorePartie(angle,balle) )
                return angle;
            return balle.getAngle();
        }
        else 
        {
            //System.out.println("angle etrange!!!!!!!!");
            return - balle.getAngle();
        }
    }

    public double hypot(Balle balle) {
        double adja = (getCentreX() - balle.getCentreX());
        double opos = (getCentreY() - balle.getCentreY());
        return Math.hypot(adja, opos);
    }

    public double sin(Balle balle) {
        double hypo = hypot(balle);
        double opos = (getCentreY() - balle.getCentreY());
        return opos / hypo;
    }

    public double cos(Balle balle) {
        double adja = (getCentreX() - balle.getCentreX());
        double hypo = hypot(balle);
        return adja / hypo;
    }

    public Point pointContact(Balle balle) {
        double cosa = cos(balle);
        double sina = sin(balle);

        return (new Point((int) (balle.getCentreX() + (balle.getRayon()) * cosa), (int) (balle.getCentreY() + (balle.getRayon()) * sina)));

    }
    
    public boolean contact(Balle balle) {
        Point pointC = pointContact(balle);
        if (this.getSurface().contains(pointC))
        {
            return true;
        }
        return false;
    }
}
