package PetriElements;

import java.awt.*;

public interface GraphicPetriElement {
    Point getPos();
    void setPos(int x, int y);
    PetriElement getCore();
}
