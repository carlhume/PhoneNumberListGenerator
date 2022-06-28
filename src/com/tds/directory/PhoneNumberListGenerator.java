package com.tds.directory;

import com.tds.repository.Repository;

import java.io.IOException;
import java.util.Collection;

public class PhoneNumberListGenerator {

    public static void main( String [] args ) throws IOException {
        System.out.println( ">> cnh >> Starting to Generate List" );
        PhoneDirectorySpider spider = new PhoneDirectorySpider();

        for( int pageCounter = 1; pageCounter <= 87; pageCounter++ ) {
            Collection<Contact> contacts = spider.findContactsFromCityPage("https://411.ca/white-pages/on/kanata/p" + pageCounter );

            Repository repository = new Repository();
            for (Contact contact : contacts) {
                repository.store(contact);
            }
        }

        System.out.println( ">> cnh >> Finished generating list" );
    }

}
