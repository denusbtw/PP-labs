package game.core;


import javax.swing.*;
import java.awt.event.KeyListener;
import java.util.logging.Logger;


public class GameStateManager {
    private static final Logger logger = Logger.getLogger(GameStateManager.class.getName());

    private final JFrame frame;

    private boolean menuSelected = false;
    private boolean user1Selected = false;
    private boolean user2Selected = false;
    private boolean AIPlaying = false;
    private boolean gameOver = false;
    private boolean menuVisible = true;

    private String gameMode = "playerVSPlayer";

    private KeyListener keyInputHandler;

    public GameStateManager(JFrame frame) {
        this.frame = frame;
    }

    public void setMenuSelected(boolean b) {
        menuSelected = b;
    }

    public void setKeyInputHandler(KeyListener newKeyInputHandler) {
        if (keyInputHandler != null) {
            frame.removeKeyListener(keyInputHandler);
        }

        keyInputHandler = newKeyInputHandler;

        frame.addKeyListener(keyInputHandler);
    }


    public void setUser1Selected(boolean b) {
        user1Selected = b;
    }

    public void setUser2Selected(boolean b) {
        user2Selected = b;
    }

    public void setGameOver(boolean b) {
        gameOver = b;
    }

    public boolean hasUser1Selected() {
        return user1Selected;
    }

    public boolean hasUser2Selected() {
        return user2Selected;
    }

    public boolean isMenuSelected() {
        return menuSelected;
    }

    public boolean isAIPlaying() {
        return AIPlaying;
    }

    public boolean isGameOver(){
        return gameOver;
    }

    public void setAIPlaying(boolean b) {
        AIPlaying = b;
    }

    public boolean isMenuVisible() {
        return menuVisible;
    }

    public void setMenuVisible(boolean b) {
        menuVisible = b;
    }

    public void setGameMode(String mode) {
        this.gameMode = mode;
    }

    public String getGameMode() {
        return gameMode;
    }
}
