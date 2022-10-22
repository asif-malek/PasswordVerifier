package com.verifier.password.verifiers;

import com.verifier.password.VerificationResponse;
import org.junit.jupiter.api.Test;

import static com.verifier.password.VerificationConstants.PASSED;
import static com.verifier.password.VerificationConstants.STRING_DOES_NOT_CONTAIN_A_LOWER_CASE_CHAR;
import static com.verifier.password.verifiers.TestConstants.VERIFY;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HasLowerCaseVerifierTest {
    private Verifier hasLowerCaseVerifier = new HasLowerCaseVerifier();

    @Test
    public void verifyIfPasswordContainsLowerCaseChar(){
        VerificationResponse verificationResponse = hasLowerCaseVerifier.verify("Test");
        assertTrue(verificationResponse.isOk());

        assertEquals(PASSED, verificationResponse.getResponse());
    }

    @Test
    public void verifyIfPasswordDoesntContainLowerCaseChar(){
        VerificationResponse verificationResponse = hasLowerCaseVerifier.verify("VERIFY");
        assertFalse(verificationResponse.isOk());

        assertEquals(STRING_DOES_NOT_CONTAIN_A_LOWER_CASE_CHAR, verificationResponse.getResponse());
    }

    @Test
    public void testNullPassword(){


        VerificationResponse notNullResponse = hasLowerCaseVerifier.verify(null);
        assertFalse(notNullResponse.isOk());

        assertEquals(STRING_DOES_NOT_CONTAIN_A_LOWER_CASE_CHAR, notNullResponse.getResponse());
    }

    @Test
    public void testEmptyPassword(){


        VerificationResponse notNullResponse = hasLowerCaseVerifier.verify("");
        assertFalse(notNullResponse.isOk());

        assertEquals(STRING_DOES_NOT_CONTAIN_A_LOWER_CASE_CHAR, notNullResponse.getResponse());
    }
}
