package com.tds.repository;

import com.tds.directory.Contact;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RepositoryTest {

    @Test
    public void testRepositoryCanBeCreated() {
        assertNotNull( new Repository() );
    }

    @Test
    public void testRepositoryCanStoreAndFindContact() {
        Repository repository = new Repository();
        Contact contact = new Contact();
        contact.setTelephoneNumber( "15671237890" );
        contact.setFamilyName( "Smith" );
        repository.store( contact );

        Contact foundContact = repository.getContactForPhoneNumber( "15671237890" );
        assertEquals( contact.getTelephoneNumber(), foundContact.getTelephoneNumber() );
        assertEquals( contact.getFamilyName(), foundContact.getFamilyName() );

    }

}
