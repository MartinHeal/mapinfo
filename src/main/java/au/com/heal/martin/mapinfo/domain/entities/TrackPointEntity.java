package au.com.heal.martin.mapinfo.domain.entities;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tracks_points")
public class TrackPointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tracks_points_id_seq")
    private Long id;

    private Double latitude;

    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "tracks_id")
    private TrackEntity track;

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
    public TrackEntity getTrack() {
        return track;
    }

    public void setTrack(TrackEntity track) {
        this.track = track;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof TrackPointEntity)) {
            return false;
        }

        TrackPointEntity other = (TrackPointEntity) o;

        return this.id.equals(other.id)
            && this.latitude.equals(other.latitude)
            && this.longitude.equals(other.longitude)
            && ((this.track == null && other.track == null)
               || (this.track != null && this.track.equals(other.track)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, latitude, longitude);
    }
}
