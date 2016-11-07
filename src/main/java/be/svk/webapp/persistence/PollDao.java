package be.svk.webapp.persistence;

import be.svk.webapp.data.PollStatusEnum;
import be.svk.webapp.model.Poll;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by u0090265 on 10/1/14.
 */
public interface PollDao extends PagingAndSortingRepository<Poll, Long>, JpaSpecificationExecutor<Poll> {
    List<Poll> findByStatus(PollStatusEnum pollStatusEnum);
}
