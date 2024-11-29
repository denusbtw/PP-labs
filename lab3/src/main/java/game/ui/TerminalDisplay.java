package game.ui;

import game.core.Game;
import game.core.GameObject;
import game.core.GameStateManager;
import game.core.ObjectManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;


public class TerminalDisplay {
    private static final Logger logger = Logger.getLogger(TerminalDisplay.class.getName());

    private final GameStateManager gameStateManager;

    private final int width;
    private final int height;
    private final ObjectManager objectManager;
    private final TerminalDisplayRenderer displayRenderer;


    public TerminalDisplay(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;

        int[] dimensions = getDimensions();

        height = dimensions[0];
        width = dimensions[1];
        objectManager = new ObjectManager();
        displayRenderer = new TerminalDisplayRenderer();
    }

    public void addGameObject(GameObject gameObject) {
        synchronized (objectManager.getGameObjects()) {
            objectManager.getGameObjects().add(gameObject);
        }
    }

    public void removeGameObject(GameObject gameObject) {
        synchronized (objectManager.getGameObjects()) {
            objectManager.getGameObjects().remove(gameObject);
        }
    }

    public void renderGameObjects(){
        displayRenderer.renderGameObjects(objectManager.getGameObjects());
    }

    public void refresh() {
        if (!gameStateManager.isMenuVisible()){
            TerminalDisplayRenderer.clearScreen();
            renderGameObjects();
            TerminalDisplayRenderer.resetCursor();
        }
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public int[] getDimensions() {
        int[] dimensions = new int[2];

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", "stty size < /dev/tty");

            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.readLine();

            if (output != null && !output.isEmpty()) {
                String[] size = output.split(" ");

                int rows = Integer.parseInt(size[0]);
                int cols = Integer.parseInt(size[1]);

                dimensions[0] = rows;
                dimensions[1] = cols;
            } else {
                dimensions[0] = -1;
                dimensions[1] = -1;
            }

            int exitCode = process.waitFor();
            System.out.println("Exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            logger.severe("Error while determining terminal dimensions: " + e.getMessage());
        }

        return dimensions;
    }

    public void clear(){
        TerminalDisplayRenderer.clearScreen();
    }

    public void moveTo(int x, int y){
        TerminalDisplayRenderer.moveTo(x, y);
    }
}
