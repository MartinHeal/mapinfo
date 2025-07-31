package au.com.heal.martin.mapinfo.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import au.com.heal.martin.mapinfo.TestDataUtil;
import au.com.heal.martin.mapinfo.domain.TrackPointEntity;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TrackPointRepositoryIntegrationTests {

    private TrackPointRepository trackPointRepository;

    @Autowired
    public TrackPointRepositoryIntegrationTests(TrackPointRepository trackPointRepository) {
        this.trackPointRepository = trackPointRepository;
    }

    @Test
    public void testThatPointCanBeCreatedAndRetrieved() {
        TrackPointEntity point = TestDataUtil.createTestTrackPoint(-37.814233512, 144.963138807);

        trackPointRepository.save(point);
        Optional<TrackPointEntity> result = trackPointRepository.findById(point.getId());

        assertThat(result).isPresent();

        assertThat(result.get().getLatitude()).isEqualTo(point.getLatitude());
        assertThat(result.get().getLongitude()).isEqualTo(point.getLongitude());
    }

    @Test
    public void testThatMultiplePointsCanBeCreatedAndRetrieved() {
        TrackPointEntity point1 = TestDataUtil.createTestTrackPoint(-37.820640807, 144.941401533);
        TrackPointEntity point2 = TestDataUtil.createTestTrackPoint(-37.821407644, 144.945846615);
        TrackPointEntity point3 = TestDataUtil.createTestTrackPoint(-37.813321828, 144.973760363);

        trackPointRepository.save(point1);
        trackPointRepository.save(point2);
        trackPointRepository.save(point3);
        Iterable<TrackPointEntity> result = trackPointRepository.findAll();

        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(point1, point2, point3);
    }

    @Test
    public void testThatPointCanBeUpdated() {
        TrackPointEntity point = TestDataUtil.createTestTrackPoint(-37.814233512, 144.963138807);

        trackPointRepository.save(point);

        point.setLatitude(-37.000000000);
        point.setLongitude(144.000000000);

        trackPointRepository.save(point);

        Optional<TrackPointEntity> result = trackPointRepository.findById(point.getId());

        assertThat(result).isPresent();

        assertThat(result.get().getLatitude()).isEqualTo(point.getLatitude());
        assertThat(result.get().getLongitude()).isEqualTo(point.getLongitude());
    }

    @Test
    public void testThatPointCanBeDeleted() {
        TrackPointEntity point = TestDataUtil.createTestTrackPoint(-37.814233512, 144.963138807);

        trackPointRepository.save(point);
        trackPointRepository.deleteById(point.getId());

        Optional<TrackPointEntity> result = trackPointRepository.findById(point.getId());

        assertThat(result).isEmpty();
    }
}

