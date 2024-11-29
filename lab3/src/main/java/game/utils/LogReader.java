package game.utils;

import java.io.File;

public class LogReader {
    public static File[] getLogFiles(){
        File folder = new File("logs/");

        File[] files = new File[0];

        if (folder.exists() && folder.isDirectory()){
            files = folder.listFiles();
        }

        return files;
    }
}
