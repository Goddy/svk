package be.svk.webapp.dto;

import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by u0090265 on 21/07/16.
 */
public class PageDTO<T> {
    private List<T> list;
    private int totalPages;
    private int totalSize;
    private boolean hasNext;
    private boolean hasPrevious;

    public PageDTO(List<T> list, int totalPages, boolean hasNext, boolean hasPrevious) {
        Assert.notNull(list);
        this.list = list;
        this.totalPages = totalPages;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    @Override
    public String toString() {
        return "PageDTO{" +
                "list=" + list +
                ", totalPages=" + totalPages +
                ", totalSize=" + totalSize +
                ", hasNext=" + hasNext +
                ", hasPrevious=" + hasPrevious +
                '}';
    }
}
