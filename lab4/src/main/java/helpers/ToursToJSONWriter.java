package helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Tour;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;


public class ToursToJSONWriter {
    private static Logger logger = Logger.getLogger(ToursToJSONWriter.class.getName());
    private final String relativeResourcesPath = "src/main/resources/";
    private List<Tour> tours;
    private String fileName;

    public ToursToJSONWriter(List<Tour> tours, String fileName) {
        this.tours = tours;
        this.fileName = fileName;
    }

    public void write(){
        String filePath = relativeResourcesPath + fileName;
        logger.info("Writing tours to " + filePath + "...");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(tours, writer);
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }

        logger.info("Finished writing tours to " + filePath);
    }
}
