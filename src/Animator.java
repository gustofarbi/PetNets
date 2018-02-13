import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

public class Animator extends JComponent implements ActionListener {
    private Point from, to, pos;
    private static final int radius = 15;
    private static final int diameter = radius*2;
    private Timer timer;
    private double stepX, stepY;
    private Ellipse2D.Double token;
    private Graphics g;
    private final int animationSpeed;
    private int counter = 0;
    private double progress = 0.0;
    private int dx, dy;

    Animator(Point start, Point end, final int speed, Graphics _g) {
        from = start;
        to = end;
        pos = from;
        g = _g;
        animationSpeed = speed;
        stepX = (double) (to.x - from.x) / (double) speed;
        stepY = (double) (to.y - from.x) / (double) speed;
        dx = to.x - from.x;
        dy = to.y - from.y;
        token = new Ellipse2D.Double(pos.x - radius, pos.y - radius, diameter, diameter);
    }


    @Override
    public void actionPerformed(ActionEvent e){
        System.out.println("Performing action");
        draw();
    }
    public void startAnimation(){
        timer.start();
    }
    public void stopAnimation(){
        timer.stop();
    }

    public void draw(){
        //if(pos.x >= to.x || pos.y >= to.y) timer.stop();
        super.paintComponent(g);
        counter++;
        progress = (double)counter/animationSpeed;

        pos.x = from.x + (int)(dx*progress);
        pos.y = from.y + (int)(dy*progress);
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHints(rh);

        token = new Ellipse2D.Double(pos.x - radius, pos.y - radius, diameter, diameter);
        g2.fill(token);
    }

    @Override public Dimension getSize(){ return new Dimension(2* radius,2* radius); }
    @Override public int getWidth(){ return 2* radius; }
    @Override public int getHeight(){return 2* radius;}
}
