package game.droids;

import game.core.Game;
import game.core.GameStateManager;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class DroidManager {
    private GameStateManager gameStateManager;

    public JSONArray getAvailableDroids;
    private List<Droid> topDroids;
    private List<Droid> bottomDroids;

    private JSONArray availableDroids;


    public DroidManager(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        topDroids = new ArrayList<>();
        bottomDroids = new ArrayList<>();
    }

    public void addTopDroid(Droid droid){
        topDroids.add(droid);
    }

    public void addBottomDroid(Droid droid){
        bottomDroids.add(droid);
    }

    public void removeTopDroid(Droid droid){
        topDroids.remove(droid);
    }

    public void removeBottomDroid(Droid droid){
        bottomDroids.remove(droid);
    }

    public JSONArray getAvailableDroids(){ return availableDroids; }

    public List<Droid> getTopDroids(){
        if (topDroids.isEmpty()){
            return null;
        }

        return topDroids;
    }

    public List<Droid> getBottomDroids(){
        return bottomDroids;
    }

    public Droid getUserTopDroid(){
        if (topDroids.isEmpty()){
            return null;
        }

        return topDroids.getLast();
    }

    public Droid getUserBottomDroid(){
        if (bottomDroids.isEmpty()){
            return null;
        }

        return bottomDroids.getLast();
    }

    public Droid getAITopDroid(){
        if (topDroids.isEmpty()){
            return null;
        }

        return topDroids.getFirst();
    }

    public Droid getAIBottomDroid(){
        if (bottomDroids.isEmpty()){
            return null;
        }

        return bottomDroids.getFirst();
    }

    public void setAvailableDroids(JSONArray availableDroids) {
        this.availableDroids = availableDroids;
    }

    public GameStateManager getGameStateManager(){ return gameStateManager; }
}
