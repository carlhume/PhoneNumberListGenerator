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

    @Test
    public void testSpiderCanParseContactFromProfilePage() throws IOException {
        Contact contact = spider.parseContactFromProfilePage( PROFILE_PAGE_URL );
        assertEquals( "16135950717", contact.getTelephoneNumber() );
        assertEquals( "Aabed", contact.getFamilyName() );
        assertEquals( "Bassam", contact.getGivenName() );
        assertEquals( "4 Milne Cres,", contact.getAddress().getStreetAddress() );
        assertEquals( "Kanata,", contact.getAddress().getLocality() );
        assertEquals( "ON", contact.getAddress().getProvince() );
        assertEquals( "K2K 1H6", contact.getAddress().getPostalCode() );
    }

    @Test
    public void testSpiderCanParseAddressFromProfilePage() throws IOException {
        Address address = spider.parseAddressFromProfilePage( PROFILE_PAGE_URL );
        assertEquals( "4 Milne Cres,", address.getStreetAddress() );
        assertEquals( "Kanata,", address.getLocality() );
        assertEquals( "ON", address.getProvince() );
        assertEquals( "K2K 1H6", address.getPostalCode() );
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
