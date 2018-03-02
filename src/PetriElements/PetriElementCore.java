package PetriElements;

import java.util.ArrayList;

public interface PetriElementCore {
    void addFromThis(Arc f);
    void addToThis(Arc f);
    ArrayList<Arc> getFromThis();
    ArrayList<Arc> getToThis();
}
