package au.com.heal.martin.mapinfo.domain.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrackPointDto {

    private Long id;

    private Double latitude;

    private Double longitude;

    private TrackDto track;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @JsonBackReference
    public TrackDto getTrack() {
        return track;
    }

    public void setTrack(TrackDto track) {
        this.track = track;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof TrackPointDto)) {
            return false;
        }

        TrackPointDto other = (TrackPointDto) o;

        return this.id.equals(other.id)
            && this.latitude.equals(other.latitude)
            && this.longitude.equals(other.longitude)
            && this.track == other.track;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, latitude, longitude, track);
    }
}
