package au.com.heal.martin.mapinfo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AreaPointDto {

    private Long id;

    private Double latitude;

    private Double longitude;

    private AreaDto area;
}
