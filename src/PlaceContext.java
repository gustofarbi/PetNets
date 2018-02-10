import PetriElements.Place;
import PetriElements.PlaceCore;
import PetriElements.Transition;

import javax.swing.*;
import java.awt.event.*;

public class PlaceContext extends JDialog {
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

    public PlaceContext(Place p) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

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
            if(nCapacity < nTokens || nTokens < 0)
                throw new RuntimeException("Capacity must be higher than number of tokens and >0!");
            success = nTokens > 0;
        }catch(NumberFormatException e){
            messageLabel.setText("Invalid input!");
        }catch(RuntimeException e){
            messageLabel.setText(e.getMessage());
        }
        if(success){
            ((PlaceCore)foo.getCore()).setTokens(nTokens);
            ((PlaceCore)foo.getCore()).setCapacity(nCapacity);
            if(!nameField.getText().isEmpty())
                foo.setName(nameField.getText());
            dispose();
        }
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        Transition t = new Transition(1,1);
        Transition t2 = new Transition(1,1);
        Place p2 = new Place(1,1);
        Place p = new Place(1,1);

        PlaceContext dialog = new PlaceContext(p);

        System.exit(0);
    }
}
