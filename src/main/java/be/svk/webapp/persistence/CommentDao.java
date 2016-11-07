package be.svk.webapp.persistence;

import be.svk.webapp.model.Comment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by u0090265 on 10/13/14.
 */
public interface CommentDao extends PagingAndSortingRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {
}
