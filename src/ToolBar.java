import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBar {
    /*
    private JToolBar toolBar;
    private JToggleButton placeToggleButton;
    private JToggleButton transitionToggleButton;
    private JToggleButton frToggleButton;
    */

    static void makeToolBar(MainFrame frame){
        int iconSize = 40;
        int offsetLeft = 3;
        int offsetTop = 3;
        int thickness = 5;
        JToggleButton placeToggleButton, transitionToggleButton, frToggleButton;

        JToolBar toolBar = new JToolBar("Tools", JToolBar.VERTICAL);

        //Initialisierungen von ToggleButtons
        placeToggleButton = new JToggleButton(new Icon() {
            @Override
            public void paintIcon(Component c, @NotNull Graphics g, int x, int y) {
                g.setColor(Color.BLACK);
                g.fillOval(offsetLeft, offsetTop,getIconWidth(),getIconHeight());
                g.setColor(Color.WHITE);
                g.fillOval(offsetLeft + thickness,offsetTop + thickness,getIconWidth() - (2*thickness), getIconHeight() - (2*thickness));
            }

            @Override
            public int getIconWidth() {
                return iconSize;
            }

            @Override
            public int getIconHeight() {
                return iconSize;
            }
        });
        transitionToggleButton = new JToggleButton(new Icon(){
            @Override
            public void paintIcon(Component c, @NotNull Graphics g, int x, int y) {
                int narrowing = 5;
                g.setColor(Color.BLACK);
                g.fillRect(offsetLeft + narrowing, offsetTop, getIconWidth()-(2*narrowing)+2, getIconHeight());
                g.setColor(Color.WHITE);
                g.fillRect(offsetLeft + thickness + narrowing, offsetTop + thickness, getIconWidth()-(3*narrowing)- 3, getIconHeight()-thickness- narrowing);
            }

            @Override
            public int getIconWidth() {
                return iconSize;
            }

            @Override
            public int getIconHeight() {
                return iconSize;
            }
        });
        frToggleButton = new JToggleButton(new Icon() {
            @Override
            public void paintIcon(Component c, @NotNull Graphics g, int x, int y) {
                int xCoords[] = {offsetLeft,getIconWidth(), getIconWidth(), getIconWidth(), getIconWidth()-10};
                int yCoords[] = {offsetTop,getIconHeight(), getIconHeight()-10, getIconHeight(), getIconHeight()};
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(1.5f));
                g2.setColor(Color.BLACK);
                g2.drawPolyline(xCoords, yCoords, xCoords.length);
            }

            @Override
            public int getIconWidth() {
                return iconSize;
            }

            @Override
            public int getIconHeight() {
                return iconSize;
            }
        });

        //ActionListener werden hier eingefuegt
        frToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transitionToggleButton.setSelected(false);
                placeToggleButton.setSelected(false);
                if(!frToggleButton.isSelected())
                    frame.setToggled("");
                else
                    frame.setToggled("fr");
            }
        });
        placeToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transitionToggleButton.setSelected(false);
                frToggleButton.setSelected(false);
                if(!placeToggleButton.isSelected())
                    frame.setToggled("");
                else
                    frame.setToggled("place");
            }
        });
        transitionToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeToggleButton.setSelected(false);
                frToggleButton.setSelected(false);
                if(!transitionToggleButton.isSelected())
                    frame.setToggled("");
                else
                    frame.setToggled("transition");
            }
        });

        //Tooltips
        placeToggleButton.setToolTipText("New places");
        frToggleButton.setToolTipText("New flow relation");
        transitionToggleButton.setToolTipText("New transition");

        //hinzufuegen
        toolBar.add(placeToggleButton);
        toolBar.add(transitionToggleButton);
        toolBar.add(frToggleButton);
        frame.add(toolBar, BorderLayout.EAST);
    }
}
