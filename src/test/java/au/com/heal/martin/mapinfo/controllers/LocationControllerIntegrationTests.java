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
import au.com.heal.martin.mapinfo.domain.dto.LocationDto;
import au.com.heal.martin.mapinfo.domain.entities.LocationEntity;
import au.com.heal.martin.mapinfo.mappers.Mapper;
import au.com.heal.martin.mapinfo.services.LocationService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class LocationControllerIntegrationTests {

    private MockMvc mockMvc;
    private LocationService locationService;
    private Mapper<LocationEntity, LocationDto> locationMapper;
    private ObjectMapper objectMapper;

    @Autowired
    public LocationControllerIntegrationTests(MockMvc mockMvc, LocationService locationService, Mapper<LocationEntity, LocationDto> locationMapper) {
        this.mockMvc = mockMvc;
        this.locationService = locationService;
        this.locationMapper = locationMapper;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateLocationSuccessfullyReturnsHttpStatus201() throws Exception {
        LocationEntity locationEntity = TestDataUtil.createTestLocation1();
        locationEntity.setId(null);
        String locationJson = objectMapper.writeValueAsString(locationEntity);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(locationJson)
        ).andExpect(
            MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateLocationSuccessfullyReturnsSavedLocation() throws Exception {
        LocationEntity locationEntity = TestDataUtil.createTestLocation1();
        locationEntity.setId(null);

        String locationJson = objectMapper.writeValueAsString(locationEntity);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(locationJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value("Melbourne")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.description").value("Melbourne, Victoria, Australia.")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.point.id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.point.latitude").value(-37.814233512)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.point.longitude").value(144.963138807)
        );
    }

    @Test
    public void testThatReadAllLocationsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/locations")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatReadAllLocationsReturnsListOfLocations() throws Exception {
        LocationEntity locationEntity = TestDataUtil.createTestLocation1();
        locationEntity.setId(null);

        LocationDto location = locationMapper.mapTo(locationEntity);
        locationService.createLocation(location);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/locations")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].name").value("Melbourne")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].description").value("Melbourne, Victoria, Australia.")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].point.id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].point.latitude").value(-37.814233512)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].point.longitude").value(144.963138807)
        );
    }

    @Test
    public void testThatReadOneLocationReturnsHttpStatus200WhenLocationExists() throws Exception {
        LocationEntity locationEntity = TestDataUtil.createTestLocation1();
        locationEntity.setId(null);

        LocationDto location = locationMapper.mapTo(locationEntity);
        locationService.createLocation(location);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/locations/1")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatReadOneLocationReturnsHttpStatus404WhenLocationDoesNotExist() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/locations/99")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatReadOneLocationReturnsLocationWhenLocationExists() throws Exception {
        LocationEntity locationEntity = TestDataUtil.createTestLocation1();
        locationEntity.setId(null);

        LocationDto location = locationMapper.mapTo(locationEntity);
        locationService.createLocation(location);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/locations/1")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value("Melbourne")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.description").value("Melbourne, Victoria, Australia.")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.point.id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.point.latitude").value(-37.814233512)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.point.longitude").value(144.963138807)
        );
    }

    @Test
    public void testThatUpdateFullLocationReturnsHttpStatus200WhenLocationExists() throws Exception {
        LocationEntity locationEntity = TestDataUtil.createTestLocation1();
        locationEntity.setId(null);

        LocationDto locationDto = locationMapper.mapTo(locationEntity);

        LocationDto savedLocation = locationService.createLocation(locationDto);

        String savedLocationJson = objectMapper.writeValueAsString(savedLocation);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/locations/" + savedLocation.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(savedLocationJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatUpdateFullLocationReturnsHttpStatus404WhenLocationDoesNotExist() throws Exception {
        LocationEntity locationEntity = TestDataUtil.createTestLocation1();
        locationEntity.setId(null);

        LocationDto locationDto = locationMapper.mapTo(locationEntity);
        String locationDtoJson = objectMapper.writeValueAsString(locationDto);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/locations/99")
            .contentType(MediaType.APPLICATION_JSON)
            .content(locationDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatUpdateFullLocationReturnsUpdatedLocationWhenLocationExists() throws Exception {
        LocationEntity locationEntity = TestDataUtil.createTestLocation1();
        locationEntity.setId(null);

        LocationDto location = locationMapper.mapTo(locationEntity);
        LocationDto savedLocation = locationService.createLocation(location);

        LocationEntity updateLocationEntity = TestDataUtil.createTestLocation2();
        LocationDto updateLocationDto = locationMapper.mapTo(updateLocationEntity);
        updateLocationDto.setId(savedLocation.getId());
        
        String updateLocationDtoJson = objectMapper.writeValueAsString(updateLocationDto);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/locations/" + savedLocation.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(updateLocationDtoJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").value(savedLocation.getId())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value(updateLocationDto.getName())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.description").value(updateLocationDto.getDescription())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.point.id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.point.latitude").value(updateLocationDto.getPoint().getLatitude())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.point.longitude").value(updateLocationDto.getPoint().getLongitude())
        );
    }

    @Test
    public void testThatUpdatePartialLocationNameReturnsHttpStatus200WhenLocationExists() throws Exception {
        LocationEntity locationEntity = TestDataUtil.createTestLocation1();
        locationEntity.setId(null);

        LocationDto locationDto = locationMapper.mapTo(locationEntity);

        LocationDto savedLocation = locationService.createLocation(locationDto);
        savedLocation.setName("UPDATED");

        String savedLocationJson = objectMapper.writeValueAsString(savedLocation);

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/locations/" + savedLocation.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(savedLocationJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatUpdatePartialLocationNameReturnsUpdatedLocationWhenLocationExists() throws Exception {
        LocationEntity locationEntity = TestDataUtil.createTestLocation1();
        locationEntity.setId(null);

        LocationDto locationDto = locationMapper.mapTo(locationEntity);

        LocationDto savedLocation = locationService.createLocation(locationDto);
        savedLocation.setName("UPDATED");

        String savedLocationJson = objectMapper.writeValueAsString(savedLocation);

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/locations/" + savedLocation.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(savedLocationJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").value(savedLocation.getId())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.description").value(savedLocation.getDescription())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.point.id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.point.latitude").value(savedLocation.getPoint().getLatitude())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.point.longitude").value(savedLocation.getPoint().getLongitude())
        );
    }

    @Test
    public void testThatDeleteLocationReturnsHttpStatus204WhenLocationDoesNotExist() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/locations/1")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteLocationReturnsHttpStatus204WhenLocationExists() throws Exception {
        LocationEntity locationEntity = TestDataUtil.createTestLocation1();
        locationEntity.setId(null);

        LocationDto locationDto = locationMapper.mapTo(locationEntity);

        LocationDto savedLocation = locationService.createLocation(locationDto);

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/locations/" + savedLocation.getId())
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
