package au.com.heal.martin.mapinfo.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import au.com.heal.martin.mapinfo.TestDataUtil;
import au.com.heal.martin.mapinfo.domain.dto.AreaDto;
import au.com.heal.martin.mapinfo.domain.entities.AreaEntity;
import au.com.heal.martin.mapinfo.mappers.Mapper;
import au.com.heal.martin.mapinfo.services.AreaService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AreaControllerIntegrationTests {

    private MockMvc mockMvc;
    private AreaService areaService;
    private Mapper<AreaEntity, AreaDto> areaMapper;
    private ObjectMapper objectMapper;

    @Autowired
    public AreaControllerIntegrationTests(MockMvc mockMvc, AreaService areaService, Mapper<AreaEntity, AreaDto> areaMapper) {
        this.mockMvc = mockMvc;
        this.areaService = areaService;
        this.areaMapper = areaMapper;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateAreaSuccessfullyReturnsHttpStatus201() throws Exception {
        AreaEntity areaEntity = TestDataUtil.createTestArea1();
        areaEntity.setId(null);
        String areaJson = objectMapper.writeValueAsString(areaEntity);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/areas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(areaJson)
        ).andExpect(
            MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAreaSuccessfullyReturnsSavedArea() throws Exception {
        AreaEntity areaEntity = TestDataUtil.createTestArea1();
        areaEntity.setId(null);

        String areaJson = objectMapper.writeValueAsString(areaEntity);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/areas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(areaJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value("Melbourne CBD")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.description").value("Melbourne CBD business district.")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.points.length()").value(4)
        );
    }

    @Test
    public void testThatReadAllAreasReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/areas")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatReadAllAreasReturnsListOfAreas() throws Exception {
        AreaEntity areaEntity = TestDataUtil.createTestArea1();
        areaEntity.setId(null);

        AreaDto area = areaMapper.mapTo(areaEntity);
        areaService.createArea(area);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/areas")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].name").value("Melbourne CBD")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].description").value("Melbourne CBD business district.")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].points.length()").value(4)
        );
    }

    @Test
    public void testThatReadOneAreaReturnsHttpStatus200WhenAreaExists() throws Exception {
        AreaEntity areaEntity = TestDataUtil.createTestArea1();
        areaEntity.setId(null);

        AreaDto area = areaMapper.mapTo(areaEntity);
        areaService.createArea(area);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/areas/1")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatReadOneAreaReturnsHttpStatus404WhenAreaDoesNotExist() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/areas/99")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatReadOneAreaReturnsAreaWhenAreaExists() throws Exception {
        AreaEntity areaEntity = TestDataUtil.createTestArea1();
        areaEntity.setId(null);

        AreaDto area = areaMapper.mapTo(areaEntity);
        areaService.createArea(area);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/areas/1")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value("Melbourne CBD")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.description").value("Melbourne CBD business district.")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.points.length()").value(4)
        );
    }

    @Test
    public void testThatUpdateFullAreaReturnsHttpStatus200WhenAreaExists() throws Exception {
        AreaEntity areaEntity = TestDataUtil.createTestArea1();
        areaEntity.setId(null);

        AreaDto areaDto = areaMapper.mapTo(areaEntity);

        AreaDto savedArea = areaService.createArea(areaDto);

        String savedAreaJson = objectMapper.writeValueAsString(savedArea);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/areas/" + savedArea.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(savedAreaJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatUpdateFullAreaReturnsHttpStatus404WhenAreaDoesNotExist() throws Exception {
        AreaEntity areaEntity = TestDataUtil.createTestArea1();
        areaEntity.setId(null);

        AreaDto areaDto = areaMapper.mapTo(areaEntity);
        String areaDtoJson = objectMapper.writeValueAsString(areaDto);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/areas/99")
            .contentType(MediaType.APPLICATION_JSON)
            .content(areaDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatUpdateFullAreaReturnsUpdatedAreaWhenAreaExists() throws Exception {
        AreaEntity areaEntity = TestDataUtil.createTestArea1();
        areaEntity.setId(null);

        AreaDto area = areaMapper.mapTo(areaEntity);
        AreaDto savedArea = areaService.createArea(area);

        AreaEntity updateAreaEntity = TestDataUtil.createTestArea2();
        AreaDto updateAreaDto = areaMapper.mapTo(updateAreaEntity);
        updateAreaDto.setId(savedArea.getId());
        
        String updateAreaDtoJson = objectMapper.writeValueAsString(updateAreaDto);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/areas/" + savedArea.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(updateAreaDtoJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").value(savedArea.getId())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value(updateAreaDto.getName())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.description").value(updateAreaDto.getDescription())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.points.length()").value(6)
        );
    }

    @Test
    public void testThatUpdatePartialAreaNameReturnsHttpStatus200WhenAreaExists() throws Exception {
        AreaEntity areaEntity = TestDataUtil.createTestArea1();
        areaEntity.setId(null);

        AreaDto areaDto = areaMapper.mapTo(areaEntity);

        AreaDto savedArea = areaService.createArea(areaDto);
        savedArea.setName("UPDATED");

        String savedAreaJson = objectMapper.writeValueAsString(savedArea);

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/areas/" + savedArea.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(savedAreaJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatUpdatePartialAreaNameReturnsUpdatedAreaWhenAreaExists() throws Exception {
        AreaEntity areaEntity = TestDataUtil.createTestArea1();
        areaEntity.setId(null);

        AreaDto areaDto = areaMapper.mapTo(areaEntity);

        AreaDto savedArea = areaService.createArea(areaDto);
        savedArea.setName("UPDATED");

        String savedAreaJson = objectMapper.writeValueAsString(savedArea);

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/areas/" + savedArea.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(savedAreaJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").value(savedArea.getId())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.description").value(savedArea.getDescription())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.points.length()").value(4)
        );
    }

    @Test
    public void testThatDeleteAreaReturnsHttpStatus204WhenAreaDoesNotExist() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/areas/1")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteAreaReturnsHttpStatus204WhenAreaExists() throws Exception {
        AreaEntity areaEntity = TestDataUtil.createTestArea1();
        areaEntity.setId(null);

        AreaDto areaDto = areaMapper.mapTo(areaEntity);

        AreaDto savedArea = areaService.createArea(areaDto);

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/areas/" + savedArea.getId())
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
