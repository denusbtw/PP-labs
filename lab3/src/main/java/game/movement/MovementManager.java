package game.movement;

import game.droids.Droid;

import java.util.logging.Logger;


public class MovementManager {
    private static final Logger logger = Logger.getLogger(MovementManager.class.getName());

    public static void move(Droid droid, String direction) {
        logger.info(droid.getName() + " moved " + direction);

        switch (direction){
            case "left" -> droid.setX(droid.getX() - droid.getMovementSpeed());
            case "right" -> droid.setX(droid.getX() + droid.getMovementSpeed());
            case "up" -> droid.setY(droid.getY() - droid.getMovementSpeed());
            case "down" -> droid.setY(droid.getY() + droid.getMovementSpeed());
        }
    }
}
