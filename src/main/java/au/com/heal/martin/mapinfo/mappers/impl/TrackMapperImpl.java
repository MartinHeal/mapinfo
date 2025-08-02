package au.com.heal.martin.mapinfo.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import au.com.heal.martin.mapinfo.domain.dto.TrackDto;
import au.com.heal.martin.mapinfo.domain.entities.TrackEntity;
import au.com.heal.martin.mapinfo.mappers.Mapper;

@Component
public class TrackMapperImpl implements Mapper<TrackEntity, TrackDto> {

    private ModelMapper modelMapper;

    public TrackMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TrackDto mapTo(TrackEntity trackEntity) {
        return modelMapper.map(trackEntity, TrackDto.class);
    }

    @Override
    public TrackEntity mapFrom(TrackDto trackDto) {
        return modelMapper.map(trackDto, TrackEntity.class);
    }
}
