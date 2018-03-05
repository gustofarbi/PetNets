import javax.swing.*;
import java.awt.*;

class MessageBar {
    private JLabel message, stats;

    /**
     * Ctor, initialisiert beide JLabels und fuegt sich selber in Container von MainFrame-Objekt
     * @param frame aufrufendes MainFrame-Objekt
     */
    MessageBar(MainFrame frame){
        JPanel panel;

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        message = new JLabel("");
        message.setPreferredSize(new Dimension(600, 30));
        message.setFont(new Font("Arial", Font.PLAIN, 18));

        stats = new JLabel("", SwingConstants.RIGHT);
        stats.setPreferredSize(new Dimension(600, 30));
        stats.setFont(new Font("Arial", Font.PLAIN, 18));

        panel.add(message, BorderLayout.WEST);
        panel.add(stats, BorderLayout.EAST);
        frame.c.add(panel, BorderLayout.SOUTH);
    }

    /**
     * Setter, setzt Message neu
     * @param s String msg
     */
    void setMessage(String s){
        message.setText(s);
    }

    /**
     * Setter, setzt Stats neu
     * @param s String stats
     */
    void setStats(String s){
        stats.setText(s);
    }
}
