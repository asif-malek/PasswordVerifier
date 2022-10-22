package com.verifier.password.verifiers;

import com.verifier.password.VerificationResponse;
import org.junit.jupiter.api.Test;

import static com.verifier.password.VerificationConstants.*;
import static com.verifier.password.VerificationConstants.STRING_DOES_NOT_CONTAIN_A_NUMBER;
import static com.verifier.password.verifiers.TestConstants.VERIFY;
import static org.junit.jupiter.api.Assertions.*;

public class HasUpperCaseVerifierTest {

    private Verifier hasUpperCaseVerifier = new HasUpperCaseVerifier();

    @Test
    public void verifyIfPasswordContainsUpperCaseChar(){
         VerificationResponse verificationResponse = hasUpperCaseVerifier.verify("Test");
        assertTrue(verificationResponse.isOk());

        assertEquals(PASSED, verificationResponse.getResponse());
    }

    @Test
    public void verifyIfPasswordDoesntContainUpperCaseChar(){
        VerificationResponse verificationResponse = hasUpperCaseVerifier.verify(VERIFY);
        assertFalse(verificationResponse.isOk());

        assertEquals(STRING_DOES_NOT_CONTAIN_AN_UPPER_CASE_CHAR, verificationResponse.getResponse());
    }

    @Test
    public void testNullPassword(){


        VerificationResponse notNullResponse = hasUpperCaseVerifier.verify(null);
        assertFalse(notNullResponse.isOk());

        assertEquals(STRING_DOES_NOT_CONTAIN_AN_UPPER_CASE_CHAR, notNullResponse.getResponse());
    }

    @Test
    public void testEmptyPassword(){


        VerificationResponse notNullResponse = hasUpperCaseVerifier.verify("");
        assertFalse(notNullResponse.isOk());

        assertEquals(STRING_DOES_NOT_CONTAIN_AN_UPPER_CASE_CHAR, notNullResponse.getResponse());
    }
}
