package au.com.heal.martin.mapinfo.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import au.com.heal.martin.mapinfo.domain.dto.AreaDto;
import au.com.heal.martin.mapinfo.domain.entities.AreaEntity;
import au.com.heal.martin.mapinfo.mappers.Mapper;

@Component
public class AreaMapperImpl implements Mapper<AreaEntity, AreaDto> {

    private ModelMapper modelMapper;

    public AreaMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AreaDto mapTo(AreaEntity areaEntity) {
        return modelMapper.map(areaEntity, AreaDto.class);
    }

    @Override
    public AreaEntity mapFrom(AreaDto areaDto) {
        return modelMapper.map(areaDto, AreaEntity.class);
    }

}
