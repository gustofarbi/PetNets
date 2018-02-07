package PetriElements;

import java.awt.*;
import java.util.ArrayList;

public class PlaceCore implements PetriElement{
    int tokens = 1, capacity = Integer.MAX_VALUE;
    ArrayList<PetriElement> fromThis;
    ArrayList<PetriElement> toThis;
    PlaceCore(){
        fromThis = new ArrayList<>();
        toThis = new ArrayList<>();
    }

    public int getTokens(){return tokens;}
    public int getCapacity(){return capacity;}
    public void addToThis(PetriElement p){toThis.add(p);}
    public void addFromThis(PetriElement p){fromThis.add(p);}
}
