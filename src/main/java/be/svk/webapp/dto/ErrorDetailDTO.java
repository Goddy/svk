package be.svk.webapp.dto;

import java.util.Date;

/**
 * Created by u0090265 on 14/06/16.
 */
public class ErrorDetailDTO {
    private String title;
    private int status;
    private String detail;
    private long timeStamp;
    private String path;
    private String developerMessage;

    public ErrorDetailDTO() {
        //Initialize timestamp;
        timeStamp = new Date().getTime();
    }

    public ErrorDetailDTO(String title, int status, String detail, String path, String developerMessage) {
        this();
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.path = path;
        this.developerMessage = developerMessage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }
}
