package com.tds.directory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PhoneDirectorySpiderTest {

    private PhoneDirectorySpider spider;

    @BeforeEach
    public void setUp() {
        spider = new PhoneDirectorySpider();
    }
/*
    @Test
    public void testFindsMultipleProfileLinksOnContactPageWhenTheyArePresentOnCanadapages() throws IOException {
        Collection<String> links = spider.findProfileLinksOnPage( "https://www.canadapages.com/wp/name/on/ottawa/abbate/" );
        assertEquals( 2, links.size() );
        assertTrue( links.contains( "https://www.canadapages.com/wp/antonio-abbate-ottawa-on-6135231425/" ) );
        assertTrue( links.contains( "https://www.canadapages.com/wp/c-abbate-ottawa-on-6132349154/" ) );
    }
*/
    @Test
    public void testFindsMultipleProfileLinksOnContactPageWhenTheyArePresentOn411CA() throws IOException {
        Collection<String> links = spider.findProfileLinksOnPage( "https://411.ca/white-pages/on/tweed/bentley" );
        assertEquals( 2, links.size() );
        assertTrue( links.contains( "https://411.ca/person/profile/58587044" ) );
        assertTrue( links.contains( "https://411.ca/person/profile/59812380" ) );
    }

    @Test
    public void testParsingMultipleContactsFromContactPage() throws BrokenPageException {
        Collection<Contact> contacts = spider.findContactsOnContactPage( "https://411.ca/white-pages/on/tweed/bentley" );
        assertEquals( 2, contacts.size() );
    }

    @Test
    public void testFindListOfContactPagesFromCityPage() throws IOException {
        Collection<String> contactPages = spider.findContactPagesFromCityPage( "https://411.ca/white-pages/on/kanata/p1" );
        assertEquals( 100, contactPages.size() );
    }

    @Test
    public void testParseAllContactsFromCityPage() throws IOException {
        Collection<Contact> contacts = spider.findContactsFromCityPage( "https://411.ca/white-pages/on/kanata/p1" );
        assertEquals( 101, contacts.size() );
        assertEquals( "16135910349", contacts.iterator().next().getTelephoneNumber() );
    }
}
