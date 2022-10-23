package com.verifier.password.verifiers;

import com.verifier.password.verifiers.exception.InvalidNoOfConditionsException;
import com.verifier.password.verifiers.exception.NoVerifiersAddedException;
import com.verifier.password.verifiers.exception.VerifierException;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
        complexPasswordVerifier.addVerifier(new MaxLengthVerifier(8));
        complexPasswordVerifier.addVerifier(new NullVerifier());
        complexPasswordVerifier.addVerifier(new HasUpperCaseVerifier());
        complexPasswordVerifier.addVerifier(new HasLowerCaseVerifier());
        complexPasswordVerifier.addVerifier(new HasNumberVerifier());
        assertNotEquals(Collections.EMPTY_LIST, complexPasswordVerifier.getVerifiers());
    }

    @Test
    public void shouldApplyMultipleVerifications() throws VerifierException {
        complexPasswordVerifier.addVerifier(new MaxLengthVerifier(8));
        complexPasswordVerifier.addVerifier(new NullVerifier());
        complexPasswordVerifier.addVerifier(new HasUpperCaseVerifier());
        complexPasswordVerifier.addVerifier(new HasLowerCaseVerifier());
        complexPasswordVerifier.addVerifier(new HasNumberVerifier());

        List<String> expectedList = Arrays.asList(PASSED,PASSED,PASSED,PASSED,PASSED);
        multiConditionResponse = complexPasswordVerifier.verify("Verify1");

        assertTrue(multiConditionResponse.isOk());
        assertNotEquals(Collections.EMPTY_LIST, multiConditionResponse.getResponse());
        System.out.println(multiConditionResponse);
        assertThat(expectedList,is(multiConditionResponse.getResponse()));

    }

    @Test
    public void emptyStringMultiVerification() throws VerifierException {
        complexPasswordVerifier.addVerifier(new MaxLengthVerifier(8));
        complexPasswordVerifier.addVerifier(new NullVerifier());
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
        complexPasswordVerifier.addVerifier(new HasNumberVerifier());
        multiConditionResponse=  complexPasswordVerifier.verify("Password1",new MaxLengthVerifier(9));
        assertTrue(multiConditionResponse.isOk());
        List<String> expectedList = Arrays.asList(PASSED,PASSED);
        assertThat(expectedList,is(multiConditionResponse.getResponse()));
    }

    @Test
    public void shouldEFailIfPrimeVerifierNotVerified()throws VerifierException {
        complexPasswordVerifier.addVerifier(new HasNumberVerifier());

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
        complexPasswordVerifier.addVerifier(new MaxLengthVerifier(2));
        complexPasswordVerifier.addVerifier(new NullVerifier());
        complexPasswordVerifier.addVerifier(new HasUpperCaseVerifier());
        complexPasswordVerifier.addVerifier(new HasLowerCaseVerifier());
        complexPasswordVerifier.addVerifier(new HasNumberVerifier());


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


    @Test
    public void shouldExecuteInParallel() throws VerifierException, ExecutionException, InterruptedException {
       // complexPasswordVerifier.addVerifier(new MaxLengthVerifier(8));
        complexPasswordVerifier.addVerifier(new NullVerifier());
        complexPasswordVerifier.addVerifier(new HasUpperCaseVerifier());
        complexPasswordVerifier.addVerifier(new HasLowerCaseVerifier());
        complexPasswordVerifier.addVerifier(new HasNumberVerifier());
        List<String> expectedList = Arrays.asList(PASSED,PASSED,PASSED,PASSED);
        multiConditionResponse = complexPasswordVerifier.verifyInParallel("Verify1",0,null);
        assertTrue(multiConditionResponse.isOk());
        assertNotEquals(Collections.EMPTY_LIST, multiConditionResponse.getResponse());
        System.out.println(multiConditionResponse);
        assertThat(expectedList,is(multiConditionResponse.getResponse()));
    }

    @Test
    public void shouldAllowMinCondtions() throws VerifierException, ExecutionException, InterruptedException {
        complexPasswordVerifier.addVerifier(new NullVerifier());
        complexPasswordVerifier.addVerifier(new HasUpperCaseVerifier());
        complexPasswordVerifier.addVerifier(new HasLowerCaseVerifier());
        complexPasswordVerifier.addVerifier(new HasNumberVerifier());
        List<String> expectedList = Arrays.asList(PASSED,PASSED);
        multiConditionResponse = complexPasswordVerifier.verifyInParallel("Verify1",2,null);
        assertTrue(multiConditionResponse.isOk());
        assertNotEquals(Collections.EMPTY_LIST, multiConditionResponse.getResponse());
        System.out.println(multiConditionResponse);
        assertThat(expectedList,is(multiConditionResponse.getResponse()));
    }


    @Test
    public void shouldFailWithAllowMinCondtions() throws VerifierException, ExecutionException, InterruptedException {
        // complexPasswordVerifier.addVerifier(new MaxLengthVerifier(8));
        complexPasswordVerifier.addVerifier(new NullVerifier());
        complexPasswordVerifier.addVerifier(new HasLowerCaseVerifier());
        complexPasswordVerifier.addVerifier(new HasNumberVerifier());
        List<String> expectedList = Arrays.asList(PASSED,PASSED,STRING_DOES_NOT_CONTAIN_A_LOWER_CASE_CHAR);
        multiConditionResponse = complexPasswordVerifier.verifyInParallel("1",3,null);
        assertFalse(multiConditionResponse.isOk());
        assertNotEquals(Collections.EMPTY_LIST, multiConditionResponse.getResponse());
        System.out.println(multiConditionResponse);
        assertTrue(CollectionUtils.isEqualCollection(multiConditionResponse.getResponse(),expectedList));
    }

    @Test
    public void shouldAllowPrimeVerifier() throws VerifierException, ExecutionException, InterruptedException {

        List<String> expectedList = Arrays.asList(PASSED);
        multiConditionResponse = complexPasswordVerifier.verifyInParallel("Verify1",0,new MaxLengthVerifier(8));
        assertTrue(multiConditionResponse.isOk());
        assertNotEquals(Collections.EMPTY_LIST, multiConditionResponse.getResponse());
        System.out.println(multiConditionResponse);
        assertTrue(CollectionUtils.isEqualCollection(multiConditionResponse.getResponse(),expectedList));
    }

    @Test
    public void shouldExecutePrimeVerifierWithNoOfCondtions() throws VerifierException, ExecutionException, InterruptedException {

        complexPasswordVerifier.addVerifier(new HasLowerCaseVerifier());
        complexPasswordVerifier.addVerifier(new HasNumberVerifier());
        List<String> expectedList = Arrays.asList(PASSED,PASSED);
        multiConditionResponse = complexPasswordVerifier.verifyInParallel("v",2,new MaxLengthVerifier(8));
        assertTrue(multiConditionResponse.isOk());
        assertNotEquals(Collections.EMPTY_LIST, multiConditionResponse.getResponse());
        System.out.println(multiConditionResponse);
        assertTrue(CollectionUtils.isEqualCollection(multiConditionResponse.getResponse(),expectedList));
    }





}
