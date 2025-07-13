package code.unittests.models.race;

import org.junit.jupiter.api.Test;
import code.models.race.Route;
import code.models.race.Track;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class TrackTest {
    private Track track;
    private ArrayList<Route> routes;


    @Test
    void getRoutesFromTrackAfterSingleAddedRoute() {
        track = new Track("Golf Club", "A golf club.", 90, 5, 8000, 200);
        Route route = new Route("A few holes", "Hard", 2, 150);

        track.addRoutes(route);
        routes = track.getRoutes();

        assertEquals(1, routes.size());
        assertSame(route, routes.getFirst());
    }

    @Test
    void getRoutesFromTrackAfterMultipleAddedRoutes() {
        track = new Track("Golf Club", "A golf club.", 120, 8, 12000, 300);
        Route route1 = new Route("Route1", "Medium", 1, 100);
        Route route2 = new Route("Route2", "Hard", 3, 200);

        track.addRoutes(route1);
        track.addRoutes(route2);
        routes = track.getRoutes();

        assertEquals(2, routes.size());
        assertSame(route1, routes.getFirst());
        assertSame(route2, routes.getLast());
    }
}