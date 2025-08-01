package au.com.heal.martin.mapinfo.controllers;

import org.springframework.web.bind.annotation.RestController;

import au.com.heal.martin.mapinfo.domain.dto.LocationDto;
import au.com.heal.martin.mapinfo.services.LocationService;
import lombok.extern.java.Log;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@Log
public class LocationController {

    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping(path = "/locations")
    public ResponseEntity<LocationDto> createLocation(@RequestBody LocationDto location) {
        log.info("Create location.");

        return new ResponseEntity<LocationDto>(locationService.createLocation(location), HttpStatus.CREATED);
    }

    @GetMapping(path = "/locations/{id}")
    public ResponseEntity<LocationDto> readOneLocation(@PathVariable("id") Long id) {
        log.info("Read one location.");

        Optional<LocationDto> foundLocation = locationService.readOneLocation(id);

        return foundLocation.map(locationDto -> {
                return new ResponseEntity<>(locationDto, HttpStatus.OK);
            })
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/locations")
    public PagedModel<LocationDto> readAllLocations(Pageable pageable) {
        log.info("Read all locations.");

        Page<LocationDto> locations = locationService.readAllLocations(pageable);

        return new PagedModel<>(locations);
    }

    @PutMapping(path = "/locations/{id}")
    public ResponseEntity<LocationDto> updateFullLocation(@PathVariable("id") Long id, @RequestBody LocationDto locationDto) {
        log.info("Update full location.");
        
        if(!locationService.ifExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        LocationDto savedLocationDto = locationService.updateFullLocation(id, locationDto);

        return new ResponseEntity<>(savedLocationDto, HttpStatus.OK);
    }

    // Partial update of name and description only.
    // If the location point needs to be updated then a full update is required.
    @PatchMapping(path = "/locations/{id}")
    public ResponseEntity<LocationDto> updatePartialLocation(@PathVariable("id") Long id, @RequestBody LocationDto locationDto) {
        log.info("Update partial location.");
        
        if(!locationService.ifExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        LocationDto updatedLocationDto = locationService.updatePartialLocation(id, locationDto);

        return new ResponseEntity<>(updatedLocationDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "locations/{id}")
    public ResponseEntity<HttpStatus> deleteLocation(@PathVariable("id") Long id) {
        log.info("Delete location.");

        locationService.deleteLocation(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
