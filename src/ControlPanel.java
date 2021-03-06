import PetriElements.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

class ControlPanel extends JPanel{
    private MainCanvas canvas;
    @Nullable
    private GraphicPetriElement draggedElement, arcFrom;

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
        public void mouseClicked(@NotNull MouseEvent e){
            GraphicPetriElement foo = null;
            try{
                foo = canvas.findElememnt(e.getPoint());
            }catch(Exception err){
                System.err.println(err.getMessage());
            }

            if(foo != null){
                //Shows PlaceContextMenu or adds/removes tokens from place
                if(foo.getClass() == Place.class) {
                    if (canvas.getToggled().equals("token")) {
                        PlaceCore pc = (PlaceCore) foo.getCore();
                        if (e.getButton() == MouseEvent.BUTTON3)
                            pc.setTokens(pc.getTokens() - 1 >= 0 ? pc.getTokens() - 1 : 0);
                        else if (e.getButton() == MouseEvent.BUTTON1)
                            pc.setTokens(pc.getTokens() + 1);
                        System.out.println("tokens added/removed");
                    } else if (e.getClickCount() == 2) {
                        new PlaceArcContextMenu((Place) foo);
                        canvas.repaint();
                    }
                }
                //Shows TransitionContextMenu
                else if(foo.getClass() == Transition.class && e.getClickCount() == 2){
                    new TransitionContextMenu((Transition) foo);
                    canvas.repaint();
                }
            }
            //Erasing an object
            if(canvas.getToggled().equals("erase") && foo != null){
                eraseElement(e.getPoint());
                canvas.repaint();
            }
            //Adding an object
            if(foo == null){
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
        public void mousePressed(@NotNull MouseEvent e){
            if(canvas.getToggled().equals("arc")){
                try{
                    arcFrom = canvas.findElememnt(e.getPoint());
                }catch (Exception err){
                    System.err.println(err.getMessage());
                }
                if(arcFrom != null) canvas.setArrowFrom(e.getPoint());
            }
        }
        @Override
        public void mouseReleased(@NotNull MouseEvent e){
            if(canvas.getToggled().equals("arc") && arcFrom != null){
                try{
                    GraphicPetriElement arcTo = canvas.findElememnt(e.getPoint());
                    if(arcTo.getClass() != arcFrom.getClass()) addArc(arcFrom, arcTo);
                }catch(Exception err){
                    System.err.println(err.getMessage());
                }
            }
            canvas.eraseArrow();
            if(draggedElement != null) draggedElement = null;
            if(arcFrom != null) arcFrom = null;
        }
    }
    class ControlPanelMouseMotionListener extends MouseMotionAdapter{
        @Override
        public void mouseDragged(@NotNull MouseEvent e){
            if(canvas.getToggled().equals("arc")){
                canvas.setArrow(e.getPoint());
                canvas.repaint();
            }else {
                if(draggedElement == null) {
                    try {
                        draggedElement = canvas.findElememnt(e.getPoint());
                    } catch (Exception err) {
                        System.err.println(err.getMessage());
                    }
                }
                if(draggedElement != null) {
                    draggedElement.setPos(e.getX(),e.getY());
                    for(Arc a: draggedElement.getCore().getFromThis())
                        a.repaint();
                    for(Arc a: draggedElement.getCore().getToThis())
                        a.repaint();
                    //canvas.repaint(e.getX()-30, e.getY()-30, 60,60);
                    //canvas.repaint();
                }
            }
        }
    }

    private void addArc(@NotNull GraphicPetriElement from, @NotNull GraphicPetriElement to){
        try {
            canvas.addArc(from, to);
        }catch (RuntimeException err){
            System.err.println(err.getMessage());
        }
    }
    private void addPlace(int x, int y){canvas.addPlace(x,y); }
    private void addTransition(int x, int y){ canvas.addTransition(x,y); }
    private void eraseElement(Point p){ canvas.eraseElement(p); }
}
