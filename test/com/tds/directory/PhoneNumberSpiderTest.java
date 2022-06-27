package com.tds.directory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneNumberSpiderTest {

    PhoneNumberSpider spider;

    @BeforeEach
    public void setUp() {
        spider = new PhoneNumberSpider();
    }

    @Test
    public void testSpiderCanBeCreated() {
        assertNotNull( new PhoneNumberSpider() );
    }
/*
    @Test
    public void testSpiderCanConnectToSite() throws IOException {
        PhoneNumberSpider spider = new PhoneNumberSpider();
        assertTrue( spider.connectTo( "https://411.ca/white-pages/on/kanata" ) );
    }
*/
    @Test
    public void testSpiderCanFindPhoneNumberOnContactPage() throws IOException {
        assertEquals( "16135950717", spider.findPhoneNumberOnProfilePage( "https://411.ca/person/profile/60444680" ) );
    }

    @Test
    public void testSpiderCanFindGivenNameOnContactPage() throws IOException {
        assertEquals( "Bassam", spider.findGivenNameOnProfilePage( "https://411.ca/person/profile/60444680" ) );
    }

    @Test
    public void testSpiderCanFindFamilyNameOnContactPage() throws IOException {
        assertEquals( "Aabed", spider.findFamilyNameOnProfilePage( "https://411.ca/person/profile/60444680" ) );
    }

}
