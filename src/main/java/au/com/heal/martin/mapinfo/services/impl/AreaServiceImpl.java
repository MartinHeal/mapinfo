package au.com.heal.martin.mapinfo.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import au.com.heal.martin.mapinfo.domain.dto.AreaDto;
import au.com.heal.martin.mapinfo.domain.entities.AreaEntity;
import au.com.heal.martin.mapinfo.mappers.Mapper;
import au.com.heal.martin.mapinfo.repositories.AreaRepository;
import au.com.heal.martin.mapinfo.services.AreaService;

@Service
public class AreaServiceImpl implements AreaService {

    private AreaRepository areaRepository;
    private Mapper<AreaEntity, AreaDto> areaMapper;

    public AreaServiceImpl(AreaRepository areaRepository, Mapper<AreaEntity, AreaDto> areaMapper) {
        this.areaRepository = areaRepository;
        this.areaMapper = areaMapper;
    }

    private AreaDto save(AreaDto area) {
        AreaEntity areaEntity = areaMapper.mapFrom(area);
        areaEntity.getPoints().stream().forEach(areaPointEntity -> areaPointEntity.setArea(areaEntity));

        AreaEntity savedAreaEntity = areaRepository.save(areaEntity);

        return areaMapper.mapTo(savedAreaEntity);
    }

    @Override
    public AreaDto createArea(AreaDto area) {
        return save(area);
    }

    @Override
    public Optional<AreaDto> readOneArea(Long id) {
        Optional<AreaEntity> foundArea = areaRepository.findById(id);

        Optional<AreaDto> areaDto = foundArea.map(areaEntity -> {
            return areaMapper.mapTo(areaEntity);
        });

        return areaDto;
    }

    @Override
    public List<AreaDto> readAllAreas() {
        Iterable<AreaEntity> areas = areaRepository.findAll();

        return StreamSupport.stream(areas.spliterator(), false)
            .map(areaMapper::mapTo).collect(Collectors.toList());
    }

    @Override
    public Page<AreaDto> readAllAreas(Pageable Pageable) {
        Page<AreaEntity> areaEntities = areaRepository.findAll(Pageable);
        
        return areaEntities.map(areaMapper::mapTo);
    }

    @Override
    public AreaDto updateFullArea(Long id, AreaDto area) {
        area.setId(id);

        return save(area);
    }

    @Override
    public AreaDto updatePartialArea(Long id, AreaDto area) {
        area.setId(id);

        AreaEntity areaEntity = areaMapper.mapFrom(area);

        Optional<AreaDto> savedAreaDto = areaRepository.findById(id)
            .map(existingArea -> {
                Optional.ofNullable(areaEntity.getName()).ifPresent(existingArea::setName);
                Optional.ofNullable(areaEntity.getDescription()).ifPresent(existingArea::setDescription);

                AreaEntity savedArea = areaRepository.save(existingArea);

                return areaMapper.mapTo(savedArea);
            }
        );

        // The controller has already checked for the existance of the area.
        // If there is no saved area at this point then throw an exception.
        return savedAreaDto.map(areaDto -> {
            return areaDto;
        }).orElseThrow(() -> new RuntimeException("Area does not exist!"));
    }

    @Override
    public void deleteArea(Long id) {
        areaRepository.deleteById(id);
    }

    @Override
    public boolean ifExists(Long id) {
        return areaRepository.existsById(id);
    }
}
