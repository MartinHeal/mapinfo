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

import au.com.heal.martin.mapinfo.domain.dto.AreaDto;
import au.com.heal.martin.mapinfo.services.AreaService;
import lombok.extern.java.Log;

@RestController
@Log
public class AreaController {

    private AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @PostMapping(path = "/areas")
    public ResponseEntity<AreaDto> createArea(@RequestBody AreaDto area) {
        log.info("Create area.");

        return new ResponseEntity<>(areaService.createArea(area), HttpStatus.CREATED);
    }

    @GetMapping(path = "/areas/{id}")
    public ResponseEntity<AreaDto> readOneArea(@PathVariable("id") Long id) {
        log.info("Read one area.");

        Optional<AreaDto> foundArea = areaService.readOneArea(id);

        return foundArea.map(areaDto -> new ResponseEntity<>(areaDto, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/areas")
    public PagedModel<AreaDto> readAllArea(Pageable pageable) {
        log.info("Read all areas.");

        Page<AreaDto> areas = areaService.readAllAreas(pageable);

        return new PagedModel<>(areas);
    }

    @PutMapping(path = "/areas/{id}")
    public ResponseEntity<AreaDto> updateFullArea(@PathVariable("id") Long id, @RequestBody AreaDto areaDto) {
        log.info("Update full area.");
        
        if(!areaService.ifExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        AreaDto savedAreaDto = areaService.updateFullArea(id, areaDto);

        return new ResponseEntity<>(savedAreaDto, HttpStatus.OK);
    }

    // Partial update of name and description only.
    // If the area points need to be updated then a full update is required.
    @PatchMapping(path = "/areas/{id}")
    public ResponseEntity<AreaDto> updatePartialArea(@PathVariable("id") Long id, @RequestBody AreaDto areaDto) {
        log.info("Update partial area.");
        
        if(!areaService.ifExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AreaDto updatedAreaDto = areaService.updatePartialArea(id, areaDto);

        return new ResponseEntity<>(updatedAreaDto, HttpStatus.OK);
    }

    @DeleteMapping(path = "areas/{id}")
    public ResponseEntity<HttpStatus> deleteArea(@PathVariable("id") Long id) {
        log.info("Delete area.");

        areaService.deleteArea(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
