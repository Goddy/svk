package be.spring.app.dto;

import java.util.Set;

/**
 * Created by u0090265 on 16/09/16.
 */
public class NewsDTO extends DTOBaseClass {
    private String header;
    private String content;
    private String postDate;
    private AccountDTO postedBy;
    private boolean editable;
    private Set<CommentDTO> comments;

    public NewsDTO(Long id, String header, String content, String postDate, AccountDTO postedBy, boolean editable,
                   Set<CommentDTO> comments) {
        super(id);
        this.header = header;
        this.content = content;
        this.postDate = postDate;
        this.postedBy = postedBy;
        this.editable = editable;
        this.comments = comments;
    }


    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public Set<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(Set<CommentDTO> comments) {
        this.comments = comments;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public AccountDTO getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(AccountDTO postedBy) {
        this.postedBy = postedBy;
    }
}
