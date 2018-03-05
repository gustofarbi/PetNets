package PetriElements;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Klasse implementiert den Kern von einer Stelle, enthaelt zwei ArrayLists mit Kanten aus dem Vor- und Nachbereich
 */
public class PlaceCore implements PetriElementCore {
    private int tokens = 0, capacity = Integer.MAX_VALUE;
    @NotNull
    private ArrayList<Arc> fromThis;
    @NotNull
    private ArrayList<Arc> toThis;

    /**
     * Ctor parameterlos
     */
    PlaceCore(){
        fromThis = new ArrayList<>();
        toThis = new ArrayList<>();
    }

    /**
     * Getter, gibt Anzahl von tokens zurueck als int
     * @return int Tokens
     */
    public int getTokens(){return tokens;}

    /**
     * Getter, gibt Kapazitaet als int zurueck
     * @return int Kapazitaet
     */
    public int getCapacity(){return capacity;}

    /**
     * Setter, prueft, ob die neue Anzahl von Tokens zulaessig ist, und setzt neu
     * @param t int neue Anzahl von Tokens
     */
    public void setTokens(int t) {
        if (t >= 0)
            tokens = t;
        else
            throw new RuntimeException("Invalid tokens input!@" + Thread.currentThread().getStackTrace()[2].getFileName()
            + ":" + Thread.currentThread().getStackTrace()[2].getLineNumber());
    }

    /**
     * Setter, prueft, ob die neue Kapazitaet zulaessig ist, und setzt neu
     * @param c int neue Kapazitaet
     */
    public void setCapacity(int c){
        if(c > 0)
            capacity = c;
        else
            throw new RuntimeException("Invalid capacity input!@"
                    + Thread.currentThread().getStackTrace()[2].getFileName() + ""
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
    }

    /**
     * Fuegt eine Kanten in den Vorbereicht hinzu
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
     * @return ArrayList Nachbereichskanten
     */
    @NotNull
    @Override public ArrayList<Arc> getFromThis() { return fromThis; }

    /**
     * Getter, gibt alle Kanten aus dem Vorebereich zurueck als ArrayList
     * @return ArrayList Vorbereichskanten
     */
    @NotNull
    @Override public ArrayList<Arc> getToThis(){return toThis;}
}
