package au.com.heal.martin.mapinfo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDto {
    private Long id;

    private String name;

    private String description;

    private LocationPointDto point;
}
