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

        // >> cnh >> TODO: Consider short circuiting if contact does not have an address ...
        int contactAddressId = 0;
        if( contact.getAddress() != null ) {
            contactAddressId = store( contact.getAddress() );
        }

        String INSERT_SQL = "INSERT INTO Contacts( phone_number, given_name, family_name, address_id, found_on_page ) VALUES( ?, ?, ?, ?, ? )";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;
        try {
            connection = DriverManager.getConnection( DATABASE_CONNECTION_URL, DATABASE_USERNAME, DATABASE_PASSWORD );
            statement = connection.prepareStatement( INSERT_SQL, Statement.RETURN_GENERATED_KEYS );
            statement.setString( 1, contact.getTelephoneNumber() );
            statement.setString( 2, contact.getGivenName() );
            statement.setString( 3, contact.getFamilyName() );
            statement.setInt( 4, contactAddressId );
            statement.setString( 5, contact.getFoundOnPage() );
            statement.execute();

            results = statement.getGeneratedKeys();
            if( results.next() ) {
                newContactID = results.getInt( 1 );
                contact.setContactId( newContactID );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        } finally {
            if( results != null ) {
                try {
                    results.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if( statement != null ) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if( connection != null ) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return newContactID;
    }

    public Contact getContactForPhoneNumber( String phoneNumber ) {
        Contact contact = new Contact();
        int contactAddressId = 0;
        // Always return the contact that was most recently created
        // >> cnh >> TODO:  Consider if this is the appropriate behavior
        String SELECT_CONTACT_SQL = "SELECT phone_number, given_name, family_name, address_id, found_on_page FROM Contacts where phone_number=? ORDER BY created_at DESC";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            connection = DriverManager.getConnection( DATABASE_CONNECTION_URL, DATABASE_USERNAME, DATABASE_PASSWORD );
            statement = connection.prepareStatement( SELECT_CONTACT_SQL );
            statement.setString( 1, phoneNumber );
            results = statement.executeQuery();
            if( results.next() ) {
                contact.setTelephoneNumber( results.getString( "phone_number" ) );
                contact.setGivenName( results.getString( "given_name" ) );
                contact.setFamilyName( results.getString( "family_name" ) );
                contactAddressId = results.getInt( "address_id" );
                contact.setFoundOnPage( results.getString( "found_on_page" ) );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        } finally {
            if (results != null) {
                try {
                    results.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        contact.setAddress( getAddressForAddressId( contactAddressId ) );
        return contact;
    }

    public int store( Address address ) {
        int newAddressID = 0;

        String INSERT_SQL = "INSERT INTO Addresses( street_address, locality, province, postal_code, found_on_page ) VALUES( ?, ?, ?, ?, ? )";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            connection = DriverManager.getConnection( DATABASE_CONNECTION_URL, DATABASE_USERNAME, DATABASE_PASSWORD );
            statement = connection.prepareStatement( INSERT_SQL, Statement.RETURN_GENERATED_KEYS );
            statement.setString( 1, address.getStreetAddress() );
            statement.setString( 2, address.getLocality() );
            statement.setString( 3, address.getProvince() );
            statement.setString( 4, address.getPostalCode() );
            statement.setString( 5, address.getFoundOnPage() );
            statement.execute();

            results = statement.getGeneratedKeys();
            if( results.next() ) {
                newAddressID = results.getInt( 1 );
                address.setAddressId( newAddressID );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        } finally {
            if( results != null ) {
                try {
                    results.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if( statement != null ) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if( connection != null ) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return newAddressID;
    }

    public Address getAddressForAddressId( int addressId ) {
        Address address = new Address();
        String SELECT_ADDRESS_SQL = "SELECT street_address, locality, province, postal_code, found_on_page FROM Addresses where address_id=?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet results = null;

        try {
            connection = DriverManager.getConnection( DATABASE_CONNECTION_URL, DATABASE_USERNAME, DATABASE_PASSWORD );
            statement = connection.prepareStatement( SELECT_ADDRESS_SQL );
            statement.setInt( 1, addressId );
            results = statement.executeQuery();
            if( results.next() ) {
                address.setStreetAddress( results.getString( "street_address" ) );
                address.setLocality( results.getString( "locality" ) );
                address.setProvince( results.getString( "province" ) );
                address.setPostalCode( results.getString( "postal_code" ) );
                address.setFoundOnPage( results.getString( "found_on_page" ) );
                address.setAddressId( addressId );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        } finally {
            if( results != null ) {
                try {
                    results.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if( statement != null ) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if( connection != null ) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return address;
    }

}
