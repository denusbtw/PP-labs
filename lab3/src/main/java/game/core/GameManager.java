package game.core;

import game.droids.DroidManager;
import game.droids.DroidSelectManager;
import game.ui.TerminalDisplay;

import java.util.Random;
import java.util.logging.Logger;


public class GameManager {
    private static Logger logger = Logger.getLogger(GameManager.class.getName());

    private  TerminalDisplay terminalDisplay;
    private  DroidSelectManager droidSelectManager;


    public GameManager(TerminalDisplay terminalDisplay, DroidSelectManager droidSelectManager) {
        this.terminalDisplay = terminalDisplay;
        this.droidSelectManager = droidSelectManager;
    }

    public void playerVSPlayer(){
        logger.info("Setting game mode 'playerVSPlayer'");
        droidSelectManager.showDroidInfo(0, 1);
        droidSelectManager.getGameStateManager().setGameMode("playerVSPlayer");
    }

    public void teamVSTeam(){
        logger.info("Setting game mode 'teamVSTeam'");

        droidSelectManager.getGameStateManager().setAIPlaying(true);
        droidSelectManager.getGameStateManager().setGameMode("teamVSTeam");

        logger.info("Randomizing AI droids indexes");
        Random rand = new Random();

        int randIndex1 = rand.nextInt(5);
        int randIndex2 = rand.nextInt(5);

        droidSelectManager.setSelectedDroidIndex(randIndex1);
        droidSelectManager.selectDroid(1);
        droidSelectManager.setSelectedDroidIndex(randIndex2);
        droidSelectManager.selectDroid(2);

        logger.info("Finished randomizing AI droids indexes");

        droidSelectManager.showDroidInfo(0, 1);
    }
}
