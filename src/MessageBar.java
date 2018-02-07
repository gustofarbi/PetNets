import javax.swing.*;
import java.awt.*;

public class MessageBar {

    static void makeMessageBar(MainFrame frame){
        JLabel message, stats;
        JPanel panel;

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        message = new JLabel("Message1");
        message.setPreferredSize(new Dimension(600, 30));
        message.setFont(new Font("Arial", Font.PLAIN, 18));

        stats = new JLabel("message2", SwingConstants.RIGHT);
        stats.setPreferredSize(new Dimension(600, 30));
        stats.setFont(new Font("Arial", Font.PLAIN, 18));

        panel.add(message, BorderLayout.WEST);
        panel.add(stats, BorderLayout.EAST);
        frame.c.add(panel, BorderLayout.SOUTH);
    }
}
