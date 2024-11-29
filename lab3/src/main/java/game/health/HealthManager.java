package game.health;

import java.util.logging.Logger;


public class HealthManager {
    private static  Logger logger = Logger.getLogger(HealthManager.class.getName());

    private int currentHealth;
    private final int maxHealth;


    public HealthManager(int health) {
        this.currentHealth = health;
        this.maxHealth = health;
    }

    public int getHealth(){ return currentHealth; }

    public int getMaxHealth(){ return maxHealth; }

    public void changeHealth(int delta) {
        currentHealth = Math.max(0, Math.min(maxHealth, currentHealth + delta));
    }
}
