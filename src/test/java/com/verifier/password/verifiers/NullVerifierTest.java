package com.verifier.password.verifiers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NullVerifierTest {
    NullVerifier nullVerifier = new NullVerifier();

    @Test
    public void testNullPassword(){



        assertTrue(nullVerifier.verify("verify").isOk());

        assertEquals("Passed",nullVerifier.verify("verify").getResponse());
    }

    @Test
    public void isInstanceOfVerifier(){
        assertInstanceOf(Verifier.class,nullVerifier);

    }
}
