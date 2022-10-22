package com.verifier.password.verifiers.exception;

import static com.verifier.password.VerificationConstants.NO_VERIFIER_ADDED;

public class NoVerifiersAddedException extends VerifierException {


    public NoVerifiersAddedException() {
        super(NO_VERIFIER_ADDED);
    }
}