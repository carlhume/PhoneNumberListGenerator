package com.tds.directory;

import com.tds.repository.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class PhoneNumberListGenerator {

    private final Repository repository;
    private final PhoneDirectorySpider spider;

    public PhoneNumberListGenerator( PhoneDirectorySpider spiderForCrawling, Repository repositoryForStoringContactData ) {
        this.repository = repositoryForStoringContactData;
        this.spider = spiderForCrawling;
    }

    public void storeNewContactsFor( Collection<CrawlData> citiesToCrawl ) throws IOException {
        for( CrawlData cityToCrawl : citiesToCrawl ) {
            System.out.println( ">> cnh >> Starting to crawl " + cityToCrawl.getCity() );
            storeNewContactsFor( cityToCrawl.getCity(), cityToCrawl.getPageCount() );
        }
    }

    public void storeNewContactsFor( String city, int numberOfPages ) throws IOException {

        for( int pageCounter = 1; pageCounter <= numberOfPages; pageCounter++ ) {
            Collection<Contact> contacts = spider.findContactsFromCityPage( "https://411.ca/white-pages/on/" + city + "/p" + pageCounter );

            for (Contact contact : contacts) {
                Contact possibleMatch = repository.getContactForPhoneNumber( contact.getTelephoneNumber() );
                if( contact.matches( possibleMatch ) ) {
                    // We have already recorded this contact
                    System.out.println( ">> cnh >> Contact found at " + contact.getFoundOnPage() + " was previously found at " + possibleMatch.getFoundOnPage() );
                } else {
                    repository.store(contact);
                }
            }
        }


    }


    public static void main( String [] args ) throws IOException {
        System.out.println( ">> cnh >> Starting to Generate List" );

        Collection<CrawlData> citiesToCrwal = new ArrayList<>();
//        citiesToCrwal.add( new CrawlData( "ottawa", 295 ) );
//        citiesToCrwal.add( new CrawlData( "kanata", 87 ) );
//        citiesToCrwal.add( new CrawlData( "stittsville", 39 ) );
//        citiesToCrwal.add( new CrawlData( "nepean", 126 ) );
//        citiesToCrwal.add( new CrawlData( "orleans", 101 ) );
//        citiesToCrwal.add( new CrawlData( "gloucester", 68 ) );
//        citiesToCrwal.add( new CrawlData( "renfrew", 18 ) );
//        citiesToCrwal.add( new CrawlData( "arnprior", 19 ) );
//        citiesToCrwal.add( new CrawlData( "carp", 13 ) );
//        citiesToCrwal.add( new CrawlData( "calabogie", 4 ) );
//        citiesToCrwal.add( new CrawlData( "chalk-river", 3 ) );
//        citiesToCrwal.add( new CrawlData( "tweed", 7 ) );

        PhoneNumberListGenerator generator = new PhoneNumberListGenerator( new PhoneDirectorySpider(), new Repository() );
        generator.storeNewContactsFor( citiesToCrwal );
        System.out.println( ">> cnh >> Finished generating list" );
    }

}
