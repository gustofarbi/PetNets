import PetriElements.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class MainCanvas extends JPanel{
    private ArrayList<Place> places;
    private ArrayList<Transition> transitions;
    private ArrayList<Arc> arcs;
    private MainFrame frame;
    private MainCanvas canvas = this;
    private ControlPanel foreground;
    @Nullable
    private Arrow arr;
    @Nullable
    private Point arrowFrom;
    private FileIO file;

    MainCanvas(MainFrame frame){
        file = new FileIO(this);
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
            System.out.println("Arc added");
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
        "   Transitions: " + transitions.size() +
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
        if(arr == null && arrowFrom != null) arr = new Arrow(arrowFrom, p);
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
        AffineTransform tx;
        Line2D.Double line;
        Polygon arrowHead;

        Arrow(Point start, Point end){
            from = start;
            to = end;
            tx = new AffineTransform();
            line = new Line2D.Double(from.x,from.y,to.x,to.y);
            arrowHead = new Polygon();
            arrowHead.addPoint(0,5);
            arrowHead.addPoint(-5,-5);
            arrowHead.addPoint(5,-5);
        }
        void setArrow(Point end){
            to = end;
            line.setLine(from.x, from.y, to.x, to.y);
        }
        @Override
        public void paintComponent(Graphics g){
            tx.setToIdentity();
            double angle = Math.atan2(line.y2-line.y1, line.x2-line.x1);
            tx.translate(line.x2, line.y2);
            tx.rotate(angle-Math.PI/2d);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setStroke(new BasicStroke(2.0f));
            g2.draw(new Line2D.Double(from.x, from.y, to.x, to.y));
            g2.setTransform(tx);
            g2.fill(arrowHead);
            g2.dispose();
        }
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

    @Override
    public Dimension getSize(){

        return frame.getSize();
    }
    public void clear(){
        arcs.clear();
        places.clear();
        transitions.clear();
        canvas.repaint();
    }
    public void animateStep(){
        Logic.setUp(transitions);
        ArrayList<Arc> arr = Logic.firstStep();

        for(Arc a: arr)
            a.setToken();
        Timer timer = new Timer(30,new AnimationListener(arr));
        timer.start();
        arr = Logic.secondStep();
        for(Arc a: arr){
            a.setToken();
        }
        timer = new Timer(30, new AnimationListener(arr));
        timer.setInitialDelay(750);
        timer.start();
    }
    public void play(){

    }
    public void stop(){

    }
    class AnimationListener implements ActionListener{
        ArrayList<Arc> arrToAnimate;
        AnimationListener(ArrayList<Arc> arrToAnimate){
            this.arrToAnimate = arrToAnimate;
        }
        @Override
        public void actionPerformed(ActionEvent e){
            int count = 0;
            for(Arc a: arrToAnimate){
                if(a.isDone()){
                    a.disposeToken();
                }
                else if(a.isTokenSet()){
                    count++;
                    a.makeStep();
                }
                canvas.repaint();
            }
            if(count == 0){
                ((Timer)e.getSource()).stop();
            }
        }
    }
}