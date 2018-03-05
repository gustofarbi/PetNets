import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainFrame extends JFrame {
    Container c = getContentPane();
    private String toggled = "";
    @NotNull private JFrame frame = this;
    private MainCanvas canvas;
    private MessageBar messageBar;

    /**
     * Ctor, setzt Attribute und erzeugt MainCanvas, MessageBar und stellt laf ein
     */
    private MainFrame(){
        setSize(1200,800);
        setSize(getPreferredSize());
        setBackground(Color.white);
        setTitle("PetNets v1.1");
        setLocation(100,100);

        canvas = new MainCanvas(this);
        MenuBar.makeMenuBar(this);
        ToolBar.makeToolBar(this);
        messageBar = new MessageBar(this);

        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }catch (Exception e){
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(this);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                canvas.setSize(frame.getWidth()-65, frame.getHeight()-90);
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Animiert ein Schritt, delegiert an MainCanvas
     */
    public void animateStep(){ canvas.animateStep(); }

    /**
     * Startet volle Animation, delegiert an MainCanvas
     */
    public void play(){canvas.play();}

    /**
     * Haelt Animation auf, delegiert an MainCanvas
     */
    public void stop(){canvas.stop();}

    /**
     * SEtzt toggled-Stringobjekt
     * @param label String toggled
     */
    public void setToggled(String label){
        this.toggled = label;
    }

    /**
     * Getter, liefert toggled-Stringobjekt
     * @return String toggled
     */
    public String getToggled(){return this.toggled;}

    /**
     * Getter, gibt preferierte Groesse zurueck
     * @return Dimension Groesse
     */
    @NotNull
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(1200,800);
    }

    /**
     * Setter, setzt Nachricht in MessageBar
     * @param s String msg
     */
    public void setMessage(String s){
        messageBar.setMessage(s);
    }

    /**
     * Setter, setzt Stats in MessageBar
     * @param s String stats
     */
    public void setStats(String s){
        messageBar.setStats(s);
    }

    /**
     * Schliesst das Hauptfenster
     */
    public void close(){
        this.setVisible(false);
        this.dispose();
    }

    /**
     * Getter, liefert FileIO-Objekt aus MainCanvas zurueck
     * @return FileIO file
     */
    public FileIO getFile(){
        return canvas.getFile();
    }

    /**
     * Hauptmethode
     * @param args not used
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
        //MainFrame foo = new MainFrame();
    }
}
