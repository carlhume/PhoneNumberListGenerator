package com.tds.repository;

import com.tds.directory.Contact;

import java.sql.*;

public class Repository {

    // >> cnh >> TODO:  Remove the database specifics from source code
/**
 * This class will fail to compile until the database variables are introduced.
 */
    private static final String DATABASE_CONNECTION_URL = "jdbc:mysql://localhost:3306/phone_directory";

    // >> cnh >> TODO:  Remove coupling to com.tds.directory package ...
     public void store( Contact contact ) {
        String INSERT_SQL = "INSERT INTO Contacts( phone_number, given_name, family_name ) VALUES( ?, ?, ? )";

        try {
            Connection connection = DriverManager.getConnection(DATABASE_CONNECTION_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement insertStatement = connection.prepareStatement( INSERT_SQL, Statement.RETURN_GENERATED_KEYS );
            insertStatement.setString( 1, contact.getTelephoneNumber() );
            insertStatement.setString( 2, contact.getGivenName() );
            insertStatement.setString( 3, contact.getFamilyName() );
            insertStatement.execute();
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

    public Contact getContactForPhoneNumber( String phoneNumber ) {
        Contact contact = new Contact();

        String SELECT_CONTACT_SQL = "SELECT phone_number, given_name, family_name FROM Contacts where phone_number=?";

        try {
            Connection connection = DriverManager.getConnection(DATABASE_CONNECTION_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectStatement = connection.prepareStatement( SELECT_CONTACT_SQL );
            selectStatement.setString( 1, phoneNumber );
            ResultSet results = selectStatement.executeQuery();
            if( results.next() ) {
                contact.setTelephoneNumber( results.getString( "phone_number" ) );
                contact.setGivenName( results.getString( "given_name" ) );
                contact.setFamilyName( results.getString( "family_name" ) );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        }

        return contact;
    }

}
