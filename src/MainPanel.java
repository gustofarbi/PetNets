import PetriElements.FlowRelation;
import PetriElements.Place;
import PetriElements.Transition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainPanel extends JPanel{
    ArrayList<Place> places;
    ArrayList<Transition> transitions;
    ArrayList<FlowRelation> flowRelations;
    MainFrame frame;
    MainPanel panel;

    public MainPanel(MainFrame frame){
        places = new ArrayList<>();
        transitions = new ArrayList<>();
        flowRelations = new ArrayList<>();
        this.frame = frame;
        this.panel = panel;
        this.setLayout(null);
        this.setBackground(Color.white);
        this.addMouseListener(new);
    }
    @Override
    public void paintComponent(Graphics g){
        for(Place p: places){
            p.draw(g);
        }
    }
    class PanelMouseListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e){
            if(!frame.getToggled().isEmpty()){
                switch(frame.getToggled()){
                    case "place":
                        addPlace(e.getX(),e.getY());
                        break;
                    case "transition":
                        addTransition(e.getX(),e.getY());
                        break;
                    default:
                        break;
                }
                repaint();
            }
        }
    }
    public void addPlace(int x, int y){
        Place p = new Place(x,y);

    }
}
