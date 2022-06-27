package com.tds.directory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ContactSpider {

    public Contact parseContactFromProfilePage( String page ) throws IOException {
        Contact contact = new Contact();
        try {
            Document webpage = Jsoup.connect( page ).get();
            contact.setTelephoneNumber( findPhoneNumberOnProfilePage( webpage ) );
            contact.setFamilyName( findFamilyNameOnProfilePage( webpage ) );
            contact.setGivenName( findGivenNameOnProfilePage( webpage ) );
            contact.setAddress( parseAddressFromProfilePage( webpage ) );
        } catch( IOException e ) {
            System.out.println( ">> cnh >> Error connecting to page: " + page );
            throw e;
        } catch( IllegalArgumentException e ) {
            System.out.println( ">> cnh >> " + page + " is not a valid URL" );
        }
        return contact;
    }

    private Address parseAddressFromProfilePage( Document webpage ) {
        Address address = new Address();
        address.setStreetAddress( findStreetAddressOnProfilePage( webpage ) );
        address.setLocality( findLocalityOnProfilePage( webpage ) );
        address.setProvince( findProvinceOnProfilePage( webpage ) );
        address.setPostalCode( findPostalCodeOnProfilePage( webpage ) );
        return address;
    }

    public Address parseAddressFromProfilePage( String page ) throws IOException {
        Document webpage = Jsoup.connect( page ).get();
        return parseAddressFromProfilePage( webpage );
    }

    /*
       <div class="container">
     <div class="contact-card card card-shadow">
      <div class="card-block">
       <div class="contact-info">
        <h1 class="h4" itemprop="name"><span itemprop="givenName">Bassam</span><!----><span itemprop="familyName">&nbsp;Aabed</span></h1>
        <div class="address"><span class="address" itemprop="address" itemscope itemtype="http://schema.org/PostalAddress"> <!----><span itemprop="streetAddress" class="ng-star-inserted">4 Milne Cres,</span> <!----> <wbr> <!----><span itemprop="addressLocality" class="ng-star-inserted">&nbsp;Kanata,</span> <!----><span itemprop="addressRegion" class="ng-star-inserted">&nbsp;ON</span> <wbr> <!----><span itemprop="postalCode" class="ng-star-inserted">&nbsp;&nbsp;&nbsp;K2K 1H6</span> </span>
        </div>
        <ul class="list-unstyled quick-contact"><!---->
        <li class="ng-star-inserted"><a class="btn btn-white btn-icon-text" href="tel:16135950717">
          <svg height="32" viewbox="0 0 24 24" width="32">
           <path d="M6.62 10.79c1.44 2.83 3.76 5.14 6.59 6.59l2.2-2.2c.27-.27.67-.36 1.02-.24 1.12.37 2.33.57 3.57.57.55 0 1 .45 1 1V20c0 .55-.45 1-1 1-9.39 0-17-7.61-17-17 0-.55.45-1 1-1h3.5c.55 0 1 .45 1 1 0 1.25.2 2.45.57 3.57.11.35.03.74-.25 1.02l-2.2 2.2z"></path>
          </svg> <span class="hidden-desktop" translate="call">Call</span> <span class="hidden-mobile" itemprop="telephone">613-595-0717</span> </a></li><!---->
        <li class="ng-star-inserted"><a class="btn btn-white btn-icon-text" rel="noopener noreferrer" target="_blank" href="https://www.google.com/maps/dir/Current+Location/45.330063,-75.90544">
          <svg height="32" viewbox="0 0 24 24" width="32">
           <path d="M21.71 11.29l-9-9c-.39-.39-1.02-.39-1.41 0l-9 9c-.39.39-.39 1.02 0 1.41l9 9c.39.39 1.02.39 1.41 0l9-9c.39-.38.39-1.01 0-1.41zM14 14.5V12h-4v3H8v-4c0-.55.45-1 1-1h5V7.5l3.5 3.5-3.5 3.5z"></path>
          </svg> <span translate="directions">Directions</span> </a></li>
       </ul>
       */

    private String findPostalCodeOnProfilePage( Document webpage ) {
        return findTextForSpanPropertyOnPage( "postalCode", webpage );
    }

    private String findProvinceOnProfilePage( Document webpage ) {
        return findTextForSpanPropertyOnPage( "addressRegion", webpage );
    }

    private String findLocalityOnProfilePage( Document webpage ) {
        return findTextForSpanPropertyOnPage( "addressLocality", webpage );
    }

    private String findStreetAddressOnProfilePage( Document webpage ) {
        return findTextForSpanPropertyOnPage( "streetAddress", webpage );
    }

    public String findPhoneNumberOnProfilePage( String page ) throws IOException {
        Document webpage = Jsoup.connect( page ).get();
        return findPhoneNumberOnProfilePage( webpage );
    }

    private String findPhoneNumberOnProfilePage( Document webpage ) {
        String phoneNumber = null;
        Elements linksOnPage = webpage.select( "a[href]" );
        for( Element link : linksOnPage ) {
            if( link.attr( "href" ).startsWith( "tel:" ) ) {
                phoneNumber = link.attr( "href" ).substring( 4 );
            }
        }

        return phoneNumber;

//        return findTextForSpanPropertyOnPage( "telephone", webpage );
    }

    public String findGivenNameOnProfilePage( String page ) throws IOException {
        return findTextForSpanPropertyOnPage( "givenName", page );
    }

    private String findGivenNameOnProfilePage( Document webpage ) {
        return findTextForSpanPropertyOnPage( "givenName", webpage );
    }

    public String findFamilyNameOnProfilePage( String page ) throws IOException {
        return findTextForSpanPropertyOnPage( "familyName", page );
    }

    private String findFamilyNameOnProfilePage( Document webpage ) {
        return findTextForSpanPropertyOnPage( "familyName", webpage );
    }

    private String findTextForSpanPropertyOnPage( String property, String page ) throws IOException {
        Document webpage = Jsoup.connect( page ).get();
        return findTextForSpanPropertyOnPage( property, webpage );
    }

    private String findTextForSpanPropertyOnPage( String propertyName, Document webpage ) {
        String text = null;
        Elements spansOnPage = webpage.select( "span" );
        for( Element span : spansOnPage ) {
            if( span.attr( "itemprop" ).equals( propertyName ) ) {
                text = span.text();
            }
        }
        return text;
    }

    /*
    public boolean connectTo( String url ) throws IOException {
        boolean connectedCleanly = false;
        Document webpage = Jsoup.connect( url ).get();

        //System.out.println( ">> cnh >> page: " + webpage );

        Elements lists = webpage.select( "ul" );
        System.out.println( ">> cnh >> Lists Found: " + lists.size() );
        for( Element list : lists ) {
            if( list.hasClass( "ng-star-inserted" ) ) {
                System.out.println( ">> cnh >> Found list of potential names" );
                Elements listItems = list.children();
                for( Element listItem : listItems ) {
                    //System.out.println( ">> cnh >> list Item: " + listItem );
                    Element link = listItem.child( 0 );
                    System.out.println( ">> cnh >> link: " + link.attr( "href" ) );
                    System.out.println( ">> cnh >> Absolute URL: " + url + "/" + link.text() );
                    System.out.println( ">> cnh >> name: " + link.text() );
                }
            }
        }
        connectedCleanly = webpage != null;
        return connectedCleanly;
    }
*/
}
