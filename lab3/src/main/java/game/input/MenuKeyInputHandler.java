package game.input;

import game.droids.DroidManager;
import game.droids.DroidSelectManager;
import game.core.GameStateManager;
import game.ui.MenuManager;
import game.ui.TerminalDisplay;
import game.ui.TerminalDisplayRenderer;
import game.utils.LogFileSelectManager;
import game.utils.SoundPlayer;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Logger;


public class MenuKeyInputHandler implements KeyListener {
    private static final Logger logger = Logger.getLogger(MenuKeyInputHandler.class.getName());

    private final GameStateManager gameStateManager;
    private final MenuManager menuManager;
    private final TerminalDisplay terminalDisplay;
    private final DroidSelectManager droidSelectManager;
    private final DroidManager droidManager;
    private final LogFileSelectManager logFileSelectManager;


    public MenuKeyInputHandler(GameStateManager gameStateManager, MenuManager menuManager,
                               TerminalDisplay terminalDisplay, DroidSelectManager droidSelectManager,
                               DroidManager droidManager, LogFileSelectManager logFileSelectManager) {
        this.gameStateManager = gameStateManager;
        this.menuManager = menuManager;
        this.terminalDisplay = terminalDisplay;
        this.droidSelectManager = droidSelectManager;
        this.droidManager = droidManager;
        this.logFileSelectManager = logFileSelectManager;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        SoundPlayer.playSound("menu_click.wav");
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_UP -> menuManager.switchMenuOption(false);
            case KeyEvent.VK_DOWN -> menuManager.switchMenuOption(true);
            case KeyEvent.VK_ENTER -> {
                TerminalDisplayRenderer.clearScreen();

                int selectedOption = menuManager.getSelectedMenuOption();

                gameStateManager.setMenuSelected(true);

                if (selectedOption == menuManager.getMenuOptions().size()-1) {
                    gameStateManager.setKeyInputHandler(new LogFileSelectKeyInputHandler(
                            logFileSelectManager, gameStateManager
                    ));
                    logFileSelectManager.printLogFilesOptions();
                    logger.info("LogFileSelectKeyInputHandler has been set as KeyListener");
                } else {
                    gameStateManager.setKeyInputHandler(new DroidSelectKeyInputHandler(
                        gameStateManager, terminalDisplay, droidSelectManager, droidManager
                    ));
                    logger.info("DroidSelectKeyInputHandler has been set as KeyListener");
                }
                menuManager.selectMenuOption();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
