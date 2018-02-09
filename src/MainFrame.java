import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    Container c;
    private String toggled = "";
    JFrame frame;

    public MainFrame(){
        frame = this;
        c = getContentPane();
        setSize(1200,800);
        setSize(getPreferredSize());
        setBackground(Color.white);
        setTitle("PetNets v1.1");
        setLocation(100,100);

        MenuBar.makeMenuBar(this);
        ToolBar.makeToolBar(this);
        MessageBar.makeMessageBar(this);
        MainCanvas panel = new MainCanvas(this);
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }catch (Exception e){
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(this);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public void setToggled(String label){
        this.toggled = label;
    }
    public String getToggled(){return this.toggled;}
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(1200,800);
    }

    public static void main(String[] args) {
        MainFrame foo = new MainFrame();
    }
}
