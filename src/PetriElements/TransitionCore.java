package PetriElements;

import java.util.ArrayList;

public class TransitionCore implements PetriElement {
    private ArrayList<Arc> fromThis;
    private ArrayList<Arc> toThis;
    TransitionCore(){
        fromThis = new ArrayList<>();
        toThis = new ArrayList<>();
    }
    @Override public void addToThis(Arc p){toThis.add(p);}
    @Override public void addFromThis(Arc p){fromThis.add(p);}
    @Override public ArrayList<Arc> getFromThis(){return fromThis;}
    @Override public ArrayList<Arc> getToThis() { return toThis;}
}
