package be.spring.app.persistence;

import be.spring.app.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * User: Tom De Dobbeleer
 * Date: 12/20/13
 * Time: 9:14 AM
 * Remarks: none
 */
public interface NewsDao extends PagingAndSortingRepository<News, Long>, JpaSpecificationExecutor<News> {
    @Query("select n from News n where n.header like ?1 OR n.content like ?1 order by n.postDate desc")
    Page<News> getSearch(String term, Pageable pageable);
}
