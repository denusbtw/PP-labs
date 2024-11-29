package game.droids;

import game.core.GameStateManager;
import game.ui.TerminalDisplay;
import game.ui.TerminalDisplayRenderer;
import game.utils.ColorConverter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.logging.Logger;

public class DroidSelectManager {
    private static Logger logger = Logger.getLogger(DroidSelectManager.class.getName());

    private  DroidManager droidManager;
    private  TerminalDisplay terminalDisplay;
    private  GameStateManager gameStateManager;
    private JSONArray availableDroids;

    private int selectedDroidIndex = 0;


    public DroidSelectManager(DroidManager droidManager, TerminalDisplay terminalDisplay,
                              GameStateManager gameStateManager) {
        this.droidManager = droidManager;
        this.terminalDisplay = terminalDisplay;
        this.gameStateManager = gameStateManager;
        this.availableDroids = new JSONArray();
    }

    public void showDroidInfo(int index, int playerIndex){
        JSONObject droidInfo = availableDroids.getJSONObject(index);

        TerminalDisplayRenderer.clearScreen();
        System.out.printf("USER %d:\n\n", playerIndex);
        System.out.printf("%s%s%s\n", ColorConverter.getTextColor(droidInfo.getString("color")),
                droidInfo.getString("representation"), ColorConverter.getReset());
        System.out.printf("Name: %s\n", droidInfo.getString("name")
                + String.format("\nHealth: %d\n", droidInfo.getInt("health"))
                + String.format("Movement speed: %d\n", droidInfo.getInt("movementSpeed"))
                + String.format("Damage: %d\n", droidInfo.getInt("damage"))
                + String.format("Fire rate: %.1f\n", droidInfo.getDouble("fireRate"))
                + String.format("Description: %s\n", droidInfo.getString("description"))
        );


        System.out.printf("Droid (%d/%d)\n", index+1, availableDroids.length());

        String firstString = "<- Previous droid";
        String middleString = "ENTER to select droid";
        String lastString = "Next droid ->";

        int padding = (terminalDisplay.getWidth() - firstString.length() - middleString.length() - lastString.length())/2;

        System.out.printf("%s%s%s%s%s", firstString, " ".repeat(padding), middleString, " ".repeat(padding), lastString);

    }

    public void switchDroid(boolean next) {
        selectedDroidIndex = (selectedDroidIndex + (next ? 1 : availableDroids.length() - 1)) % availableDroids.length();
    }

    public void selectDroid(int playerIndex) {
        Droid droid = createDroidFromJSONObject(availableDroids.getJSONObject(selectedDroidIndex), playerIndex);

        if (playerIndex == 1){
            droidManager.addTopDroid(droid);
        } else {
            droidManager.addBottomDroid(droid);
        }

        logger.info("Player " + playerIndex + " selected droid " + droid.getName());
    }

    public Droid createDroidFromJSONObject(JSONObject jsonDroid, int playerIndex){
        int startX = terminalDisplay.getWidth()/2;
        int startY;

        // playerIndex = 1 means top, 2 - bottom
        if (playerIndex == 1){
            startY = 3;
        } else {
            startY = terminalDisplay.getHeight() - 2;
        }

        if (gameStateManager.isAIPlaying() && gameStateManager.getGameMode().equals("teamVSTeam")) {
            startX -= 20;

            if ((playerIndex == 1 && (droidManager.getTopDroids() == null || droidManager.getTopDroids().isEmpty()))
                    || (playerIndex == 2 && (droidManager.getBottomDroids() == null || droidManager.getBottomDroids().isEmpty()))) {
                startX += 40;
            }
        }

        return new Droid(startX, startY, jsonDroid.getString("representation"),
                jsonDroid.getString("color"), terminalDisplay, jsonDroid.getString("name"),
                jsonDroid.getInt("health"), jsonDroid.getInt("damage"), jsonDroid.getInt("movementSpeed"),
                jsonDroid.getFloat("fireRate"));
    }

    public JSONArray getAvailableDroids(){
        return availableDroids;
    }

    public int getSelectedDroidIndex(){ return selectedDroidIndex; }

    public void resetSelectedDroidIndex() {
        selectedDroidIndex = 0;
    }

    public void setSelectedDroidIndex(int idx){
        selectedDroidIndex = idx;
    }

    public void setAvailableDroids(JSONArray availableDroids) {
        this.availableDroids = availableDroids;
    }

    public GameStateManager getGameStateManager(){
        return gameStateManager;
    }

    public DroidManager getDroidManager(){
        return droidManager;
    }
}
