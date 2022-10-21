package com.verifier.password.verifiers;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LengthVerifierTest {

    @Test
    public void testVerifyLength(){

        LengthVerifier lengthVerifier = new LengthVerifier(8);

        assertTrue(lengthVerifier.verify("verify").isOk());
        assertEquals("Passed",lengthVerifier.verify("verify").getResponse());
    }
}
