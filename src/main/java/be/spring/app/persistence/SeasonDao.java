package be.spring.app.persistence;

import be.spring.app.model.Season;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by u0090265 on 5/3/14.
 */
public interface SeasonDao extends PagingAndSortingRepository<Season, Long>, JpaSpecificationExecutor<Season> {
}