import PetriElements.*;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class FileIO {

    private File file;
    ArrayList<Transition> transitions;
    ArrayList<Place> places;
    ArrayList<Arc> arcs;
    private MainCanvas canvas;

    FileIO(MainCanvas canvas){
        this.canvas = canvas;
        transitions = new ArrayList<>();
        places = new ArrayList<>();
        arcs = new ArrayList<>();
    }
    public void addPlace(Place p){places.add(p);}
    public void addTransition(Transition t){transitions.add(t);t.setBounds(t.getPos().x-30, t.getPos().y-30, 60,60);}
    public void addArc(Arc a){arcs.add(a);}
    public void clear(){
        arcs.clear();
        transitions.clear();
        places.clear();
        canvas.repaint();
    }
    public void saveFile(){
        if(file == null){
            final JFileChooser fc = new JFileChooser();
            int retVal = fc.showSaveDialog(canvas);
            if(retVal == JFileChooser.APPROVE_OPTION)
                file = fc.getSelectedFile();
            else
                return;
        }
        try{
            Element project = new Element("project");
            Document doc = new Document(project);

            Date now = new Date();
            project.setAttribute("timestamp", now.getTime() + "");

            Element placesE = new Element("places");
            for(Place p: places){
                Element placeE = new Element("place");
                placeE.setAttribute("x",p.getPos().x + "");
                placeE.setAttribute("y", p.getPos().y + "");
                placeE.setAttribute("tokens", ((PlaceCore) p.getCore()).getTokens() + "");
                placeE.setAttribute("capacity", ((PlaceCore)p.getCore()).getCapacity() + "");
                placeE.setAttribute("name", p.getName());
                placesE.addContent(placeE);
            }
            doc.getRootElement().addContent(placesE);

            Element transitionsE = new Element("transitions");
            for(Transition t: transitions){
                Element transitionE = new Element("transition");
                transitionE.setAttribute("x", t.getPos().x + "");
                transitionE.setAttribute("y", t.getPos().y + "");
                transitionE.setAttribute("name", t.getName());
                transitionsE.addContent(transitionE);
            }
            doc.getRootElement().addContent(transitionsE);

            Element arcsE = new Element("arcs");
            for(Arc a: arcs){
                Element arcE = new Element("arc");
                arcE.setAttribute("fromx", a.getFromPos().x + "");
                arcE.setAttribute("fromy", a.getFromPos().y + "");
                arcE.setAttribute("tox", a.getToPos().x + "");
                arcE.setAttribute("toy", a.getToPos().y + "");
                arcE.setAttribute("weight", a.getWeight() + "");
                arcE.setAttribute("id", a.getID() + "");
                arcsE.addContent(arcE);
            }
            doc.getRootElement().addContent(arcsE);
            XMLOutputter output = new XMLOutputter();

            output.setFormat(Format.getPrettyFormat());
            output.output(doc, new FileOutputStream(file));
            canvas.setMessage("File saved.@ " + now);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public void saveAs(){
        file = null;
        saveFile();
    }
    public void openFile(){
        final JFileChooser fc = new JFileChooser();
        int retVal = fc.showOpenDialog(canvas);
        if(retVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            clear();
            try{
                SAXBuilder builder = new SAXBuilder();
                Document doc = builder.build(file);
                Element rootElement = doc.getRootElement();

                Element placesE = rootElement.getChild("places");
                Element transitionsE = rootElement.getChild("transitions");
                Element arcsE = rootElement.getChild("arcs");

                addPlaces(placesE);
                addTransitions(transitionsE);
                canvas.repaint();
                addArcs(arcsE);

                canvas.repaint();
                canvas.updateStats();
                Attribute timeStamp = rootElement.getAttribute("timestamp");
                System.out.println("timestamp " + timeStamp.getValue());
                Date fileDate = new Date();
                fileDate.setTime(Long.parseLong(timeStamp.getValue()));
                canvas.setMessage("File from " + fileDate + " was opened.");
            }
            catch(JDOMException | IOException e){
                e.printStackTrace();
            }
        }
        else
            return;

    }
    private void addArcs(Element aE){
        java.util.List<Element> arcsE = aE.getChildren();
        System.out.println("Adding arcs.");
        try{
            for(int i = 0; i < arcsE.size(); i++){
                Element e = arcsE.get(i);
                Attribute fromX = e.getAttribute("fromx");
                Attribute fromY = e.getAttribute("fromy");
                System.out.println("ArcFrom @" + fromX.getIntValue() + ":" + fromY.getIntValue());
                GraphicPetriElement from = findElement(new Point(fromX.getIntValue(), fromY.getIntValue()));
                Attribute toX = e.getAttribute("tox");
                Attribute toY = e.getAttribute("toy");
                System.out.println("Arcto @" + toX.getIntValue() + ":" + toY.getIntValue());
                GraphicPetriElement to = findElement(new Point(toX.getIntValue(), toY.getIntValue()));
                Arc a = new Arc(from,to);
                Attribute weight = e.getAttribute("weight");
                a.setWeight(weight.getIntValue());
                arcs.add(a);
                canvas.add(a);
            }
        }catch (DataConversionException err){
            err.printStackTrace();
        }
    }
    private void addTransitions(Element tE){
        java.util.List<Element> transitionsE = tE.getChildren();
        System.out.println("Adding transitions.");
        try{
            for (int i = 0; i< transitionsE.size(); i++) {
                Element e = transitionsE.get(i);
                Attribute x = e.getAttribute("x");
                Attribute y = e.getAttribute("y");
                Attribute name = e.getAttribute("name");
                Transition t = new Transition(x.getIntValue(), y.getIntValue());
                t.setName(name.getValue());
                t.setBounds(t.getPos().x-30, t.getPos().y-30, 60,60);
                transitions.add(t);
                canvas.add(t);
                System.out.println("Transition added. @" + x.getIntValue() + ":" + y.getIntValue());
            }

        }catch(DataConversionException err) {
            err.printStackTrace();
        }
    }
    private void addPlaces(Element pE){
        java.util.List<Element> placesE = pE.getChildren();
        System.out.println("Adding places.");
        try {
            for (int i = 0; i< placesE.size(); i++) {
                Element e = placesE.get(i);
                Attribute x = e.getAttribute("x");
                Attribute y = e.getAttribute("y");
                Attribute tokens = e.getAttribute("tokens");
                Attribute capacity = e.getAttribute("capacity");
                Attribute name = e.getAttribute("name");
                Place p = new Place(x.getIntValue(), y.getIntValue());
                p.setName(name.getValue());
                p.setBounds(p.getPos().x-30, p.getPos().y-30, 60,60);
                ((PlaceCore)p.getCore()).setTokens(tokens.getIntValue());
                ((PlaceCore)p.getCore()).setCapacity(capacity.getIntValue());
                places.add(p);
                canvas.add(p);
                System.out.println("Place added@ " + x.getIntValue() + ":" + y.getIntValue());
            }
        }catch(DataConversionException err){
            err.printStackTrace();
        }
    }

    public void eraseElement(Point point){
        try {
            GraphicPetriElement g = findElement(point);
            for(Arc a: g.getCore().getToThis()){
                GraphicPetriElement foo = a.getFrom();
                foo.getCore().getFromThis().remove(a);
            }
            for(Arc a: g.getCore().getFromThis()){
                GraphicPetriElement foo = a.getTo();
                foo.getCore().getToThis().remove(a);
            }
            arcs.removeAll(g.getCore().getFromThis());
            arcs.removeAll(g.getCore().getToThis());
            if (g.getClass() == Place.class)
                places.remove(g);
            else if (g.getClass() == Transition.class)
                transitions.remove(g);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    GraphicPetriElement findElement(Point point) throws RuntimeException{
        for(Place p: places)
            if (p.getBounds().contains(point))
                return p;
        for(Transition t: transitions)
            if(t.getBounds().contains(point))
                return t;
        throw new RuntimeException("No element found!");
    }
    public boolean isDuplicate(Arc f){
        for(Arc a: arcs)
            if(a.equals(f))
                return true;
        return false;
    }
}
