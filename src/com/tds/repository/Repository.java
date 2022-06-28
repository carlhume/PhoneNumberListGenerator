package com.tds.repository;

import com.tds.directory.Address;
import com.tds.directory.Contact;

import java.sql.*;

public class Repository {

    // >> cnh >> TODO:  Remove the database specifics from source code
/**
 * This class will fail to run until the database variables are introduced.
 */
    private static final String DATABASE_CONNECTION_URL = "jdbc:mysql://localhost:3306/phone_directory";
    private static final String DATABASE_USERNAME = "";
    private static final String DATABASE_PASSWORD = "";

    // >> cnh >> TODO:  Remove coupling to com.tds.directory package ...
     public int store( Contact contact ) {
        int newContactID = 0;

        // >> cnh >> TODO: Consider short circuting if contact does not have an address ...
        int contactAddressId = 0;
        if( contact.getAddress() != null ) {
            contactAddressId = store( contact.getAddress() );
        }

        String INSERT_SQL = "INSERT INTO Contacts( phone_number, given_name, family_name, address_id ) VALUES( ?, ?, ?, ? )";

        try {
            Connection connection = DriverManager.getConnection( DATABASE_CONNECTION_URL, DATABASE_USERNAME, DATABASE_PASSWORD );
            PreparedStatement insertStatement = connection.prepareStatement( INSERT_SQL, Statement.RETURN_GENERATED_KEYS );
            insertStatement.setString( 1, contact.getTelephoneNumber() );
            insertStatement.setString( 2, contact.getGivenName() );
            insertStatement.setString( 3, contact.getFamilyName() );
            insertStatement.setInt( 4, contactAddressId );
            insertStatement.execute();

            ResultSet keys = insertStatement.getGeneratedKeys();
            if( keys.next() ) {
                newContactID = keys.getInt( 1 );
                contact.setContactId( newContactID );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        }

        return newContactID;
    }

    public Contact getContactForPhoneNumber( String phoneNumber ) {
        Contact contact = new Contact();
        int contactAddressId = 0;
        String SELECT_CONTACT_SQL = "SELECT phone_number, given_name, family_name, address_id FROM Contacts where phone_number=?";

        try {
            Connection connection = DriverManager.getConnection( DATABASE_CONNECTION_URL, DATABASE_USERNAME, DATABASE_PASSWORD );
            PreparedStatement selectStatement = connection.prepareStatement( SELECT_CONTACT_SQL );
            selectStatement.setString( 1, phoneNumber );
            ResultSet results = selectStatement.executeQuery();
            if( results.next() ) {
                contact.setTelephoneNumber( results.getString( "phone_number" ) );
                contact.setGivenName( results.getString( "given_name" ) );
                contact.setFamilyName( results.getString( "family_name" ) );
                contactAddressId = results.getInt( "address_id" );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        }

        contact.setAddress( getAddressForAddressId( contactAddressId ) );
        return contact;
    }

    public int store( Address address ) {
        int newAddressID = 0;

        String INSERT_SQL = "INSERT INTO Addresses( street_address, locality, province, postal_code ) VALUES( ?, ?, ?, ? )";

        try {
            Connection connection = DriverManager.getConnection( DATABASE_CONNECTION_URL, DATABASE_USERNAME, DATABASE_PASSWORD );
            PreparedStatement insertStatement = connection.prepareStatement( INSERT_SQL, Statement.RETURN_GENERATED_KEYS );
            insertStatement.setString( 1, address.getStreetAddress() );
            insertStatement.setString( 2, address.getLocality() );
            insertStatement.setString( 3, address.getProvince() );
            insertStatement.setString( 4, address.getPostalCode() );
            insertStatement.execute();

            ResultSet keys = insertStatement.getGeneratedKeys();
            if( keys.next() ) {
                newAddressID = keys.getInt( 1 );
                address.setAddressId( newAddressID );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        }

        return newAddressID;
    }

    public Address getAddressForAddressId( int addressId ) {
        Address address = new Address();
        String SELECT_ADDRESS_SQL = "SELECT street_address, locality, province, postal_code FROM Addresses where address_id=?";

        try {
            Connection connection = DriverManager.getConnection( DATABASE_CONNECTION_URL, DATABASE_USERNAME, DATABASE_PASSWORD );
            PreparedStatement selectStatement = connection.prepareStatement( SELECT_ADDRESS_SQL );
            selectStatement.setInt( 1, addressId );
            ResultSet results = selectStatement.executeQuery();
            if( results.next() ) {
                address.setStreetAddress( results.getString( "street_address" ) );
                address.setLocality( results.getString( "locality" ) );
                address.setProvince( results.getString( "province" ) );
                address.setPostalCode( results.getString( "postal_code" ) );
                address.setAddressId( addressId );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        }

        return address;
    }

}
