package au.com.heal.martin.mapinfo.domain.dto;

import java.util.List;

import au.com.heal.martin.mapinfo.domain.entities.TrackPointEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrackDto {

    private Long id;

    private String name;

    private String description;

    List<TrackPointEntity> points;
}
