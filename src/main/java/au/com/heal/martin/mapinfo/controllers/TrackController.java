package au.com.heal.martin.mapinfo.controllers;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import au.com.heal.martin.mapinfo.domain.dto.TrackDto;
import au.com.heal.martin.mapinfo.services.TrackService;
import lombok.extern.java.Log;

@RestController
@Log
public class TrackController {

    private TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @PostMapping(path = "/tracks")
    public ResponseEntity<TrackDto> createTrack(@RequestBody TrackDto track) {
        log.info("Create track.");

        return new ResponseEntity<>(trackService.createTrack(track), HttpStatus.CREATED);
    }

    @GetMapping(path = "/tracks/{id}")
    public ResponseEntity<TrackDto> readOneTrack(@PathVariable("id") Long id) {
        log.info("Read one track.");

        Optional<TrackDto> foundTrack = trackService.readOneTrack(id);

        return foundTrack.map(trackDto -> new ResponseEntity<>(trackDto, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/tracks")
    public PagedModel<TrackDto> readAllTrack(Pageable pageable) {
        log.info("Read all tracks.");

        Page<TrackDto> tracks = trackService.readAllTracks(pageable);

        return new PagedModel<>(tracks);
    }

    @PutMapping(path = "/tracks/{id}")
    public ResponseEntity<TrackDto> updateFullTrack(@PathVariable("id") Long id, @RequestBody TrackDto trackDto) {
        log.info("Update full track.");
        
        if(!trackService.ifExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        TrackDto savedTrackDto = trackService.updateFullTrack(id, trackDto);

        return new ResponseEntity<>(savedTrackDto, HttpStatus.OK);
    }

    // Partial update of name and description only.
    // If the track points need to be updated then a full update is required.
    @PatchMapping(path = "/tracks/{id}")
    public ResponseEntity<TrackDto> updatePartialTrack(@PathVariable("id") Long id, @RequestBody TrackDto trackDto) {
        log.info("Update partial track.");
        
        if(!trackService.ifExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TrackDto updatedTrackDto = trackService.updatePartialTrack(id, trackDto);

        return new ResponseEntity<>(updatedTrackDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "tracks/{id}")
    public ResponseEntity<HttpStatus> deleteTrack(@PathVariable("id") Long id) {
        log.info("Delete track.");

        trackService.deleteTrack(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
