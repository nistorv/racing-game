package code.services.racing.track;

import code.models.race.Track;

import java.util.ArrayList;
import java.util.Random;

/**
 * Generates a list of three unique tracks to display for the Track Selector Controller.
 *
 * <p>
 * Generates a list of unique tracks. Constructor ensures first track of generated list has a
 * $0 entry cost and randomly selects two additional tracks.
 * </p>
 */
public class TrackGeneration {

    static ArrayList<Track> tracks;

    /**
     * Generates a random selection of tracks.
     * First track is always free, selects 2 additional tracks.
     */
    public static void generateTracks() {
        ArrayList<Track> displayedTracks = new ArrayList<>();
        ArrayList<Track> allTracks = TrackArray.getTracks();
        while (displayedTracks.size() <= 2) {
            Random random = new Random();
            Track candidateTrack = allTracks.get(random.nextInt(allTracks.size()));
            if (!displayedTracks.contains(candidateTrack)) {
                if (!(displayedTracks.isEmpty())) {
                    displayedTracks.add(candidateTrack);
                } else {
                    if (candidateTrack.getEntryCost() == 0) {
                        displayedTracks.add(candidateTrack);
                    }
                }
            }
        }
        tracks = displayedTracks;
    }

    /**
     * Gets the current list of generated tracks.
     *
     * @return ArrayList of Track objects selected.
     */
    public static ArrayList<Track> getTracks() {
        return tracks;
    }
}
