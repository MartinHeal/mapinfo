package au.com.heal.martin.mapinfo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import au.com.heal.martin.mapinfo.domain.dto.AreaDto;

public interface AreaService {

    AreaDto createArea(AreaDto area);

    Optional<AreaDto> readOneArea(Long id);

    List<AreaDto> readAllAreas();

    Page<AreaDto> readAllAreas(Pageable Pageable);

    AreaDto updateFullArea(Long id, AreaDto area);

    AreaDto updatePartialArea(Long id, AreaDto area);

    void deleteArea(Long id);


    boolean ifExists(Long id);
}
