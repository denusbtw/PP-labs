package menu;

import helpers.TablePrinter;
import models.Wishlist;

import java.util.Scanner;
import java.util.logging.Logger;


public class EditWishlist implements MenuCommand {
    private static Logger logger = Logger.getLogger(AddToWishList.class.getName());
    private Wishlist wishlist;

    public EditWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

    @Override
    public void execute() {
        logger.info("Executing EditWishlist command...");

        System.out.println();
        TablePrinter tablePrinter = new TablePrinter(wishlist.getTours());
        tablePrinter.print();

        Scanner scanner = new Scanner(System.in);
        int option;

        System.out.print("\nEnter index of tour you want to remove from wishlist: ");
        if (scanner.hasNextInt()) {
            option = scanner.nextInt();
            wishlist.removeTour(option-1);
        }

        logger.info("Finished executing EditWishlist command");
    }
}
