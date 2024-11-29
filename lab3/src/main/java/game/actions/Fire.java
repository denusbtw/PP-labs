package game.actions;

import game.core.GameObject;
import game.core.GameStateManager;
import game.droids.Droid;
import game.ui.TerminalDisplay;
import game.utils.SoundPlayer;


import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import java.util.List;


public class Fire {
    private static final Logger logger = Logger.getLogger(Fire.class.getName());

    public static void fire(Droid droid, List<Droid> enemyDroids, String direction, TerminalDisplay terminalDisplay,
                            GameStateManager gameStateManager) {
        AtomicBoolean running = new AtomicBoolean(true);

        SoundPlayer.playSound("projectile.wav");
        logger.info(droid.getName() + " shoot " + direction);

        Thread t = new Thread(() -> {
            if (!droid.canFire()){
                return;
            }

            droid.setLastFireTime(System.currentTimeMillis());

            int startY = droid.getY() + (direction.equals("down") ? -1 : 1);
            String projectileSymbol = direction.equals("down") ? "⬇" : "⬆";
            GameObject projectile = new GameObject(droid.getX()+1, startY, projectileSymbol, "red", terminalDisplay);

            terminalDisplay.addGameObject(projectile);

            while (running.get()) {
                int oldY = projectile.getY();
                projectile.setY(projectile.getY() + (direction.equals("down") ? 1 : -1));

                if (projectile.getY() == oldY){
                    running.set(false);
                    break;
                }

                Droid hitDroid = getHitEnemy(projectile, enemyDroids);

                if (hitDroid != null) {
                    logger.info(hitDroid.getName() + " has been hit. Current HP: " + hitDroid.getHealth());

                    SoundPlayer.playSound("hit.wav");
                    hitDroid.changeHealth(-droid.getDamage());

                    if (hitDroid.getHealth() == 0) {
                        SoundPlayer.playSound("explosion.wav");
                        logger.info(hitDroid.getName() + " has exploded");

                        if (enemyDroids.size() == 1){
                            gameStateManager.setGameOver(true);
                        }

                        terminalDisplay.removeGameObject(hitDroid);
                        enemyDroids.remove(hitDroid);
                    }

                    terminalDisplay.removeGameObject(projectile);
                    running.set(false);
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    logger.severe("Error while firing " + direction + " " + e.getMessage());
                    running.set(false);
                }
            }

            synchronized (terminalDisplay) {
                terminalDisplay.removeGameObject(projectile);
            }
        });

        t.start();
    }

    private static Droid getHitEnemy(GameObject projectile, List<Droid> droids) {
        for (Droid droid: droids){
            boolean b = (projectile.getX() == droid.getX() || projectile.getX() == droid.getX() + 1 || projectile.getX() == droid.getX() + 2)
                    && (projectile.getY() == droid.getY());
            if (b){
                return droid;
            }
        }

        return null;
    }
}
