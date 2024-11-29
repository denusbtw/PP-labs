package game.ui;

import game.core.GameObject;

import java.util.List;
import java.util.logging.Logger;


public class TerminalDisplayRenderer {
    private static final Logger logger = Logger.getLogger(TerminalDisplayRenderer.class.getName());


    public void renderGameObjects(List<GameObject> gameObjects){
        synchronized (gameObjects) {
            for (GameObject gameObject : gameObjects) {
                gameObject.render();
            }
        }
    }

    public static void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void moveTo(int x, int y) {
        System.out.printf("\033[%d;%dH", y, x);
    }

    public static void resetCursor() {
        moveTo(0, 0);
    }
}