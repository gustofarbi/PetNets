package PetriElements;

import java.util.ArrayList;

public class TransitionCore implements PetriElementCore {
    private ArrayList<Arc> fromThis;
    private ArrayList<Arc> toThis;

    /**
     * Ctor, initialisiert beide ArrayLists
     */
    TransitionCore(){
        fromThis = new ArrayList<>();
        toThis = new ArrayList<>();
    }

    /**
     * Fuegt eine Kante in den Vorbereich hinzu
     * @param p Arc Kante
     */
    @Override public void addToThis(Arc p){toThis.add(p);}

    /**
     * Fuegt eine Kante in den Nachbereich hinzu
     * @param p Arc Kante
     */
    @Override public void addFromThis(Arc p){fromThis.add(p);}

    /**
     * Getter, gibt alle Kanten aus dem Nachbereich zurueck als ArrayList
     * @return ArrayList mit Nachbereichskanten
     */
    @Override public ArrayList<Arc> getFromThis(){return fromThis;}

    /**
     * Getter, gibt alle Kanten aus dem Vorbereich zurueck als ArrayList
     * @return ArrayList mit Vorbereichskanten
     */
    @Override public ArrayList<Arc> getToThis() { return toThis;}
}
