package PetriElements;

import java.util.ArrayList;

public class TransitionCore implements PetriElement {
    private ArrayList<Arc> fromThis;
    private ArrayList<Arc> toThis;
    public TransitionCore(){
        fromThis = new ArrayList<>();
        toThis = new ArrayList<>();
    }
    @Override public void addToThis(Arc p){toThis.add(p);}
    @Override public void addFromThis(Arc p){fromThis.add(p);}
    @Override public ArrayList<Arc> getFromThis(){return fromThis;}
    @Override public ArrayList<Arc> getToThis() { return toThis;}
    public boolean checkArcsToThis(){
        for(Arc a: toThis){
            if(((PlaceCore)a.getFrom().getCore()).getTokens() < a.getWeight())
                return false;
        }
        return true;
    }
    public boolean checkArcsFromThis(){
        for(Arc a: fromThis){
            if(((PlaceCore)a.getTo().getCore()).getCapacity() < ((PlaceCore)a.getTo().getCore()).getTokens() + a.getWeight())
                return false;
        }
        return true;
    }
}
