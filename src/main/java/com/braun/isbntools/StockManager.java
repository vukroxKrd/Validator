package com.braun.isbntools;

public class StockManager {

    private ExternalISBNDataService webService;
    private ExternalISBNDataService dbService;

    public void setDbService(ExternalISBNDataService dbService) {
        this.dbService = dbService;
    }
    public void setWebService(ExternalISBNDataService webService) {
        this.webService = webService;
    }

    public String getLocatorCode(String isbn) {
        Book book = dbService.lookUp(isbn);
        if (book == null) book = webService.lookUp(isbn);

        StringBuilder locator = new StringBuilder();
        locator.append(isbn.substring(isbn.length() - 4))
                .append(book.getAuthor().substring(0,1))
                .append(book.getTitle().split(" ").length);
        String res = locator.toString();
        System.out.println(res);
        return res;
    }
}
