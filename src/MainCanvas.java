import PetriElements.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainCanvas extends JPanel{
    private MainFrame frame;
    private MainCanvas canvas = this;
    private ControlPanel foreground;
    @Nullable
    private Arrow arr;
    @Nullable
    private Point arrowFrom;
    private FileIO file;
    private ScheduledExecutorService executorService;

    MainCanvas(MainFrame frame){
        file = new FileIO(this);
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


    @NotNull GraphicPetriElement findElememnt(@NotNull Point point) throws RuntimeException {
        return file.findElement(point);
    }
    public void addArc(@NotNull GraphicPetriElement from, @NotNull GraphicPetriElement to) throws RuntimeException{
        Arc f = new Arc(from, to);
        if(file.isDuplicate(f)){
            frame.setMessage("Arc already exists!");
            throw new RuntimeException("Arc already exists!");
        }
        else {
            file.addArc(f);
            this.add(f);
            System.out.println("Arc added");
            updateStats();
        }
    }
    public void addPlace(int x, int y){
        Place p = new Place(x,y);
        file.addPlace(p);
        this.add(p);
        System.out.println("Place added@ " + x + ":" + y);
        updateStats();
    }
    public void addTransition(int x, int y){
        Transition t = new Transition(x,y);
        file.addTransition(t);
        this.add(t);
        System.out.println("Transition added@" + x + ":" + y);
        updateStats();
    }
    public void updateStats(){
        frame.setStats("Places: " + file.places.size() +
        "   Transitions: " + file.transitions.size() +
        "   Arcs: " + file.arcs.size() + "   ");
    }
    public void setMessage(String msg){
        frame.setMessage(msg);
    }
    public void eraseElement(Point p){
        System.out.println("Erasing element");
        file.eraseElement(p);
        updateStats();
    }

    public String getToggled(){
        return frame.getToggled();
    }
    public void setArrow(Point p){
        if(arr == null && arrowFrom != null) arr = new Arrow(arrowFrom, p);
        else if(arrowFrom != null) arr.setArrow(p);
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
        for(Arc f: file.arcs){
            f.draw(g);
        }
        for(Place p: file.places) {
            p.draw(g);
            p.setBounds(p.getPos().x-30, p.getPos().y-30, 60,60);
        }
        for(Transition t: file.transitions){
            t.draw(g);
            t.setBounds(t.getPos().x-30, t.getPos().y-30, 60,60);
        }
        if(arr != null) arr.paintComponent(g);
    }

    @Override
    public Dimension getSize(){

        return frame.getSize();
    }
    public FileIO getFile(){
        return this.file;
    }

    public void animateStep(){
        final int delay = 50;
        Logic.setUp(file.transitions);
        if(!Logic.stepPossible()){
            if(executorService != null)
                executorService.shutdown();
            JOptionPane.showMessageDialog(null, "Another step is not possible!");
        }else {
            ArrayList<Arc> arr = Logic.firstStep();

            for (Arc a : arr)
                a.setToken();
            Timer timer = new Timer(delay, new AnimationListener(arr));
            timer.start();
            arr = Logic.secondStep();
            for (Arc a : arr) {
                a.setToken();
            }
            timer = new Timer(delay, new AnimationListener(arr));
            timer.setInitialDelay(1000);
            timer.start();
        }
    }
    public void play(){
        if(executorService == null) {
            executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(this::animateStep, 0, 5000, TimeUnit.MILLISECONDS);
        }
    }
    public void stop(){
        if(executorService != null) {
            executorService.shutdown();
            executorService = null;
        }
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