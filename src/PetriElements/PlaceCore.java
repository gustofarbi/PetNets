package PetriElements;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PlaceCore implements PetriElement{
    private int tokens = 1, capacity = Integer.MAX_VALUE;
    @NotNull
    private ArrayList<FlowRelation> fromThis;
    @NotNull
    private ArrayList<FlowRelation> toThis;
    public PlaceCore(){
        fromThis = new ArrayList<>();
        toThis = new ArrayList<>();
    }

    public int getTokens(){return tokens;}
    public int getCapacity(){return capacity;}
    public void setTokens(int t) {
        if (t > 0)
            tokens = t;
        else
            throw new RuntimeException("Invalid tokens input!@" + Thread.currentThread().getStackTrace()[2].getFileName()
            + Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    public void setCapacity(int c){
        if(c > 0)
            capacity = c;
        else
            throw new RuntimeException("Invalid capacity input!@"
                    + Thread.currentThread().getStackTrace()[2].getFileName()
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
    }
    @Override public void addToThis(FlowRelation p){toThis.add(p);}
    @Override public void addFromThis(FlowRelation p){fromThis.add(p);}
    @NotNull
    @Override public ArrayList<FlowRelation> getFromThis() { return fromThis; }
    @NotNull
    @Override public ArrayList<FlowRelation> getToThis(){return toThis;}
}
