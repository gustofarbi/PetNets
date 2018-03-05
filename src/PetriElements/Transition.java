package PetriElements;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Transition extends JComponent implements GraphicPetriElement {
    private static final int size = 60;
    private int xPos, yPos;
    private TransitionCore core;
    private String name;
    private BufferedImage bi;
    private static int counter = 0;

    /**
     * Ctor initialisiert Position, Name und Core-Objekt
     * @param x int xPosition
     * @param y int yPosition
     */
    public Transition(int x, int y){
        name = "T: " + ++counter;
        xPos = x;
        yPos = y;
        core = new TransitionCore();
    }

    /**
     * Getter, gibt Name zurueck
     * @return String Name
     */
    public String getName(){return name;}

    /**
     * Setter, setzt den Namen neu
     * @param nName String neuer Name
     */
    public void setName(String nName){name = nName;}

    /**
     * Getter, gibt Position als java.awt.Point zurueck
     * @return jawa.awt.Point Position
     */
    @NotNull
    @Override public Point getPos(){return new Point(xPos,yPos);}

    /**
     * Setter, setzt Position neu
     * @param x int xKoordinate
     * @param y int yKoordinate
     */
    @Override public void setPos(int x, int y){
        xPos = x;
        yPos = y;
    }

    /**
     * Getter, gibt PetriElementCore-Objekt zuruec
     * @return PetriElementCore Kern
     */
    @Override public TransitionCore getCore(){
        return core;
    }

    /**
     * Initialisiert das Bild von Transition und zeichnet auf Graphics2D-Objekt
     */
    private void setImage(){
        try{
            bi = ImageIO.read(getClass().getResource("/ressources/images/transition.png"));
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
        g.drawString(name, 12, 23);
    }

    /**
     * Funktion laesst Bild von Transiton initialisieren und setzt dessen Position
     * @param g Graphics-Objekt
     */
    public void draw(@NotNull Graphics g){
        setImage();
        g.drawImage(bi, xPos-(size/2), yPos-(size/2), null);
    }

    /**
     * Funktion ueberlaedt void paintComponent(Graphics g) Methode aus JComponent
     * @param g Graphics-Objekt
     */
    @Override public void paintComponent(@NotNull Graphics g){draw(g);}
}
