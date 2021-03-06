package com.tds.repository;

import com.tds.directory.Address;
import com.tds.directory.Contact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RepositoryTest {

    private Repository repository;
    private Contact contact;
    private Address address;

    @BeforeEach
    public void setUp() {
        this.repository = new Repository();

        address = new Address();
        address.setStreetAddress( "123 Any St" );
        address.setLocality( "Town" );
        address.setProvince( "ON" );
        address.setPostalCode( "N0N 0N0" );
        address.setFoundOnPage( "https://411.ca/white-pages/on/kanata/amos" );

        contact = new Contact();
        contact.setTelephoneNumber( "15671237890" );
        contact.setFamilyName( "Smith" );
        contact.setAddress( address );
        contact.setFoundOnPage( "https://411.ca/white-pages/on/kanata/amos" );
    }

    @Test
    public void testRepositoryCanStoreAndFindContact() {
        assertTrue( repository.store( contact ) > 0);
        Contact foundContact = repository.getContactForPhoneNumber( "15671237890" );
        assertEquals( contact.getTelephoneNumber(), foundContact.getTelephoneNumber() );
        assertEquals( contact.getFamilyName(), foundContact.getFamilyName() );
        assertEquals( contact.getFoundOnPage(), foundContact.getFoundOnPage() );
        assertEquals( contact.getAddress().getAddressId(), foundContact.getAddress().getAddressId() );
        assertEquals( contact.getAddress().getStreetAddress(), foundContact.getAddress().getStreetAddress() );
        assertEquals( contact.getAddress().getFoundOnPage(), foundContact.getAddress().getFoundOnPage() );
    }

    @Test
    public void testStoredContactHasIDThatWasAutoGenerated() {
        int generatedId = repository.store( contact );
        assertEquals( generatedId, contact.getContactId() );
    }

    @Test
    public void testRepositoryCanStoreAddress() {
        assertTrue( repository.store( address ) > 0 );
    }

    @Test
    public void testStoredAddressHasIDThatWasAutoGenerated() {
        int generatedId = repository.store( address );
        assertEquals( generatedId, address.getAddressId() );
    }

}
