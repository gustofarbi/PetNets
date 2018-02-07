package PetriElements;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Place extends JComponent implements GraphicPetriElement {
    private static final int size = 60;
    private int xPos, yPos;
    private PlaceCore core;
    private String name;
    private BufferedImage bi;
    private static int counter = 0;
    public Place(int x, int y){
        name = "P: " + ++counter;
        xPos = x;
        yPos = y;
        core = new PlaceCore();
    }
    public String getName(){ return name; }
    public void setName(String nName){name = nName; }
    public Point getPos(){ return new Point(xPos,yPos); }
    public void setPos(int x, int y){ xPos = x;yPos = y; }
    private void setImage(){
        try{
            bi = ImageIO.read(new File("ressources/place.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        Graphics2D g = bi.createGraphics();
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.setColor(Color.black);
        g.drawString(name, 12,23);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString("t: " + core.getTokens(), 12, 36);
        g.drawString("C: " + (core.getCapacity() > 99 ? "k.A." : core.getCapacity()), 12, 48);
    }

    public void draw(Graphics g){
        setImage();
        g.drawImage(bi, xPos-(size/2), yPos-(size/2), null);
    }
}
