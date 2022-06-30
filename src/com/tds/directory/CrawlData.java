package com.tds.directory;

public class CrawlData {
    private final String city;
    private final int pageCount;

    public CrawlData( String cityToCrawl, int pages ) {
        city = cityToCrawl;
        pageCount = pages;
    }

    public String getCity() {
        return this.city;
    }

    public int getPageCount() {
        return this.pageCount;
    }

}
