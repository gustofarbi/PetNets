package PetriElements;

import javax.swing.*;
import java.awt.*;

public class Arc extends JComponent {
    private int ID;
    private static int counter = 0;
    private int weight;
    private GraphicPetriElement from, to;
    private Point fromPos, toPos;

    public Arc(GraphicPetriElement start, GraphicPetriElement end){
        if(start.getClass() == end.getClass()) {
            throw new RuntimeException("Start element is the same as end element!@"
            + Thread.currentThread().getStackTrace()[2].getFileName()
            + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        from = start;
        to = end;
        from.getCore().addFromThis(this);
        to.getCore().addToThis(this);
        weight = 1;

        ID = ++counter;
        fromPos = from.getPos();
        toPos = to.getPos();
    }
    public int getID(){return ID;}
    public void setWeight(int w){ if(w > 0) weight = w; }
    public int getWeight(){return weight;}
    private void refreshPosition(){
        fromPos = from.getPos();
        toPos = to.getPos();
    }
    private Point getFromPos(){return fromPos;}
    private Point getToPos(){return toPos;}
    private Point getTextPos(){ return new Point((fromPos.x+toPos.x)/2, (fromPos.y+toPos.y)/2); }
    public void draw(Graphics g){
        refreshPosition();
        final int offset = 10;
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(2.0f));
        Point textPos = getTextPos();
        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        g2.drawString("ID: " + ID, textPos.x+offset,textPos.y+offset);
        g2.drawLine(fromPos.x, fromPos.y, toPos.x, toPos.y);
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Arc))
            return false;
        Arc foo = (Arc) o;
        return  getFromPos().x == foo.getFromPos().x &&
                getFromPos().y == foo.getFromPos().y &&
                getToPos().x == foo.getToPos().x &&
                getToPos().y == foo.getToPos().y;
    }
}
