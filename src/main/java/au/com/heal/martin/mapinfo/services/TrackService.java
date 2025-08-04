package au.com.heal.martin.mapinfo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import au.com.heal.martin.mapinfo.domain.dto.TrackDto;

public interface TrackService {

    TrackDto createTrack(TrackDto track);

    Optional<TrackDto> readOneTrack(Long id);

    List<TrackDto> readAllTracks();

    Page<TrackDto> readAllTracks(Pageable pageable);

    TrackDto updateFullTrack(Long id, TrackDto track);

    TrackDto updatePartialTrack(Long id, TrackDto track);

    void deleteTrack(Long id);


    boolean ifExists(Long id);
}
