package com.verifier.password.verifiers;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.verifier.password.VerificationConstants.PASSED;
import static com.verifier.password.VerificationConstants.STRING_IS_NULL;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;


public class ComplexPasswordVerifierTest {

    MultiConditionVerifier complexPasswordVerifier = new ComplexPasswordVerifier();

    @Test
    public void isInstanceOfMultiVerification(){

        assertInstanceOf(ComplexPasswordVerifier.class,complexPasswordVerifier);

    }

    @Test
    public void shouldContainMultipleVerification(){
        complexPasswordVerifier.addVerifiers(new MaxLengthVerifier(8));
        complexPasswordVerifier.addVerifiers(new NullVerifier());
        assertNotEquals(Collections.EMPTY_LIST, complexPasswordVerifier.getVerifiers());
    }

    @Test
    public void shouldApplyMultipleVerifications(){
        complexPasswordVerifier.addVerifiers(new MaxLengthVerifier(8));
        complexPasswordVerifier.addVerifiers(new NullVerifier());

        List<String> expectedList = Arrays.asList(PASSED,PASSED);
        MultiConditionVerificationResponse multiConditionResponse = complexPasswordVerifier.verify("verify");

        assertTrue(multiConditionResponse.isOk());
        assertNotEquals(Collections.EMPTY_LIST, multiConditionResponse.getResponse());
        assertThat(expectedList,is(multiConditionResponse.getResponse()));

    }

    @Test
    public void emptyStringMultiVerification(){
        complexPasswordVerifier.addVerifiers(new MaxLengthVerifier(8));
        complexPasswordVerifier.addVerifiers(new NullVerifier());
        MultiConditionVerificationResponse emptyStringResponse = complexPasswordVerifier.verify("");
        List<String> expectedList = Arrays.asList(PASSED,STRING_IS_NULL);
        assertFalse(emptyStringResponse.isOk());
        assertNotEquals(Collections.EMPTY_LIST, emptyStringResponse.getResponse());
        assertThat(expectedList,is(emptyStringResponse.getResponse()));

    }
}
