package game.droids;

import game.core.GameObject;
import game.ui.TerminalDisplay;
import game.health.HealthManager;

public class Droid extends GameObject {
    private String name;
    private final HealthManager healthManager;
    private int damage;
    private double fireRate;
    private int movementSpeed;
    private long lastFireTime = 0;

    public Droid(int x, int y, String representation, String color,
                 TerminalDisplay terminalDisplay, String name, int health, int damage, int movementSpeed,
                 float fireRate) {
        super(x, y, representation, color, terminalDisplay);
        this.name = name;
        healthManager = new HealthManager(health);
        this.damage = damage;
        this.movementSpeed = movementSpeed;
        this.fireRate = fireRate;
    }

    @Override
    public void setX(int x){
        if (x > 0 && x < getDisplay().getWidth()-2){
            super.setX(x);
        }
    }

    public boolean isAlive(){ return healthManager.getHealth() > 0; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public HealthManager getHealthManager() { return healthManager; }

    public int getHealth(){ return healthManager.getHealth(); }

    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }

    public int getMovementSpeed() { return movementSpeed; }
    public void setMovementSpeed(int movementSpeed) { this.movementSpeed = movementSpeed; }

    public void changeHealth(int delta) {
        healthManager.changeHealth(delta);
    }

    public void setLastFireTime(long lastFireTime) { this.lastFireTime = lastFireTime; }

    public boolean canFire() {
        long currentTime = System.currentTimeMillis();
        long timeBetweenShots = (long) (1000 / this.fireRate);

        if (currentTime - lastFireTime >= timeBetweenShots) {
            lastFireTime = currentTime;
            return true;
        }
        return false;
    }
}
