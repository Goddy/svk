package be.spring.app.dto;

import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by u0090265 on 21/07/16.
 */
public class PageDTO<T> {
    private List<T> list;
    private int totalPages;

    public PageDTO(List<T> list) {
        Assert.notNull(list);
        this.list = list;
        this.totalPages = list.size();
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

    @Override
    public String toString() {
        return "PageDTO{" +
                "list=" + list +
                ", totalPages=" + totalPages +
                '}';
    }
}
