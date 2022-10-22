package com.verifier.password.verifiers;

import com.verifier.password.VerificationResponse;
import org.junit.jupiter.api.Test;

import static com.verifier.password.VerificationConstants.PASSED;
import static com.verifier.password.VerificationConstants.STRING_DOES_NOT_CONTAIN_A_NUMBER;
import static org.junit.jupiter.api.Assertions.*;

class HasNumberVerifierTest {

    public static final String stringWithNumber = "has1";
    private Verifier hasNumberVerifier= new HasNumberVerifier();

    @Test
    public void testPasswordWithANumber(){


        VerificationResponse notNullResponse = hasNumberVerifier.verify(stringWithNumber);
        assertTrue(notNullResponse.isOk());

        assertEquals(PASSED, notNullResponse.getResponse());
    }

    @Test
    public void testPasswordWithOutNumber(){


        VerificationResponse notNullResponse = hasNumberVerifier.verify(TestConstants.VERIFY);
        assertFalse(notNullResponse.isOk());

        assertEquals(STRING_DOES_NOT_CONTAIN_A_NUMBER, notNullResponse.getResponse());
    }

    @Test
    public void testNullPassword(){


        VerificationResponse notNullResponse = hasNumberVerifier.verify(null);
        assertFalse(notNullResponse.isOk());

        assertEquals(STRING_DOES_NOT_CONTAIN_A_NUMBER, notNullResponse.getResponse());
    }

    @Test
    public void testEmptyPassword(){


        VerificationResponse notNullResponse = hasNumberVerifier.verify("");
        assertFalse(notNullResponse.isOk());

        assertEquals(STRING_DOES_NOT_CONTAIN_A_NUMBER, notNullResponse.getResponse());
    }
}