package com.verifier.password.verifiers;

import com.verifier.password.verifiers.exception.VerifierException;

import java.util.List;

public interface MultiConditionVerifier{

    void addVerifier(Verifier verifier);

    List<Verifier> getVerifiers();

    MultiConditionVerificationResponse verify(String password) throws  VerifierException;

    MultiConditionVerificationResponse verify(String password, Verifier primeVerifier) throws VerifierException;

    MultiConditionVerificationResponse verify(String password, int noOfConditions) throws VerifierException;

    MultiConditionVerificationResponse verifyInParallel(String password, int noOfConditions, Verifier primeVerifier) throws VerifierException;
}
