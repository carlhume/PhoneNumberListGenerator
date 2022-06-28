package com.tds.repository;

import com.tds.directory.Contact;

import java.sql.*;

public class Repository {

    // >> cnh >> TODO:  Remove coupling to com.tds.directory package ...
    // >> cnh >> TODO:  Remove the database specifics from source code

    /**
     * The store method will fail to compile until the database variables are introduced.
     * @param contact
     */
    public void store( Contact contact ) {
        String INSERT_SQL = "INSERT INTO Contacts( phone_number, given_name, family_name ) VALUES( ?, ?, ? )";

        String url = "jdbc:mysql://localhost:3306/phone_directory";

        try {
            Connection connection = DriverManager.getConnection( url, username, password );
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
        return contact;
    }

}
