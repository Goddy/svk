package be.svk.webapp.utils;

import org.springframework.ui.Model;

/**
 * User: Tom De Dobbeleer
 * Date: 1/17/14
 * Time: 5:06 PM
 * Remarks: none
 */
public class PageObject {
    private Model model;
    private String url;
    private Integer firstPage;
    private Integer lastPage;
    private Integer previousPage;
    private Integer nextPage;

    public PageObject (Model model, int pageCount, int requestedPage, String url ) {
        this.model = model;
        this.url = url;
        //initialize previousPage / nextPage
        this.getPages(requestedPage, pageCount);
    }

    public void addAttributes() {
        model.addAttribute("first", firstPage == null ? null : String.format("%s/%s", url, firstPage));
        model.addAttribute("last", lastPage == null ? null : String.format("%s/%s", url, lastPage));
        model.addAttribute("previous", previousPage == null ? null : String.format("%s/%s", url, previousPage));
        model.addAttribute("next", nextPage == null ? null : String.format("%s/%s", url, nextPage));
    }

    private void getPages(int requestedPage, int count) {
        int last = count - Constants.ONE;
        int next = requestedPage + Constants.ONE;
        previousPage = requestedPage == Constants.ZERO ? null : (requestedPage - Constants.ONE < Constants.ZERO) ? Constants.ZERO : requestedPage - Constants.ONE;
        firstPage = requestedPage == Constants.ZERO || previousPage == Constants.ZERO ? null : Constants.ZERO;
        nextPage = next > last ? null : next;
        lastPage = nextPage == null || next == last ? null : last;
    }
    private int floor(int count) {
        return (int)(Math.floor((double)(count/ Constants.TEN))* Constants.TEN);
    }

    private int subtractPages(int count) {
        return count - Constants.TEN <= Constants.ZERO ? Constants.ZERO : floor(count) - Constants.TEN;
    }

    public Model getModel() {
        return model;
    }

    public String getUrl() {
        return url;
    }

    public Integer getFirstPage() {
        return firstPage;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public Integer getPreviousPage() {
        return previousPage;
    }

    public Integer getNextPage() {
        return nextPage;
    }
}
