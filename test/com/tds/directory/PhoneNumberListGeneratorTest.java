package com.tds.directory;

import com.tds.repository.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneNumberListGeneratorTest {

    private RepositoryStub repository;
    private PhoneDirectorySpiderStub spider;
    private PhoneNumberListGenerator generator;

    @BeforeEach
    public void setUp() {
        repository = new RepositoryStub();
        spider = new PhoneDirectorySpiderStub();
        generator = new PhoneNumberListGenerator( spider, repository );
    }

    @Test
    public void testGeneratorStoresResultsInDatabase() throws IOException {
        generator.storeNewContactsFor( "kanata", 1 );
        assertTrue( repository.triedToStoreContact() );
    }

    @Test
    public void testGeneratorCrawlsAllSpecifiedPages() throws IOException {
        generator.storeNewContactsFor( "kanata", 3 );
        assertEquals( 3, spider.getCrawledPageCounter() );
    }

    @Test
    public void testGeneratorCrawlsPageForCity() throws IOException {
        generator.storeNewContactsFor( "kanata", 3 );
        assertTrue( spider.getLastCrawledPage().contains( "kanata" ) );
    }

    @Test
    public void testGeneratorCrawlsMultipleCities() throws IOException {
        Collection<CrawlData> citiesToCrawl = new ArrayList<>();
        citiesToCrawl.add( new CrawlData( "aCity", 5 ) );
        citiesToCrawl.add( new CrawlData( "anotherCity", 7 ) );
        generator.storeNewContactsFor( citiesToCrawl );
        assertEquals( 12, spider.getCrawledPageCounter() );
    }

    private class PhoneDirectorySpiderStub extends PhoneDirectorySpider {

        private int crawledPageCounter;
        private String lastCrawledPage;

        public String getLastCrawledPage() {
            return this.lastCrawledPage;
        }

        public int getCrawledPageCounter() {
            return this.crawledPageCounter;
        }

        public Collection<Contact> findContactsFromCityPage( String page ) throws IOException {
            crawledPageCounter++;
            this.lastCrawledPage = page;

            Collection<Contact> foundContacts = new ArrayList<>();
            Address foundAddress = new Address();
            foundAddress.setStreetAddress( "123 Any St" );
            foundAddress.setLocality( "City" );
            foundAddress.setProvince( "ON" );
            foundAddress.setPostalCode( "N0N 0N0" );

            Contact foundContact = new Contact();
            foundContact.setTelephoneNumber( "5555555555" );
            foundContact.setGivenName( "Given" );
            foundContact.setFamilyName( "Family" );
            foundContact.setAddress( foundAddress );
            foundContacts.add( foundContact );
            return  foundContacts;
        }
    }

    private class RepositoryStub extends Repository {

        private Contact lastStoredContact = new Contact();
        private Address lastStoredAddress = new Address();

        public int store( Contact contact ) {
            lastStoredContact = contact;
            return 0;
        }

        public int store( Address address ) {
            lastStoredAddress = address;
            return 0;
        }

        public Contact getContactForPhoneNumber( String phoneNumber ) {
            return this.lastStoredContact;
        }

        public Address getAddressForAddressId( int addressId ) {
            return this.lastStoredAddress;
        }

        public boolean triedToStoreContact() {
            return this.lastStoredContact != null;
        }

        public boolean triedToStoreContact( Contact contact ) {
            return this.lastStoredContact != null && this.lastStoredContact.matches( contact );
        }
    }
}
