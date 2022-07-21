package com.tds.directory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class CityPageSpider {

    public Collection<Contact> findContactsOnContactPage( String page ) throws BrokenPageException {
        Collection<Contact> contacts = new ArrayList<>();
        try {
            Collection<String> profileLinks = findProfileLinksOnPage( page );

            // TODO:  Consider removing this behavior ...
            if( profileLinks.size() == 0 ) {
                throw new BrokenPageException( page );
            }

            ContactSpider contactSpider = new ContactSpider();
            for( String profileLink : profileLinks ) {
                contacts.add( contactSpider.parseContactFromProfilePage( profileLink ) );
            }
        } catch( IOException e ) {
            throw new BrokenPageException( page );
        }
        return contacts;
    }

    public Collection<String> findProfileLinksOnPage( String page ) throws IOException {
        Collection<String> profileLinks = new ArrayList<>();
        Document webpage = Jsoup.connect( page ).get();
        Elements links = webpage.select( "a.listing-card-link" );

        // Different sites have different elements where the profile cards are stored.
        // If we don't find any with the first approach, fall back to the next
        // TODO: Refactor to clean this up...
        if( links.size() == 0 ) {
            links = webpage.select("a.rsslink-m");
        }

        for( Element link : links ) {
            profileLinks.add( link.attr("abs:href" ) );
        }
        return profileLinks;
    }

    public Collection<String> findContactPagesFromCityPage( String page ) throws IOException {
        Collection<String> contactPages = new ArrayList<>();
        Document webpage = null;
        try {
            webpage = Jsoup.connect(page).get();
        } catch( IOException e ) {
            System.out.println( ">> cnh >> failed to connect to page: " + page );
            e.printStackTrace();
            System.out.println( ">> cnh >> Trying again ... " );

            // If we're not able to connect to the page immediately, we want to wait a while and try again
            // We're not going full exponential backoff - keeping it simple for the moment
            webpage = attemptToGetDocumentForPageWithRetriesEveryInterval( page, 6, 10000 );
            if( webpage == null ) { webpage = attemptToGetDocumentForPageWithRetriesEveryInterval( page, 60, 60000 ); }
            if( webpage == null ) { webpage = attemptToGetDocumentForPageWithRetriesEveryInterval( page, 60, 120000 ); }
            if( webpage == null ) { webpage = attemptToGetDocumentForPageWithRetriesEveryInterval( page, 60, 180000 ); }
            if( webpage == null ) { webpage = attemptToGetDocumentForPageWithRetriesEveryInterval( page, 60, 240000 ); }
            if( webpage == null ) { webpage = attemptToGetDocumentForPageWithRetriesEveryInterval( page, 60, 300000 ); }
            if( webpage == null ) { webpage = attemptToGetDocumentForPageWithRetriesEveryInterval( page, 60, 360000 ); }
            if( webpage == null ) { webpage = attemptToGetDocumentForPageWithRetriesEveryInterval( page, 60, 420000 ); }
                // If we still can't get the page at this point, we'll stop and bail
        }

        Elements links = findLinksToContactPages(webpage);
        for( Element link : links ) {
            if( isContactPageLink( link )) {
                contactPages.add( link.attr("abs:href") );
            }
        }

        return contactPages;
    }

    private Document attemptToGetDocumentForPageWithRetriesEveryInterval( String page, int retries, int interval ) {
        Document webpage = null;

        int attemptCounter = 1;
        while( webpage == null && attemptCounter <= retries ) {
            try {
                Thread.currentThread().sleep( interval );
            } catch (InterruptedException ex) {
                // Expected case
            }

            try {
                System.out.println( ">> cnh >> Attempting to load: " + page + " attempt #" + attemptCounter );
                webpage = Jsoup.connect(page).get();
            } catch( IOException yetAnotherException ) {
                System.out.println(">> cnh >> failed to connect to page after waiting: " + page);
                yetAnotherException.printStackTrace();
                System.out.println(">> cnh >> Trying again ... ");
            }
            attemptCounter++;
        }

        return webpage;
    }

    public Collection<Contact> findContactsFromCityPage( String page ) throws IOException {
        Collection<Contact> contacts = new ArrayList<>();
        Collection<String> contactPages = findContactPagesFromCityPage( page );
        for( String url : contactPages ) {
            System.out.println( ">> cnh >> find contact for: " + url );
            try {
                contacts.addAll( findContactsOnContactPage( url ) );
            } catch( BrokenPageException e ) {
                System.out.println( ">> cnh >> Could not load details since " + e );
            }

            try {
                Thread.currentThread().sleep( 100 );
            } catch( InterruptedException e ) {
                // Expected
            }
        }
        return contacts;
    }

    private Elements findLinksToContactPages( Document webpage ) {
        // TODO:  >> cnh >> Cleanup strategies for spidering different sites
        // The links to contact pages belongs to one of 2 CSS classes that I've detected (so far).
        // This behavior belongs for 411.ca
        Elements links = webpage.select( "a.ng-tns-c23-1" ); // >> cnh >> check to see if the class changes ...
        if( links.size() == 0 ) {
            links = webpage.select( "a.ng-tns-c17-1" );
        }

        // If we still don't have links, fall back to looking for specific list items instead of links
        // This behavior is required for Canadapages
        if( links.size() == 0 ) {
            Elements listItems = webpage.select( "li.cat-item" );
            for( Element listItem : listItems ) {
                links.addAll( listItem.select( "a[href]" ) );
            }
        }

        // If we still don't have any links, print out the page for troubleshooting
        // >> cnh >> TODO:  Add Logging
        if (links.size() == 0) {
            System.out.println(webpage);
        }
        return links;
    }

    // The Contact Page currently has empty itemprops for contact page links, but non-empty itemprops for other links
    // This is a 411.ca property, and works by accident with Canadapages
    private boolean  isContactPageLink(Element link) {
        return link.attr("itemprop").length() == 0;
    }

}
