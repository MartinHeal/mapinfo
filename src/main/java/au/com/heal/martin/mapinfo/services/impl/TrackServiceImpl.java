package au.com.heal.martin.mapinfo.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import au.com.heal.martin.mapinfo.domain.dto.TrackDto;
import au.com.heal.martin.mapinfo.domain.entities.TrackEntity;
import au.com.heal.martin.mapinfo.mappers.Mapper;
import au.com.heal.martin.mapinfo.repositories.TrackRepository;
import au.com.heal.martin.mapinfo.services.TrackService;

@Service
public class TrackServiceImpl implements TrackService {

    private TrackRepository trackRepository;
    private Mapper<TrackEntity, TrackDto> trackMapper;

    public TrackServiceImpl(TrackRepository trackRepository, Mapper<TrackEntity, TrackDto> trackMapper) {
        this.trackRepository = trackRepository;
        this.trackMapper = trackMapper;
    }

    private TrackDto save(TrackDto track) {
        TrackEntity trackEntity = trackMapper.mapFrom(track);
        trackEntity.getPoints().stream().forEach(trackPointEntity -> trackPointEntity.setTrack(trackEntity));

        TrackEntity savedTrackEntity = trackRepository.save(trackEntity);

        return trackMapper.mapTo(savedTrackEntity);
    }

    @Override
    public TrackDto createTrack(TrackDto track) {
        return save(track);
    }

    @Override
    public Optional<TrackDto> readOneTrack(Long id) {
        Optional<TrackEntity> foundTrack = trackRepository.findById(id);

        return foundTrack.map(trackEntity -> trackMapper.mapTo(trackEntity));
    }

    @Override
    public List<TrackDto> readAllTracks() {
        Iterable<TrackEntity> tracks = trackRepository.findAll();

        return StreamSupport.stream(tracks.spliterator(), false)
            .map(trackMapper::mapTo).toList();
    }

    @Override
    public Page<TrackDto> readAllTracks(Pageable pageable) {
        Page<TrackEntity> trackEntities = trackRepository.findAll(pageable);
        
        return trackEntities.map(trackMapper::mapTo);
    }

    @Override
    public TrackDto updateFullTrack(Long id, TrackDto track) {
        track.setId(id);

        return save(track);
    }

    @Override
    public TrackDto updatePartialTrack(Long id, TrackDto track) {
        track.setId(id);

        TrackEntity trackEntity = trackMapper.mapFrom(track);

        Optional<TrackDto> savedTrackDto = trackRepository.findById(id)
            .map(existingTrack -> {
                Optional.ofNullable(trackEntity.getName()).ifPresent(existingTrack::setName);
                Optional.ofNullable(trackEntity.getDescription()).ifPresent(existingTrack::setDescription);

                TrackEntity savedTrack = trackRepository.save(existingTrack);

                return trackMapper.mapTo(savedTrack);
            }
        );

        // The controller has already checked for the existance of the track.
        // If there is no saved track at this point then throw an exception.
        return savedTrackDto.map(trackDto -> trackDto)
            .orElseThrow(() -> new RuntimeException("Track does not exist!"));
    }

    @Override
    public void deleteTrack(Long id) {
        trackRepository.deleteById(id);
    }


    @Override
    public boolean ifExists(Long id) {
        return trackRepository.existsById(id);
    }
}
