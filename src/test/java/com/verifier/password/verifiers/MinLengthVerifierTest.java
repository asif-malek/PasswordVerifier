package com.verifier.password.verifiers;


import com.verifier.password.VerificationResponse;
import org.junit.jupiter.api.Test;

import static com.verifier.password.VerificationConstants.LENGTH_IS_SMALLER_THAN;
import static com.verifier.password.VerificationConstants.PASSED;
import static com.verifier.password.verifiers.TestConstants.VERIFY;
import static org.junit.jupiter.api.Assertions.*;

public class MinLengthVerifierTest {


    private int length;

    @Test
    public void shouldAllowLargePassword(){

        MinLengthVerifier minLengthVerifier = new MinLengthVerifier(8);

        VerificationResponse verificationResponse = minLengthVerifier.verify(VERIFY);
        assertTrue(verificationResponse.isOk());
        assertEquals(PASSED, verificationResponse.getResponse());
    }

    @Test
    public void shouldNotAllowSmallPassword(){

        length = 8;
        MinLengthVerifier minLengthVerifier = new MinLengthVerifier(length);

        VerificationResponse verifyBigPasswordResponse = minLengthVerifier.verify("verify");
        assertFalse(verifyBigPasswordResponse.isOk());
        assertEquals(LENGTH_IS_SMALLER_THAN +length, verifyBigPasswordResponse.getResponse());
    }




}
