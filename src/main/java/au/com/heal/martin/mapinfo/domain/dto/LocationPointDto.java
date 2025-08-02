package au.com.heal.martin.mapinfo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationPointDto {

    private Long id;

    private double latitude;

    private double longitude;
}
