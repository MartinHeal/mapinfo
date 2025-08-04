package au.com.heal.martin.mapinfo.domain.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AreaDto {

    private Long id;

    private String name;

    private String description;

    @Builder.Default private List<AreaPointDto> points = new ArrayList<>();

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

    @JsonManagedReference
    public List<AreaPointDto> getPoints() {
        return points;
    }

    public void setPoints(List<AreaPointDto> points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof AreaDto)) {
            return false;
        }

        AreaDto other = (AreaDto) o;

        return this.id.equals(other.id)
            && this.name.equals(other.name)
            && this.description.equals(other.description)
            && this.points.equals(other.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, points);
    }
}
