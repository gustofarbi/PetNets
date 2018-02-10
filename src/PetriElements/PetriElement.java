package PetriElements;

import java.util.ArrayList;

public interface PetriElement {
    void addFromThis(FlowRelation f);
    void addToThis(FlowRelation f);
    ArrayList<FlowRelation> getFromThis();
    ArrayList<FlowRelation> getToThis();
}
