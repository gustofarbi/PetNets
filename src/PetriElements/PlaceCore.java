package PetriElements;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PlaceCore implements PetriElement{
    private int tokens = 0, capacity = Integer.MAX_VALUE;
    @NotNull
    private ArrayList<Arc> fromThis;
    @NotNull
    private ArrayList<Arc> toThis;
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
    @Override public void addToThis(Arc p){toThis.add(p);}
    @Override public void addFromThis(Arc p){fromThis.add(p);}
    @NotNull
    @Override public ArrayList<Arc> getFromThis() { return fromThis; }
    @NotNull
    @Override public ArrayList<Arc> getToThis(){return toThis;}
}
