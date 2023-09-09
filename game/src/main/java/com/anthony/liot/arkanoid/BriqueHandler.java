/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anthony.liot.arkanoid;

import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import javax.xml.parsers.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author Administrateur
 */
public class BriqueHandler extends DefaultHandler {

    private ArrayList<ArrayList<Brique>> _briques;
    private int _level;
    private ArkanoidJPanel _parent;
    private int MAX;
    private int MAY;
    private int nbBrique;
    private ArrayList<Brique> _listBriques;

    public BriqueHandler(ArkanoidJPanel parent) {
        super();
        MAX = 0;
        MAY = 0;
        _parent = parent;
    }

    public void parse(String nFile) {
        InputStream in = getClass().getResourceAsStream(nFile);
        SAXParser parser;
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            parser = factory.newSAXParser();
            parser.parse(in, this);

        } catch (ParserConfigurationException pce) {
            System.out.println("Erreur de configuration du parseur");
            System.out.println("Lors de l'appel � SAXParser.newSAXParser()");
        } catch (SAXException se) {
            System.out.println("Erreur de parsing");
            System.out.println("Lors de l'appel � parse()");
            se.printStackTrace();
        } catch (IOException ioe) {
            System.out.println("Erreur d'entr�e/sortie");
            System.out.println("Lors de l'appel � parse()");
        }
    }

    //d�tection d'ouverture de balise
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("arkanoid")) {
            _listBriques = new ArrayList<Brique>();
        } else if (qName.equals("level")) {
            _level = Integer.parseInt(attributes.getValue("id"));
        } else if (qName.equals("brique")) {
            int posx = Integer.parseInt(attributes.getValue("posx"));
            if (posx + 1 > MAX) {
                MAX = posx + 1;
            }

            int posy = Integer.parseInt(attributes.getValue("posy"));
            if (posy + 1 > MAY) {
                MAY = posy + 1;
            }

            int resist = Integer.parseInt(attributes.getValue("resist"));

            Brique temp = new Brique(posx, posy, resist, _parent);
            if (resist != Brique.RESISTANCE_INFINIE) {
                nbBrique++;
            } else {
                System.out.println("---------------");
            }
            _listBriques.add(temp);
        } else {
            //erreur, on peut lever une exception
            throw new SAXException("Balise " + qName + " inconnue.");
        }
    }

    //d�tection fin de balise
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("arkanoid")) {
            System.out.println("Fin de lecture du fichier xml");
            createMyMatrice();
        }
    }

    public int getMAX() {
        return MAX;
    }

    public int getNbBrique() {
        return nbBrique;
    }

    public int level() {
        return _level;
    }

    public ArrayList<ArrayList<Brique>> briques() {
        return _briques;
    }

    private void createMyMatrice() {
        System.out.println("MAX: " + MAX + " MAY: " + MAY);
        _briques = new ArrayList<ArrayList<Brique>>(MAY);

        for (int i = 0; i < MAY; i++) {
            _briques.add(new ArrayList<Brique>(MAX));
            for (int j = 0; j < MAX; j++) {
                _briques.get(i).add(j, null);
            }
        }

        for (Brique b : _listBriques) {
            _briques.get(b.getJ()).add(b.getI(), b);
        }
    }
}                
