package com.verifier.password.verifiers;

import com.verifier.password.VerificationResponse;

import static com.verifier.password.VerificationConstants.*;

public class HasLowerCaseVerifier implements Verifier {
    @Override
    public VerificationResponse verify(String password) {

        return password!=null&&password.matches(".*[a-z].*")?new VerificationResponse(true,PASSED):new VerificationResponse(false, STRING_DOES_NOT_CONTAIN_A_LOWER_CASE_CHAR);
    }
}
