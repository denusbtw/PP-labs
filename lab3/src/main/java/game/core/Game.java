package game.core;

import game.droids.Droid;
import game.droids.DroidManager;
import game.droids.DroidSelectManager;
import game.ui.DroidHud;
import game.ui.TerminalDisplay;
import game.ui.MenuManager;
import game.input.MenuKeyInputHandler;
import game.utils.GlobalLoggerSetup;
import game.utils.JSONReader;
import game.utils.LogFileSelectManager;
import org.json.JSONArray;

import javax.swing.*;
import java.util.logging.Logger;

public class Game {
    private static final Logger logger = Logger.getLogger(Game.class.getName());

    private final TerminalDisplay terminalDisplay;
    private final GameStateManager gameStateManager;
    private final MenuManager menuManager;
    private final DroidManager droidManager;
    private final DroidSelectManager droidSelectManager;
    private final GameManager gameManager;
    private final LogFileSelectManager logFileSelectManager;
    private Timer gameTimer;

    private DroidHud topHud;
    private DroidHud bottomHud;

    public Game() {
        GlobalLoggerSetup.setupGlobalLogging();

        JFrame frame = new JFrame();

        logger.info("Initializing components...");

        logFileSelectManager = new LogFileSelectManager();
        gameStateManager = new GameStateManager(frame);
        terminalDisplay = new TerminalDisplay(gameStateManager);
        droidManager = new DroidManager(gameStateManager);
        droidSelectManager = new DroidSelectManager(droidManager, terminalDisplay, gameStateManager);
        gameManager = new GameManager(terminalDisplay, droidSelectManager);
        menuManager = new MenuManager(gameManager);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logger.info("Components initialized successfully.");
    }

    private void initialize(){
        logger.info("Initializing game...");

        try {
            JSONArray availableDroids = JSONReader.readFile("src/main/resource/droids/droids.json");
            droidManager.setAvailableDroids(availableDroids);
            droidSelectManager.setAvailableDroids(availableDroids);

            gameStateManager.setKeyInputHandler(new MenuKeyInputHandler(
                    gameStateManager, menuManager, terminalDisplay, droidSelectManager, droidManager,
                    logFileSelectManager
            ));
            logger.info("MenuKeyInputHandler has been set as KeyListener");
        } catch (Exception e){
            logger.severe("Error during game initialization: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Game game = new Game();

        game.initialize();
        game.startGame();
    }

    private void updateGame(){
        if (gameStateManager.isGameOver()){
            endGame();
            return;
        }

        terminalDisplay.refresh();
    }

    public void startGame() {
        logger.info("Game has started successfully.");

        try {
            menuManager.printMenuChoices();

            new Thread(() -> {
                while (!gameStateManager.isGameOver()) {
                    updateGame();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        logger.severe("Game loop interrupted: " + e.getMessage());
                    }
                }
                endGame();
            }).start();
        } catch (Exception e) {
            logger.severe("Error during game start: " + e.getMessage());
        }
    }

    public void endGame(){
        logger.info("Game is ending...");

        if (gameTimer != null){
            gameTimer.stop();
        }

        terminalDisplay.clear();
        System.out.println("GAME OVER");

        System.exit(0);

        logger.info("Game has ended.");
    }
}