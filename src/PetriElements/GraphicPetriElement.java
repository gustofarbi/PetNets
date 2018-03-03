package PetriElements;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Interface fuer Petri-Elemente
 */
public interface GraphicPetriElement {
    /**
     * Gibt Position zurueck
     * @return Point mit Position
     */
    @NotNull Point getPos();

    /**
     * Setzt neue Position
     * @param x int xKoordinate
     * @param y int yKoordinate
     */
    void setPos(int x, int y);

    /**
     * Gibt das Core-Objekt zurueck
     * @return PetriElementCore Kern
     */
    PetriElementCore getCore();

    /**
     * Gibt den Name zurueck
     * @return String mit dem Objektnamen
     */
    String getName();
}
