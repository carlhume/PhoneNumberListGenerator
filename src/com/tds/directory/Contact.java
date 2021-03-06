package com.tds.directory;

public class Contact {

    public void updateNameDetails( String name ) {
        if( name != null ) {
            // Assumption:  Given and family names are separated by spaces.
            // The last name in the list is the family name, anything previous are given Name(s)
            String[] names = name.split(" ");
            setFamilyName( names[ names.length - 1] );

            int familyNameIndex = name.indexOf( getFamilyName() ) - 1;
            if( familyNameIndex >= 1 ) {
                setGivenName( name.substring( 0, familyNameIndex ) );
            }
        }
    }

    public boolean matches( Contact anotherContact ) {
        return telephoneNumberMatches( anotherContact ) && givenNameMatches( anotherContact ) && familyNameMatches( anotherContact );
    }

    private boolean telephoneNumberMatches( Contact anotherContact ) {
        return ( getTelephoneNumber() == null && anotherContact.getTelephoneNumber() == null )
                || ( getTelephoneNumber() != null && getTelephoneNumber().equals( anotherContact.getTelephoneNumber() ) );
    }

    private boolean givenNameMatches( Contact anotherContact ) {
        return ( getGivenName() == null && anotherContact.getGivenName() == null )
                || ( getGivenName() != null && getGivenName().equals( anotherContact.getGivenName() ) );
    }

    private boolean familyNameMatches( Contact anotherContact ) {
        return ( getFamilyName() == null && anotherContact.getFamilyName() == null )
                || ( getFamilyName() != null && getFamilyName().equals( anotherContact.getFamilyName() ) );
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    private String telephoneNumber;
    private String givenName;
    private String familyName;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    private Address address;

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    private int contactId;

    public String getFoundOnPage() {
        return foundOnPage;
    }

    public void setFoundOnPage(String foundOnPage) {
        this.foundOnPage = foundOnPage;
    }

    private String foundOnPage;
}
