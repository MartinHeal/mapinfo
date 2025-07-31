package au.com.heal.martin.mapinfo.domain;

import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "locations")
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locations_id_seq")
    private long id;

    private String name;

    private String description;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "locations_points_id", unique = true)
    private LocationPointEntity point;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocationPointEntity getPoint() {
        return point;
    }

    public void setPoint(LocationPointEntity point) {
        this.point = point;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof LocationEntity)) {
            return false;
        }

        LocationEntity other = (LocationEntity) o;

        return this.id == other.id
            && this.name.equals(other.name)
            && this.description.equals(other.description)
            && this.point.equals(other.point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, point);
    }
}
