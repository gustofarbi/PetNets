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
    private ArrayList<Arc> arcs;
    private MainFrame frame;
    private ControlPanel foreground;
    @Nullable
    private Arrow arr;
    @Nullable
    private Point arrowFrom;

    MainCanvas(MainFrame frame){
        places = new ArrayList<>();
        transitions = new ArrayList<>();
        arcs = new ArrayList<>();
        this.frame = frame;
        this.setLayout(null);
        this.setBackground(Color.white);
        this.updateStats();
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
        for(Arc f: arcs){
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
        if(arr != null) arr.paintComponent(g);
    }
    @NotNull GraphicPetriElement findElememnt(@NotNull Point point) throws RuntimeException {
        for(Place p: places){
            if(p.getBounds().contains(point))
                return p;
        }
        for(Transition t: transitions){
            if(t.getBounds().contains(point)) return t;
        }
        throw new RuntimeException("No element found");
    }
    private boolean isDuplicate(Arc f){
        for(Arc foo: arcs)
            if(foo.equals(f)) return true;
        return false;
    }
    public void addArc(@NotNull GraphicPetriElement from, @NotNull GraphicPetriElement to) throws RuntimeException{
        Arc f = new Arc(from, to);
        if(isDuplicate(f)){
            frame.setMessage("Arc already exists!");
            throw new RuntimeException("Arc already exists!");
        }
        else {
            arcs.add(f);
            this.add(f);
            System.out.println("Arc relation added");
            updateStats();
        }
    }
    public void addPlace(int x, int y){
        Place p = new Place(x,y);
        places.add(p);
        this.add(p);
        System.out.println("Place added@ " + x + ":" + y);
        updateStats();
    }
    public void addTransition(int x, int y){
        Transition t = new Transition(x,y);
        transitions.add(t);
        this.add(t);
        System.out.println("Transition added@" + x + ":" + y);
        updateStats();
    }
    private void updateStats(){
        frame.setStats("Places: " + places.size() +
        "   Transition: " + transitions.size() +
        "   Arcs: " + arcs.size() + "   ");
    }
    public void eraseElement(Point p){
        System.out.println("Erasing element");
        try{
            GraphicPetriElement g = findElememnt(p);
            for(Arc a: g.getCore().getFromThis())
                arcs.remove(a);
            for(Arc a: g.getCore().getToThis())
                arcs.remove(a);
            if(g.getClass() == Place.class) places.remove(g);
            else if(g.getClass() == Transition.class) transitions.remove(g);
            updateStats();
        }catch (Exception err) {
            System.err.println(err.getMessage());
        }
    }

    public String getToggled(){
        return frame.getToggled();
    }
    public void setArrow(Point p){
        if(arr == null) arr = new Arrow(arrowFrom, p);
        else arr.setArrow(p);
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