package be.spring.spring.service;

import be.spring.spring.interfaces.ConcurrentDataService;
import be.spring.spring.interfaces.MatchesDao;
import be.spring.spring.interfaces.TeamDao;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;

/**
 * Created by u0090265 on 9/12/14.
 */
@Service
public class ConcurrentDataServiceImpl implements ConcurrentDataService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String ALL_FILTER = "*";
    private static final int N_THREADS = 20;

    private ListeningExecutorService executorService;

    private MatchesDao matchesDao;
    private TeamDao teamDao;


    @Autowired
    public ConcurrentDataServiceImpl(MatchesDao matchesDao, TeamDao teamDao){
        this.matchesDao = matchesDao;
        this.teamDao = teamDao;
        executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(N_THREADS));
    }

}
