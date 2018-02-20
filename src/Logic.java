import PetriElements.Arc;
import PetriElements.PlaceCore;
import PetriElements.Transition;
import PetriElements.TransitionCore;

import java.util.ArrayList;

public class Logic {

    private static ArrayList<Transition> ret;

    private static boolean isEnabled(Transition t){
        TransitionCore tc = t.getCore();
        for(Arc a: tc.getToThis()){
            if(((PlaceCore)a.getFrom().getCore()).getTokens() < a.getWeight()){
                return false;
            }
        }
        return true;
    }
    private static boolean canFire(Transition t){
        TransitionCore tc = t.getCore();
        for(Arc a: tc.getFromThis()){
            if(((PlaceCore)a.getTo().getCore()).getTokens() + a.getWeight() > (((PlaceCore) a.getTo().getCore()).getCapacity())){
                return false;
            }
        }
        return true;
    }
    private static void checkTransitions(ArrayList<Transition> arr){
        for(Transition t: arr){
            if(Logic.isEnabled(t) && Logic.canFire(t)){
                Logic.ret.add(t);
            }
        }
    }
    private static void dispose(){ret = null;}
    public static void setUp(ArrayList<Transition> arr){
        Logic.ret = new ArrayList<>();
        Logic.checkTransitions(arr);
    }
    public static ArrayList<Arc> firstStep() throws RuntimeException{
        if(Logic.ret == null){
            throw new RuntimeException("ArrayList not initialized@ " + Thread.currentThread().getStackTrace()[2].getFileName() + ":"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        ArrayList<Arc> first = new ArrayList<>();
        for(Transition t: Logic.ret){
            first.addAll(t.getCore().getToThis());
        }
        for(Arc a: first){
            ((PlaceCore)a.getFrom().getCore()).setTokens(((PlaceCore)a.getFrom().getCore()).getTokens() - a.getWeight());
        }
        return first;
    }
    public static ArrayList<Arc> secondStep() throws RuntimeException{
        if(Logic.ret == null){
            throw new RuntimeException("ArrayList not initialized@ " + Thread.currentThread().getStackTrace()[2].getFileName() + ":"
                    + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }
        ArrayList<Arc> second = new ArrayList<>();
        for(Transition t: Logic.ret){
            second.addAll(t.getCore().getFromThis());
        }
        for(Arc a: second){
            ((PlaceCore)a.getTo().getCore()).setTokens(((PlaceCore)a.getTo().getCore()).getTokens() + a.getWeight());
        }
        Logic.dispose();
        return second;
    }
}
