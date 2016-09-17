package be.spring.app.controller.REST.v1;

import be.spring.app.dto.CommentDTO;
import be.spring.app.dto.NewsDTO;
import be.spring.app.dto.PageDTO;
import be.spring.app.model.News;
import be.spring.app.service.DTOConversionHelper;
import be.spring.app.service.NewsService;
import be.spring.app.validators.SanitizeUtils;
import com.google.common.base.Optional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by u0090265 on 16/09/16.
 */
@org.springframework.web.bind.annotation.RestController
@Api(value = "News REST api", description = "News REST operations")
public class NewsRestController extends AbstractRestController {
    private static final Logger logger = LoggerFactory.getLogger(NewsRestController.class);
    private final NewsService newsService;
    private final DTOConversionHelper dtoConversionHelper;

    public NewsRestController(NewsService newsService, DTOConversionHelper dtoConversionHelper) {
        this.newsService = newsService;
        this.dtoConversionHelper = dtoConversionHelper;
    }

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    @ApiOperation(value = "Get news", nickname = "getNewsPage")
    public ResponseEntity<PageDTO<NewsDTO>> getNewsPage(@RequestParam int page, @RequestParam(required =
            false) int size) {
        Page<News> news = newsService.getPagedNews(page, size, Optional.<Sort>absent());
        return new ResponseEntity<>(dtoConversionHelper.convertNewsPage(getAccountFromSecurity(), news, isAdmin()),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/news/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Get news", nickname = "getNews")
    public ResponseEntity<NewsDTO> getNews(@PathVariable Long id) {
        News news = newsService.getNewsItem(id);
        return new ResponseEntity<>(dtoConversionHelper.convertNews(getAccountFromSecurity(), news, isAdmin()),
                HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/news/{id}/comment", method = RequestMethod.POST)
    @ApiOperation(value = "Get news", nickname = "postComment")
    public ResponseEntity postComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        newsService.addNewsComment(id, SanitizeUtils.SanitizeHtml(commentDTO.getContent()), getAccountFromSecurity());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/news/{id}/comment", method = RequestMethod.PUT)
    @ApiOperation(value = "Get news", nickname = "editComment")
    public ResponseEntity editComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        newsService.changeNewsComment(commentDTO.getId(), id, SanitizeUtils.SanitizeHtml(commentDTO.getContent()),
                getAccountFromSecurity());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/news/{id}/comment/{commentId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Get news", nickname = "deleteComment")
    public ResponseEntity deleteComment(@PathVariable Long id, @PathVariable Long commentId) {
        newsService.deleteNewsComment(commentId, id, getAccountFromSecurity());
        return new ResponseEntity(HttpStatus.OK);
    }
}
