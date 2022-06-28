package com.tds.directory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PhoneDirectorySpiderTest {

    private PhoneDirectorySpider spider;

    @BeforeEach
    public void setUp() {
        spider = new PhoneDirectorySpider();
    }

    @Test
    public void testFindProfileLinkOnContactPage() throws IOException {
        assertEquals( "https://411.ca/person/profile/60444680", spider.findProfileLinkOnPage( "https://411.ca/white-pages/on/kanata/aabed" ) );
    }

    @Test
    public void testParsingContactFromContactPage() throws IOException {
        Contact contact =  spider.findContactOnContactPage( "https://411.ca/white-pages/on/kanata/aabed" );
        assertEquals( "16135950717", contact.getTelephoneNumber() );
    }

    @Test
    public void testFindListOfContactPagesFromCityPage() throws IOException {
        Collection<String> contactPages = spider.findContactPagesFromCityPage( "https://411.ca/white-pages/on/kanata/p1" );
        assertEquals( 100, contactPages.size() );
    }

    @Test
    public void testParseAllContactsFromCityPage() throws IOException {
        Collection<Contact> contacts = spider.findContactsFromCityPage( "https://411.ca/white-pages/on/kanata/p1" );
        assertEquals( 74, contacts.size() );
        assertEquals( "16135910349", contacts.iterator().next().getTelephoneNumber() );
    }
}
