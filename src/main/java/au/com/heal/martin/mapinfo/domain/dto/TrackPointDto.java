package au.com.heal.martin.mapinfo.domain.dto;

import au.com.heal.martin.mapinfo.domain.entities.TrackEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrackPointDto {

    private Long id;

    private Double latitude;

    private Double longitude;

    private TrackEntity track;
}
