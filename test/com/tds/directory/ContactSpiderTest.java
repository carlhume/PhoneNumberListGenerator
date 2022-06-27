package com.tds.directory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ContactSpiderTest {

    public static final String PROFILE_PAGE_URL = "https://411.ca/person/profile/60444680";
    ContactSpider spider;

    @BeforeEach
    public void setUp() {
        spider = new ContactSpider();
    }
/*
    @Test
    public void testSpiderCanConnectToSite() throws IOException {
        PhoneNumberSpider spider = new PhoneNumberSpider();
        assertTrue( spider.connectTo( "https://411.ca/white-pages/on/kanata" ) );
    }
*/
    @Test
    public void testSpiderCanParseContactFromProfilePage() throws IOException {
        Contact contact = spider.parseContactFromProfilePage( PROFILE_PAGE_URL );
        assertEquals( "16135950717", contact.getTelephoneNumber() );
        assertEquals( "Aabed", contact.getFamilyName() );
        assertEquals( "Bassam", contact.getGivenName() );
    }

    @Test
    public void testSpiderCanFindPhoneNumberOnProfilePage() throws IOException {
        assertEquals( "16135950717", spider.findPhoneNumberOnProfilePage( PROFILE_PAGE_URL)  );
    }

    @Test
    public void testSpiderCanFindGivenNameOnProfilePage() throws IOException {
        assertEquals( "Bassam", spider.findGivenNameOnProfilePage( PROFILE_PAGE_URL ) );
    }

    @Test
    public void testSpiderCanFindFamilyNameOnProfilePage() throws IOException {
        assertEquals( "Aabed", spider.findFamilyNameOnProfilePage( PROFILE_PAGE_URL ) );
    }

}
