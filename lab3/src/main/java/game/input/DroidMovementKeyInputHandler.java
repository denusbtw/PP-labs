package game.input;


import game.actions.Fire;
import game.core.GameStateManager;
import game.droids.Droid;
import game.droids.DroidManager;
import game.movement.MovementManager;
import game.ui.TerminalDisplay;
import game.ui.TerminalDisplayRenderer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;


public class DroidMovementKeyInputHandler implements KeyListener {
    private static  Logger logger = Logger.getLogger(DroidMovementKeyInputHandler.class.getName());

    private  DroidManager droidManager;
    private  TerminalDisplay terminalDisplay;

    public DroidMovementKeyInputHandler(DroidManager droidManager, TerminalDisplay terminalDisplay) {
        this.droidManager = droidManager;
        this.terminalDisplay = terminalDisplay;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        Droid topDroid = droidManager.getUserTopDroid();
        Droid bottomDroid = droidManager.getUserBottomDroid();
        GameStateManager gameStateManager = droidManager.getGameStateManager();

        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_LEFT -> MovementManager.move(bottomDroid, "left");
            case KeyEvent.VK_RIGHT -> MovementManager.move(bottomDroid, "right");
            case KeyEvent.VK_UP -> MovementManager.move(bottomDroid, "up");
            case KeyEvent.VK_DOWN -> MovementManager.move(bottomDroid, "down");
                case KeyEvent.VK_ENTER -> Fire.fire(bottomDroid, droidManager.getTopDroids(), "up", terminalDisplay, gameStateManager);

            case KeyEvent.VK_A -> MovementManager.move(topDroid, "left");
            case KeyEvent.VK_D -> MovementManager.move(topDroid, "right");
            case KeyEvent.VK_W -> MovementManager.move(topDroid, "up");
            case KeyEvent.VK_S -> MovementManager.move(topDroid, "down");
                case KeyEvent.VK_SPACE -> Fire.fire(topDroid, droidManager.getBottomDroids(), "down", terminalDisplay, gameStateManager);
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}