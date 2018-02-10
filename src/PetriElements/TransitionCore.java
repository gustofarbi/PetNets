package PetriElements;

import java.util.ArrayList;

public class TransitionCore implements PetriElement {
    ArrayList<FlowRelation> fromThis;
    ArrayList<FlowRelation> toThis;
    TransitionCore(){
        fromThis = new ArrayList<>();
        toThis = new ArrayList<>();
    }
    @Override public void addToThis(FlowRelation p){toThis.add(p);}
    @Override public void addFromThis(FlowRelation p){fromThis.add(p);}
    @Override public ArrayList<FlowRelation> getFromThis(){return fromThis;}
    @Override public ArrayList<FlowRelation> getToThis() { return toThis;}
}
