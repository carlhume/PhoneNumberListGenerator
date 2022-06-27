package com.tds.directory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PhoneNumberSpiderTest {

    @Test
    public void testSpiderCanBeCreated() {
        assertNotNull( new PhoneNumberSpider() );
    }

}
