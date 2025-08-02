package au.com.heal.martin.mapinfo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import au.com.heal.martin.mapinfo.domain.entities.AreaEntity;

@Repository
public interface AreaRepository extends CrudRepository<AreaEntity, Long>,
    PagingAndSortingRepository<AreaEntity, Long> {

}
