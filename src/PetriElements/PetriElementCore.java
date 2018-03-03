package PetriElements;

import java.util.ArrayList;

/**
 * Interface fuer Kerne der Petriknoten, enthaelt zwei ArrayLists mit Kanten zu vor- und nachgelagerten Petri-Elementen
 */
public interface PetriElementCore {
    /**
     * Fuegt eine Kanten im Nachbereich zu
     * @param f Arc zum einfuegen
     */
    void addFromThis(Arc f);

    /**
     * Fuegt eine Kante im Vorbereich zu
     * @param f Arc zum einfuegen
     */
    void addToThis(Arc f);

    /**
     * Gibt alle Kanten aus dem Nachbereich zurueck
     * @return ArrayList mit Kanten aus dem Nachbereich
     */
    ArrayList<Arc> getFromThis();

    /**
     * Gibt alle Kanten aus dem Vorbereich zurueck
     * @return ArrayList mit Kanten aus dem Vorbereich
     */
    ArrayList<Arc> getToThis();
}
