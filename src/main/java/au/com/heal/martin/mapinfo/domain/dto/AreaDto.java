package au.com.heal.martin.mapinfo.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AreaDto {

    private Long id;

    private String name;

    private String description;

    private List<AreaPointDto> points;
}
