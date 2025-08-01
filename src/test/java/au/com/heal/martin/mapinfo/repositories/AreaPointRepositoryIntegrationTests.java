package au.com.heal.martin.mapinfo.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import au.com.heal.martin.mapinfo.TestDataUtil;
import au.com.heal.martin.mapinfo.domain.entities.AreaPointEntity;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AreaPointRepositoryIntegrationTests {

    private AreaPointRepository areaPointRepository;

    @Autowired
    public AreaPointRepositoryIntegrationTests(AreaPointRepository areaPointRepository) {
        this.areaPointRepository = areaPointRepository;
    }

    @Test
    public void testThatPointCanBeCreatedAndRetrieved() {
        AreaPointEntity point = TestDataUtil.createTestAreaPoint(-37.814233512, 144.963138807);

        areaPointRepository.save(point);
        Optional<AreaPointEntity> result = areaPointRepository.findById(point.getId());

        assertThat(result).isPresent();

        assertThat(result.get().getLatitude()).isEqualTo(point.getLatitude());
        assertThat(result.get().getLongitude()).isEqualTo(point.getLongitude());
    }

    @Test
    public void testThatMultiplePointsCanBeCreatedAndRetrieved() {
        AreaPointEntity point1 = TestDataUtil.createTestAreaPoint(-37.820640807, 144.941401533);
        AreaPointEntity point2 = TestDataUtil.createTestAreaPoint(-37.821407644, 144.945846615);
        AreaPointEntity point3 = TestDataUtil.createTestAreaPoint(-37.813321828, 144.973760363);

        areaPointRepository.save(point1);
        areaPointRepository.save(point2);
        areaPointRepository.save(point3);
        Iterable<AreaPointEntity> result = areaPointRepository.findAll();

        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(point1, point2, point3);
    }

    @Test
    public void testThatPointCanBeUpdated() {
        AreaPointEntity point = TestDataUtil.createTestAreaPoint(-37.814233512, 144.963138807);

        areaPointRepository.save(point);

        point.setLatitude(-37.000000000);
        point.setLongitude(144.000000000);

        areaPointRepository.save(point);

        Optional<AreaPointEntity> result = areaPointRepository.findById(point.getId());

        assertThat(result).isPresent();

        assertThat(result.get().getLatitude()).isEqualTo(point.getLatitude());
        assertThat(result.get().getLongitude()).isEqualTo(point.getLongitude());
    }

    @Test
    public void testThatPointCanBeDeleted() {
        AreaPointEntity point = TestDataUtil.createTestAreaPoint(-37.814233512, 144.963138807);

        areaPointRepository.save(point);
        areaPointRepository.deleteById(point.getId());

        Optional<AreaPointEntity> result = areaPointRepository.findById(point.getId());

        assertThat(result).isEmpty();
    }
}
