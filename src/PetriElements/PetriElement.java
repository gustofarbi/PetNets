package PetriElements;

public interface PetriElement {
    //Point getPos();
    //void setPos(int x, int y);
    void addFromThis(PetriElement p);
    void addToThis(PetriElement p);
    //void setBounds(int x, int y, int width, int height);
}
