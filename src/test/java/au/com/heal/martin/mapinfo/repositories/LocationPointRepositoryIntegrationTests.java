package au.com.heal.martin.mapinfo.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import au.com.heal.martin.mapinfo.TestDataUtil;
import au.com.heal.martin.mapinfo.domain.entities.LocationPointEntity;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LocationPointRepositoryIntegrationTests {

    private LocationPointRepository locationPointRepository;

    @Autowired
    public LocationPointRepositoryIntegrationTests(LocationPointRepository locationPointRepository) {
        this.locationPointRepository = locationPointRepository;
    }

    @Test
    void testThatPointCanBeCreatedAndRetrieved() {
        LocationPointEntity point = TestDataUtil.createTestLocationPoint(-37.814233512, 144.963138807);

        locationPointRepository.save(point);
        Optional<LocationPointEntity> result = locationPointRepository.findById(point.getId());

        assertThat(result).isPresent();

        assertThat(result.get().getLatitude()).isEqualTo(point.getLatitude());
        assertThat(result.get().getLongitude()).isEqualTo(point.getLongitude());
    }

    @Test
    void testThatMultiplePointsCanBeCreatedAndRetrieved() {
        LocationPointEntity point1 = TestDataUtil.createTestLocationPoint(-37.820640807, 144.941401533);
        LocationPointEntity point2 = TestDataUtil.createTestLocationPoint(-37.821407644, 144.945846615);
        LocationPointEntity point3 = TestDataUtil.createTestLocationPoint(-37.813321828, 144.973760363);

        locationPointRepository.save(point1);
        locationPointRepository.save(point2);
        locationPointRepository.save(point3);
        Iterable<LocationPointEntity> result = locationPointRepository.findAll();

        assertThat(result).hasSize(3).containsExactly(point1, point2, point3);
    }

    @Test
    void testThatPointCanBeUpdated() {
        LocationPointEntity point = TestDataUtil.createTestLocationPoint(-37.814233512, 144.963138807);

        locationPointRepository.save(point);

        point.setLatitude(-37.000000000);
        point.setLongitude(144.000000000);

        locationPointRepository.save(point);

        Optional<LocationPointEntity> result = locationPointRepository.findById(point.getId());

        assertThat(result).isPresent();

        assertThat(result.get().getLatitude()).isEqualTo(point.getLatitude());
        assertThat(result.get().getLongitude()).isEqualTo(point.getLongitude());
    }

    @Test
    void testThatPointCanBeDeleted() {
        LocationPointEntity point = TestDataUtil.createTestLocationPoint(-37.814233512, 144.963138807);

        locationPointRepository.save(point);
        locationPointRepository.deleteById(point.getId());

        Optional<LocationPointEntity> result = locationPointRepository.findById(point.getId());

        assertThat(result).isEmpty();
    }
}
