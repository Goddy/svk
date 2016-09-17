package be.spring.app.dto;

import static be.spring.app.utils.GeneralUtils.convertToDate;

/**
 * Created by u0090265 on 16/09/16.
 */
public class CommentDTO extends DTOBaseClass implements Comparable<CommentDTO> {
    private AccountDTO postedBy;
    private String content;
    private String postDate;
    private boolean editable;

    public CommentDTO(Long id, AccountDTO postedBy, String content, String postDate, boolean editable) {
        super(id);
        this.postedBy = postedBy;
        this.content = content;
        this.postDate = postDate;
        this.editable = editable;
    }

    public AccountDTO getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(AccountDTO postedBy) {
        this.postedBy = postedBy;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @Override
    public int compareTo(CommentDTO o) {
        return convertToDate(this.postDate).compareTo(convertToDate(o.postDate));
    }
}
