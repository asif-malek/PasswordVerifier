package com.verifier.password.verifiers;


import com.verifier.password.VerificationResponse;
import org.junit.jupiter.api.Test;

import static com.verifier.password.VerificationConstants.LENGTH_IS_LARGER_THAN;
import static com.verifier.password.VerificationConstants.PASSED;
import static org.junit.jupiter.api.Assertions.*;

public class MaxLengthVerifierTest {

    public static final String VERIFY = "verify";
    private int length;

    @Test
    public void shouldAllowSmallPassword(){

        MaxLengthVerifier maxLengthVerifier = new MaxLengthVerifier(8);

        assertTrue(maxLengthVerifier.verify("verify").isOk());
        assertEquals(PASSED, maxLengthVerifier.verify(VERIFY).getResponse());
    }

    @Test
    public void shouldNotAllowLargePassword(){

        length = 8;
        MaxLengthVerifier maxLengthVerifier = new MaxLengthVerifier(length);

        VerificationResponse verifyBigPasswordResponse = maxLengthVerifier.verify("verifyBigPassword");
        assertFalse(verifyBigPasswordResponse.isOk());
        assertEquals(LENGTH_IS_LARGER_THAN+length, verifyBigPasswordResponse.getResponse());
    }




}
