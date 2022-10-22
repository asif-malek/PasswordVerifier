package com.verifier.password.verifiers.exception;

import static com.verifier.password.VerificationConstants.INVALID_NO_OF_CONDITIONS;

public class InvalidNoOfConditionsException extends VerifierException{
    public InvalidNoOfConditionsException() {
        super(INVALID_NO_OF_CONDITIONS);
    }
}
