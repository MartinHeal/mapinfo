package au.com.heal.martin.mapinfo.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import au.com.heal.martin.mapinfo.domain.dto.LocationDto;
import au.com.heal.martin.mapinfo.domain.entities.LocationEntity;
import au.com.heal.martin.mapinfo.mappers.Mapper;
import au.com.heal.martin.mapinfo.repositories.LocationRepository;
import au.com.heal.martin.mapinfo.services.LocationService;

@Service
public class LocationServiceImpl implements LocationService {
    private LocationRepository locationRepository;
    private Mapper<LocationEntity, LocationDto> locationMapper;

    public LocationServiceImpl(LocationRepository locationRepository, Mapper<LocationEntity, LocationDto> locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    private LocationDto save(LocationDto location) {
        LocationEntity locationEntity = locationMapper.mapFrom(location);

        LocationEntity savedLocationEntity = locationRepository.save(locationEntity);

        return locationMapper.mapTo(savedLocationEntity);
    }

    @Override
    public LocationDto createLocation(LocationDto location) {
        return save(location);
    }

    @Override
    public Optional<LocationDto> readOneLocation(Long id) {
        Optional<LocationEntity> foundLocation = locationRepository.findById(id);

        Optional<LocationDto> locationDto = foundLocation.map(locationEntity -> {
            return locationMapper.mapTo(locationEntity);
        });

        return locationDto;
    }

    @Override
    public List<LocationDto> readAllLocations() {
        Iterable<LocationEntity> locations = locationRepository.findAll();

        return StreamSupport.stream(locations.spliterator(), false).map(locationMapper::mapTo).collect(Collectors.toList());
    }

    @Override
    public Page<LocationDto> readAllLocations(Pageable Pageable) {
        Page<LocationEntity> locationEntities = locationRepository.findAll(Pageable);
        
        return locationEntities.map(locationMapper::mapTo);
    }

    @Override
    public LocationDto updateFullLocation(Long id, LocationDto location) {
        location.setId(id);
        return save(location);
    }

    @Override
    public LocationDto updatePartialLocation(Long id, LocationDto location) {
        location.setId(id);

        LocationEntity locationEntity = locationMapper.mapFrom(location);

        Optional<LocationDto> savedLocationDto = locationRepository.findById(id)
            .map(existingLocation -> {
                Optional.ofNullable(locationEntity.getName()).ifPresent(existingLocation::setName);
                Optional.ofNullable(locationEntity.getDescription()).ifPresent(existingLocation::setDescription);

                LocationEntity savedLocation = locationRepository.save(existingLocation);

                return locationMapper.mapTo(savedLocation);
            }
        );

        // The controller has already checked for the existance of the location.
        // If there is no saved location at this point then throw an exception.
        return savedLocationDto.map(locationDto -> {
            return locationDto;
        }).orElseThrow(() -> new RuntimeException("Location does not exist!"));
    }

    @Override
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }


    @Override
    public boolean ifExists(Long id) {
        return locationRepository.existsById(id);
    }
}
