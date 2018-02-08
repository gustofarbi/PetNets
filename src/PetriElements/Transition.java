package PetriElements;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Transition extends JComponent implements GraphicPetriElement {
    private static final int size = 60;
    private int xPos, yPos;
    private TransitionCore core;
    private String name;
    private BufferedImage bi;
    private static int counter = 0;
    public Transition(int x, int y){
        name = "T: " + ++counter;
        xPos = x;
        yPos = y;
        core = new TransitionCore();
    }
    public String getname(){return name;}
    public void setName(String nName){name = nName;}
    public Point getPos(){return new Point(xPos,yPos);}
    public void setPos(int x, int y){xPos = x; yPos = y;}
    public TransitionCore getCore(){
        return core;
    }
    public void addToThis(PetriElement p){
        core.addToThis(p);
    }
    public void addFromThis(PetriElement p){
        core.addFromThis(p);
    }
    private void setImage(){
        try{
            bi = ImageIO.read(new File("ressources/transition.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        Graphics2D g = bi.createGraphics();
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.setColor(Color.black);
        g.drawString(name, 12, 23);
    }
    public void draw(Graphics g){
        setImage();
        g.drawImage(bi, xPos-(size/2), yPos-(size/2), null);
    }
}
