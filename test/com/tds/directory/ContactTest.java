package com.tds.directory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContactTest {

    @Test
    public void testContactsWithDifferentTelephoneNumbersDoNotMatch() {
        Contact contact = new Contact();
        contact.setTelephoneNumber( "11234567890" );

        Contact anotherContact = new Contact();
        anotherContact.setTelephoneNumber( "15555555555" );

        assertFalse( contact.matches( anotherContact ) );
    }

    @Test
    public void testContactsWithSameTelephoneNumbersMatch() {
        Contact contact = new Contact();
        contact.setTelephoneNumber( "11234567890" );

        Contact anotherContact = new Contact();
        anotherContact.setTelephoneNumber( "11234567890" );

        assertTrue( contact.matches( anotherContact ) );
    }

    @Test
    public void testContactsWithSameGivenNameMatch() {
        Contact contact = new Contact();
        contact.setGivenName( "John" );

        Contact anotherContact = new Contact();
        anotherContact.setGivenName( "John" );

        assertTrue( contact.matches( anotherContact ) );
    }

    @Test
    public void testContactsWithDifferentGivenNameDoNotMatch() {
        Contact contact = new Contact();
        contact.setGivenName( "John" );

        Contact anotherContact = new Contact();
        anotherContact.setGivenName( "Sandra" );

        assertFalse( contact.matches( anotherContact ) );
    }

    @Test
    public void testContactsWithSameFamilyNameMatch() {
        Contact contact = new Contact();
        contact.setFamilyName( "Smith" );

        Contact anotherContact = new Contact();
        anotherContact.setFamilyName( "Smith" );

        assertTrue( contact.matches( anotherContact ) );
    }

    @Test
    public void testContactsWithDifferentFamilyNameDoNotMatch() {
        Contact contact = new Contact();
        contact.setFamilyName( "Smith" );

        Contact anotherContact = new Contact();
        anotherContact.setFamilyName( "Jones" );

        assertFalse( contact.matches( anotherContact ) );
    }

    @Test
    public void testNameParsingWithNullLeavesNameTheSame() {
        Contact contact = new Contact();
        contact.setGivenName( "John" );
        contact.setFamilyName( "Smith" );
        contact.updateNameDetails( null );
        assertEquals( "John", contact.getGivenName() );
        assertEquals( "Smith", contact.getFamilyName() );
    }

    @Test
    public void testNameParsingWithGivenNameAndFamilyName() {
        Contact contact = new Contact();
        contact.updateNameDetails( "John Smith" );
        assertEquals( "John", contact.getGivenName() );
        assertEquals( "Smith", contact.getFamilyName() );
    }

    @Test
    public void testNameParsingWithMultipleGivenNames() {
        Contact contact = new Contact();
        contact.updateNameDetails( "D J Abbinett" );  // From https://www.canadapages.com/wp/d-j-abbinett-ottawa-on-6137252715/
        assertEquals( "D J", contact.getGivenName() );
        assertEquals( "Abbinett", contact.getFamilyName() );
    }

    @Test
    public void testNameParsingWithOnlyOneNameIsFamilyName() {
        Contact contact = new Contact();
        contact.updateNameDetails( "Lalande" );  // From https://www.canadapages.com/wp/lalande-alfred-on-6136792279/
        assertEquals( null, contact.getGivenName() );
        assertEquals( "Lalande", contact.getFamilyName() );
    }

}
