import PetriElements.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainCanvas extends JPanel{
    private ArrayList<Place> places;
    private ArrayList<Transition> transitions;
    private ArrayList<FlowRelation> flowRelations;
    private MainFrame frame;
    private ControlPanel foreground;
    @Nullable
    private Arrow arr;
    @Nullable
    private Point arrowFrom;

    MainCanvas(MainFrame frame){
        places = new ArrayList<>();
        transitions = new ArrayList<>();
        flowRelations = new ArrayList<>();
        this.frame = frame;
        this.setLayout(null);
        this.setBackground(Color.white);
        foreground = new ControlPanel(this);
        this.add(foreground);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                foreground.setSize(getWidth(), getHeight());
            }
        });
        frame.c.add(this);
    }

    @Override
    public void paintComponent(@NotNull Graphics g){
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
    @NotNull GraphicPetriElement findElememnt(@NotNull Point point) throws Exception {
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
    public boolean isDuplicate(FlowRelation f){
        for(FlowRelation foo: flowRelations)
            if(foo.equals(f))
                return true;
        return false;
    }
    public void addFlowRelation(@NotNull GraphicPetriElement from, @NotNull GraphicPetriElement to){
        FlowRelation f = new FlowRelation(from, to);
        if(isDuplicate(f))
            throw new RuntimeException("Flow relations already exists!");
        else {
            flowRelations.add(f);
            this.add(f);
            System.out.println("Flow relation added");
        }
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
        System.out.println("Transition added@" + x + ":" + y);
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
    public void setArrowFrom(@Nullable Point p){
        arrowFrom = p;
    }
    public void eraseArrow(){
        arrowFrom = null;
        arr = null;
        repaint();
    }
    class Arrow extends JComponent{
        Point from, to;

        Arrow(Point start, Point end){
            from = start;
            to = end;
        }
        void setArrow(Point end){
            to = end;
        }
        @Override
        public void paintComponent(@NotNull Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.black);
            g2.setStroke(new BasicStroke(2.0f));
            g2.drawLine(from.x,from.y,to.x,to.y);
        }
    }
    @Override
    public Dimension getSize(){
        return frame.getSize();
    }
}