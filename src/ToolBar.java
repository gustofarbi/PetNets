import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ToolBar {
    static ArrayList<JToggleButton> buttons;
    static MainFrame frame;

    static void makeToolBar(MainFrame f){
        frame = f;
        int iconSize = 40;
        int offsetLeft = 3;
        int offsetTop = 3;
        int thickness = 5;
        buttons = new ArrayList<>();

        JToolBar toolBar = new JToolBar("Tools", JToolBar.VERTICAL);

        //Initialisierungen von ToggleButtons
        JToggleButton placeToggleButton = new JToggleButton(new Icon() {
            @Override public void paintIcon(Component c, @NotNull Graphics g, int x, int y) {
                g.setColor(Color.BLACK);
                g.fillOval(offsetLeft, offsetTop,getIconWidth(),getIconHeight());
                g.setColor(Color.WHITE);
                g.fillOval(offsetLeft + thickness,offsetTop + thickness,getIconWidth() - (2*thickness), getIconHeight() - (2*thickness));
            }
            @Override public int getIconWidth() {
                return iconSize;
            }
            @Override public int getIconHeight() {
                return iconSize;
            }
        });
        JToggleButton transitionToggleButton = new JToggleButton(new Icon(){
            @Override public void paintIcon(Component c, @NotNull Graphics g, int x, int y) {
                int narrowing = 5;
                g.setColor(Color.BLACK);
                g.fillRect(offsetLeft + narrowing, offsetTop, getIconWidth()-(2*narrowing)+2, getIconHeight());
                g.setColor(Color.WHITE);
                g.fillRect(offsetLeft + thickness + narrowing, offsetTop + thickness, getIconWidth()-(3*narrowing)- 3, getIconHeight()-thickness- narrowing);
            }
            @Override public int getIconWidth() {
                return iconSize;
            }
            @Override public int getIconHeight() {
                return iconSize;
            }
        });
        JToggleButton arcToggleButton = new JToggleButton(new Icon() {
            @Override public void paintIcon(Component c, @NotNull Graphics g, int x, int y) {
                int xCoords[] = {offsetLeft,getIconWidth(), getIconWidth(), getIconWidth(), getIconWidth()-10};
                int yCoords[] = {offsetTop,getIconHeight(), getIconHeight()-10, getIconHeight(), getIconHeight()};
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(1.5f));
                g2.setColor(Color.BLACK);
                g2.drawPolyline(xCoords, yCoords, xCoords.length);
            }
            @Override public int getIconWidth() {
                return iconSize;
            }
            @Override public int getIconHeight() {
                return iconSize;
            }
        });
        JToggleButton eraseToggleButton = new JToggleButton(new Icon() {
            @Override public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.red);
                g2.setStroke(new BasicStroke(4f));
                g2.drawLine(offsetLeft, offsetTop, getIconHeight(), getIconWidth());
                g2.drawLine(getIconWidth(), offsetTop, offsetLeft, getIconHeight());
            }
            @Override public int getIconWidth() { return iconSize; }
            @Override public int getIconHeight() { return iconSize; }
        });

        buttons.add(placeToggleButton);
        buttons.add(transitionToggleButton);
        buttons.add(arcToggleButton);
        buttons.add(eraseToggleButton);

        //ActionListener
        placeToggleButton.addActionListener(new ToggleButtonListener());
        transitionToggleButton.addActionListener(new ToggleButtonListener());
        arcToggleButton.addActionListener(new ToggleButtonListener());
        eraseToggleButton.addActionListener(new ToggleButtonListener());

        //Tooltips
        placeToggleButton.setToolTipText("New places");
        arcToggleButton.setToolTipText("New flow relation");
        transitionToggleButton.setToolTipText("New transition");
        eraseToggleButton.setToolTipText("Erase element");

        //ActionCommand
        placeToggleButton.setActionCommand("place");
        transitionToggleButton.setActionCommand("transition");
        arcToggleButton.setActionCommand("arc");
        eraseToggleButton.setActionCommand("erase");

        //hinzufuegen
        toolBar.add(placeToggleButton);
        toolBar.add(transitionToggleButton);
        toolBar.add(arcToggleButton);
        toolBar.add(eraseToggleButton);

        frame.add(toolBar, BorderLayout.EAST);
    }
    static class ToggleButtonListener implements ActionListener{
        @Override public void actionPerformed(ActionEvent e){
            JToggleButton source = (JToggleButton)e.getSource();
            for(JToggleButton b: buttons)
                if(source != b) b.setSelected(false);
            if(!source.isSelected()) frame.setToggled("");
            else frame.setToggled(e.getActionCommand());
        }
    }
}
