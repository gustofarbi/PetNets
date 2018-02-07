package PetriElements;

import java.util.ArrayList;

public class TransitionCore implements PetriElement {
    ArrayList<PetriElement> fromThis;
    ArrayList<PetriElement> toThis;
    TransitionCore(){
        fromThis = new ArrayList<>();
        toThis = new ArrayList<>();
    }
    public void addToThis(PetriElement p){toThis.add(p);}
    public void addFromThis(PetriElement p){fromThis.add(p);}
}
