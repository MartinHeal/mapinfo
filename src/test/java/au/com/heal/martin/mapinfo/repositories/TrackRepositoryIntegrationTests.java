package au.com.heal.martin.mapinfo.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import au.com.heal.martin.mapinfo.TestDataUtil;
import au.com.heal.martin.mapinfo.domain.entities.TrackEntity;
import au.com.heal.martin.mapinfo.domain.entities.TrackPointEntity;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TrackRepositoryIntegrationTests {

    private TrackRepository trackRepository;

    @Autowired
    public TrackRepositoryIntegrationTests(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Test
    void testThatTrackCanBeCreatedAndRetrieved() {
        TrackEntity track = TestDataUtil.createTestTrack1();

        trackRepository.save(track);
        Optional<TrackEntity> result = trackRepository.findById(track.getId());

        assertThat(result).isPresent();

        assertThat(result.get().getName()).isEqualTo(track.getName());
        assertThat(result.get().getDescription()).isEqualTo(track.getDescription());
        assertThat(result.get().getPoints()).hasSize(track.getPoints().size());
    }

    @Test
    void testThatMultipleTracksCanBeCreatedAndRetrieved() {
        TrackEntity track1 = TestDataUtil.createTestTrack1();
        TrackEntity track2 = TestDataUtil.createTestTrack2();
        TrackEntity track3 = TestDataUtil.createTestTrack3();

        trackRepository.save(track1);
        trackRepository.save(track2);
        trackRepository.save(track3);
        Iterable<TrackEntity> result = trackRepository.findAll();

        assertThat(result).hasSize(3);

        // Also need to check that the result contains the 3 tracks.
        // Look at the TrackEntity & TrackPointEntity equals() & hashCode().
    }

    @Test
    void testThatTrackCanBeUpdated() {
        TrackEntity track = TestDataUtil.createTestTrack1();

        trackRepository.save(track);

        track.setName("New Name");
        track.setDescription("New Description");

        TrackPointEntity newTrackPoint1 = TestDataUtil.createTestTrackPoint(-37.000000000, 144.000000000);
        newTrackPoint1.setTrack(track);
        TrackPointEntity newTrackPoint2 = TestDataUtil.createTestTrackPoint(-37.500000000, 144.500000000);
        newTrackPoint2.setTrack(track);

        List<TrackPointEntity> newPoints = new ArrayList<>(List.of(newTrackPoint1, newTrackPoint2));
        track.setPoints(newPoints);

        trackRepository.save(track);

        Optional<TrackEntity> result = trackRepository.findById(track.getId());

        assertThat(result).isPresent();

        assertThat(result.get().getName()).isEqualTo(track.getName());
        assertThat(result.get().getDescription()).isEqualTo(track.getDescription());

        assertThat(result.get().getPoints()).hasSize(track.getPoints().size());

        // Also need to check that the result points contains the 2 new points.
        // Look at the TrackEntity & TrackPointEntity equals() & hashCode().
    }

    @Test
    void testThatTrackCanBeDeleted() {
        TrackEntity track = TestDataUtil.createTestTrack1();

        trackRepository.save(track);
        trackRepository.deleteById(track.getId());

        Optional<TrackEntity> result = trackRepository.findById(track.getId());

        assertThat(result).isEmpty();
    }
}
