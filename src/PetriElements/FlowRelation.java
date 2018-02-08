package PetriElements;

import javax.swing.*;
import java.awt.*;

public class FlowRelation extends JComponent {
    Point from, to;
    FlowRelationCore core;

    public FlowRelation(GraphicPetriElement start, GraphicPetriElement end){
        core = new FlowRelationCore(start.getCore(), end.getCore());
        from = start.getPos();
        to = end.getPos();
    }
    public void draw(Graphics g){
        g.setColor(Color.black);
        g.drawLine(from.x, from.y, to.x, to.y);
    }
}
