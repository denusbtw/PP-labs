package game.input;

import game.core.GameStateManager;
import game.droids.Droid;
import game.droids.DroidManager;
import game.droids.DroidSelectManager;
import game.ui.DroidHud;
import game.ui.TerminalDisplay;
import game.ui.TerminalDisplayRenderer;
import game.utils.SoundPlayer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;


public class DroidSelectKeyInputHandler implements KeyListener {
    private static final Logger logger = Logger.getLogger(DroidSelectKeyInputHandler.class.getName());

    private final GameStateManager gameStateManager;
    private final DroidSelectManager droidSelectManager;
    private final TerminalDisplay terminalDisplay;
    private final DroidManager droidManager;


    public DroidSelectKeyInputHandler(GameStateManager gameStateManager, TerminalDisplay terminalDisplay,
                                      DroidSelectManager droidSelectManager, DroidManager droidManager) {
        this.gameStateManager = gameStateManager;
        this.droidSelectManager = droidSelectManager;
        this.terminalDisplay = terminalDisplay;
        this.droidManager = droidManager;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        SoundPlayer.playSound("menu_click.wav");
        int playerIndex = gameStateManager.hasUser1Selected() ? 2 : 1;

        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_LEFT -> {
                droidSelectManager.switchDroid(false);
                droidSelectManager.showDroidInfo(droidSelectManager.getSelectedDroidIndex(), playerIndex);
            }
            case KeyEvent.VK_RIGHT -> {
                droidSelectManager.switchDroid(true);
                droidSelectManager.showDroidInfo(droidSelectManager.getSelectedDroidIndex(), playerIndex);
            }
            case KeyEvent.VK_ENTER -> {
                if (!gameStateManager.hasUser1Selected()){
                    droidSelectManager.selectDroid(1);
                    droidSelectManager.resetSelectedDroidIndex();
                    gameStateManager.setUser1Selected(true);
                    gameStateManager.setKeyInputHandler(new DroidSelectKeyInputHandler(
                            gameStateManager, terminalDisplay, droidSelectManager, droidManager
                    ));
                    droidSelectManager.showDroidInfo(0, 2);
                } else {
                    droidSelectManager.selectDroid(2);
                    gameStateManager.setUser2Selected(true);

                    logger.info("DroidMovementKeyInputHandler has been set as KeyListener");
                    gameStateManager.setKeyInputHandler(new DroidMovementKeyInputHandler(droidManager, terminalDisplay));
                    gameStateManager.setMenuVisible(false);

                    logger.info("Adding droids of users to terminal display");
                    terminalDisplay.addGameObject(droidManager.getUserTopDroid());
                    terminalDisplay.addGameObject(droidManager.getUserBottomDroid());
                    logger.info("Finished adding droids of users to terminal display");

                    if (gameStateManager.isAIPlaying()){
                        logger.info("Adding AI droids to terminal display");

                        terminalDisplay.addGameObject(droidManager.getAITopDroid());
                        terminalDisplay.addGameObject(droidManager.getAIBottomDroid());

                        logger.info("Finished adding AI droids to terminal display");
                    }

                    DroidHud topHud, bottomHud;

                    if (gameStateManager.isAIPlaying()){
                        topHud = new DroidHud(1, 1, "", "white", terminalDisplay, "top",
                                droidManager.getUserTopDroid(), droidManager.getAITopDroid());
                        bottomHud = new DroidHud(1, terminalDisplay.getHeight()-1, "", "white",
                                terminalDisplay, "bottom", droidManager.getUserBottomDroid(), droidManager.getAIBottomDroid());
                    } else {
                        topHud = new DroidHud(1, 1, "", "white", terminalDisplay, "top", droidManager.getUserTopDroid());
                        bottomHud = new DroidHud(1, terminalDisplay.getHeight()-1, "", "white", terminalDisplay, "bottom", droidManager.getUserBottomDroid());
                    }

                    terminalDisplay.addGameObject(topHud);
                    terminalDisplay.addGameObject(bottomHud);

//                    TerminalDisplayRenderer.clearScreen();
//                    terminalDisplay.renderGameObjects();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
