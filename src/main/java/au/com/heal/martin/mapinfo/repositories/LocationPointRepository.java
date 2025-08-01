package au.com.heal.martin.mapinfo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import au.com.heal.martin.mapinfo.domain.entities.LocationPointEntity;

@Repository
public interface LocationPointRepository extends CrudRepository<LocationPointEntity, Long> {

}
