package com.tds.directory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

}
