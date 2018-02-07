package PetriElements;

import javax.swing.*;

public class FlowRelationCore {
    PetriElement from, to;
    int weight;
    public FlowRelationCore(PetriElement start, PetriElement end){
        from = start;
        to = end;
        //weight = JOptionPane.showInputDialog("Adjust weight:", null);
    }

    public static void main(String[] args) {
        new FlowRelationCore(new PlaceCore(),new PlaceCore());
    }
}
