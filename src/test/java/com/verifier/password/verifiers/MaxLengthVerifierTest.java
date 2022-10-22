package com.verifier.password.verifiers;


import com.verifier.password.VerificationResponse;
import org.junit.jupiter.api.Test;

import static com.verifier.password.VerificationConstants.LENGTH_IS_LARGER_THAN;
import static com.verifier.password.VerificationConstants.PASSED;
import static com.verifier.password.verifiers.TestConstants.VERIFY;
import static org.junit.jupiter.api.Assertions.*;

public class MaxLengthVerifierTest {


    private int length;

    @Test
    public void shouldAllowSmallPassword(){

        MaxLengthVerifier maxLengthVerifier = new MaxLengthVerifier(8);

        VerificationResponse verificationResponse = maxLengthVerifier.verify(VERIFY);
        assertTrue(verificationResponse.isOk());
        assertEquals(PASSED, verificationResponse.getResponse());
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
