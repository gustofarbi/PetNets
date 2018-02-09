import PetriElements.FlowRelation;
import PetriElements.GraphicPetriElement;
import PetriElements.Place;
import PetriElements.Transition;

import javax.swing.*;

public class ControlPanel extends JPanel{
    private MainCanvas canvas;
    public ControlPanel(MainCanvas canvas){
        this.canvas = canvas;
        setLayout(null);
        setOpaque(false);

    }
    public void addFlowRelation(GraphicPetriElement from, GraphicPetriElement to){
        FlowRelation f = new FlowRelation(from, to);
        canvas.addFlowRelation();
        flowRelations.add(f);
        this.add(f);
        System.out.println("Flow relation added");
    }
    public void addPlace(int x, int y){
        Place p = new Place(x,y);
        p.addMouseMotionListener(new MainCanvas.GraphicElementMouseMotionListener());
        p.addMouseListener(new MainCanvas.GraphicElementMouseListener());
        places.add(p);
        this.add(p);
        System.out.println("Place added@ " + x + ":" + y);
    }
    public void addTransition(int x, int y){
        Transition t = new Transition(x,y);
        t.addMouseMotionListener(new MainCanvas.GraphicElementMouseMotionListener());
        t.addMouseListener(new MainCanvas.GraphicElementMouseListener());
        transitions.add(t);
        this.add(t);
        System.out.println("Transition added");
    }
}
