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
import au.com.heal.martin.mapinfo.domain.dto.TrackDto;
import au.com.heal.martin.mapinfo.domain.entities.TrackEntity;
import au.com.heal.martin.mapinfo.mappers.Mapper;
import au.com.heal.martin.mapinfo.services.TrackService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class TrackControllerIntegrationTests {

    private MockMvc mockMvc;
    private TrackService trackService;
    private Mapper<TrackEntity, TrackDto> trackMapper;
    private ObjectMapper objectMapper;

    @Autowired
    public TrackControllerIntegrationTests(MockMvc mockMvc, TrackService trackService, Mapper<TrackEntity, TrackDto> trackMapper) {
        this.mockMvc = mockMvc;
        this.trackService = trackService;
        this.trackMapper = trackMapper;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateTrackSuccessfullyReturnsHttpStatus201() throws Exception {
        TrackEntity trackEntity = TestDataUtil.createTestTrack1();
        trackEntity.setId(null);
        String trackJson = objectMapper.writeValueAsString(trackEntity);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/tracks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(trackJson)
        ).andExpect(
            MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateTrackSuccessfullyReturnsSavedTrack() throws Exception {
        TrackEntity trackEntity = TestDataUtil.createTestTrack1();
        trackEntity.setId(null);

        String trackJson = objectMapper.writeValueAsString(trackEntity);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/tracks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(trackJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value("Collins St")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.description").value("Collins Street Melbourne.")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.points.length()").value(3)
        );
    }

    @Test
    public void testThatReadAllTracksReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/tracks")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatReadAllTracksReturnsListOfTracks() throws Exception {
        TrackEntity trackEntity = TestDataUtil.createTestTrack1();
        trackEntity.setId(null);

        TrackDto track = trackMapper.mapTo(trackEntity);
        trackService.createTrack(track);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/tracks")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].id").isNumber()
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].name").value("Collins St")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].description").value("Collins Street Melbourne.")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].points.length()").value(3)
        );
    }

    @Test
    public void testThatReadOneTrackReturnsHttpStatus200WhenTrackExists() throws Exception {
        TrackEntity trackEntity = TestDataUtil.createTestTrack1();
        trackEntity.setId(null);

        TrackDto track = trackMapper.mapTo(trackEntity);
        trackService.createTrack(track);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/tracks/1")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatReadOneTrackReturnsHttpStatus404WhenTrackDoesNotExist() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/tracks/99")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatReadOneTrackReturnsTrackWhenTrackExists() throws Exception {
        TrackEntity trackEntity = TestDataUtil.createTestTrack1();
        trackEntity.setId(null);

        TrackDto track = trackMapper.mapTo(trackEntity);
        trackService.createTrack(track);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/tracks/1")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value("Collins St")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.description").value("Collins Street Melbourne.")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.points.length()").value(3)
        );
    }

    @Test
    public void testThatUpdateFullTrackReturnsHttpStatus200WhenTrackExists() throws Exception {
        TrackEntity trackEntity = TestDataUtil.createTestTrack1();
        trackEntity.setId(null);

        TrackDto trackDto = trackMapper.mapTo(trackEntity);

        TrackDto savedTrack = trackService.createTrack(trackDto);

        String savedTrackJson = objectMapper.writeValueAsString(savedTrack);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/tracks/" + savedTrack.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(savedTrackJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatUpdateFullTrackReturnsHttpStatus404WhenTrackDoesNotExist() throws Exception {
        TrackEntity trackEntity = TestDataUtil.createTestTrack1();
        trackEntity.setId(null);

        TrackDto trackDto = trackMapper.mapTo(trackEntity);
        String trackDtoJson = objectMapper.writeValueAsString(trackDto);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/tracks/99")
            .contentType(MediaType.APPLICATION_JSON)
            .content(trackDtoJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatUpdateFullTrackReturnsUpdatedTrackWhenTrackExists() throws Exception {
        TrackEntity trackEntity = TestDataUtil.createTestTrack1();
        trackEntity.setId(null);

        TrackDto track = trackMapper.mapTo(trackEntity);
        TrackDto savedTrack = trackService.createTrack(track);

        TrackEntity updateTrackEntity = TestDataUtil.createTestTrack2();
        TrackDto updateTrackDto = trackMapper.mapTo(updateTrackEntity);
        updateTrackDto.setId(savedTrack.getId());
        
        String updateTrackDtoJson = objectMapper.writeValueAsString(updateTrackDto);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/tracks/" + savedTrack.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(updateTrackDtoJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").value(savedTrack.getId())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value(updateTrackDto.getName())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.description").value(updateTrackDto.getDescription())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.points.length()").value(4)
        );
    }

    @Test
    public void testThatUpdatePartialTrackNameReturnsHttpStatus200WhenTrackExists() throws Exception {
        TrackEntity trackEntity = TestDataUtil.createTestTrack1();
        trackEntity.setId(null);

        TrackDto trackDto = trackMapper.mapTo(trackEntity);

        TrackDto savedTrack = trackService.createTrack(trackDto);
        savedTrack.setName("UPDATED");

        String savedTrackJson = objectMapper.writeValueAsString(savedTrack);

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/tracks/" + savedTrack.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(savedTrackJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatUpdatePartialTrackNameReturnsUpdatedTrackWhenTrackExists() throws Exception {
        TrackEntity trackEntity = TestDataUtil.createTestTrack1();
        trackEntity.setId(null);

        TrackDto trackDto = trackMapper.mapTo(trackEntity);

        TrackDto savedTrack = trackService.createTrack(trackDto);
        savedTrack.setName("UPDATED");

        String savedTrackJson = objectMapper.writeValueAsString(savedTrack);

        mockMvc.perform(
            MockMvcRequestBuilders.patch("/tracks/" + savedTrack.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(savedTrackJson)
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.id").value(savedTrack.getId())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.description").value(savedTrack.getDescription())
        ).andExpect(
            MockMvcResultMatchers.jsonPath("$.points.length()").value(3)
        );
    }

    @Test
    public void testThatDeleteTrackReturnsHttpStatus204WhenTrackDoesNotExist() throws Exception {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/tracks/1")
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteTrackReturnsHttpStatus204WhenTrackExists() throws Exception {
        TrackEntity trackEntity = TestDataUtil.createTestTrack1();
        trackEntity.setId(null);

        TrackDto trackDto = trackMapper.mapTo(trackEntity);

        TrackDto savedTrack = trackService.createTrack(trackDto);

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/tracks/" + savedTrack.getId())
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
