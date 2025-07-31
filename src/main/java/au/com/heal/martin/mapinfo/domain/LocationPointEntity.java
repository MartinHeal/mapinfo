package au.com.heal.martin.mapinfo.domain;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "locations_points")
public class LocationPointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locations_points_id_seq")
    private Long id;

    private double latitude;

    private double longitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof LocationPointEntity)) {
            return false;
        }

        LocationPointEntity other = (LocationPointEntity) o;

        return this.id == other.id
            && this.latitude == other.latitude
            && this.longitude == other.longitude;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, latitude, longitude);
    }
}
