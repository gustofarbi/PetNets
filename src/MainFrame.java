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

    public MainFrame(){
        setSize(1200,800);
        setSize(getPreferredSize());
        setBackground(Color.white);
        setTitle("PetNets v1.1");
        setLocation(100,100);

        MenuBar.makeMenuBar(this);
        ToolBar.makeToolBar(this);
        MessageBar.makeMessageBar(this);
        //MainCanvas panel = new MainCanvas(this);
        canvas = new MainCanvas(this);
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
    public void setToggled(String label){
        this.toggled = label;
    }
    public String getToggled(){return this.toggled;}
    @NotNull
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(1200,800);
    }

    public static void main(String[] args) {
        MainFrame foo = new MainFrame();
    }
}
