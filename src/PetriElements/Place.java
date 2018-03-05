package PetriElements;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Place extends JComponent implements GraphicPetriElement {
    private static final int size = 60;
    private int xPos, yPos;
    private PlaceCore core;
    private String name;
    private BufferedImage bi;
    private static int counter = 0;

    /**
     * Ctor - initialisiert Position und Name, und PetriElementCore-Objekt
     * @param x int xPosition
     * @param y int yPosition
     */
    public Place(int x, int y){
        name = "P: " + ++counter;
        xPos = x;
        yPos = y;
        core = new PlaceCore();
    }

    /**
     * Name-setter
     * @param nName String neuer Name
     */
    public void setName(String nName){name = nName; }

    /**
     * Getter, gibt Position als Point zurueck
     * @return java.awt.Point Position
     */
    @NotNull
    @Override public Point getPos(){ return new Point(xPos,yPos); }

    /**
     * Setter, setzt Position neu
     * @param x int xKoordinate
     * @param y int yKoordinate
     */
    @Override public void setPos(int x, int y){ xPos = x;yPos = y; }

    /**
     * Getter, gibt PetriElementCore-Objekt zurueck
     * @return PetriElementCore Kern
     */
    @Override public PetriElementCore getCore(){return core;}

    /**
     * Getter, gibt Name als String zurück
     * @return String Name
     */
    @Override public String getName(){ return name; }

    /**
     * Initialisiert das Bild von Stelle und zeichnet auf Graphics2D-Objekt
     */
    private void imageInit(){
        try{
            bi = ImageIO.read(getClass().getResource("/ressources/images/place.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        Graphics2D g = bi.createGraphics();

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHints(rh);

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.setColor(Color.black);
        g.drawString(name, 12,23);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("C: " + (core.getCapacity() > 99 ? "k.A." : core.getCapacity()), 12, 48);
        if(((PlaceCore)getCore()).getTokens() > 0) {
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.setColor(Color.red);
        }
        g.drawString("t: " + core.getTokens(), 12, 36);
    }

    /**
     * Funktion laesst Stellenbild initialisieren und setzt dessen Position
     * @param g Graphics-Objekt
     */
    public void draw(@NotNull Graphics g){
        imageInit();
        g.drawImage(bi, xPos-(size/2), yPos-(size/2), null);
    }

    /**
     * Funktion ueberladet void paintComponent(Graphics g) Methode von JComponent
     * @param g Graphics-Objekt
     */
    @Override public void paintComponents(@NotNull Graphics g){draw(g);}
}
