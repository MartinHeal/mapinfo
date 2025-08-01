package au.com.heal.martin.mapinfo.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import au.com.heal.martin.mapinfo.domain.dto.LocationDto;
import au.com.heal.martin.mapinfo.domain.entities.LocationEntity;
import au.com.heal.martin.mapinfo.mappers.Mapper;

@Component
public class LocationMapperImpl implements Mapper<LocationEntity, LocationDto> {

    private ModelMapper modelMapper;

    public LocationMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public LocationDto mapTo(LocationEntity locationEntity) {
        return modelMapper.map(locationEntity, LocationDto.class);
    }

    @Override
    public LocationEntity mapFrom(LocationDto locationDto) {
        return modelMapper.map(locationDto, LocationEntity.class);
    }
}
