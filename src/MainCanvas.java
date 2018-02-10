import PetriElements.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class MainCanvas extends JPanel{
    ArrayList<Place> places;
    ArrayList<Transition> transitions;
    ArrayList<FlowRelation> flowRelations;
    MainFrame frame;
    MainCanvas panel;
    ControlPanel foreground;
    Arrow arr;
    Point arrowFrom;
    GraphicPetriElement from;

    public MainCanvas(MainFrame frame){
        places = new ArrayList<>();
        transitions = new ArrayList<>();
        flowRelations = new ArrayList<>();
        this.frame = frame;
        this.panel = this;
        this.setLayout(null);
        this.setBackground(Color.white);
        foreground = new ControlPanel(this);
        this.add(foreground);
        frame.c.add(this);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(FlowRelation f: flowRelations){
            f.draw(g);
        }
        for(Place p: places) {
            p.draw(g);
            p.setBounds(p.getPos().x-30, p.getPos().y-30, 60,60);
        }
        for(Transition t: transitions){
            t.draw(g);
            t.setBounds(t.getPos().x-30, t.getPos().y-30, 60,60);
        }
        if(arr != null) {
            arr.paintComponent(g);
        }
    }
    GraphicPetriElement findElememnt(Point point) throws Exception {
        for(Place p: places){
            if(p.getBounds().contains(point))
                return p;
        }
        for(Transition t: transitions){
            if(t.getBounds().contains(point))
                return t;
        }
        throw new Exception("No element found");
    }

    public void addFlowRelation(GraphicPetriElement from, GraphicPetriElement to){
        FlowRelation f = new FlowRelation(from, to);
        flowRelations.add(f);
        this.add(f);
        System.out.println("Flow relation added");
    }
    public void addPlace(int x, int y){
        Place p = new Place(x,y);
        places.add(p);
        this.add(p);
        System.out.println("Place added@ " + x + ":" + y);
    }
    public void addTransition(int x, int y){
        Transition t = new Transition(x,y);
        transitions.add(t);
        this.add(t);
        System.out.println("Transition added");
    }

    public String getToggled(){
        return frame.getToggled();
    }
    public void setArrow(int x, int y){
        if(arr == null){
            arr = new Arrow(arrowFrom, new Point(x,y));
        }else{
            arr.setArrow(new Point(x,y));
        }
    }
    public void setArrowFrom(Point p){
        arrowFrom = p;
    }
    public void eraseArrow(){
        arrowFrom = null;
        arr = null;
        repaint();
    }
    class Arrow extends JComponent{
        Point from, to;

        public Arrow(Point start, Point end){
            from = start;
            to = end;
        }
        public void setArrow(Point end){
            to = end;
        }
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(Color.black);
            g.drawLine(from.x,from.y,to.x,to.y);
        }
    }
    @Override
    public Dimension getSize(){
        return frame.getSize();
    }
}