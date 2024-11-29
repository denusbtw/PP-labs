package game.ui;

import game.core.GameObject;
import game.droids.Droid;

public class DroidHud extends GameObject {
    private Droid droid1;
    private Droid droid2;
    private String position;


    public DroidHud(int x, int y, String representation, String color, TerminalDisplay terminalDisplay, String position, Droid droid1){
        super(x, y, representation, color, terminalDisplay);
        this.position = position;
        this.droid1 = droid1;
        this.droid2 = null;
    }

    public DroidHud(int x, int y, String representation, String color, TerminalDisplay terminalDisplay,
                    String position, Droid droid1, Droid droid2) {
        super(x, y, representation, color, terminalDisplay);
        this.position = position;
        this.droid1 = droid1;
        this.droid2 = droid2;
    }



    @Override
    public void render() {
        TerminalDisplayRenderer.moveTo(getX(), getY());

        int screenWidth = getDisplay().getWidth();

        int halfScreenWidth = screenWidth / 2;

        String separatorLine = "=".repeat(screenWidth);

        if (position.equals("bottom")) {
            System.out.print(separatorLine);
        }

        String string1 = String.format("♥ %d\t\uD83D\uDDE1 %d\t\uD83D\uDDF2 %d\t%s",
                droid1.getHealth(), droid1.getDamage(), droid1.getMovementSpeed(), droid1.getName());

        System.out.print(string1);

        if (droid2 != null){
            // Calculate the remaining space for the second droid (right side)
            String string2 = String.format("♥ %d\t\uD83D\uDDE1 %d\t\uD83D\uDDF2 %d\t%s",
                    droid2.getHealth(), droid2.getDamage(), droid2.getMovementSpeed(), droid2.getName());

            int remainingSpace = (halfScreenWidth - string1.length() - 11)  + (halfScreenWidth - string2.length() - 11);

            System.out.print(" ".repeat(remainingSpace > 0 ? remainingSpace : 1));
            System.out.print(string2);
        }

        // If HUD is for the top position, print the top separator line
        if (position.equals("top")) {
            System.out.println();
            System.out.print(separatorLine);
        }
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
