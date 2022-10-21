package com.verifier.password.verifiers;

import com.verifier.password.VerificationResponse;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ComplexVerifierTest {

    MultiConditionVerifier complexPasswordVerifier = new ComplexPasswordVerifier();

    @Test
    public void isInstanceOfMultiVerification(){

        assertInstanceOf(ComplexPasswordVerifier.class,complexPasswordVerifier);

    }

    @Test
    public void shouldContainMultipleVerification(){
        complexPasswordVerifier.addVerifiers(new LengthVerifier(8));
        complexPasswordVerifier.addVerifiers(new NullVerifier());
        assertNotEquals(Collections.EMPTY_LIST, complexPasswordVerifier.getVerifiers());
    }

    @Test
    public void shouldApplyMultipleVerifications(){
        complexPasswordVerifier.addVerifiers(new LengthVerifier(8));
        complexPasswordVerifier.addVerifiers(new NullVerifier());
        MultiConditionVerificationResponse response = complexPasswordVerifier.verify("test");

        assertTrue(response.isOk());
        assertNotEquals(Collections.EMPTY_LIST, response.getResponse());
    }
}
