package au.com.heal.martin.mapinfo.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import au.com.heal.martin.mapinfo.TestDataUtil;
import au.com.heal.martin.mapinfo.domain.entities.LocationEntity;
import au.com.heal.martin.mapinfo.domain.entities.LocationPointEntity;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LocationRepositoryIntegrationTests {

    private LocationRepository locationRepository;

    @Autowired
    public LocationRepositoryIntegrationTests(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Test
    void testThatLocationCanBeCreatedAndRetrieved() {
        LocationEntity location = TestDataUtil.createTestLocation1();

        locationRepository.save(location);
        Optional<LocationEntity> result = locationRepository.findById(location.getId());

        assertThat(result).isPresent();

        assertThat(result.get().getName()).isEqualTo(location.getName());
        assertThat(result.get().getDescription()).isEqualTo(location.getDescription());
        assertThat(result.get().getPoint().getLatitude()).isEqualTo(location.getPoint().getLatitude());
        assertThat(result.get().getPoint().getLongitude()).isEqualTo(location.getPoint().getLongitude());
    }

    @Test
    void testThatMultipleLocationsCanBeCreatedAndRetrieved() {
        LocationEntity location1 = TestDataUtil.createTestLocation1();
        LocationEntity location2 = TestDataUtil.createTestLocation2();
        LocationEntity location3 = TestDataUtil.createTestLocation3();

        locationRepository.save(location1);
        locationRepository.save(location2);
        locationRepository.save(location3);
        Iterable<LocationEntity> result = locationRepository.findAll();

        assertThat(result).hasSize(3).containsExactly(location1, location2, location3);
    }

    @Test
    void testThatLocationCanBeUpdated() {
        LocationEntity location = TestDataUtil.createTestLocation1();

        locationRepository.save(location);

        location.setName("New Name");
        location.setDescription("New Description");
        location.setPoint(LocationPointEntity.builder().latitude(-37.000000000).longitude(144.000000000).build());

        locationRepository.save(location);

        Optional<LocationEntity> result = locationRepository.findById(location.getId());

        assertThat(result).isPresent();

        assertThat(result.get().getName()).isEqualTo(location.getName());
        assertThat(result.get().getDescription()).isEqualTo(location.getDescription());
        assertThat(result.get().getPoint().getLatitude()).isEqualTo(location.getPoint().getLatitude());
        assertThat(result.get().getPoint().getLongitude()).isEqualTo(location.getPoint().getLongitude());
    }

    @Test
    void testThatLocationCanBeDeleted() {
        LocationEntity location = TestDataUtil.createTestLocation1();

        locationRepository.save(location);
        locationRepository.deleteById(location.getId());

        Optional<LocationEntity> result = locationRepository.findById(location.getId());

        assertThat(result).isEmpty();
    }
}
