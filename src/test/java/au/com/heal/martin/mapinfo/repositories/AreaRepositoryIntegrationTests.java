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

        AreaEntity savedArea1 = areaRepository.save(area1);
        AreaEntity savedArea2 = areaRepository.save(area2);
        AreaEntity savedArea3 = areaRepository.save(area3);
        Iterable<AreaEntity> result = areaRepository.findAll();

        assertThat(result).hasSize(3).containsExactly(savedArea1, savedArea2, savedArea3);
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

        AreaEntity savedArea = areaRepository.save(area);

        Optional<AreaEntity> result = areaRepository.findById(area.getId());

        assertThat(result).isPresent();

        assertThat(result.get().getName()).isEqualTo(area.getName());
        assertThat(result.get().getDescription()).isEqualTo(area.getDescription());

        assertThat(result.get().getPoints()).hasSize(area.getPoints().size())
            .containsAll(savedArea.getPoints());
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
