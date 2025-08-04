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
import au.com.heal.martin.mapinfo.domain.entities.AreaEntity;
import au.com.heal.martin.mapinfo.domain.entities.AreaPointEntity;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AreaRepositoryIntegrationTests {

    private AreaRepository areaRepository;

    @Autowired
    public AreaRepositoryIntegrationTests(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @Test
    void testThatAreaCanBeCreatedAndRetrieved() {
        AreaEntity area = TestDataUtil.createTestArea1();

        areaRepository.save(area);
        Optional<AreaEntity> result = areaRepository.findById(area.getId());

        assertThat(result).isPresent();

        assertThat(result.get().getName()).isEqualTo(area.getName());
        assertThat(result.get().getDescription()).isEqualTo(area.getDescription());
        assertThat(result.get().getPoints()).hasSize(area.getPoints().size());
    }

    @Test
    void testThatMultipleAreasCanBeCreatedAndRetrieved() {
        AreaEntity area1 = TestDataUtil.createTestArea1();
        AreaEntity area2 = TestDataUtil.createTestArea2();
        AreaEntity area3 = TestDataUtil.createTestArea3();

        areaRepository.save(area1);
        areaRepository.save(area2);
        areaRepository.save(area3);
        Iterable<AreaEntity> result = areaRepository.findAll();

        assertThat(result).hasSize(3);

        // Also need to check that the result contains the 3 areas.
        // Look at the AreaEntity & AreaPointEntity equals() & hashCode().
    }

    @Test
    void testThatAreaCanBeUpdated() {
        AreaEntity area = TestDataUtil.createTestArea1();

        areaRepository.save(area);

        area.setName("New Name");
        area.setDescription("New Description");

        AreaPointEntity newAreaPoint1 = TestDataUtil.createTestAreaPoint(-37.000000000, 144.000000000);
        newAreaPoint1.setArea(area);
        AreaPointEntity newAreaPoint2 = TestDataUtil.createTestAreaPoint(-37.500000000, 144.000000000);
        newAreaPoint2.setArea(area);
        AreaPointEntity newAreaPoint3 = TestDataUtil.createTestAreaPoint(-37.500000000, 144.500000000);
        newAreaPoint3.setArea(area);
        AreaPointEntity newAreaPoint4 = TestDataUtil.createTestAreaPoint(-37.000000000, 144.500000000);
        newAreaPoint4.setArea(area);

        List<AreaPointEntity> newPoints = new ArrayList<>(List.of(newAreaPoint1, newAreaPoint2, newAreaPoint3, newAreaPoint4));
        area.setPoints(newPoints);

        areaRepository.save(area);

        Optional<AreaEntity> result = areaRepository.findById(area.getId());

        assertThat(result).isPresent();

        assertThat(result.get().getName()).isEqualTo(area.getName());
        assertThat(result.get().getDescription()).isEqualTo(area.getDescription());

        assertThat(result.get().getPoints()).hasSize(area.getPoints().size());

        // Also need to check that the result points contains the 4 new points.
        // Look at the AreaEntity & AreaPointEntity equals() & hashCode().
    }

    @Test
    void testThatAreaCanBeDeleted() {
        AreaEntity area = TestDataUtil.createTestArea1();

        areaRepository.save(area);
        areaRepository.deleteById(area.getId());

        Optional<AreaEntity> result = areaRepository.findById(area.getId());

        assertThat(result).isEmpty();
    }
}
