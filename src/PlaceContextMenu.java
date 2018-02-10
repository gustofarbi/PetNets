import PetriElements.Place;
import PetriElements.PlaceCore;

import javax.swing.*;
import java.awt.event.*;

public class PlaceContextMenu extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameField;
    private JTextField capacityField;
    private JTextField tokensField;
    private JLabel nameLabel;
    private JLabel tokensLabel;
    private JLabel capacityLabel;
    private JLabel messageLabel;
    private Place foo;

    PlaceContextMenu(Place p) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel()
                , KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        foo = p;
        nameField.setText(p.getName());
        tokensField.setText(((PlaceCore)p.getCore()).getTokens() + "");
        capacityField.setText(((PlaceCore)p.getCore()).getCapacity() + "");

        pack();
        setVisible(true);
        setLocation(500,500);
    }

    private void onOK() {
        boolean success = false;
        int nTokens = 0, nCapacity = 0;
        try{
            nTokens = Integer.parseInt(tokensField.getText());
            nCapacity = Integer.parseInt(capacityField.getText());
            success = nTokens > 0 && nCapacity >= nTokens;
            if(!success) throw new RuntimeException("Capacity must be higher than number of tokens and >0!");
        }catch(NumberFormatException e){
            messageLabel.setText("Invalid input!");
        }catch(RuntimeException e){
            messageLabel.setText(e.getMessage());
        }
        if(success){
            ((PlaceCore)foo.getCore()).setTokens(nTokens);
            ((PlaceCore)foo.getCore()).setCapacity(nCapacity);
            if(!nameField.getText().isEmpty()) foo.setName(nameField.getText());
            dispose();
        }
    }
    private void onCancel() {
        dispose();
    }
}
