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
@Table(name = "areas_points")
public class AreaPointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "areas_points_id_seq")
    private Long id;

    private Double latitude;

    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "areas_id")
    private AreaEntity area;

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
    public AreaEntity getArea() {
        return area;
    }

    public void setArea(AreaEntity area) {
        this.area = area;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof AreaPointEntity)) {
            return false;
        }

        AreaPointEntity other = (AreaPointEntity) o;

        return this.id.equals(other.id)
            && this.latitude.equals(other.latitude)
            && this.longitude.equals(other.longitude)
            && (this.area == null && other.area == null)
               || (this.area != null && (this.area.equals(other.area)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, latitude, longitude);
    }
}
