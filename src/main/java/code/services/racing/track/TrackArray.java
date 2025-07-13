package code.services.racing.track;

import code.models.race.Route;
import code.models.race.Track;

import java.util.ArrayList;

/**
 * Creates, stores and provides access to all tracks and routes in the game.
 *
 * <p>
 * Constructor for initialising, accessing and managing tracks and their routes.
 * </p>
 */
public class TrackArray {
    private static final ArrayList<Track> ALL_TRACKS = createAllTracks();

    /**
     * Creates all tracks and routes in the game.
     *
     * @return ArrayList containing all created Track objects.
     */
    private static ArrayList<Track> createAllTracks() {
        ArrayList<Track> tracks = new ArrayList<>();

        Track templeton = new Track("Templeton G.C", "A classic golf club in West Christchurch.",
                5, 6, 4000, 0);
        templeton.addRoutes(new Route("Blue Front 9 holes at Templeton.",
                "Easy", 4, 2974));
        templeton.addRoutes(new Route("Blue Back 9 holes at Templeton.",
                "Hard", 2, 3135));
        tracks.add(templeton);

        Track coringa = new Track("Coringa Golf Club",
                "Endure long distance holes at this rural Canterbury golf club.",
                5, 6, 5000, 0);
        coringa.addRoutes(new Route("Black Front 9 holes at Christchurch's Coringa.",
                "Medium", 3, 3320));
        coringa.addRoutes(new Route("Black Back 9 holes at Christchurch's Coringa.",
                "Hard", 2, 3173));
        tracks.add(coringa);

        Track hagley = new Track("Hagley Golf Club",
                "Play golf in the heart of New Zealand's second largest city.",
                5, 6, 2500, 0);
        hagley.addRoutes(new Route("White Front 9 holes at Christchurch's Hagley.",
                "Easy", 3, 2753));
        hagley.addRoutes(new Route("White Back 9 holes at Christchurch's Hagley.",
                "Normal", 3, 3061));
        tracks.add(hagley);

        Track ohariu = new Track("Ohariu Golf Club",
                "Travel to this golf club nestled in the valley near the world's southernmost capital.",
                5, 12, 10000, 2000);
        ohariu.addRoutes(new Route("White Front 9 holes at Wellington's Ohariu.",
                "Hard", 1, 2032));
        ohariu.addRoutes(new Route("White Back 9 holes at Wellington's Ohariu.",
                "Hard", 1, 1981));
        tracks.add(ohariu);

        Track greenacres = new Track("Greenacres G.C", "Travel to this Tasman seaside golf club.",
                5, 12, 4000, 800);
        greenacres.addRoutes(new Route("Blue Front 9 holes at Nelson's Greenacres.",
                "Hard", 2, 2984));
        greenacres.addRoutes(new Route("Blue Back 9 holes at Nelson's Greenacres.",
                "Hard", 2, 3232));
        tracks.add(greenacres);

        Track feilding = new Track("Feilding Golf Club",
                "Travel to a standard kiwi town for a classic golfing experience.",
                5, 12, 5000, 1000);
        feilding.addRoutes(new Route("HBS Black Front 9 holes at Feilding.",
                "Hard", 2, 2888));
        feilding.addRoutes(new Route("HBS Black Back 9 holes at Feilding.",
                "Hard", 2, 2903));
        tracks.add(feilding);

        Track melbourne = new Track("Albert Park G.C",
                "Travel to Melbourne to play in the world famous Albert Park, home of the Melbourne Grand Prix.",
                5, 20, 15000, 3000);
        melbourne.addRoutes(new Route("Male Front 9 at Melbourne's Albert Park.",
                "Hard", 2, 2985 ));
        melbourne.addRoutes(new Route("Male Back 9 at Melbourne's Albert Park.",
                "Hard", 2, 2785));
        tracks.add(melbourne);

        return tracks;
    }

    /**
     * Returns all available tracks in the game.
     *
     * @return New ArrayAList containing all Track objects.
     */
    public static ArrayList<Track> getTracks() {
        return new ArrayList<>(ALL_TRACKS);
    }
}

