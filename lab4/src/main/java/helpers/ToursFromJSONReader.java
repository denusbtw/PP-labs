package helpers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.Tour;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ToursFromJSONReader {
    private static Logger logger = Logger.getLogger(ToursFromJSONReader.class.getName());
    private final String relativeResourcesPath = "src/main/resources/";

    public List<Tour> read(String fileName){
        String filePath = relativeResourcesPath + fileName;

        logger.info("Reading tours from " + filePath + "...");

        List<Tour> toursTmp = new ArrayList<>();

        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            Type tourListType = new TypeToken<List<Tour>>() {}.getType();

            toursTmp = gson.fromJson(reader, tourListType);
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }

        logger.info("Finished reading tours from " + filePath);
        return toursTmp;
    }
}
