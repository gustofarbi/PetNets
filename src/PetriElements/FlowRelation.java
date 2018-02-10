package PetriElements;

import javax.swing.*;
import java.awt.*;

public class FlowRelation extends JComponent {
    private int ID;
    private static int counter = 0;
    private int weight = 0;
    private GraphicPetriElement from, to;
    private Point fromPos, toPos;

    public FlowRelation(GraphicPetriElement start, GraphicPetriElement end){
        if(start.getClass() == end.getClass())
            throw new RuntimeException("Start element is the same as end element!@"
            + Thread.currentThread().getStackTrace()[2].getFileName()
            + Thread.currentThread().getStackTrace()[2].getLineNumber());
        from = start;
        to = end;
        from.getCore().addFromThis(this);
        to.getCore().addToThis(this);
        weight = Integer.parseInt(JOptionPane.showInputDialog("Adjust weight:"));

        ID = ++counter;
        fromPos = from.getPos();
        toPos = to.getPos();
    }
    private void refreshPosition(){
        fromPos = from.getPos();
        toPos = to.getPos();
    }
    public void draw(Graphics g){
        refreshPosition();
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(2.0f));
        g2.drawLine(fromPos.x, fromPos.y, toPos.x, toPos.y);
    }
}
