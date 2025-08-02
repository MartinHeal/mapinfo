package au.com.heal.martin.mapinfo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import au.com.heal.martin.mapinfo.domain.dto.LocationDto;

public interface LocationService {

    LocationDto createLocation(LocationDto location);

    Optional<LocationDto> readOneLocation(Long id);

    List<LocationDto> readAllLocations();

    Page<LocationDto> readAllLocations(Pageable Pageable);

    LocationDto updateFullLocation(Long id, LocationDto location);

    LocationDto updatePartialLocation(Long id, LocationDto location);

    void deleteLocation(Long id);


    boolean ifExists(Long id);
}
