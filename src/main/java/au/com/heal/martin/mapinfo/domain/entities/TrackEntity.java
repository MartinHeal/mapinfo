package au.com.heal.martin.mapinfo.domain.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tracks")
public class TrackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tracks_id_seq")
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "track", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default private List<TrackPointEntity> points = new ArrayList<>();

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
    public List<TrackPointEntity> getPoints() {
        return points;
    }

    public void setPoints(List<TrackPointEntity> points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof TrackEntity)) {
            return false;
        }

        TrackEntity other = (TrackEntity) o;

        return this.id.equals(other.id)
            && this.name.equals(other.name)
            && this.description.equals(other.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
