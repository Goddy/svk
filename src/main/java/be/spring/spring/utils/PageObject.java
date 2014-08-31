package be.spring.spring.utils;

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
    private int firstPage;
    private int lastPage;
    private int previousPage;
    private int nextPage;

    public PageObject (Model model, int entryCount, int requestedPage, String url ) {
        this.model = model;
        this.url = url;
        //initialize previousPage / nextPage
        this.getPages(requestedPage, entryCount);
    }

    public void addAttributes() {
        model.addAttribute("first", String.format("%s/%s", url , firstPage));
        model.addAttribute("last", String.format("%s/%s", url , lastPage));
        model.addAttribute("previous", String.format("%s/%s", url , previousPage));
        model.addAttribute("next", String.format("%s/%s", url ,nextPage));
    }

    private void getPages(int requestedPage, int count) {
        int totalPages = count;
        firstPage = Constants.ONE;
        previousPage = (requestedPage + Constants.MINUS_TEN < Constants.ONE) ? Constants.ONE : requestedPage + Constants.MINUS_TEN;
        nextPage = (requestedPage + Constants.TEN > totalPages) ? floor(totalPages) : floor(requestedPage + Constants.TEN);
        lastPage =  (count % Constants.TEN == Constants.ZERO) ? subtractPages(count) : floor(count);
    }
    private int floor(int count) {
        return (int)(Math.floor((double)(count/ Constants.TEN))* Constants.TEN);
    }

    private int subtractPages(int count) {
        return count - Constants.TEN <= Constants.ZERO ? Constants.ZERO : floor(count) - Constants.TEN;
    }
}
