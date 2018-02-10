import PetriElements.FlowRelation;
import PetriElements.GraphicPetriElement;
import PetriElements.PetriElement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class ControlPanel extends JPanel{
    private MainCanvas canvas;
    private GraphicPetriElement draggedElement, FlowRelationFrom;

    ControlPanel(MainCanvas canvas){
        this.canvas = canvas;
        setLayout(null);
        setOpaque(false);
        setSize(canvas.getSize());
        addMouseListener(new ControlPanelMouseListener());
        addMouseMotionListener(new ControlPanelMouseMotionListener());
    }

    class ControlPanelMouseListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e){
            GraphicPetriElement foo = null;
            try{
                foo = canvas.findElememnt(e.getPoint());
            }catch(Exception err){
                System.out.println(err.getMessage());
            }
            if(!canvas.getToggled().isEmpty() && foo == null){
                switch(canvas.getToggled()){
                    case "place":
                        addPlace(e.getX(), e.getY());
                        break;
                    case "transition":
                        addTransition(e.getX(),e.getY());
                        break;
                    default:
                        break;
                }
                canvas.repaint();
            }
        }
        @Override
        public void mousePressed(MouseEvent e){
            if(canvas.getToggled().equals("fr")){
                try{
                    FlowRelationFrom = canvas.findElememnt(e.getPoint());
                }catch (Exception err){
                    System.out.println(err.getMessage());
                }
                if(FlowRelationFrom != null){
                    canvas.setArrowFrom(e.getPoint());
                    System.out.println("FlowRealtionFrom added");
                }
            }
        }
        @Override
        public void mouseReleased(MouseEvent e){
            if(canvas.getToggled().equals("fr") && FlowRelationFrom != null){
                GraphicPetriElement FlowRelationTo;
                try{
                    FlowRelationTo = canvas.findElememnt(e.getPoint());
                    if(FlowRelationTo.getClass() != FlowRelationFrom.getClass())
                        addFlowRelation(FlowRelationFrom, FlowRelationTo);
                }catch(Exception err){
                    System.out.println(err.getMessage());
                }
            }
            canvas.eraseArrow();
            if(draggedElement != null)
                draggedElement = null;
            if(FlowRelationFrom != null)
                FlowRelationFrom = null;
        }
    }
    class ControlPanelMouseMotionListener extends MouseMotionAdapter{
        @Override
        public void mouseDragged(MouseEvent e){
            if(canvas.getToggled().equals("fr")){
                canvas.setArrow(e.getX(),e.getY());
                canvas.repaint();
            }else {
                if(draggedElement == null) {
                    try {
                        draggedElement = canvas.findElememnt(e.getPoint());
                    } catch (Exception err) {
                        System.out.println(err.getMessage());
                    }
                }
                if(draggedElement != null) {
                    draggedElement.setPos(e.getX(),e.getY());
                    for(FlowRelation f: draggedElement.getCore().getFromThis())
                        f.repaint();
                    for(FlowRelation f: draggedElement.getCore().getToThis())
                        f.repaint();
                    canvas.repaint();
                }
            }
        }
    }




    public void addFlowRelation(GraphicPetriElement from, GraphicPetriElement to){
        canvas.addFlowRelation(from, to);
    }
    public void addPlace(int x, int y){
        canvas.addPlace(x,y);
    }
    public void addTransition(int x, int y){
        canvas.addTransition(x,y);
    }
}
