import PetriElements.Transition;

import javax.swing.*;
import java.awt.event.*;

public class TransitionContextMenu extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameField;
    private JLabel nameLabel;
    private JLabel messageLabel;
    private Transition foo;

    public TransitionContextMenu(Transition t) {
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
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        foo = t;
        nameField.setText(t.getName());
        pack();
        setVisible(true);
    }

    private void onOK() {
        if(nameField.getText().isEmpty())
            messageLabel.setText("Name cannot be empty!");
        else {
            foo.setName(nameField.getText());
            dispose();
        }
    }

    private void onCancel() { dispose(); }

    public static void main(String[] args) {
        Transition t = new Transition(1,1);
        TransitionContextMenu dialog = new TransitionContextMenu(t);
        System.out.println(t.getName());
    }
}
