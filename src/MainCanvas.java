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
    JPanel foreground;
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
        this.addMouseListener(new PanelMouseListener());
        foreground = new JPanel(null);
        foreground.setSize(this.getSize());
        foreground.setBackground(Color.red);
        foreground.setOpaque(false);
        foreground.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Foreground clicked");
            }
        });
        this.add(foreground);
        frame.c.add(this);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(Place p: places) {
            p.draw(g);
            p.setBounds(p.getPos().x-30, p.getPos().y-30, 60,60);
        }
        for(Transition t: transitions){
            t.draw(g);
            t.setBounds(t.getPos().x-30, t.getPos().y-30, 60,60);
        }
        for(FlowRelation f: flowRelations){
            f.draw(g);
        }
        if(arr != null) {
            arr.paintComponent(g);
        }
    }
    class PanelMouseListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e){
            if(!frame.getToggled().isEmpty()){
                switch(frame.getToggled()){
                    case "place":
                        addPlace(e.getX(),e.getY());
                        break;
                    case "transition":
                        addTransition(e.getX(),e.getY());
                        break;
                    default:
                        break;
                }
                repaint();
            }
        }
        @Override
        public void mouseReleased(MouseEvent e){
            if(arr != null){
                arr = null;
                repaint();
            }
            Point offset = getLocationOnScreen();
            int x = e.getXOnScreen()-offset.x;
            int y = e.getYOnScreen()-offset.y;

            boolean jop = false;

            for(Place p: places){
                System.out.println("Bounds: " + p.getBounds());
                System.out.println("Click:  " + x + ":" + y);
                if(p.getBounds().contains(x, y))
                    jop = true;
            }
            for(Transition t: transitions){
                if(t.contains(x,y))
                    jop = true;
            }
            if(jop){
                System.out.println("Mouse released over object");
            }
        }
    }
    class GraphicElementMouseMotionListener extends MouseMotionAdapter{
        @Override
        public void mouseDragged(MouseEvent e){
            if(frame.getToggled().equals("fr")){
                Point offset = getLocationOnScreen();
                int x = e.getXOnScreen()-offset.x;
                int y = e.getYOnScreen()-offset.y;
                if(panel.arr == null){
                    arr = new Arrow(panel.arrowFrom, new Point(x,y));
                    panel.add(arr);
                }else{
                    arr.setArrow(new Point(x,y));
                }
                repaint();
            }else {
                GraphicPetriElement g = (GraphicPetriElement) e.getSource();
                Point offset = getLocationOnScreen();
                int x = e.getXOnScreen() - offset.x;
                int y = e.getYOnScreen() - offset.y;
                g.setPos(x, y);
                repaint();
            }
        }
    }
    class GraphicElementMouseListener extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e){
            if(frame.getToggled().equals("fr")){
                from = (GraphicPetriElement)e.getSource();
                Point offset = getLocationOnScreen();
                int x = e.getXOnScreen()-offset.x;
                int y = e.getYOnScreen()-offset.y;
                arrowFrom = new Point(x,y);
            }
        }
        @Override
        public void mouseReleased(MouseEvent e){
            if(frame.getToggled().equals("fr") && from.getClass() != e.getSource().getClass()){
                addFlowRelation(from, (GraphicPetriElement)e.getSource());
            }
            arr = null;
            repaint();
        }

    }
    public void addFlowRelation(GraphicPetriElement from, GraphicPetriElement to){
        FlowRelation f = new FlowRelation(from, to);
        flowRelations.add(f);
        this.add(f);
        System.out.println("Flow relation added");
    }
    public void addPlace(int x, int y){
        Place p = new Place(x,y);
        p.addMouseMotionListener(new GraphicElementMouseMotionListener());
        p.addMouseListener(new GraphicElementMouseListener());
        places.add(p);
        this.add(p);
        System.out.println("Place added@ " + x + ":" + y);
    }
    public void addTransition(int x, int y){
        Transition t = new Transition(x,y);
        t.addMouseMotionListener(new GraphicElementMouseMotionListener());
        t.addMouseListener(new GraphicElementMouseListener());
        transitions.add(t);
        this.add(t);
        System.out.println("Transition added");
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
        System.out.println("size of frame" + frame.getSize());
        return frame.getSize();
    }
}