package com.tds.directory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PhoneNumberListGeneratorTest {

    @Test
    public void testGeneratorCanBeCreated() {
        assertNotNull( new PhoneNumberListGenerator() );
    }

}
