package PetriElements;

import javax.swing.*;

public class FlowRelationCore {
    private PetriElement from, to;
    private int weight;
    private static int counter = 0;

    public FlowRelationCore(PetriElement start, PetriElement end){
        from = start;
        to = end;
        from.addFromThis(to);
        to.addToThis(from);
        weight = Integer.parseInt(JOptionPane.showInputDialog("Adjust weight:", null));
        counter++;
    }

}
