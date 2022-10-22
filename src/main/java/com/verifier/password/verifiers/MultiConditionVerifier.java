package com.verifier.password.verifiers;

import com.verifier.password.VerificationResponse;
import com.verifier.password.verifiers.exception.NoVerifiersAddedException;
import com.verifier.password.verifiers.exception.VerifierException;

import java.util.List;

public interface MultiConditionVerifier{

    void addVerifiers(Verifier verifier);

    List<Verifier> getVerifiers();

    MultiConditionVerificationResponse verify(String password) throws  VerifierException;

    MultiConditionVerificationResponse verify(String password, Verifier primeVerifier) throws VerifierException;
}
