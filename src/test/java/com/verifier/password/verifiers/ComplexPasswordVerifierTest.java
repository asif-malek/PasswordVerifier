package com.verifier.password.verifiers;

import com.verifier.password.verifiers.exception.InvalidNoOfConditionsException;
import com.verifier.password.verifiers.exception.NoVerifiersAddedException;
import com.verifier.password.verifiers.exception.VerifierException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.verifier.password.VerificationConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;


public class ComplexPasswordVerifierTest {


    MultiConditionVerifier complexPasswordVerifier= new ComplexPasswordVerifier();

    MultiConditionVerificationResponse multiConditionResponse;
    @BeforeEach
    public void setup(){

    }

    @AfterEach
    public void tearDown(){
        complexPasswordVerifier.getVerifiers().clear();
        multiConditionResponse = null;
    }

    @Test
    public void isInstanceOfMultiVerification(){

        assertInstanceOf(ComplexPasswordVerifier.class,complexPasswordVerifier);

    }

    @Test
    public void shouldContainMultipleVerification(){
        complexPasswordVerifier.addVerifiers(new MaxLengthVerifier(8));
        complexPasswordVerifier.addVerifiers(new NullVerifier());
        complexPasswordVerifier.addVerifiers(new HasUpperCaseVerifier());
        complexPasswordVerifier.addVerifiers(new HasLowerCaseVerifier());
        complexPasswordVerifier.addVerifiers(new HasNumberVerifier());
        assertNotEquals(Collections.EMPTY_LIST, complexPasswordVerifier.getVerifiers());
    }

    @Test
    public void shouldApplyMultipleVerifications() throws VerifierException {
        complexPasswordVerifier.addVerifiers(new MaxLengthVerifier(8));
        complexPasswordVerifier.addVerifiers(new NullVerifier());
        complexPasswordVerifier.addVerifiers(new HasUpperCaseVerifier());
        complexPasswordVerifier.addVerifiers(new HasLowerCaseVerifier());
        complexPasswordVerifier.addVerifiers(new HasNumberVerifier());

        List<String> expectedList = Arrays.asList(PASSED,PASSED,PASSED,PASSED,PASSED);
        multiConditionResponse = complexPasswordVerifier.verify("Verify1");

        assertTrue(multiConditionResponse.isOk());
        assertNotEquals(Collections.EMPTY_LIST, multiConditionResponse.getResponse());
        System.out.println(multiConditionResponse);
        assertThat(expectedList,is(multiConditionResponse.getResponse()));

    }

    @Test
    public void emptyStringMultiVerification() throws VerifierException {
        complexPasswordVerifier.addVerifiers(new MaxLengthVerifier(8));
        complexPasswordVerifier.addVerifiers(new NullVerifier());
        multiConditionResponse = complexPasswordVerifier.verify("");
        List<String> expectedList = Arrays.asList(PASSED,STRING_IS_NULL);
        assertFalse(multiConditionResponse.isOk());
        assertNotEquals(Collections.EMPTY_LIST, multiConditionResponse.getResponse());
        assertThat(expectedList,is(multiConditionResponse.getResponse()));

    }

    @Test
    public void whenNoVerifiersAddedThenThrowException() throws VerifierException {


        NoVerifiersAddedException thrown = assertThrows(
                NoVerifiersAddedException.class,
                () -> complexPasswordVerifier.verify(""), NO_VERIFIER_ADDED
        );

        assertTrue(thrown.getMessage().contains(NO_VERIFIER_ADDED));
    }

    @Test
    public void shouldExecutePrimeVerifierAlongWithOtherVerifier()throws VerifierException {
        complexPasswordVerifier.addVerifiers(new HasNumberVerifier());
        multiConditionResponse=  complexPasswordVerifier.verify("Password1",new MaxLengthVerifier(9));
        assertTrue(multiConditionResponse.isOk());
        List<String> expectedList = Arrays.asList(PASSED,PASSED);
        assertThat(expectedList,is(multiConditionResponse.getResponse()));
    }

    @Test
    public void shouldEFailIfPrimeVerifierNotVerified()throws VerifierException {
        complexPasswordVerifier.addVerifiers(new HasNumberVerifier());

        multiConditionResponse =  complexPasswordVerifier.verify("Password1",new MaxLengthVerifier(8));
        assertFalse(multiConditionResponse.isOk());
        List<String> expectedList = Arrays.asList(PASSED,LENGTH_IS_LARGER_THAN);
        assertNotEquals(Collections.EMPTY_LIST, multiConditionResponse.getResponse());

    }

    @Test
    public void shouldEPassIfOnlyPrimeVerifierProvided()throws VerifierException {

        multiConditionResponse =  complexPasswordVerifier.verify("Password1",new MaxLengthVerifier(9));
        assertTrue(multiConditionResponse.isOk());
        List<String> expectedList = Arrays.asList(PASSED);
        assertNotEquals(Collections.EMPTY_LIST, multiConditionResponse.getResponse());

    }

    @Test
    public void shouldPassIfAtleastSpecificNoOfConditionsVerified() throws VerifierException {
        complexPasswordVerifier.addVerifiers(new MaxLengthVerifier(2));
        complexPasswordVerifier.addVerifiers(new NullVerifier());
        complexPasswordVerifier.addVerifiers(new HasUpperCaseVerifier());
        complexPasswordVerifier.addVerifiers(new HasLowerCaseVerifier());
        complexPasswordVerifier.addVerifiers(new HasNumberVerifier());


        List<String> expectedList = Arrays.asList(LENGTH_IS_LARGER_THAN+2,PASSED,PASSED,PASSED,STRING_DOES_NOT_CONTAIN_A_NUMBER);
        multiConditionResponse =  complexPasswordVerifier.verify("Password",2);
        assertTrue(multiConditionResponse.isOk());
        assertNotEquals(Collections.EMPTY_LIST, multiConditionResponse.getResponse());
        System.out.println(multiConditionResponse);
        assertThat(expectedList,is(multiConditionResponse.getResponse()));
    }


    @Test
    public void whenNoOfConditionsAreInvalidThenThrowException() throws VerifierException {


        InvalidNoOfConditionsException thrown = assertThrows(
                InvalidNoOfConditionsException.class,
                () -> complexPasswordVerifier.verify("",-1), INVALID_NO_OF_CONDITIONS
        );

        assertTrue(thrown.getMessage().contains(INVALID_NO_OF_CONDITIONS));
    }





}
