package PetriElements;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public interface GraphicPetriElement {
    @NotNull Point getPos();
    void setPos(int x, int y);
    PetriElement getCore();
}
