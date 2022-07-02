package com.tds.directory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class PhoneDirectorySpider {

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
        for( Element link : links ) {
            profileLinks.add( link.attr("abs:href" ) );
        }
        return profileLinks;
    }

    public Collection<String> findContactPagesFromCityPage( String page ) throws IOException {
        Collection<String> contactPages = new ArrayList<>();
        Document webpage;
        try {
            webpage = Jsoup.connect(page).get();
        } catch( IOException e ) {
            System.out.println( ">> cnh >> failed to connect to page: " + page );
            e.printStackTrace();
            System.out.println( ">> cnh >> Trying again ... " );

            // If we're not able to connect to the page, we want to wait 10 seconds and try again
            try {
                Thread.currentThread().sleep( 10000 );
            } catch (InterruptedException ex) {
                // Expected case
            }

            try {
                webpage = Jsoup.connect(page).get();
            } catch( IOException anotherException ) {
                System.out.println(">> cnh >> failed to connect to page after waiting: " + page);
                anotherException.printStackTrace();
                System.out.println(">> cnh >> Trying again one more time ... ");


                // If we're still not able to connect, lets wait for a minute
                try {
                    Thread.currentThread().sleep(60000);
                } catch (InterruptedException ex) {
                    // Expected case
                }

                // If we still can't get the page at this point, we'll stop and bail
                webpage = Jsoup.connect(page).get();
            }
        }

        Elements links = findLinksToContactPages(webpage);
        for( Element link : links ) {
            if( isContactPageLink( link )) {
                contactPages.add( link.attr("abs:href") );
            }
        }

        return contactPages;
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

    private Elements findLinksToContactPages(Document webpage) {
        //The links to contact pages belong to one of 2 CSS classes that I've detected (so far).
        Elements links = webpage.select( "a.ng-tns-c23-1" ); // >> cnh >> check to see if the class changes ...
        if( links.size() == 0 ) {
            links = webpage.select( "a.ng-tns-c17-1" );
        }
        // If we still don't have any links, print out the page for troubleshooting
        // >> cnh >> TODO:  Add Logging
        if (links.size() == 0) {
            System.out.println(webpage);
        }
        return links;
    }

    // The Contact Page currently has empty itemprops for contact page links, but non-empty itemprops for other links
    private boolean  isContactPageLink(Element link) {
        return link.attr("itemprop").length() == 0;
    }

}
