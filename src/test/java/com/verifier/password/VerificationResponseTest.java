package com.verifier.password;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VerificationResponseTest {

    VerificationResponse dummyResponse= new VerificationResponse(true,"Passed");

    @Test
    public void isOk() {
        assertTrue(dummyResponse.isOk());
    }

    @Test
    public void getResponse() {
        assertEquals("Passed",dummyResponse.getResponse());
    }
}