package com.verifier.password.verifiers;

import com.verifier.password.VerificationResponse;
import org.junit.jupiter.api.Test;

import static com.verifier.password.VerificationConstants.PASSED;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegexVerifierTest {

    private Verifier regexVerifier = new RegexVerifier(".+","fail") {
        @Override
        public VerificationResponse verify(String password) {
            return super.verify(password);
        }
    };

    @Test
    public void verifyResponse(){
        VerificationResponse verificationResponse = regexVerifier.verify("Test");
        assertTrue(verificationResponse.isOk());

        assertEquals(PASSED, verificationResponse.getResponse());
    }

    @Test
    public void verifyNegativeResponse(){
        VerificationResponse verificationResponse = regexVerifier.verify("");
        assertFalse(verificationResponse.isOk());

        assertEquals("fail", verificationResponse.getResponse());
    }



}
