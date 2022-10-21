package com.verifier.password.verifiers;


import com.verifier.password.VerificationResponse;
import com.verifier.password.verifiers.Verifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class VerifierTest {


    @Test
   public void testVerify() {

       Verifier mock = new Verifier() {

           public VerificationResponse verify(String test) {
               return new VerificationResponse(true, "passed");

            }
        };
        assertNotNull(mock.verify("dummy"));


    }

}