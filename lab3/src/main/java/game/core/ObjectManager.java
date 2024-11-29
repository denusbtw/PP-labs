package game.core;

import game.actions.Fire;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ObjectManager {
    private static final Logger logger = Logger.getLogger(Fire.class.getName());

    private List<GameObject> gameObjects;


    public ObjectManager() {
        gameObjects = new ArrayList<>();
    }

    public void addGameObject (GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void removeGameObject (GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public List<GameObject> getGameObjects(){
        return gameObjects;
    }
}
