package be.spring.app.persistence;

import be.spring.app.model.Season;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by u0090265 on 5/3/14.
 */
public interface SeasonDao extends PagingAndSortingRepository<Season, Long>, JpaSpecificationExecutor<Season> {
    @Query("select s from Season s order by s.description desc")
    List<Season> findAllOrderByDescriptionAsc();
}