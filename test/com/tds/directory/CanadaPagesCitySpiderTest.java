package com.tds.directory;

import com.tds.repository.Repository;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CanadaPagesCitySpiderTest {

    @Test
    public void testCanFindHowManyPagesOfContactsAreAvailable() throws IOException {
        CanadaPagesCitySpider canadaPagesCitySpider = new CanadaPagesCitySpider( new CityPageSpiderStub(), new RepositoryStub() );
        assertEquals(26, canadaPagesCitySpider.findPageCountFor("https://www.canadapages.com/wp/on/ottawa/a-z-names/A/"));
        assertEquals(2, canadaPagesCitySpider.findPageCountFor("https://www.canadapages.com/wp/on/ottawa/a-z-names/Q/"));
    }

    @Test
    public void crawlsAllPagesForALetterWhereContactsAreAvailable() throws IOException {
        CityPageSpiderStub cityPageSpiderStub = new CityPageSpiderStub();
        CanadaPagesCitySpider canadaPagesCitySpider = new CanadaPagesCitySpider(cityPageSpiderStub, new RepositoryStub() );
        canadaPagesCitySpider.findContactsFor("https://www.canadapages.com/wp/on/ottawa/a-z-names/A/");
        assertEquals(26, cityPageSpiderStub.crawledPageCount);
    }

    @Test
    public void testVisitsAllLettersOfTheAlphabetForTheCity() throws IOException {
        CityPageSpiderStub cityPageSpiderStub = new CityPageSpiderStub();
        CanadaPagesCitySpider canadaPagesCitySpider = new CanadaPagesCitySpider(cityPageSpiderStub, new RepositoryStub() );
        canadaPagesCitySpider.findContactsForCityUrlByLetter("https://www.canadapages.com/wp/on/crysler/a-z-names/");
        assertEquals(26, cityPageSpiderStub.crawledPageCount);
    }

    private class CityPageSpiderStub extends CityPageSpider {

        public int crawledPageCount;

        public Collection<Contact> findContactsFromCityPage(String page) throws IOException {
            crawledPageCount++;
            Collection<Contact> foundContacts = new ArrayList<>();
            Address foundAddress = new Address();
            foundAddress.setStreetAddress("123 Any St");
            foundAddress.setLocality("City");
            foundAddress.setProvince("ON");
            foundAddress.setPostalCode("N0N 0N0");

            Contact foundContact = new Contact();
            foundContact.setTelephoneNumber("5555555555");
            foundContact.setGivenName("Given");
            foundContact.setFamilyName("Family");
            foundContact.setAddress(foundAddress);
            foundContacts.add(foundContact);
            return foundContacts;
        }
    }

    private class RepositoryStub extends Repository {

    }
}