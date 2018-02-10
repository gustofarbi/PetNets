package PetriElements;

import java.util.ArrayList;

public interface PetriElement {
    void addFromThis(Arc f);
    void addToThis(Arc f);
    ArrayList<Arc> getFromThis();
    ArrayList<Arc> getToThis();
}
