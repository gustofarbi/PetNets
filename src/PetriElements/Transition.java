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
    public Transition(int x, int y){
        name = "T: " + ++counter;
        xPos = x;
        yPos = y;
        core = new TransitionCore();
    }
    public String getname(){return name;}
    public void setName(String nName){name = nName;}
    @NotNull
    @Override public Point getPos(){return new Point(xPos,yPos);}
    @Override public void setPos(int x, int y){
        xPos = x;
        yPos = y;
    }
    @Override public TransitionCore getCore(){
        return core;
    }
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
    public void draw(@NotNull Graphics g){
        setImage();
        g.drawImage(bi, xPos-(size/2), yPos-(size/2), null);
    }
    @Override public void paintComponent(@NotNull Graphics g){draw(g);}
}
