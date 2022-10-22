package com.verifier.password.verifiers;

import com.verifier.password.VerificationResponse;

import static com.verifier.password.VerificationConstants.*;

public class HasLowerCaseVerifier extends RegexVerifier{

    public HasLowerCaseVerifier() {
        super(".*[a-z].*", STRING_DOES_NOT_CONTAIN_A_LOWER_CASE_CHAR);
    }


}
