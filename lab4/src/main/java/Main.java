import helpers.ToursFromJSONReader;
import models.Tour;

import java.util.List;
import java.util.logging.Logger;

import helpers.GlobalLoggerSetup;
import models.Wishlist;
import menu.MainMenu;


public class Main {
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        GlobalLoggerSetup.setupGlobalLogging();

        logger.info("Initialing components...");

        ToursFromJSONReader toursFromJSONReader = new ToursFromJSONReader();

        List<Tour> tours = toursFromJSONReader.read("tours.json");
        List<Tour> wishlistTours = toursFromJSONReader.read("wishlist.json");

        Wishlist wishlist = new Wishlist(wishlistTours);
        MainMenu menu = new MainMenu(tours, wishlist);

        logger.info("Finished initializing components");

        menu.run();
    }
}
