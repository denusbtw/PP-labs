package game.utils;

import org.json.JSONArray;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONReader {
    public static JSONArray readFile(String filePath){
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            JSONArray jsonArray = new JSONArray(content);

            return jsonArray;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
