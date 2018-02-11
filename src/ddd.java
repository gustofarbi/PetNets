import PetriElements.Place;

import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class ddd extends JDialog {
    Container c;
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

    ddd(Place p){
        foo = p;
        //c = getContentPane();
        this.setLayout(new GridBagLayout());
        GridBagConstraints co = new GridBagConstraints();

        JLabel[] labels = new JLabel[]{new JLabel("Name:"), new JLabel("Tokens:"), new JLabel("Capacity:")};
        co.fill = GridBagConstraints.HORIZONTAL;
        co.gridx = 0;
        for(int i = 0; i< 3; i++){
            co.gridy = i;
            this.add(labels[i],co);
        }
        JTextField[] textFields = new JTextField[]{new JTextField(),new JTextField(),new JTextField()};
        co.gridx = 1;
        co.gridwidth = 1;
        co.weightx = 1.5;
        JTextField field;
        for(int i = 0; i<3;i++){
            field = new JTextField();
            co.gridy = i;
            this.add(field, co);
        }


    }

    public static void main(String[] args) {
        Place p = new Place(1,1);
        ddd d = new ddd(p);
        d.setSize(500,300);
        d.setVisible(true);
        d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        d.setTitle("Place " + p.getName());
    }
}
