package com.tds.directory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class PhoneDirectorySpider {

    public Contact findContactOnContactPage( String page ) throws IOException {
        Contact contact;
        ContactSpider contactSpider = new ContactSpider();

        // TODO: Cleanup
        // If the parse fails for an unexpected reason, the entire process grinds to a halt
        // Use the Broken behavior for now to address.
        try {
            String profileLink = findProfileLinkOnPage( page );
            if( profileLink != null ) {
                contact = contactSpider.parseContactFromProfilePage( profileLink );
            } else {
                throw new BrokenPageException( page );
            }
        } catch( IOException e ) {
            throw new BrokenPageException( page );
        }

        return contact;
    }

    public String findProfileLinkOnPage( String page ) throws IOException {
        String profileLink = null;
        Document webpage = Jsoup.connect( page ).get();
        Elements links = webpage.select( "a.listing-card-link" );
        for( Element link : links ) {
            profileLink = link.attr("abs:href" );
        }
        return profileLink;
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
            webpage = Jsoup.connect(page).get();
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
                contacts.add( findContactOnContactPage( url ) );
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
