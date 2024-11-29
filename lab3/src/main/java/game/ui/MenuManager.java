package game.ui;

import game.core.GameManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class MenuManager {
    private static final Logger logger = Logger.getLogger(MenuManager.class.getName());

    private final GameManager gameManager;
    private final List<String> menuOptions;
    private int selectedMenuOption;


    public MenuManager(GameManager gameManager) {
        this.gameManager = gameManager;
        menuOptions = initMenu();
    }

    public void switchMenuOption(boolean next){
        selectedMenuOption = (selectedMenuOption + (next ? 1 : menuOptions.size() - 1)) % menuOptions.size();

        printMenuChoices();
    }

    public void selectMenuOption(){
        switch (selectedMenuOption){
            case 0 -> System.exit(0);
            case 1 -> gameManager.playerVSPlayer();
            case 2 -> gameManager.teamVSTeam();
        }
    }

    public void printMenuChoices(){
        TerminalDisplayRenderer.clearScreen();

        for (String choice: menuOptions) {
            if (menuOptions.indexOf(choice) == selectedMenuOption) {
                System.out.print("--> ");
            }
            System.out.println(choice);
        }
    }

    public List<String> initMenu(){
        List<String> options = new ArrayList<>();

        options.add("Exit");
        options.add("Player VS Player");
        options.add("Team VS Team");
        options.add("Show Logs");

        return options;
    }

    public int getSelectedMenuOption(){ return selectedMenuOption; }

    public List<String> getMenuOptions(){ return menuOptions; }
}
