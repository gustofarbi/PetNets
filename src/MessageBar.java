import javax.swing.*;
import java.awt.*;

class MessageBar {
    private JLabel message, stats;
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
    void setMessage(String s){
        message.setText(s);
    }
    void setStats(String s){
        stats.setText(s);
    }
}
