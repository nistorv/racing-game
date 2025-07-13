package code.unittests.services.racing.track;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import code.models.race.Track;
import code.services.racing.track.TrackGeneration;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TrackGenerationTest {
    private ArrayList<Track> generatedTracks;
    
    @BeforeEach
    void init() {
        TrackGeneration.generateTracks();
        generatedTracks = TrackGeneration.getTracks();
    }
    
    @Test
    void testTracksGeneratedWithCorrectSize() {
        assertEquals(3, generatedTracks.size());
    }

    @Test
    void testTracksGeneratedAreUnique() {
        assertEquals(generatedTracks.size(), generatedTracks.stream().distinct().count());
    }

    @Test
    void testFirstTrackHasZeroEntryCostWhenEmpty() {
        assertEquals(0, generatedTracks.getFirst().getEntryCost());
    }
}