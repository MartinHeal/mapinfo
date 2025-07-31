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
import au.com.heal.martin.mapinfo.domain.AreaEntity;
import au.com.heal.martin.mapinfo.domain.AreaPointEntity;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AreaRepositoryIntegrationTests {

    private AreaRepository areaRepository;

    @Autowired
    public AreaRepositoryIntegrationTests(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    @Test
    public void testThatAreaCanBeCreatedAndRetrieved() {
        AreaEntity area = TestDataUtil.createTestArea1();

        areaRepository.save(area);
        Optional<AreaEntity> result = areaRepository.findById(area.getId());

        assertThat(result).isPresent();

        assertThat(result.get().getName()).isEqualTo(area.getName());
        assertThat(result.get().getDescription()).isEqualTo(area.getDescription());

        assertThat(result.get().getPoints().equals(area.getPoints()));
    }

    @Test
    public void testThatMultipleAreasCanBeCreatedAndRetrieved() {
        AreaEntity area1 = TestDataUtil.createTestArea1();
        AreaEntity area2 = TestDataUtil.createTestArea2();
        AreaEntity area3 = TestDataUtil.createTestArea3();

        areaRepository.save(area1);
        areaRepository.save(area2);
        areaRepository.save(area3);
        Iterable<AreaEntity> result = areaRepository.findAll();

        assertThat(result).hasSize(3);
        List<AreaEntity> resultAreas = StreamSupport.stream(result.spliterator(), false).collect(Collectors.toList());
        assertThat(resultAreas.equals(List.of(area1, area2, area3)));
    }

    @Test
    public void testThatAreaCanBeUpdated() {
        AreaEntity area = TestDataUtil.createTestArea1();

        areaRepository.save(area);

        area.setName("New Name");
        area.setDescription("New Description");

        List<AreaPointEntity> newPoints = new ArrayList<>();
        newPoints.add(TestDataUtil.createTestAreaPoint(-37.000000000, 144.000000000));
        newPoints.add(TestDataUtil.createTestAreaPoint(-37.500000000, 144.000000000));
        newPoints.add(TestDataUtil.createTestAreaPoint(-37.500000000, 144.500000000));
        newPoints.add(TestDataUtil.createTestAreaPoint(-37.000000000, 144.500000000));
        area.setPoints(newPoints);

        areaRepository.save(area);

        Optional<AreaEntity> result = areaRepository.findById(area.getId());

        assertThat(result).isPresent();

        assertThat(result.get().getName()).isEqualTo(area.getName());
        assertThat(result.get().getDescription()).isEqualTo(area.getDescription());

        assertThat(result.get().getPoints().equals(area.getPoints()));
    }

    @Test
    public void testThatAreaCanBeDeleted() {
        AreaEntity area = TestDataUtil.createTestArea1();

        areaRepository.save(area);
        areaRepository.deleteById(area.getId());

        Optional<AreaEntity> result = areaRepository.findById(area.getId());

        assertThat(result).isEmpty();
    }
}
