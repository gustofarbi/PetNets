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

    /**
     * Ctor setzt alle Attribute und erzeugt ControlPanel-Objekt und FileIO-Objekt
     * Zum Schluss fuegt sich selber in den Container von frame hinzu
     * @param frame aufrufendes MainFrame-Objekt
     */
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


    /**
     * Suche nach einem Element, wird an FileIO.findElement delegiert
     * @param point java.awt.Point Position
     * @return GraphicPetriElement Treffer
     * @throws RuntimeException wenn kein Element gefunden ist
     */
    @NotNull GraphicPetriElement findElememnt(@NotNull Point point) throws RuntimeException {
        return file.findElement(point);
    }

    /**
     * Fuegt eine Kante in FileIO-Objekt hinzu, schaut davor ob es ein Duplikat ist
     * @param from GraphicPetriElement von
     * @param to GraphicPetriElement bis
     * @throws RuntimeException wenn Kante ein Duplikat ist
     */
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

    /**
     * Fuegt Stelle in FileIO-Objekt
     * @param x xPositoin
     * @param y yPosition
     */
    public void addPlace(int x, int y){
        Place p = new Place(x,y);
        file.addPlace(p);
        this.add(p);
        System.out.println("Place added@ " + x + ":" + y);
        updateStats();
    }

    /**
     * Fuegt Transition in FileIO-Objekt
     * @param x xPosition
     * @param y yPosition
     */
    public void addTransition(int x, int y){
        Transition t = new Transition(x,y);
        file.addTransition(t);
        this.add(t);
        System.out.println("Transition added@" + x + ":" + y);
        updateStats();
    }

    /**
     * Aktualisiert Statistik und zeichnet diese auf MessageBar-Objekt
     */
    public void updateStats(){
        frame.setStats("Places: " + file.places.size() +
        "   Transitions: " + file.transitions.size() +
        "   Arcs: " + file.arcs.size() + "   ");
    }

    /**
     * Schreibt Nachricht auf MessageBar-Objekt
     * @param msg String Nachricht
     */
    public void setMessage(String msg){
        frame.setMessage(msg);
    }

    /**
     * Loescht Objekt, delegiert an FileIO
     * @param p java.awt.Point Position
     */
    public void eraseElement(Point p){
        System.out.println("Erasing element");
        file.eraseElement(p);
        updateStats();
    }

    /**
     * Getter, gibt ActionCommand vom toggled-Buttom aus dem ToolBar-Objekt zurueck
     * @return String toggled
     */
    public String getToggled(){
        return frame.getToggled();
    }

    /**
     * Zeichnet ein Pfeil, wenn kein vorhanden ist, wenn schon setzt nur Pfeilspitze
     * @param p java.awt.Point Pfeilspitzenposition
     */
    public void setArrow(Point p){
        if(arr == null && arrowFrom != null) arr = new Arrow(arrowFrom, p);
        else if(arrowFrom != null) arr.setArrow(p);
    }

    /**
     * Setzt Anfangsposition vom Pfeil
     * @param p java.awt.Point Anfang vom Pfeil
     */
    public void setArrowFrom(@Nullable Point p){
        arrowFrom = p;
    }

    /**
     * Loescht Pfeil und dessen Anfangspunkt, ruft Repaint-Manager auf
     */
    public void eraseArrow(){
        arrowFrom = null;
        arr = null;
        repaint();
    }

    /**
     * Innere Klasse zum Zeichnen vom Pfeil
     */
    class Arrow extends JComponent{
        Point from, to;
        AffineTransform tx;
        Line2D.Double line;
        Polygon arrowHead;

        /**
         * Ctor, setzt die Linie und Pfeilspitze
         * @param start java.awt.Point Anfang
         * @param end java.awt.Point Ende
         */
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

        /**
         * Setzt Endposition
         * @param end java.awt.Point Endposition
         */
        void setArrow(Point end){
            to = end;
            line.setLine(from.x, from.y, to.x, to.y);
        }

        /**
         * Ueberlaedt void paintComponent(Graphics g) vom JComponent
         * @param g Graphics-Objekt
         */
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

    /**
     * Ueberlaedt void paintComponent von JPanel
     * Zeichnet alle Elemente in folgender Reihenfolge: Kanten->Stellen->Transitionen->ggf. ein Pfeil
     * @param g Graphics-Objekt
     */
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

    /**
     * Getter, gibt Groesse zurueck
     * @return Dimension Groesse
     */
    @Override
    public Dimension getSize(){

        return frame.getSize();
    }

    /**
     * Getter, liefert FileIO-Objekt zurueck
     * @return FileIO file
     */
    public FileIO getFile(){
        return this.file;
    }

    /**
     * animiert ein Schritt, nutzt dabei den Repaint-Manager von MainCanvas
     */
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

    /**
     * Startet eine Animation mit allen Schritten, nutzt dabei animateStep() und Executor-Objekt
     */
    public void play(){
        if(executorService == null) {
            executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(this::animateStep, 0, 5000, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Kann laufende Animation aufhalten
     */
    public void stop(){
        if(executorService != null) {
            executorService.shutdown();
            executorService = null;
        }
    }

    /**
     * Klasse zur Animation von Simulation
     */
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