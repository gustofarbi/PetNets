package PetriElements;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class Arc extends JComponent {
    private int ID;
    private static int counter = 0;
    private int weight;
    private GraphicPetriElement from, to;
    private Point fromPos, toPos, relPos;
    private static int offset = 32;
    private AffineTransform at;
    private Line2D.Double line;
    private static Polygon arrowHead;
    private Ellipse2D.Double token;
    private static final int radius = 15;
    private static final int diameter = 2*radius;
    private double stepX, stepY;
    private static final int speed = 30;
    private boolean isDone = false;
    private double tokenPosX, tokenPosY;

    /**
     * Ctor: zeichnet die Kante und fuegt sie in ArrayLists von start und end
     * @param start GraphicPetriElement Anfangspetrielement
     * @param end GraphicPetriElement Endpetrielement
     */
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
        at = new AffineTransform();
        line = new Line2D.Double(fromPos.x, fromPos.y, toPos.x, toPos.y);
        if(arrowHead == null) makeArrowHead();
    }

    /**
     * Gibt ID-Nummer der Kante zurück
     * @return int ID der Kante
     */
    public int getID(){return ID;}

    /**
     * Setzt gewicht neu
     * @param w int gewichtNeu
     */
    public void setWeight(int w){ if(w > 0) weight = w; }

    /**
     * Gibt das Kantengewicht zurück
     * @return int gewicht
     */
    public int getWeight(){return weight;}

    /**
     * Zeichnet die Pfeilspitze
     */
    private void makeArrowHead(){
        arrowHead = new Polygon();
        arrowHead.addPoint(0,5);
        arrowHead.addPoint(-5,-5);
        arrowHead.addPoint(5,-5);
    }

    /**
     * Aktualisiert die Position von Kante und deren Spitze
     */
    private void refreshPosition(){
        fromPos = from.getPos();
        toPos = to.getPos();
        relPos();
        line.setLine(fromPos.x, fromPos.y, relPos.x, relPos.y);
        at.setToIdentity();
        double angle = Math.atan2(line.y2-line.y1, line.x2-line.x1);
        at.translate(line.x2, line.y2);
        at.rotate(angle-Math.PI/2d);

    }

    /**
     * Gibt die Position des Anfangselements zurück
     * @return Point positionFrom
     */
    public Point getFromPos(){return fromPos;}

    /**
     * Gibt die Position des Endelements zurück
     * @return Point positionTo
     */
    public Point getToPos(){return toPos;}

    /**
     * Gibt das Anfangselement zurück
     * @return GraphicPetriElement from
     */
    public GraphicPetriElement getFrom(){
        return from;
    }

    /**
     * Gibt das Endelement zurück
     * @return GraphicPetriElement to
     */
    public GraphicPetriElement getTo() {
        return to;
    }

    /**
     * Gibt die Textposition zurück
     * @return Point Textposition
     */
    private Point getTextPos(){ return new Point((fromPos.x+toPos.x)/2, (fromPos.y+toPos.y)/2); }

    /**
     * Prueft ob das Token zu weit ist
     * @return boolean, true wenn das Token zu weit ist
     */
    public boolean isDone(){return isDone;}

    /**
     * Initialisiert das Token und die Schrittlaenge
     */
    public void setToken(){
        token = new Ellipse2D.Double(fromPos.x-radius, fromPos.y-radius, diameter, diameter);
        stepX = (double)(relPos.x-fromPos.x)/(double)speed;
        stepY = (double)(relPos.y-fromPos.y)/(double)speed;
        tokenPosX = fromPos.x;
        tokenPosY = fromPos.y;
    }

    /**
     * Macht ein Schritt in Richtung Endelement
     */
    public void makeStep(){
        if(relPos.distance(tokenPosX, tokenPosY) < 50)
            isDone = true;
        else {
            tokenPosX += stepX;
            tokenPosY += stepY;
            token.setFrame(tokenPosX - radius, tokenPosY - radius, diameter, diameter);
        }
    }

    /**
     * Zersoert das Token
     */
    public void disposeToken(){
        token.setFrame(0,0,0,0);
        token = null;
        isDone = false;
    }

    /**
     * Prueft ob das Token gesetzt ist
     * @return boolean, true wenn Token gesetzt ist
     */
    public boolean isTokenSet(){
        return token != null;
    }

    /**
     * Positioniert die Kante relativ zum Endelement (Am Rand)
     */
    private void relPos(){
        relPos = new Point();
        if(fromPos.x > toPos.x)
            relPos.x = toPos.x + offset;
        else
            relPos.x = toPos.x - offset;
        if(fromPos.y > toPos.y)
            relPos.y = toPos.y + offset;
        else
            relPos.y = toPos.y - offset;
    }

    /**
     * Zeichnet die Kante und, wenn gesetzt, das Token
     * @param g Graphics-Objekt
     */
    public void draw(Graphics g){
        refreshPosition();
        Graphics2D g2 = (Graphics2D) g.create();

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(rh);

        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(2.0f));
        Point textPos = getTextPos();
        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        g2.drawString(weight + "", textPos.x+offset,textPos.y+offset);
        if(token != null){
            g2.fill(token);
        }
        g2.draw(line);
        g2.setTransform(at);
        g2.fill(arrowHead);
        g2.dispose();
    }

    /**
     * Prueft zwei Kanten auf Gleichheit
     * @param o Vergleichsobjekt
     * @return boolean, true wenn zwei Objekte gleich sind
     */
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
