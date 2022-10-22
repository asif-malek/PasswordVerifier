package com.verifier.password.verifiers;

import com.verifier.password.VerificationConstants;
import com.verifier.password.VerificationResponse;
import org.junit.jupiter.api.Test;

import static com.verifier.password.verifiers.TestConstants.VERIFY;
import static org.junit.jupiter.api.Assertions.*;

public class NullVerifierTest implements VerificationConstants {


    private Verifier nullVerifier = new NullVerifier();

    @Test
    public void testNotNullPassword(){


        VerificationResponse notNullResponse = nullVerifier.verify(VERIFY);
        assertTrue(notNullResponse.isOk());

        assertEquals(PASSED, notNullResponse.getResponse());
    }

    @Test
    public void testNullPassword(){


        VerificationResponse nullResponse = nullVerifier.verify(null);

        assertFalse(nullResponse.isOk());

        assertEquals(STRING_IS_NULL, nullResponse.getResponse());
    }

    @Test
    public void testEmptyPassword(){


        VerificationResponse nullResponse = nullVerifier.verify("");

        assertFalse(nullResponse.isOk());

        assertEquals(STRING_IS_NULL, nullResponse.getResponse());
    }

    @Test
    public void isInstanceOfVerifier(){
        assertInstanceOf(Verifier.class,nullVerifier);

    }
}
