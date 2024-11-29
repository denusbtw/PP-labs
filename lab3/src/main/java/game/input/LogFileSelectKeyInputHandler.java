package game.input;

import game.core.GameStateManager;
import game.ui.TerminalDisplay;
import game.ui.TerminalDisplayRenderer;
import game.utils.LogFileSelectManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.logging.Logger;


public class LogFileSelectKeyInputHandler implements KeyListener {
    private static final Logger logger = Logger.getLogger(LogFileSelectKeyInputHandler.class.getName());

    private final LogFileSelectManager logFileSelectManager;
    private final GameStateManager gameStateManager;


    public LogFileSelectKeyInputHandler(LogFileSelectManager logFileSelectManager, GameStateManager gameStateManager) {
        this.logFileSelectManager = logFileSelectManager;
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()){
            case KeyEvent.VK_UP -> logFileSelectManager.switchOption(false);
            case KeyEvent.VK_DOWN -> logFileSelectManager.switchOption(true);
            case KeyEvent.VK_ENTER -> {
                try {
                    gameStateManager.setMenuVisible(true);
                    logFileSelectManager.selectOption();

                    gameStateManager.setKeyInputHandler(null);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
