import PetriElements.Arc;
import PetriElements.Place;
import PetriElements.Transition;

import java.util.ArrayList;

public class FileIO {

    ArrayList<Transition> transitions;
    ArrayList<Place> places;
    ArrayList<Arc> arcs;
    private MainCanvas canvas;

    FileIO(MainCanvas canvas){
        this.canvas = canvas;
        transitions = new ArrayList<>();
        places = new ArrayList<>();
        arcs = new ArrayList<>();
    }
    public void addPlace(Place p){places.add(p);}
    public void addTransition(Transition t){transitions.add(t);}
    public void addArc(Arc a){arcs.add(a);}

    public static void
}
