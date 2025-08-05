package au.com.heal.martin.mapinfo.domain.dto;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDto {

    private Long id;

    private String name;

    private String description;

    private LocationPointDto point;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocationPointDto getPoint() {
        return point;
    }

    public void setPoint(LocationPointDto point) {
        this.point = point;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof LocationDto)) {
            return false;
        }

        LocationDto other = (LocationDto) o;

        return this.id.equals(other.id)
            && this.name.equals(other.name)
            && this.description.equals(other.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
