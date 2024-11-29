package wishlist;

import models.Tour;
import models.Wishlist;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RemoveTourTest {
    private List<Tour> tours;
    private Wishlist wishlist;

    @Before
    public void setUp() {
        tours = new ArrayList<>();
        tours.add(new Tour("Tour 1", "Adventure", "Bus", "All-Inclusive", 7, 500, 4.5));
        tours.add(new Tour("Tour 2", "Cultural", "Train", "Breakfast Only", 5, 300, 4.2));
        tours.add(new Tour("Tour 3", "Relaxation", "Flight", "Full-Board", 10, 1000, 5.0));

        wishlist = new Wishlist(new ArrayList<>());
    }

    @Test
    public void testRemoveTourWithNegativeIndex(){
        wishlist.removeTour(-1);

        assertEquals(0, wishlist.getTours().size());
    }

    @Test
    public void testRemoveTourWithOutOfBoundsIndex(){
        wishlist.removeTour(5);

        assertEquals(0, wishlist.getTours().size());
    }

    @Test
    public void testRemoveTourAtValidIndex(){
        int index = 0;
        Tour tourToAdd = tours.get(index);

        wishlist.addTour(tourToAdd);

        wishlist.removeTour(index);

        assertEquals(0, wishlist.getTours().size());
    }
}
