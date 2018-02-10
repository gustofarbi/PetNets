package PetriElements;

import java.util.ArrayList;

public class TransitionCore implements PetriElement {
    private ArrayList<FlowRelation> fromThis;
    private ArrayList<FlowRelation> toThis;
    public TransitionCore(){
        fromThis = new ArrayList<>();
        toThis = new ArrayList<>();
    }
    @Override public void addToThis(FlowRelation p){toThis.add(p);}
    @Override public void addFromThis(FlowRelation p){fromThis.add(p);}
    @Override public ArrayList<FlowRelation> getFromThis(){return fromThis;}
    @Override public ArrayList<FlowRelation> getToThis() { return toThis;}
}
