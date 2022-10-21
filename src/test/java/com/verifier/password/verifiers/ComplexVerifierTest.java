package com.verifier.password.verifiers;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class ComplexVerifierTest {

    MasterConditionVerifier complexPasswordVerifier = new ComplexPasswordVerifier();

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
}
