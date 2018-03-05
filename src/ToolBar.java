import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ToolBar {
    static ArrayList<JToggleButton> buttons;
    static MainFrame frame;

    /**
     * Ctor, initialisiert alle Buttons, zeichnet diese und setzt ActionCommands und ActionListener
     * @param f aufrufendes MainFrame
     */
    static void makeToolBar(MainFrame f){
        frame = f;
        int iconSize = 40;
        int offsetLeft = 3;
        int offsetTop = 3;
        int thickness = 5;
        buttons = new ArrayList<>();

        JToolBar toolBar = new JToolBar("Tools", JToolBar.VERTICAL);

        //Initialisierungen von JToggleButtons und JButtons
        JToggleButton placeToggleButton = new JToggleButton(new Icon() {
            @Override public void paintIcon(Component c, @NotNull Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D)g;
                RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHints(rh);
                g2.setColor(Color.BLACK);
                g2.fillOval(offsetLeft, offsetTop,getIconWidth(),getIconHeight());
                g2.setColor(Color.WHITE);
                g2.fillOval(offsetLeft + thickness,offsetTop + thickness,getIconWidth() - (2*thickness), getIconHeight() - (2*thickness));
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
                Graphics2D g2 = (Graphics2D) g;
                RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHints(rh);
                g2.setColor(Color.BLACK);
                g2.fillRect(offsetLeft + narrowing, offsetTop, getIconWidth()-(2*narrowing)+2, getIconHeight());
                g2.setColor(Color.WHITE);
                g2.fillRect(offsetLeft + thickness + narrowing, offsetTop + thickness, getIconWidth()-(3*narrowing)- 3, getIconHeight()-thickness- narrowing);
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
                RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHints(rh);
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
                RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHints(rh);
                g2.setColor(Color.red);
                g2.setStroke(new BasicStroke(4f));
                g2.drawLine(offsetLeft, offsetTop, getIconHeight(), getIconWidth());
                g2.drawLine(getIconWidth(), offsetTop, offsetLeft, getIconHeight());
            }
            @Override public int getIconWidth() { return iconSize; }
            @Override public int getIconHeight() { return iconSize; }
        });
        JToggleButton tokensButton = new JToggleButton(new Icon() {
            @Override public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g;
                RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHints(rh);
                g2.setColor(Color.black);
                g2.fillOval(18,18, 10,10);
            }
            @Override public int getIconWidth() { return iconSize; }
            @Override public int getIconHeight() { return iconSize; }
        });
        JButton stepButton = new JButton(new Icon() {
            @Override public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D)g;
                RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHints(rh);
                g2.setColor(Color.orange);
                int[] xPoints = {offsetLeft, offsetLeft, iconSize-offsetLeft, offsetLeft};
                int[] yPoints = {offsetTop, iconSize, iconSize/2+offsetTop, offsetTop};
                g2.fill(new Polygon(xPoints, yPoints, xPoints.length));
                g2.setColor(Color.black);
                g2.setFont(new Font("Arial", Font.PLAIN, 20));
                g2.drawString("1", 15,30);
            }
            @Override public int getIconWidth() { return iconSize; }
            @Override public int getIconHeight() { return iconSize; }
        });
        JButton playButton = new JButton(new Icon() {
            @Override public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D)g;
                RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHints(rh);
                g2.setColor(Color.orange);
                int[] xPoints = {offsetLeft, offsetLeft, iconSize-offsetLeft, offsetLeft};
                int[] yPoints = {offsetTop, iconSize, iconSize/2+offsetTop, offsetTop};
                g2.fill(new Polygon(xPoints, yPoints, xPoints.length));
            }
            @Override public int getIconWidth() { return iconSize; }
            @Override public int getIconHeight() { return iconSize; }
        });
        JButton stopButton = new JButton(new Icon() {
            @Override public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D)g;
                RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHints(rh);
                g2.setColor(Color.red);
                g2.fillRect(offsetLeft, offsetTop, iconSize,iconSize);
            }
            @Override public int getIconWidth() { return iconSize; }
            @Override public int getIconHeight() { return iconSize; }
        });

        buttons.add(placeToggleButton);
        buttons.add(transitionToggleButton);
        buttons.add(arcToggleButton);
        buttons.add(eraseToggleButton);
        buttons.add(tokensButton);

        //ActionListener
        placeToggleButton.addActionListener(new ToggleButtonListener());
        transitionToggleButton.addActionListener(new ToggleButtonListener());
        arcToggleButton.addActionListener(new ToggleButtonListener());
        eraseToggleButton.addActionListener(new ToggleButtonListener());
        tokensButton.addActionListener(new ToggleButtonListener());
        stepButton.addActionListener(new ButtonListener());
        playButton.addActionListener(new ButtonListener());
        stopButton.addActionListener(new ButtonListener());


        //Tooltips
        placeToggleButton.setToolTipText("New places");
        arcToggleButton.setToolTipText("New flow relation");
        transitionToggleButton.setToolTipText("New transition");
        eraseToggleButton.setToolTipText("Erase element");
        tokensButton.setToolTipText("Add/remove tokens");
        stepButton.setToolTipText("Animate one step");
        playButton.setToolTipText("Animate simulation");
        stopButton.setToolTipText("Stop simulation");

        //ActionCommand
        placeToggleButton.setActionCommand("place");
        transitionToggleButton.setActionCommand("transition");
        arcToggleButton.setActionCommand("arc");
        eraseToggleButton.setActionCommand("erase");
        tokensButton.setActionCommand("token");
        stepButton.setActionCommand("step");
        playButton.setActionCommand("play");
        stopButton.setActionCommand("stop");

        //hinzufuegen
        toolBar.add(placeToggleButton);
        toolBar.add(transitionToggleButton);
        toolBar.add(arcToggleButton);
        toolBar.add(eraseToggleButton);
        toolBar.add(tokensButton);
        toolBar.add(new JSeparator(SwingConstants.HORIZONTAL));
        toolBar.add(stepButton, BorderLayout.SOUTH);
        toolBar.add(playButton, BorderLayout.SOUTH);
        toolBar.add(stopButton, BorderLayout.SOUTH);

        //fuegt sich selber ins Container von MainFrame-Objekt
        frame.add(toolBar, BorderLayout.EAST);
    }
    static class ToggleButtonListener implements ActionListener{
        /**
         *
         * @param e ActionEvent
         * @throws RuntimeException beim ungueltigen ActionCommand
         */
        @Override public void actionPerformed(ActionEvent e){
            JToggleButton source = (JToggleButton)e.getSource();
            for(JToggleButton b: buttons)
                if(source != b) b.setSelected(false);
            if(!source.isSelected()) frame.setToggled("");
            else frame.setToggled(e.getActionCommand());
            switch(e.getActionCommand()){
                case "place":
                    frame.setMessage("You can add new places now by clicking.");
                    break;
                case "transition":
                    frame.setMessage("You can add new transitions now by clicking.");
                    break;
                case "arc":
                    frame.setMessage("You can add new arcs now by dragging the cursor from one element to another.");
                    break;
                case "erase":
                    frame.setMessage("You can erase elements by clicking them.(only places and transitions)");
                    break;
                case "token":
                    frame.setMessage("Left-click to add / right-click to remove tokens.");
                    break;
                default:
                    throw new RuntimeException("Illegal action command! @"
                            + Thread.currentThread().getStackTrace()[2].getFileName() + ":"
                            + Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
        }
    }
    static class ButtonListener implements ActionListener{
        /**
         *
         * @param e ActionEvent
         * @throws RuntimeException beim ungueltigem ActionCommand
         */
        @Override
        public void actionPerformed(ActionEvent e) throws RuntimeException{
            switch(e.getActionCommand()){
                case "step":
                    frame.animateStep();
                    break;
                case "play":
                    frame.play();
                    break;
                case "stop":
                    frame.stop();
                    break;
                default:
                    throw new RuntimeException("Invalid action command! @"
                    + Thread.currentThread().getStackTrace()[2].getFileName() + ":"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
            }
        }
    }
}
