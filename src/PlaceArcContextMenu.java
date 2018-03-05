import PetriElements.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class PlaceArcContextMenu extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameField;
    private JTextField tokensField;
    private JTextField capacityField;
    private JLabel nameLabel;
    private JLabel tokensLabel;
    private JLabel capacityLabel;
    private JLabel messageLabel;
    private JTable arcsTable;
    private Place foo;

    PlaceArcContextMenu(Place p) {
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
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        foo = p;
        nameField.setText(p.getName());
        tokensField.setText(((PlaceCore)p.getCore()).getTokens() + "");
        capacityField.setText(((PlaceCore)p.getCore()).getCapacity() + "");

        //Aufbau vom JTable
        String[] columnNames = {"ID", "From", "To", "Weight"};
        ArrayList<Arc> fromList = foo.getCore().getFromThis();
        ArrayList<Arc> toList = foo.getCore().getToThis();
        Object data[][] = new Object[fromList.size()+toList.size()][4];
        int i = 0;
        for(Arc a: toList){
            data[i][0] = a.getID();
            data[i][1] = a.getFrom().getName();
            data[i][2] = a.getTo().getName();
            data[i][3] = a.getWeight();
            i++;
        }
        for(Arc a: fromList){
            data[i][0] = a.getID();
            data[i][1] = a.getFrom().getName();
            data[i][2] = a.getTo().getName();
            data[i][3] = a.getWeight();
            i++;
        }

        AbstractTableModel model = new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return data.length+1;
            }

            @Override
            public int getColumnCount() {
                return columnNames.length;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                if(rowIndex == 0)
                    return columnNames[columnIndex];
                else
                    return data[rowIndex-1][columnIndex];
            }

            @Override
            public boolean isCellEditable(int row, int col){
                return col > 2 && row > 0;
            }

            @Override
            public void setValueAt(Object value, int row, int col){
                data[row-1][col] = Math.abs(Integer.parseInt((String)value));
                fireTableCellUpdated(row,col);
            }
        };

        arcsTable.setModel(model);

        pack();
        setVisible(true);
    }

    private void onOK() {
        boolean success = false;
        int nTokens = 0, nCapacity = 0;
        try{
            nTokens = Integer.parseInt(tokensField.getText());
            nCapacity = Integer.parseInt(capacityField.getText());
            success = nTokens >= 0 && nCapacity >= nTokens;
            if(!success) throw new RuntimeException("Capacity must be higher than number of tokens and >0!");
        }catch(NumberFormatException e){
            messageLabel.setText("Invalid input!");
        }catch(RuntimeException e){
            messageLabel.setText(e.getMessage());
        }
        for(Arc a: foo.getCore().getFromThis())
            for(int i = 1; i< arcsTable.getRowCount(); i++)
                if(a.getID() == (int)arcsTable.getValueAt(i, 0))
                    a.setWeight((int)arcsTable.getValueAt(i, 3));
        for(Arc a: foo.getCore().getToThis())
            for(int i = 1; i< arcsTable.getRowCount(); i++)
                if(a.getID() == (int)arcsTable.getValueAt(i, 0))
                    a.setWeight((int)arcsTable.getValueAt(i, 3));

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

    public static void main(String[] args) {
        Transition t1 = new Transition(1,1);
        Transition t2 = new Transition(2,2);
        Transition t3 = new Transition(3,3);
        Place p = new Place(3,3);
        Arc a1 = new Arc(t1,p);
        Arc a2 = new Arc(t2,p);
        Arc a3 = new Arc(p,t2);
        Arc a4 = new Arc(p,t3);
        new PlaceArcContextMenu(p);
    }
}
