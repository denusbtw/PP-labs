package game.utils;

import game.core.Game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.logging.Logger;

public class SoundPlayer {
    private static final Logger logger = Logger.getLogger(SoundPlayer.class.getName());


    public static void playSound(String soundFileName) {
        try {
            File soundFile = new File("src/main/resource/sounds/" + soundFileName);
            if (!soundFile.exists()) {
                logger.severe("Sound file " + soundFileName + " not found");
                return;
            }

//            logger.info("Playing sound file " + soundFileName);

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start(); // Play the sound

        } catch (Exception e) {
            logger.severe("Error playing sound file " + soundFileName);
        }
    }
}
