package au.com.heal.martin.mapinfo.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import au.com.heal.martin.mapinfo.TestDataUtil;
import au.com.heal.martin.mapinfo.domain.TrackEntity;
import au.com.heal.martin.mapinfo.domain.TrackPointEntity;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TrackRepositoryIntegrationTests {

    private TrackRepository trackRepository;

    @Autowired
    public TrackRepositoryIntegrationTests(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Test
    public void testThatTrackCanBeCreatedAndRetrieved() {
        TrackEntity track = TestDataUtil.createTestTrack1();

        trackRepository.save(track);
        Optional<TrackEntity> result = trackRepository.findById(track.getId());

        assertThat(result).isPresent();

        assertThat(result.get().getName()).isEqualTo(track.getName());
        assertThat(result.get().getDescription()).isEqualTo(track.getDescription());

        assertThat(result.get().getPoints().equals(track.getPoints()));
    }

    @Test
    public void testThatMultipleTracksCanBeCreatedAndRetrieved() {
        TrackEntity track1 = TestDataUtil.createTestTrack1();
        TrackEntity track2 = TestDataUtil.createTestTrack2();
        TrackEntity track3 = TestDataUtil.createTestTrack3();

        trackRepository.save(track1);
        trackRepository.save(track2);
        trackRepository.save(track3);
        Iterable<TrackEntity> result = trackRepository.findAll();

        assertThat(result).hasSize(3);
        List<TrackEntity> resultTracks = StreamSupport.stream(result.spliterator(), false).collect(Collectors.toList());
        assertThat(resultTracks.equals(List.of(track1, track2, track3)));
    }

    @Test
    public void testThatTrackCanBeUpdated() {
        TrackEntity track = TestDataUtil.createTestTrack1();

        trackRepository.save(track);

        track.setName("New Name");
        track.setDescription("New Description");

        List<TrackPointEntity> newPoints = new ArrayList<>();
        newPoints.add(TestDataUtil.createTestTrackPoint(-37.000000000, 144.000000000));
        newPoints.add(TestDataUtil.createTestTrackPoint(-37.500000000, 144.500000000));
        track.setPoints(newPoints);

        trackRepository.save(track);

        Optional<TrackEntity> result = trackRepository.findById(track.getId());

        assertThat(result).isPresent();

        assertThat(result.get().getName()).isEqualTo(track.getName());
        assertThat(result.get().getDescription()).isEqualTo(track.getDescription());

        assertThat(result.get().getPoints().equals(track.getPoints()));
    }

    @Test
    public void testThatTrackCanBeDeleted() {
        TrackEntity track = TestDataUtil.createTestTrack1();

        trackRepository.save(track);
        trackRepository.deleteById(track.getId());

        Optional<TrackEntity> result = trackRepository.findById(track.getId());

        assertThat(result).isEmpty();
    }
}
